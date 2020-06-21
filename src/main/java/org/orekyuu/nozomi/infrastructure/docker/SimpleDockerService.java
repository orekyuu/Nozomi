//package org.orekyuu.nozomi.infrastructure.docker;
//
//import com.github.dockerjava.api.DockerClient;
//import com.github.dockerjava.api.async.ResultCallback;
//import com.github.dockerjava.api.command.CreateContainerResponse;
//import com.github.dockerjava.api.model.Frame;
//import com.github.dockerjava.core.DockerClientConfig;
//import com.github.dockerjava.core.DockerClientImpl;
//import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
//import com.github.dockerjava.transport.DockerHttpClient;
//import org.orekyuu.nozomi.domain.deploy.docker.DockerService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.core.io.ClassPathResource;
//
//import java.util.concurrent.CompletableFuture;
//import java.util.concurrent.CountDownLatch;
//
//public class SimpleDockerService implements DockerService {
//
//    private final DockerClientConfig dockerClientConfig;
//    static final Logger logger = LoggerFactory.getLogger(DockerService.class);
//
//    public SimpleDockerService(DockerClientConfig dockerClientConfig) {
//        this.dockerClientConfig = dockerClientConfig;
//    }
//
//    public DockerClient client() {
//        DockerHttpClient httpClient = new ApacheDockerHttpClient.Builder()
//                .dockerHost(dockerClientConfig.getDockerHost())
//                .sslConfig(dockerClientConfig.getSSLConfig())
//                .build();
//        return DockerClientImpl.getInstance(dockerClientConfig, httpClient);
//    }
//
//    public CompletableFuture<Void> pullDeploymentImage() {
//        CountDownLatch latch = new CountDownLatch(1);
//        try {
//            client().pullImageCmd("debian").withTag("stretch-slim").start().awaitCompletion();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        logger.info("Start create container.");
//        CreateContainerResponse response = client().createContainerCmd("debian:stretch-slim")
//                .withCmd("echo", "hello world")
//                .exec();
//        logger.debug("Complete create container.");
//        client().startContainerCmd(response.getId()).exec();
//
//        return CompletableFuture.runAsync(() -> {
//            client().logContainerCmd(response.getId())
//                    .withStdErr(true)
//                    .withStdOut(true)
//                    .withTailAll()
//                    .exec(new ResultCallback.Adapter<>() {
//                        @Override
//                        public void onNext(Frame object) {
//                            String s = new String(object.getPayload());
//                            System.out.println(s);
//                        }
//
//                        @Override
//                        public void onComplete() {
//                            latch.countDown();
//                        }
//                    });
//            try {
//                latch.await();
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        });
//    }
//
//    public void findContainers() {
//        ClassPathResource resource = new ClassPathResource("Dockerfile");
//    }
//}
