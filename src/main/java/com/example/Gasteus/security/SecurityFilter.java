package com.example.Gasteus.security;

import com.example.Gasteus.repository.ClienteRepository;
import com.example.Gasteus.repository.FuncionarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var tokenJWT = recuperarToken(request);
        if (tokenJWT != null) {
            var subject = tokenService.getSubject(tokenJWT);

            // Tenta encontrar o cliente pelo CPF
            var cliente = clienteRepository.findByCpf(subject);
            if (cliente.isPresent()) {
                var authentication = new UsernamePasswordAuthenticationToken(cliente.get(), null, cliente.get().getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                // Se não encontrar um cliente, tenta encontrar o funcionário pelo número da carteira
                var funcionario = funcionarioRepository.findByNroCarteira(Long.parseLong(subject));
                if (funcionario.isPresent()) {
                    var authentication = new UsernamePasswordAuthenticationToken(funcionario.get(), null, funcionario.get().getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }
}

