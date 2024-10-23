package Projeto.DAC.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfigService {

    private final PasswordEncoder passwordEncoder;
    private final UsuarioService usuarioService;

    public SecurityConfigService(PasswordEncoder passwordEncoder, UsuarioService usuarioService) {
        this.passwordEncoder = passwordEncoder;
        this.usuarioService = usuarioService;
    }

   /* @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
            	.requestMatchers("/v3/api-docs/*", "/swagger-ui/*", "/swagger-ui.html").permitAll()
                .anyRequest().authenticated() 
            )
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = 
            http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
            .userDetailsService(usuarioService) 
            .passwordEncoder(passwordEncoder);
        return authenticationManagerBuilder.build();
    }*/
}