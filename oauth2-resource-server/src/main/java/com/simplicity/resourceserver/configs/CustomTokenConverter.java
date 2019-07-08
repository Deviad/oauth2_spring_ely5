package com.simplicity.resourceserver.configs;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomTokenConverter extends DefaultAccessTokenConverter {

    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
        OAuth2Authentication authentication = super.extractAuthentication(map);
        CustomOauth2Request customOauth2Request = new CustomOauth2Request(authentication.getOAuth2Request());
        customOauth2Request.setRoles(new ArrayList<>((List<String>)map.get("roles")));
        customOauth2Request.setState((String)map.get("state"));
        return new OAuth2Authentication(customOauth2Request, authentication.getUserAuthentication());
    }

    public static CustomOauth2Request getOAuth2RequestFromAuthentication() {
        Authentication authentication = getAuthentication();
        return getCustomOAuth2Request(authentication);
    }

    private static CustomOauth2Request getCustomOAuth2Request(Authentication authentication) {
        if (!authentication.getClass().isAssignableFrom(OAuth2Authentication.class)) {
            throw new RuntimeException("unexpected authentication object, expected OAuth2 authentication object");
        }

        return (CustomOauth2Request) ((OAuth2Authentication) authentication).getOAuth2Request();
    }

    private static Authentication getAuthentication() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return securityContext.getAuthentication();
    }

}
