package com.learn.java.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityCustomForm {

    @Bean
    public UserDetailsService registeredUsersInMemoryForm() {
        UserDetails user1 = User.builder().username("manu").password("{noop}manu123").build();
        UserDetails user2 = User.builder().username("vivi").password("{noop}vivi123").build();

        return new InMemoryUserDetailsManager(user1, user2);
    }

    /**
     * Modifica login e logout padrão do Security
     * <a href="https://docs.spring.io/spring-security/reference/servlet/authentication/logout.html#logout-java-configuration">...</a>
     * @param http HttpSecurity
     * @return SecurityFilterChain
     * @throws Exception Throws by authorizeHttpRequests()
     */
    @Bean
    public SecurityFilterChain securityFilter(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(req -> {
                    req.requestMatchers("/css/**", "/js/**", "assets/**").permitAll();
                    req.anyRequest().authenticated();
                })
                .formLogin(form -> form.loginPage("/login") // Login Filter
                    .defaultSuccessUrl("/dashboard")
                    .permitAll()
                )
                .logout(logout -> logout  // Logout Filter
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
        .build();
    }
}
