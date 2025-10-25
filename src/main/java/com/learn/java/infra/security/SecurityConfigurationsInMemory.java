package com.learn.java.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfigurationsInMemory {

    /**
     * Global AuthenticationManager configured with UserDetailsService bean with name registeredUsersInMemory
     * @return UserDetailsService
     */
    @Bean
    public UserDetailsService registeredUsersInMemory() {
        // Given that there is no default password encoder configured, each password must have a password encoding prefix.
        // Please either prefix this password with '{noop}' or set a default password encoder in `DelegatingPasswordEncoder`.
        UserDetails user1 = User.builder().username("manu").password("{noop}manu123").build();
        UserDetails user2 = User.builder().username("vivi").password("{noop}vivi123").build();

        return new InMemoryUserDetailsManager(user1, user2);
    }
}
