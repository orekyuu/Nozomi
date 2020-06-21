package org.orekyuu.nozomi.infrastructure.docker;

import java.util.List;

public record DockerTask(String image, String tag, List<EnvironmentVariable>env, List<String>command) {
}
