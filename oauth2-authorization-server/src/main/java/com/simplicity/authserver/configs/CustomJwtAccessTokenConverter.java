package com.simplicity.authserver.configs;

import com.simplicity.authserver.security.CustomUserPrincipal;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.LinkedHashMap;
import java.util.Map;

public class CustomJwtAccessTokenConverter extends JwtAccessTokenConverter {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Map<String, Object> info = new LinkedHashMap<>(accessToken.getAdditionalInformation());
        info.put("roles", ((CustomUserPrincipal) authentication.getUserAuthentication().getPrincipal()).getRoles());

        DefaultOAuth2AccessToken customAccessToken = new DefaultOAuth2AccessToken(accessToken);
        customAccessToken.setAdditionalInformation(info);
        return super.enhance(customAccessToken, authentication);
    }
}
