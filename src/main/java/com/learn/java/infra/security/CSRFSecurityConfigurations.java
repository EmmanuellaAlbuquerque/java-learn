package com.learn.java.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class CSRFSecurityConfigurations {

    @Bean
    public UserDetailsService registeredUsersInMemoryForm() {
        UserDetails user1 = User.builder().username("manu").password("{noop}manu123").build();
        UserDetails user2 = User.builder().username("vivi").password("{noop}vivi123").build();

        return new InMemoryUserDetailsManager(user1, user2);
    }

    /**
     * CSRF: Cross-site Request Forgery - Requisição forjada entre sites
     * No Thymeleaf o input hidden para o csrf é criado automaticamente
     * @param http HttpSecurity
     * @return SecurityFilterChain
     * @throws Exception Exception
     */
    @Bean
    public SecurityFilterChain securityFilter(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(req -> {
                    req.requestMatchers("/css/**", "/js/**", "assets/**").permitAll();
                    req.anyRequest().authenticated();
                })
                .formLogin(form -> form.loginPage("/login")
                        .defaultSuccessUrl("/dashboard")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .csrf(Customizer.withDefaults()) // Já vem habilitado por default
                .build();
    }
}
