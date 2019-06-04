package com.simplicity.resourceserver.configs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.oauth2.provider.OAuth2Request;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomOauth2Request extends OAuth2Request {
    public CustomOauth2Request(OAuth2Request other) {
        super(other);
    }
    private List<String> roles;
}
