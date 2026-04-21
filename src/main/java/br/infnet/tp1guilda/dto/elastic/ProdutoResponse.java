package br.infnet.tp1guilda.dto.elastic;

public record ProdutoResponse(
        String nome,
        String descricao,
        String categoria,
        String raridade,
        Double preco
) {
}
