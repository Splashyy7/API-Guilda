package br.infnet.tp1guilda.dto;

import br.infnet.tp1guilda.enums.Classe;
import jakarta.validation.constraints.Min;

public record AtualizarAventureiro(
        String nome,
        Classe classe,
        @Min(value = 1, message = "O nível deve ser pelo menos 1")
        Integer nivel
) {
}