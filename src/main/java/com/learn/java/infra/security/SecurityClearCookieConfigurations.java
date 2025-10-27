package com.learn.java.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

@Configuration
@EnableWebSecurity
public class SecurityClearCookieConfigurations {

    /**
     * Limpeza pós Logout.
     * Invalidate the HTTP session (SecurityContextLogoutHandler) Interface (LogoutHandler)
     * - Invalida a sessão no servidor e também limpa o contexto, prevenindo que dados sensíveis persistam após o logout (SecurityContext)
     * Clear the SecurityContextHolderStrategy (SecurityContextLogoutHandler)
     * - Chama SecurityContextHolder.clearContext()
     * Clear the SecurityContextRepository (SecurityContextLogoutHandler)
     * - SecurityContextLogoutHandler invoca o método SecurityContextRepository.saveContext(null, request, response)
     * Clean up any RememberMe authentication (TokenRememberMeServices / PersistentTokenRememberMeServices)
     * Clear out any saved CSRF token (CsrfLogoutHandler)
     * Fire a LogoutSuccessEvent (LogoutSuccessEventPublishingLogoutHandler)
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        /* Hijacking(Cookies): Sequestro de Sessão
         * É possível pegar o JSESSIONID de outra pessoa se a sessão dela estiver ativa
         *
         *  Java => Cookie de Sessão (nome, valor) => JSESSIONID
         *  /login (JSESSIONID1, dados login) => Usuário Logado => /medicos (JSESSIONID2) => /consultas (JSESSIONID2)
         *
         *  Padrão: 30min da sessão
         */

        http
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .addLogoutHandler(new SecurityContextLogoutHandler())
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }
}
