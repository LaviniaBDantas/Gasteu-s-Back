package com.example.Gasteus.model.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.Gasteus.model.cliente.autenticacao.AutenticacaoClienteService;
import com.example.Gasteus.model.funcionario.autenticacao.AutenticacaoFuncionarioService;

@Service
public class AutenticacaoAdapter implements AutenticacaoService {

    @Autowired
    private AutenticacaoClienteService clienteService;

    @Autowired
    private AutenticacaoFuncionarioService funcionarioService;

    @Override
    public UserDetails autenticar(String identificador) throws UsernameNotFoundException {
        try {
            // Tenta autenticar como cliente usando o CPF
            return clienteService.loadUserByUsername(identificador);
        } catch (UsernameNotFoundException e) {
            // Se falhar, tenta autenticar como funcionário usando o número da carteira
            return funcionarioService.loadUserByUsername(identificador);
        }
    }
}

