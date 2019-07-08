package com.simplicity.authserver.configs;

import com.simplicity.authserver.configs.properties.Oauth2SecurityProperties;
import com.simplicity.authserver.security.CustomUserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

@RequiredArgsConstructor
public class CustomJwtAccessTokenConverter extends JwtAccessTokenConverter {

    private final Oauth2SecurityProperties props;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Map<String, Object> info = new LinkedHashMap<>(accessToken.getAdditionalInformation());
        info.put("roles", ((CustomUserPrincipal) authentication.getUserAuthentication().getPrincipal()).getRoles());
        info.put("state", getState(authentication));
        DefaultOAuth2AccessToken customAccessToken = new DefaultOAuth2AccessToken(accessToken);
        customAccessToken.setAdditionalInformation(info);
        return super.enhance(customAccessToken, authentication);
    }

    @SneakyThrows
    private String getState(Authentication authentication) {
        byte[] hmacData;
        final long unixTime = Instant.now().getEpochSecond();
        long adhocTime = unixTime / 60;

        String rawState = adhocTime + ((CustomUserPrincipal)authentication.getPrincipal()).getUsername();
        SecretKeySpec secretKey = new SecretKeySpec(rawState.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(secretKey);
        hmacData = mac.doFinal(rawState.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hmacData);
    }
}
