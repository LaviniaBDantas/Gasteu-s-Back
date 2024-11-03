package com.example.Gasteus.model;

import com.example.Gasteus.model.cliente.Cliente;
import com.example.Gasteus.model.reserva.DadosCadastroReserva;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
