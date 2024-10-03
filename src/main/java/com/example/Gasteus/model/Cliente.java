package com.example.Gasteus.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cliente")
public class Cliente {
    @Id
    private String cpf;
    private String nome;
    private String telefone;
    @ManyToOne
    private Funcionario funcionario;

}
