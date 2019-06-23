package com.simplicity.authserver.configs;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry
                        .addMapping("/api/**")
                        .allowedOrigins("*")
                        .allowedMethods("*")
                        .maxAge(3600)
                        .allowCredentials(true);
                registry
                        .addMapping("/oauth/token")
                        .allowedOrigins("*")
                        .allowedMethods("*")
                        .maxAge(3600)
                        .allowCredentials(true);
                registry
                        .addMapping("/oauth/confirm_access")
                        .allowedOrigins("*")
                        .allowedMethods("*")
                        .maxAge(3600);

            }

            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {

//                registry.addResourceHandler("/documentation/swagger-ui.html**").addResourceLocations("classpath:/META-INF/resources/swagger-ui.html");
//                registry.addResourceHandler("/documentation/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
                registry
                    .addResourceHandler("/resources/**")
                    .addResourceLocations("classpath:/resources/");
//                    .resourceChain(true)
//                    .addResolver(new PathResourceResolver() {
//                        @Override
//                        protected Resource getResource(String resourcePath,
//                                                       Resource location) throws IOException {
//                            Resource requestedResource = location.createRelative(resourcePath);
//                            return requestedResource.exists() && requestedResource.isReadable() ? requestedResource
//                                    : new ClassPathResource("/gui-new/app.html");
//                        }
//                    });

            }

            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
//                registry.addRedirectViewController("/documentation/v2/api-docs", "/v2/api-docs?group=restful-api");
//                registry.addRedirectViewController("/documentation/swagger-resources/configuration/ui","/swagger-resources/configuration/ui");
//                registry.addRedirectViewController("/documentation/swagger-resources/configuration/security","/swagger-resources/configuration/security");
//                registry.addRedirectViewController("/documentation/swagger-resources", "/swagger-resources");
//                registry.addRedirectViewController("/documentation", "/documentation/swagger-ui.html");
            }
        };
    }
}
