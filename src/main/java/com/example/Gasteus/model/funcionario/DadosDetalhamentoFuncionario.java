package com.example.Gasteus.model.funcionario;

import com.example.Gasteus.model.cliente.Cliente;

import java.sql.Date;
import java.time.LocalDate;

public record DadosDetalhamentoFuncionario(Long nroCarteira, String nome, String telefone, String funcao, LocalDate dataContratacao, Double salario, byte[] curriculo ) {
    public DadosDetalhamentoFuncionario(Funcionario funcionario){
        this(funcionario.getNroCarteira(), funcionario.getNome(), funcionario.getTelefone(), funcionario.getFuncao(), funcionario.getDataContratacao(), funcionario.getSalario(), funcionario.getCurriculo());
    }
}
