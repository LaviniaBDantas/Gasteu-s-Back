package com.example.Gasteus.model.cliente.autenticacaoANTES_ADAPTER;

import jakarta.validation.constraints.NotBlank;

public record DadosAutenticacaoCliente(@NotBlank String cpf, @NotBlank String senha) {
}
