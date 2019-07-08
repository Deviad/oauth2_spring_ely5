package com.simplicity.resourceserver;

import com.simplicity.resourceserver.configs.SecurityProperties;
import groovy.util.logging.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import java.util.TimeZone;

@SpringBootApplication
@EnableConfigurationProperties({SecurityProperties.class})
@EnableWebSecurity
@Slf4j
public class SpringResourceServerApp implements InitializingBean {

    private void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
    public static void main(String[] args) {
        SpringApplication.run(SpringResourceServerApp.class, args);
    }

    @Override
    public void afterPropertiesSet() {
        started();
    }
}
