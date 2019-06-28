package com.simplicity.resourceserver.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.resource.ResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolverChain;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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
//                .resourceChain(true)
//                .addResolver(new PathResourceResolver() {
//            @Override
//            protected Resource getResource(String resourcePath,
//                                           Resource location) throws IOException {
//                Resource requestedResource = location.createRelative(resourcePath);
//                return requestedResource.exists() && requestedResource.isReadable() ? requestedResource
//                        : new ClassPathResource("/vanilla/authorize.html");
//            }
//        });
        ResourceResolver resolver = new ReactResourceResolver();
        registry.addResourceHandler("/react/**")
//                .addResourceLocations("classpath:/react/")
                .resourceChain(true)
                .addResolver(resolver);

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

//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/react/{spring:\\w+}")
//                .setViewName("forward:/react/index.html");
//        registry.addViewController("/react/**/{spring:\\w+}")
//                .setViewName("forward:/react/index.html");
//        registry.addViewController("/react/{spring:\\w+}/**{spring:?!(\\.js|\\.css)$}")
//                .setViewName("forward:/react/index.html");
//    }

    public class ReactResourceResolver implements ResourceResolver {
        // root dir of react files
        // example REACT_DIR/index.html
        private static final String REACT_DIR = "/react/";

        // this is directory inside REACT_DIR for react static files
        // example REACT_DIR/REACT_STATIC_DIR/js/
        // example REACT_DIR/REACT_STATIC_DIR/css/
        private static final String REACT_STATIC_DIR = "static";

        private Resource index = new ClassPathResource(REACT_DIR + "index.html");
        private List<String> rootStaticFiles = Arrays.asList("favicon.io",
                "asset-manifest.json", "manifest.json", "service-worker.js");

        @Override
        public Resource resolveResource(
                HttpServletRequest request, String requestPath,
                List<? extends Resource> locations, ResourceResolverChain chain) {

            return resolve(requestPath, locations);
        }

        @Override
        public String resolveUrlPath(
                String resourcePath, List<? extends Resource> locations,
                ResourceResolverChain chain) {

            Resource resolvedResource = resolve(resourcePath, locations);
            if (resolvedResource == null) {
                return null;
            }
            try {
                return resolvedResource.getURL().toString();
            } catch (IOException e) {
                return resolvedResource.getFilename();
            }
        }

        private Resource resolve(
                String requestPath, List<? extends Resource> locations) {

            if (requestPath == null) return null;

            if (rootStaticFiles.contains(requestPath)
                    || requestPath.startsWith(REACT_STATIC_DIR)) {
                return new ClassPathResource(REACT_DIR + requestPath);
            } else
                return index;
        }

    }
}
