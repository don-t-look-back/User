package com.example.dlbuser.common.config;

import com.example.dlbuser.auth.jwt.JwtProvider;
import com.example.dlbuser.common.response.SimpleResponse;
import com.example.dlbuser.domains.member.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig{

    private static final Set<String> IGNORED_LOGGING_URI_SET = new HashSet<>(Arrays.asList(
            "/",
            "/csrf"
    ));
    private final JwtProvider jwtProvider;
    private final MemberService memberService;

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable().headers().frameOptions().disable()
                .and()
                .authorizeRequests() // url별 권한 관리를 설정하는 옵션의 시작점
                .requestMatchers("/oauth/login", "/fcm/message", "/api/v1/**", "/api/**").permitAll()
                .requestMatchers("/", "/css/**", "**.html", "/images/**", "/js/**"
                        , "/assets/**", "/swagger-ui.html").permitAll()

                //.cors()

                .and()

                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()

                .exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, authException) -> {
                            String requestURI = request.getRequestURI();
                            if (!IGNORED_LOGGING_URI_SET.contains(requestURI)) {
                                log.warn("UNAUTHORIZED: " + requestURI, authException);
                            }
                            response.setStatus(HttpStatus.UNAUTHORIZED.value());
                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                            apiResponseObjectMapper().writeValue(
                                    response.getOutputStream(),
                                    SimpleResponse.from("인증이 필요한 요청입니다.")
                            );
                        }
                )
                .accessDeniedHandler(
                        (request, response, accessDeniedException) -> {
                            String requestURI = request.getRequestURI();
                            if (!IGNORED_LOGGING_URI_SET.contains(requestURI)) {
                                log.warn("FORBIDDEN: " + request.getRequestURI(), accessDeniedException);
                            }
                            response.setStatus(HttpStatus.FORBIDDEN.value());
                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                            apiResponseObjectMapper().writeValue(
                                    response.getOutputStream(),
                                    SimpleResponse.from("접근 권한이 없습니다.")
                            );
                        }
                )

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
//                .requestMatchers(HttpMethod.POST, ALLOWED_URI_PATTERN).permitAll()
//                .requestMatchers(HttpMethod.GET, ALLOWED_URI_PATTERN).permitAll()
//                .requestMatchers(HttpMethod.DELETE, ALLOWED_URI_PATTERN).permitAll()
//                .requestMatchers(HttpMethod.PUT, ALLOWED_URI_PATTERN).permitAll()
//                .requestMatchers(HttpMethod.PATCH, ALLOWED_URI_PATTERN).permitAll()
                //.anyRequest().hasRole("USER")

                //.and()
                //.addFilterAt(jwtAuthenticationFilter(), AbstractPreAuthenticatedProcessingFilter.class)

        ;

        return http.build();
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().requestMatchers( "/v2/api-docs",
//                "/swagger-resources/**",
//                "/swagger-ui.html",
//                "/webjars/**",
//                "/swagger/**",
//                "/health",
//                "/actuator",
//                "/actuator/**",
//                "/error");
//    }

//    @Bean
//    public JwtPreAuthenticatedProcessingFilter jwtAuthenticationFilter() {
//        JwtPreAuthenticatedProcessingFilter filter = new JwtPreAuthenticatedProcessingFilter();
//        filter.setAuthenticationManager(jwtAuthenticationManager());
//        return filter;
//    }

//    @Bean
//    public AuthenticationManager jwtAuthenticationManager() {
//        return new ProviderManager(
//                new JwtAuthenticationProvider(jwtProvider, memberService)
//        );
//    }

    @Bean
    public ObjectMapper apiResponseObjectMapper() {
        return new ObjectMapper();
    }
}
