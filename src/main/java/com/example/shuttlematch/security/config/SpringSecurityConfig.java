package com.example.shuttlematch.security.config;

import com.example.shuttlematch.security.CustomerUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomerUserDetailsService customerUserDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf((csrf) -> {
                csrf.ignoringRequestMatchers("/h2-console/**")
                    .disable();
            })
            .sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests((authorizeHttpRequests) ->
                authorizeHttpRequests
                    .requestMatchers(
                        "/user/**",
                        "/swagger-ui/**",
                        "/api-docs/**",
                        "/h2-console/**",
                        "/user/*/register",
                        "/user/*/login",
                        "/user/*/login",
                        "/user/*/login-google",
                        "user/*/checkOtp/register",
                            "/film/**",
                            "/file/**",
                            "/voucher/**",
                            "/transaction/v1/vnpayCallback"
                    ).permitAll()
//                    .requestMatchers("/admin/**",
//                            "/film/*/delete/**",
//                            "/voucher/*/delete/**"
//                    ).hasAuthority("ADMIN")
//                    .requestMatchers("/superadmin/**").hasAuthority("SUPERADMIN")
                    .anyRequest().authenticated()
            );

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.headers((headers) ->
            headers
                .frameOptions(
                    frameOptionsConfig -> frameOptionsConfig.disable()
                ));
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
