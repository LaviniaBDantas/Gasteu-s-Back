package com.example.Gasteus.model.factory;

import com.example.Gasteus.model.Role;
import com.example.Gasteus.model.cliente.Cliente;
import com.example.Gasteus.model.cliente.DadosCadastroCliente;
import com.example.Gasteus.model.template.FactoryTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;

//ANTES DO TEMPLATE:
//@Component
//public class ClienteFactory implements IClienteFactory{
//
//    @Autowired
//    private RoleRepository roleRepository;
//
//    @Override
//    public Cliente criarCliente(DadosCadastroCliente dadosCadastroCliente) {
//
//        Role clienteRole = roleRepository.findByNome("USER");
//        if (clienteRole==null){
//            throw new IllegalArgumentException("Role USER não encontrada.");
//        }
//
//        var cliente = new Cliente(dadosCadastroCliente);
//        cliente.setRole(clienteRole);
//        if (dadosCadastroCliente.fotoPerfil()!=null && !dadosCadastroCliente.fotoPerfil().isEmpty()){
//            try{
//                cliente.setFotoPerfil(dadosCadastroCliente.fotoPerfil().getBytes());
//            }catch (IOException e){
//                throw new RuntimeException("Erro ao processar foto de perfil",e);
//            }
//        }
//        return cliente;
//    }
//}

//DEPOIS:

@Component
public class ClienteFactory extends FactoryTemplate<Cliente,DadosCadastroCliente> {
    @Override
    protected Role obterRole() {
        return roleRepository.findByNome("USER");
    }

    @Override
    protected Cliente criarInstanciaUsuario(DadosCadastroCliente dadosCadastroCliente) {
        return new Cliente(dadosCadastroCliente);
    }

    @Override
    protected void atribuirRole(Cliente cliente, Role role) {
        cliente.setRole(role);
    }


    @Override
    protected void processarDadosEspecificos(DadosCadastroCliente dadosCadastro, Cliente cliente) {
        if (dadosCadastro.fotoPerfil() != null && !dadosCadastro.fotoPerfil().isEmpty()) {
            try {
                cliente.setFotoPerfil(dadosCadastro.fotoPerfil().getBytes());
            } catch (IOException e) {
                throw new RuntimeException("Erro ao processar foto de perfil", e);
            }
        }
    }
}
