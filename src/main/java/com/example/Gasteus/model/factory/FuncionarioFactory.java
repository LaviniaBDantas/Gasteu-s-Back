package com.example.Gasteus.model.factory;

import com.example.Gasteus.model.Role;
import com.example.Gasteus.model.funcionario.DadosCadastroFuncionario;
import com.example.Gasteus.model.funcionario.Funcionario;
import com.example.Gasteus.model.template.FactoryTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;

//ANTES DO TEMPLATE
//@Component
//public class FuncionarioFactory implements IFuncionarioFactory{
//
//    @Autowired
//    RoleRepository roleRepository;
//
//    @Override
//    public Funcionario criarFuncionario(DadosCadastroFuncionario dadosCadastroFuncionario) {
//        Role funcionarioRole= roleRepository.findByNome("ADMIN");
//
//        if (funcionarioRole==null){
//            throw new IllegalArgumentException("Role ADMIN não encontrada.");
//        }
//        var funcionario = new Funcionario(dadosCadastroFuncionario);
//        if (dadosCadastroFuncionario.curriculo() != null && !dadosCadastroFuncionario.curriculo().isEmpty()) {
//            try {
//                funcionario.setCurriculo(dadosCadastroFuncionario.curriculo().getBytes());
//            } catch (IOException e) {
//                throw new RuntimeException("Erro ao processar o currículo", e);
//            }
//        }
//
//        return funcionario;
//    }
//}

@Component
public class FuncionarioFactory extends FactoryTemplate<Funcionario, DadosCadastroFuncionario> {

    @Override
    protected Role obterRole() {
        return roleRepository.findByNome("ADMIN");
    }

    @Override
    protected Funcionario criarInstanciaUsuario(DadosCadastroFuncionario dadosCadastro) {
        return new Funcionario(dadosCadastro);
    }

    @Override
    protected void atribuirRole(Funcionario funcionario, Role role) {
        funcionario.setRole(role);
    }

    @Override
    protected void processarDadosEspecificos(DadosCadastroFuncionario dadosCadastro, Funcionario funcionario) {
        if (dadosCadastro.curriculo() != null && !dadosCadastro.curriculo().isEmpty()) {
            try {
                funcionario.setCurriculo(dadosCadastro.curriculo().getBytes());
            } catch (IOException e) {
                throw new RuntimeException("Erro ao processar o currículo", e);
            }
        }
    }
}
