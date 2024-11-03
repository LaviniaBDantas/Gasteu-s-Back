package com.example.Gasteus.model.funcionario.autenticacao;

import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosAutenticacaoFuncionario(@NotNull Long nroCarteira, @NotBlank String senha) {
}
