package com.simplicity.authserver.configs.properties;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

@Slf4j
@ToString
@EqualsAndHashCode
@Component
@ConfigurationProperties(prefix = "security")
public class Oauth2SecurityProperties {

        @Getter
        @Setter
        @NestedConfigurationProperty
        private  Oauth2 oauth2 = new Oauth2();

        @Data
        public static class Oauth2 {

                @NestedConfigurationProperty
                private  Client client = new Client();

                @Data
                public static class Client {
                        String clientId;
                        String clientSecret;
                        String accessTokenUri;
                }

        }
}
