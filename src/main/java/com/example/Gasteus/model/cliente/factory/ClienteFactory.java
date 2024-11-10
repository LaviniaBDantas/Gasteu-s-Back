package com.example.Gasteus.model.cliente.factory;

import com.example.Gasteus.model.Role;
import com.example.Gasteus.model.cliente.Cliente;
import com.example.Gasteus.model.cliente.DadosCadastroCliente;
import com.example.Gasteus.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ClienteFactory implements IClienteFactory{

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Cliente criarCliente(DadosCadastroCliente dadosCadastroCliente) {

        Role clienteRole = roleRepository.findByNome("USER");
        if (clienteRole==null){
            throw new IllegalArgumentException("Role USER não encontrada.");
        }

        var cliente = new Cliente(dadosCadastroCliente);
        cliente.setRole(clienteRole);
        if (dadosCadastroCliente.fotoPerfil()!=null && !dadosCadastroCliente.fotoPerfil().isEmpty()){
            try{
                cliente.setFotoPerfil(dadosCadastroCliente.fotoPerfil().getBytes());
            }catch (IOException e){
                throw new RuntimeException("Erro ao processar foto de perfil",e);
            }
        }
        return cliente;
    }
}
