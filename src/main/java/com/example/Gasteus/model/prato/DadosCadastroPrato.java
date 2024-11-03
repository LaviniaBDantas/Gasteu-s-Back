package com.example.Gasteus.model.prato;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroPrato

   (@NotBlank String nome,
    @NotNull Double preco,
    Double avaliacaoMed,
    @NotBlank
    String descricao,
   @NotBlank
   String modoPreparo){

}
