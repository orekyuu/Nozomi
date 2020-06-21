package org.orekyuu.nozomi.domain.docker;

import io.reactivex.rxjava3.core.Observable;
import org.orekyuu.nozomi.infrastructure.docker.DockerTask;

public interface DockerService {

    void ping();

    Observable<String> runContainer(DockerTask task);
}
