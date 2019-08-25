package com.simplicity.resourceserver.configs;

import lombok.*;
import org.springframework.security.oauth2.provider.OAuth2Request;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class CustomOauth2Request extends OAuth2Request {
    public CustomOauth2Request(OAuth2Request other) {
        super(other);
    }
    @Getter
    @Setter
    private List<String> roles;
    @Getter
    @Setter
    private String state;


}
