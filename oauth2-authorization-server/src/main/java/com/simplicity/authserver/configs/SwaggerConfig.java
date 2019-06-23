package com.simplicity.authserver.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.paths.RelativePathProvider;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.ServletContext;

import static com.google.common.base.Strings.isNullOrEmpty;

@Configuration
@EnableSwagger2
@Profile("development")
public class SwaggerConfig {
    public static final String ROOT = "/";

    @Autowired
    private ServletContext servletContext;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/")

                .pathProvider(new RelativePathProvider(servletContext) {
                    @Override
                    public String getApplicationBasePath() {
//                String contextPath = Anole.getProperty("server.context-path");
                        String contextPath = "/docs";
                        return isNullOrEmpty(contextPath) ? ROOT : contextPath;
                    }
//                    @Override
//                    protected String getDocumentationPath() {
//                        return "/docs";
//                    }
                });
    }
}
