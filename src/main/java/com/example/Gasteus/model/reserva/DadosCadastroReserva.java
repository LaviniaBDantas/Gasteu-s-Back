package com.example.Gasteus.model.reserva;

import jakarta.validation.constraints.NotBlank;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

public record DadosCadastroReserva(
    @NotBlank
    Integer cod,
    @NotBlank
    Integer mesa,
    @NotBlank
    LocalDateTime dataHora,
    @NotBlank
    Integer qtd
) {
}
