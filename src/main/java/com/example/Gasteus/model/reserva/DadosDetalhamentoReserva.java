package com.example.Gasteus.model.reserva;

import java.time.LocalDateTime;

public record DadosDetalhamentoReserva(Integer cod,Integer mesa,LocalDateTime dataHora,Integer qtd) {
    public DadosDetalhamentoReserva(Reserva reserva){
        this(reserva.getCod(), reserva.getMesa(), reserva.getDataHora(), reserva.getQtdPessoas());
    }
}
