package br.infnet.tp1guilda.dto.companheiro;

import br.infnet.tp1guilda.enums.Especie;

public record ResponseCompanheiro(
        Long aventureiroId,
        String nome,
        Especie especie,
        int lealdade
) {
}