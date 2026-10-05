package com.prasanna.claude;

import com.prasanna.claude.config.OllamaProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(OllamaProperties.class)
public class ClaudeLearningPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClaudeLearningPlatformApplication.class, args);
    }

}
