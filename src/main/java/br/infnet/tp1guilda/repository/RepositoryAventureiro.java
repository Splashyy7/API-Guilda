package br.infnet.tp1guilda.repository;

import br.infnet.tp1guilda.dto.FilterRequestAventureiro;
import br.infnet.tp1guilda.dto.PageResult;
import br.infnet.tp1guilda.domain.aventura.Aventureiro;

import java.util.List;
import java.util.Optional;

public interface RepositoryAventureiro {

    List<Aventureiro> findAll();

    PageResult<Aventureiro> findWithFilter(
            FilterRequestAventureiro filtro,
            int page,
            int size
    );

    Optional<Aventureiro> findById(Long id);

    Aventureiro save(Aventureiro aventureiro);
}