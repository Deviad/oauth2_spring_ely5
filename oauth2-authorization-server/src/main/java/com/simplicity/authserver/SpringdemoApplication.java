package com.simplicity.authserver;

import com.simplicity.authserver.configs.properties.Oauth2SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@Slf4j
@SpringBootApplication
@EnableAuthorizationServer
@EnableWebSecurity
public class SpringdemoApplication {
    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
    public static void main(String[] args) {
        SpringApplication.run(SpringdemoApplication.class, args);
    }
    @Bean
    public ApplicationRunner applicationRunner(final Oauth2SecurityProperties properties) {
        return applicationArguments -> log.info("MyProperties: {}", properties);
    }
}
