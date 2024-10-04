package com.example.Gasteus.model.cliente.autenticacao;

import jakarta.validation.constraints.NotBlank;

public record DadosAutenticacao(@NotBlank String cpf, @NotBlank String senha) {
}
