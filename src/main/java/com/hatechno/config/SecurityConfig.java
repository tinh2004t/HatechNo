package com.hatechno.config;

import com.hatechno.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(UserDetailsService userDetailsService, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // ✅ Tắt CSRF để tránh lỗi khi gọi API từ frontend
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // ✅ Không lưu session
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**").permitAll() // ✅ Cho phép đăng nhập, đăng ký
                .requestMatchers("/residents/**").permitAll() // ✅ Mở API cư dân
                .requestMatchers("/apartments/**").permitAll() // ✅ Mở API căn hộ
                .requestMatchers("/payments/**").permitAll()
                .requestMatchers("/invoices/**").permitAll()
                .requestMatchers("/api/admin/**").hasRole("ADMIN") // ✅ Yêu cầu ADMIN với API quản trị
                .requestMatchers("/api/user/**").hasRole("USER") // ✅ Yêu cầu USER với API người dùng
                .requestMatchers("/api/complaints/**").hasAnyRole("USER", "ADMIN") // ✅ Cả USER và ADMIN truy cập
                .anyRequest().authenticated() // 🛑 Các API khác yêu cầu đăng nhập
            )
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
