package br.infnet.tp1guilda.dto.elastic;

public record ContagemCampoAggregation(
        String campo,
        String valor,
        Long quantidade
) {
}
