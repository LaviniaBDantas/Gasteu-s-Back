package com.example.Gasteus.model.funcionario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDate;

public record DadosCadastroFuncionario(
        @NotNull Long nroCarteira,
        @NotBlank String nome,
        @NotBlank String telefone,
        @NotBlank String funcao,
        @NotNull LocalDate dataContratacao,
        @NotBlank String senha,
        MultipartFile curriculo  // Novo campo para o documento
) {
}
