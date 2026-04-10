package br.infnet.tp1guilda.dto.aventureiro;

import br.infnet.tp1guilda.enums.Classe;

public record ResponseAventureiro(
        Long id,
        String nome,
        Classe classe,
        int nivel,
        boolean ativo
) {
}