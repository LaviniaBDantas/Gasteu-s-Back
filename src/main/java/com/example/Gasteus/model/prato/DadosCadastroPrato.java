package com.example.Gasteus.model.prato;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroPrato

   (@NotBlank String nome,
    @NotNull Double preco,
    @NotBlank
    String descricao,
   @NotBlank
   String modoPreparo){

}
