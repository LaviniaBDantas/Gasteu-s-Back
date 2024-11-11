package com.example.Gasteus.security;

import com.example.Gasteus.model.cliente.autenticacao.AutenticacaoClienteService;
import com.example.Gasteus.model.funcionario.autenticacao.AutenticacaoFuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter securityFilter;

    @Autowired
    private AutenticacaoClienteService autenticacaoServiceCliente;

    @Autowired
    private AutenticacaoFuncionarioService autenticacaoServiceFuncionario;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(req -> {
                    req.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()  // Permite todos os requests OPTIONS
                            .requestMatchers("/login/cliente", "/cadastro/cliente", "/home", "/pratos", "/relatorioAvaliacao").permitAll()
                            .requestMatchers("/login/funcionario", "/cadastro/funcionario").permitAll()
                            .requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()
                            .requestMatchers("/reserva").authenticated()
                            .requestMatchers("/user/**").hasRole("USER")
                            .requestMatchers("/admin/**").hasRole("ADMIN")
                            .anyRequest().authenticated();
                })
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return new ProviderManager(Arrays.asList(clienteAuthenticationProvider(), funcionarioAuthenticationProvider()));
    }


    // Configurações para o serviço de autenticação de Cliente
    @Bean
    public DaoAuthenticationProvider clienteAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(autenticacaoServiceCliente);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // Configurações para o serviço de autenticação de Funcionário
    @Bean
    public DaoAuthenticationProvider funcionarioAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(autenticacaoServiceFuncionario);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
