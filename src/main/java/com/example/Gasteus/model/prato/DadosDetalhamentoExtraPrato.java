package com.example.Gasteus.model.prato;

public record DadosDetalhamentoExtraPrato(
        Integer cod,
        String nome,
        Double preco,
        Double avaliacaoMed,
        String descricao,
        String modoPreparo
) {
    public DadosDetalhamentoExtraPrato(Prato prato) {
        this(
                prato.getCod(),
                prato.getNome(),
                prato.getPreco(),
                prato.getAvaliacaoMed(),
                prato.getDescricao(),
                prato.getModoPreparo()
        );
    }
}
