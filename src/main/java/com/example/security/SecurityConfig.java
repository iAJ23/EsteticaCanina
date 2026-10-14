package com.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/styles.css", "/home.css", "/palette.css", "/access.css", "/mascot.css", "/agenda.css", "/pulguita.png", "/favicon.ico").permitAll()
                        .requestMatchers("/usuarios/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/", "/mascotas", "/agenda", "/api/mascotas/**", "/api/citas/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/mascotas/nueva", "/mascotas/*/editar").hasAnyRole("ADMIN", "OPERADOR")
                        .requestMatchers(HttpMethod.GET, "/agenda/nueva", "/agenda/*/editar").hasAnyRole("ADMIN", "OPERADOR")
                        .requestMatchers(HttpMethod.POST, "/mascotas/*/eliminar", "/agenda/*/eliminar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/mascotas/**", "/api/citas/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/mascotas", "/mascotas/*", "/agenda", "/agenda/*", "/api/mascotas/**", "/api/citas/**")
                        .hasAnyRole("ADMIN", "OPERADOR")
                        .requestMatchers(HttpMethod.PUT, "/api/mascotas/**", "/api/citas/**").hasAnyRole("ADMIN", "OPERADOR")
                        .anyRequest().authenticated())
                .formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/", true).permitAll())
                .logout(logout -> logout.logoutSuccessUrl("/login?logout").permitAll());
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }
}
