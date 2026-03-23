package br.infnet.tp1guilda.dto;

import br.infnet.tp1guilda.enums.Classe;
import jakarta.validation.constraints.Min;

public record FilterRequestAventureiro(
        Classe classe,
        Boolean ativo,
        @Min(value = 1, message = "O nível deve ser maior ou igual a 1")
        Integer nivelMinimo
) {
}