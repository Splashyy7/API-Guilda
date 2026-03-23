package br.infnet.tp1guilda.dto;

import br.infnet.tp1guilda.enums.Especie;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

public record DefinirCompanheiro(
        @NotBlank(message = "O nome não pode ser vazio")
        String nome,
        @NotNull(message = "A espécie é obrigatória")
        Especie especie,
        @NotNull
        @Range(min = 0, max = 100, message = "A lealdade deve ser um inteiro entre 0 e 100")
        Integer lealdade
) {
}