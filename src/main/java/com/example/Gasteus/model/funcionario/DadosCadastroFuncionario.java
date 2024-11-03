package com.example.Gasteus.model.funcionario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.sql.Date;
import java.time.LocalDate;

public record DadosCadastroFuncionario(
        @NotNull Long nroCarteira,
        @NotBlank String nome,
        @NotBlank String telefone,
        @NotBlank String funcao,
        @NotNull LocalDate dataContratacao,  // Alteração aqui
        @NotBlank String senha
) {
}
