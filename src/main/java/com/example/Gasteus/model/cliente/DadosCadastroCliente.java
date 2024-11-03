package com.example.Gasteus.model.cliente;

import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

public record DadosCadastroCliente(
        @NotBlank
        String cpf,
        @NotBlank
        String nome,
        @NotBlank
        String telefone,
        @NotBlank
        String senha,
        MultipartFile fotoPerfil
) {
}
