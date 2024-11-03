package com.example.Gasteus.model.prato;

import jakarta.persistence.*;

@Entity
@Table(name = "prato")
public class Prato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cod;

    private String nome;

    private Double preco;

    @Column(name = "d_avaliacao_med")
    private Double avaliacaoMed;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "modo_preparo", columnDefinition = "TEXT")
    private String modoPreparo;

    // Getters e Setters
    public Integer getCod() {
        return cod;
    }

    public void setCod(Integer cod) {
        this.cod = cod;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Double getAvaliacaoMed() {
        return avaliacaoMed;
    }

    public void setAvaliacaoMed(Double avaliacaoMed) {
        this.avaliacaoMed = avaliacaoMed;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getModoPreparo() {
        return modoPreparo;
    }

    public void setModoPreparo(String modoPreparo) {
        this.modoPreparo = modoPreparo;
    }
}
