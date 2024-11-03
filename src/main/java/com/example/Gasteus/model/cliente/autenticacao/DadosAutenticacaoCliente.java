package com.example.Gasteus.model.cliente.autenticacao;

import jakarta.validation.constraints.NotBlank;

public record DadosAutenticacaoCliente(@NotBlank String cpf, @NotBlank String senha) {
}
