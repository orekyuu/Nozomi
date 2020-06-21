package org.orekyuu.nozomi.infrastructure.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import org.orekyuu.nozomi.domain.docker.DockerService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class SimpleDockerService implements DockerService {

    final DockerClientConfig config;
    private final TaskExecutor executor;

    public SimpleDockerService(DockerClientConfig config, @Qualifier(DockerClientConfiguration.DOCKER_TASK_EXECUTOR) TaskExecutor executor) {
        this.config = config;
        this.executor = executor;
    }

    private DockerClient client() {
        DockerHttpClient httpClient = new ApacheDockerHttpClient.Builder()
                .dockerHost(config.getDockerHost())
                .sslConfig(config.getSSLConfig())
                .build();
        return DockerClientImpl.getInstance(config, httpClient);
    }

    @Override
    public void ping() {
        executor.execute(() -> client().pingCmd().exec());
    }

    @Override
    public Observable<String> runContainer(DockerTask task) {
        PublishSubject<String> subject = PublishSubject.create();
        executor.execute(() -> {
            try {
                client().pullImageCmd(task.image()).withTag(task.tag()).start().awaitCompletion();

                CreateContainerCmd containerCmd = client().createContainerCmd(task.image() + ":" + task.tag());
                List<String> environmentVariables = task.env().stream().flatMap(it -> Stream.of(it.name(), it.value())).collect(Collectors.toList());
                containerCmd.withEnv(environmentVariables);
                containerCmd.withCmd(task.command());
                CreateContainerResponse response = containerCmd.exec();

                client().startContainerCmd(response.getId()).exec();

                client().logContainerCmd(response.getId())
                        .withStdErr(true)
                        .withStdOut(true)
                        .withTailAll()
                        .withFollowStream(true)
                        .exec(new ResultCallback.Adapter<>() {
                            @Override
                            public void onNext(Frame object) {
                                super.onNext(object);
                                String t = new String(object.getPayload());
                                subject.onNext(t);
                            }

                            @Override
                            public void onComplete() {
                                super.onComplete();
                                subject.onComplete();
                            }
                        });
            } catch (Exception e) {
                subject.onError(e);
            }
        });

        return subject;
    }
}
