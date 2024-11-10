package com.example.Gasteus.model.funcionario.factory;

import com.example.Gasteus.model.funcionario.DadosCadastroFuncionario;
import com.example.Gasteus.model.funcionario.Funcionario;

public interface IFuncionarioFactory {
    Funcionario criarFuncionario(DadosCadastroFuncionario dadosCadastroFuncionario);
}
