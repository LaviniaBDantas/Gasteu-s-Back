package com.example.Gasteus.model.prato;

public record DadosDetalhamentoPrato(
        Integer cod,
        String nome,
        Double preco,
        Double avaliacaoMed,
        String descricao
) {
    public DadosDetalhamentoPrato(Prato prato) {
        this(
                prato.getCod(),
                prato.getNome(),
                prato.getPreco(),
                prato.getAvaliacaoMed(),
                prato.getDescricao()
        );
    }
}
