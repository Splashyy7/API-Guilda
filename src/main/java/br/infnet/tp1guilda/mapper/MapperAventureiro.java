package br.infnet.tp1guilda.mapper;

import org.springframework.stereotype.Component;
import br.infnet.tp1guilda.dto.CriarAventureiro;
import br.infnet.tp1guilda.domain.aventura.Aventureiro;
import br.infnet.tp1guilda.dto.ResponseAventureiro;

@Component
public class MapperAventureiro {
    public Aventureiro toEntity(CriarAventureiro dto) {
        return new Aventureiro(
                dto.nome(),
                dto.classe(),
                dto.nivel()
        );
    }

    public ResponseAventureiro toResponse(Aventureiro aventureiro) {
        return new ResponseAventureiro(
                aventureiro.getId(),
                aventureiro.getNome(),
                aventureiro.getClasse(),
                aventureiro.getNivel(),
                aventureiro.getAtivo()
        );
    }
}
