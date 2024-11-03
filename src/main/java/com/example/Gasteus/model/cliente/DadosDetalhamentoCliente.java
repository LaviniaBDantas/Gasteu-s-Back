package com.example.Gasteus.model.cliente;

import com.example.Gasteus.model.cliente.Cliente;

public record DadosDetalhamentoCliente(String cpf, String nome, String telefone, byte[] fotoPerfil) {
    public DadosDetalhamentoCliente(Cliente cliente){
        this(cliente.getCpf(), cliente.getNome(), cliente.getTelefone(), cliente.getFotoPerfil());
    }
}
