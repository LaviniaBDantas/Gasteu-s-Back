package com.example.Gasteus.model.adapter;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface AutenticacaoService {
    UserDetails autenticar(String identificador) throws UsernameNotFoundException;
}
