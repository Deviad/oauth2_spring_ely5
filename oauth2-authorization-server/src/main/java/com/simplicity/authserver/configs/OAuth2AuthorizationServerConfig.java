package com.simplicity.authserver.configs;

import com.simplicity.authserver.configs.properties.Oauth2SecurityProperties;
import com.simplicity.authserver.security.CustomUserDetailsService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;
import org.springframework.core.io.FileSystemResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

@Slf4j
@Configuration
@DependsOn({"authenticationManagerBean"})
@Import(CustomUserDetailsService.class)
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{
    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Autowired
    @Qualifier("encoder")
    private PasswordEncoder passwordEncoder;


    @Autowired
    private CustomUserDetailsService userDetailsService;


    @Autowired
    private Oauth2SecurityProperties oauth2Props;

    /*
        tokenKeyAccess, checkTokenAccess take as a parameter one
        of the security expressions defined in CustomSecurityExpressionRoot
     */

    @Override
    public void configure(final AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.allowFormAuthenticationForClients().tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
        // A few examples with implementations of different grant types
        log.info("Client id, Client secret: {}, {}", oauth2Props.getOauth2().getClient().getClientId(), oauth2Props.getOauth2().getClient().getClientSecret());


        String[] authorizedUrls = {
                "http://localhost:5051/vanilla/issue_token",
                "http://localhost:5051/vanilla/authorization_code",
                "http://localhost:5051/vanilla/something_to_see",
                "http://localhost:5051/react/oauth/authorization",
                "http://localhost:5051/react/oauth/token",
                "http://localhost:3000/oauth/authorization",
                "http://localhost:3000/oauth/token",

        };

        clients.inMemory().
                withClient(oauth2Props.getOauth2().getClient().getClientId())
                .secret(passwordEncoder.encode(oauth2Props.getOauth2().getClient().getClientSecret()))
                .authorizedGrantTypes("password", "authorization_code", "refresh_token")
                .scopes("foo", "read", "write")
                .redirectUris(authorizedUrls)
                .accessTokenValiditySeconds(3600).resourceIds()
                // 1 hour
                .refreshTokenValiditySeconds(2592000);
                // 30 days
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }

    @Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        final TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
//        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), accessTokenConverter()));
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(accessTokenConverter()));
        endpoints
                .tokenStore(tokenStore())
                .tokenEnhancer(tokenEnhancerChain)
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    @SneakyThrows
    public JwtAccessTokenConverter accessTokenConverter() {
        // specifically the following line:
        Path resourceDirectory = Paths.get("src", "main","resources");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();
        JwtAccessTokenConverter converter = new CustomJwtAccessTokenConverter(oauth2Props);
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new FileSystemResource(absolutePath + "/jwt.jks"), "password".toCharArray());
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("jwt"));
        return converter;
    }

}
