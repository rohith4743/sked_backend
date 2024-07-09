package com.rohithkankipati.projects.Sked.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

	http.authorizeHttpRequests(
		auth -> auth.requestMatchers("/users/api/**").permitAll().anyRequest().authenticated())
		.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.csrf(csrf -> csrf.disable()) // Adjusted for hypothetical new CSRF configuration
		.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

	return http.build();
    }

    @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter() {
    	return new JwtAuthenticationFilter();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }

}
