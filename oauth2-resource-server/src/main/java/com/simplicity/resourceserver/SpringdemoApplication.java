package com.simplicity.resourceserver;

import com.simplicity.resourceserver.configs.SecurityProperties;
import groovy.util.logging.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.TimeZone;

@SpringBootApplication
@EnableConfigurationProperties({SecurityProperties.class})
@Slf4j
public class SpringdemoApplication implements InitializingBean {

    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
    public static void main(String[] args) {
        SpringApplication.run(SpringdemoApplication.class, args);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        started();
    }
}
