package Projeto.DAC.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfigService {

    private final PasswordEncoder passwordEncoder;
    private final UsuarioService usuarioService;

    public SecurityConfigService(PasswordEncoder passwordEncoder, UsuarioService usuarioService) {
        this.passwordEncoder = passwordEncoder;
        this.usuarioService = usuarioService;
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**").hasAnyRole("ADMIN", "USER")
                .requestMatchers("/api/carro").hasAnyRole("ADMIN", "USER")
                .requestMatchers("/api/carro/listar").hasAnyRole("ADMIN", "USER")
                .requestMatchers("/api/carro/qrcode/**", "/api/carro/pdf/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form.permitAll())
            .httpBasic(Customizer.withDefaults())
            .logout(logout -> logout
                    .logoutUrl("/logout-custom") 
                    .logoutSuccessUrl("/swagger-ui.html") 
                    .invalidateHttpSession(true) 
                    .clearAuthentication(true) 
                    .deleteCookies("JSESSIONID")
            );
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}