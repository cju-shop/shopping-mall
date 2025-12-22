package com.cju.shoppingmall.config;

import com.cju.shoppingmall.auth.CustomAuthFailureHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String LOGIN_URL = "/login";

    private final CustomAuthFailureHandler failureHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        // 비로그인 허용 URL
                        .requestMatchers(
                                "/", LOGIN_URL,
                                "/css/**", "/js/**", "/images/**", "/img/**",
                                "/products/**"
                        ).permitAll()
                        // 로그인 필요 URL
                        .requestMatchers("/mypage/**").authenticated()
                        // 나머지 전부 허용
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form
                        .loginPage(LOGIN_URL)
                        .loginProcessingUrl(LOGIN_URL)
                        .defaultSuccessUrl("/", false)
                        .failureUrl(LOGIN_URL + "?error")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .failureHandler(failureHandler)
                        .permitAll()
                )
                .logout(LogoutConfigurer::permitAll);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 평문 비밀번호 테스트용
        return org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance();
    }
}
