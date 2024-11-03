package com.example.Gasteus.model.funcionario.autenticacao;

import com.example.Gasteus.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoFuncionarioService implements UserDetailsService{

    @Autowired
    private FuncionarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String nroCarteira) throws UsernameNotFoundException {
        Long numeroCarteira = Long.parseLong(nroCarteira);
        return repository.findByNroCarteira(numeroCarteira)
                .orElseThrow(() -> new UsernameNotFoundException("Funcionário não encontrado"));
    }
}

//@Override
//public UserDetails loadUserByUsername(String nroCarteira) throws UsernameNotFoundException {
//    try {
//        Long numeroCarteira = Long.parseLong(nroCarteira);
//        return repository.findByNroCarteira(numeroCarteira)
//                .orElseThrow(() -> new UsernameNotFoundException("Funcionário não encontrado"));
//    } catch (NumberFormatException e) {
//        throw new UsernameNotFoundException("Número da carteira inválido", e);
//    }
//}
