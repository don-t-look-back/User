package com.example.dlbuser.common.config;


import com.example.dlbuser.resolver.RequestMemberIdArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class ArgResolverConfig implements WebMvcConfigurer {

    @Bean
    RequestMemberIdArgumentResolver requestMemberIdArgumentResolver() {
        return new RequestMemberIdArgumentResolver();
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(requestMemberIdArgumentResolver());
    }
}
