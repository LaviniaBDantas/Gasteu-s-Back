//package com.example.Gasteus.model.cliente.autenticacao;
//
//import com.example.Gasteus.repository.ClienteRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class AutenticacaoClienteService implements UserDetailsService{
//
//    @Autowired
//    private ClienteRepository repository;
//    @Override
//    public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
//        return repository.findByCpf(cpf);
//    }
//}

package com.example.Gasteus.model.cliente.autenticacao;

import com.example.Gasteus.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoClienteService implements UserDetailsService {

    @Autowired
    private ClienteRepository repository;

    @Override
    public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
        return repository.findByCpf(cpf)
                .orElseThrow(() -> new UsernameNotFoundException("Cliente não encontrado com CPF: " + cpf));
    }
}
