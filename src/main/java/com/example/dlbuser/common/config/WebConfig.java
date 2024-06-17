package com.example.dlbuser.common.config;

import com.example.dlbuser.auth.jwt.JwtProvider;
import com.example.dlbuser.common.config.interceptor.AuthInterceptor;
import com.example.dlbuser.domains.member.repository.MemberRepository;
import com.example.dlbuser.domains.memberToken.repository.MemberTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final JwtProvider tokenProvider;
    private final MemberTokenRepository memberTokenRepository;
    private final MemberRepository memberRepository;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 모든 경로에 앞으로 만들 모든 CORS 정보를 적용한다
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                // 모든 HTTP Method를 허용한다.
                .allowedMethods("*")
                // HTTP 요청의 Header에 어떤 값이든 들어갈 수 있도록 허용한다.
                .allowedHeaders("*")
                // 자격증명 사용을 허용한다.
                // 해당 옵션 사용시 allowedOrigins를 * (전체)로 설정할 수 없다.
                .allowCredentials(true);
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilterRegistrationBean() {
        System.out.println("filter starting");
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(false);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean<CorsFilter> filterBean = new FilterRegistrationBean<>(new CorsFilter(source));
        filterBean.setOrder(0);
        return filterBean;
    }

    @Bean
    public AuthInterceptor authInterceptor() {
        return new AuthInterceptor(tokenProvider, memberTokenRepository, memberRepository);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor())
                .order(1)
                .addPathPatterns("/api/v1/mypage/**");
                //.excludePathPatterns("/api/oauth/login", "/api/member/profile-image", "/api/fcm/message");
    }

}