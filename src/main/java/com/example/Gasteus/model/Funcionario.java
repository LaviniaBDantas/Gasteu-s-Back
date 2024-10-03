package com.example.Gasteus.model;


import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "funcionario")
public class Funcionario {

    @Id
    private Integer nroCarteira;
    private String telefone;
    private LocalDate dataContratacao;
    private String funcao;
    private Double salario;
    private String nome;
}
