package com.simplicity.resourceserver.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
public class WebMvcConfiguration implements WebMvcConfigurer {

    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/META-INF/resources/", "classpath:/resources/",
            "classpath:/static/", "classpath:/public/" };



    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);

        registry.addResourceHandler("/vanilla/**")
                .addResourceLocations("classpath:/vanilla/");

            registry.addResourceHandler("/react/**")
                    .addResourceLocations("classpath:/react/")
                .resourceChain(true);
//                .addResolver(new PathResourceResolver() {
//            @Override
//            protected Resource getResource(String resourcePath,
//                                           Resource location) throws IOException {
//                Resource requestedResource = location.createRelative(resourcePath);
//                return requestedResource.exists() && requestedResource.isReadable() ? requestedResource
//                        : new ClassPathResource("/vanilla/authorize.html");
//            }
//        });

    }

    @Bean
    public ViewResolver getViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setSuffix(".html");
        return resolver;
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setUseTrailingSlashMatch(true);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/react")
                .setViewName("forward:/react/index.html");
        registry.addViewController("/react/{path:[^\\.]*}")
                .setViewName("forward:/react/index.html");
        registry.addViewController("/react/**/{path:[^\\.]*}")
                .setViewName("forward:/react/index.html");
//        registry.addViewController("/react/{spring:\\w+}/**{spring:?!(\\.js|\\.css)$}")
//                .setViewName("forward:/react/index.html");
    }
}
