package com.example.Gasteus.model.cliente;

public record DadosDetalhamentoCliente(String cpf, String nome, String telefone, byte[] fotoPerfil, String classificacao) {
    public DadosDetalhamentoCliente(Cliente cliente){
        this(cliente.getCpf(), cliente.getNome(), cliente.getTelefone(), cliente.getFotoPerfil(), cliente.getClassificacao());
    }
}
