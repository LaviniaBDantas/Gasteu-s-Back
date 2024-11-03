package com.example.Gasteus.model.prato;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizaPrato
        (String nome,
        Double preco,
        String descricao){

}
