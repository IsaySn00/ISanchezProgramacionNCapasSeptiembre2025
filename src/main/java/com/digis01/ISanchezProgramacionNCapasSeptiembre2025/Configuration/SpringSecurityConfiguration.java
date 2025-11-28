package com.digis01.ISanchezProgramacionNCapasSeptiembre2025.Configuration;

import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.Service.ForbiddenHandler;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.Service.LoginHandler;
import com.digis01.ISanchezProgramacionNCapasSeptiembre2025.Service.UserDetailsJPAService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SpringSecurityConfiguration {

    private final UserDetailsJPAService userDetailsJPAService;

    public SpringSecurityConfiguration(UserDetailsJPAService userDetailsJPAService) {
        this.userDetailsJPAService = userDetailsJPAService;
    }
    
    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new ForbiddenHandler();
    }
    
    @Bean
    public LoginHandler loginHandler(){
        return new LoginHandler();
    } 

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configurer -> configurer
                .requestMatchers("/usuario/login", "/login").permitAll()
                .requestMatchers("/usuario/**")
                .hasAnyRole("admin", "usuario", "invitado")
                .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/usuario/login")
                        .loginProcessingUrl("/login")
                        .successHandler(loginHandler())
//                        .defaultSuccessUrl("/usuario/indexUsuario", true)
                        .failureUrl("/usuario/login-error")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/usuario/userLogout")
                        .logoutSuccessUrl("/usuario/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                ).exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler(accessDeniedHandler())
                )
                .userDetailsService(userDetailsJPAService);
        return http.build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
