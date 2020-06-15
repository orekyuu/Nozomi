package org.orekyuu.nozomi.utils.database;

import org.seasar.doma.jdbc.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AssertJDBConfiguration {

    @Bean
    Tables tables(Config config) {
        // Use doma datasouce.
        return new Tables(config.getDataSource());
    }
}
