package com.example.Gasteus.model.reserva;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

public record DadosCadastroReserva(
    @NotNull
    Integer mesa,
    @NotNull
    LocalDateTime dataHora,
    @NotNull
    Integer qtd
) {
}
