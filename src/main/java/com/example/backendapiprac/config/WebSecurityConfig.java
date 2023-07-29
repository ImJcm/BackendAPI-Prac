package com.example.backendapiprac.config;

import com.example.backendapiprac.jwt.JwtAuthorizationFilter;
import com.example.backendapiprac.jwt.JwtExceptionFilter;
import com.example.backendapiprac.jwt.JwtUtil;
import com.example.backendapiprac.jwt.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.swing.text.html.FormSubmitEvent;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;


    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(jwtUtil,userDetailsService);
    }

    /* Authorization Filter 예외 처리 핸들링 Filter */
    @Bean
    public JwtExceptionFilter jwtExceptionFilter() {
        return new JwtExceptionFilter();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        /* CSRF 설정 */
        http.csrf((csrf) -> csrf.disable());

        /* 기본 설정인 Session 방식은 사용하지 않고 JWT 방식을 사용하기 위한 설정 */
        http.sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authorizeHttpRequests((authorizeHttpRequests) ->
                authorizeHttpRequests
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/api/users/**").permitAll()
                        .requestMatchers("/api/post/**", HttpMethod.GET.name()).permitAll()
                        .anyRequest().authenticated()
        );

        http.formLogin(AbstractHttpConfigurer::disable);

        http.addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtExceptionFilter(), JwtAuthorizationFilter.class);

        return http.build();
    }
}
