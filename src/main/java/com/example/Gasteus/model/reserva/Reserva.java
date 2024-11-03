package com.example.Gasteus.model.reserva;

import com.example.Gasteus.model.cliente.Cliente;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reserva")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cod;
    private int mesa;
    private LocalDateTime dataHora;
    private int qtdPessoas;
    @ManyToOne
    @JoinColumn(name="fk_cliente_cpf", referencedColumnName = "cpf", nullable = false)
    private Cliente cliente;

    public Reserva(DadosCadastroReserva dados, Cliente cliente){
        this.dataHora=dados.dataHora();
        this.mesa=dados.mesa();
        this.qtdPessoas=dados.qtd();
        this.cliente=cliente;
    }

    public Reserva() {

    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public int getMesa() {
        return mesa;
    }

    public void setMesa(int mesa) {
        this.mesa = mesa;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public int getQtdPessoas() {
        return qtdPessoas;
    }

    public void setQtdPessoas(int qtdPessoas) {
        this.qtdPessoas = qtdPessoas;
    }
}
