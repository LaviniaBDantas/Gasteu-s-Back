package com.example.Gasteus.model.funcionario.factory;

import com.example.Gasteus.model.Role;
import com.example.Gasteus.model.funcionario.DadosCadastroFuncionario;
import com.example.Gasteus.model.funcionario.Funcionario;
import com.example.Gasteus.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class FuncionarioFactory implements IFuncionarioFactory{

    @Autowired
    RoleRepository roleRepository;

    @Override
    public Funcionario criarFuncionario(DadosCadastroFuncionario dadosCadastroFuncionario) {
        Role funcionarioRole= roleRepository.findByNome("ADMIN");

        if (funcionarioRole==null){
            throw new IllegalArgumentException("Role ADMIN não encontrada.");
        }
        var funcionario = new Funcionario(dadosCadastroFuncionario);
        if (dadosCadastroFuncionario.curriculo() != null && !dadosCadastroFuncionario.curriculo().isEmpty()) {
            try {
                funcionario.setCurriculo(dadosCadastroFuncionario.curriculo().getBytes());
            } catch (IOException e) {
                throw new RuntimeException("Erro ao processar o currículo", e);
            }
        }

        return funcionario;
    }
}
