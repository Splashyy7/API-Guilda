package br.infnet.tp1guilda.repository;

import br.infnet.tp1guilda.dto.FilterRequestAventureiro;
import br.infnet.tp1guilda.dto.PageResult;
import br.infnet.tp1guilda.enums.Classe;
import br.infnet.tp1guilda.domain.aventura.Aventureiro;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class FakeRepository implements RepositoryAventureiro {

    private final List<Aventureiro> aventureiros = new ArrayList<>();
    private Long nextId = 1L;

    public FakeRepository() {
        Classe[] classes = Classe.values();

        for (int i = 1; i <= 100; i++) {
            Classe classe = classes[(i - 1) % classes.length];
            int nivel = (i % 20) + 1;

            Aventureiro aventureiro = new Aventureiro(
                    "Aventureiro " + i,
                    classe,
                    nivel
            );

            aventureiro.setId(nextId++);

            if (i % 4 == 0) {
                aventureiro.encerrarVinculo();
            }

            aventureiros.add(aventureiro);
        }
    }

    @Override
    public List<Aventureiro> findAll() {
        return new ArrayList<>(aventureiros);
    }

    @Override
    public PageResult<Aventureiro> findWithFilter(FilterRequestAventureiro filtro, int page, int size) {
        List<Aventureiro> filtrados = aventureiros.stream()
                .filter(aventureiro -> filtro == null || filtro.classe() == null || aventureiro.getClasse().equals(filtro.classe()))
                .filter(aventureiro -> filtro == null || filtro.ativo() == null || aventureiro.getAtivo().equals(filtro.ativo()))
                .filter(aventureiro -> filtro == null || filtro.nivelMinimo() == null || aventureiro.getNivel() >= filtro.nivelMinimo())
                .toList();

        int total = filtrados.size();
        int inicio = page * size;

        if (inicio >= total) {
            return new PageResult<>(page, size, total, List.of());
        }

        int fim = Math.min(inicio + size, total);
        List<Aventureiro> pagina = filtrados.subList(inicio, fim);

        return new PageResult<>(page, size, total, pagina);
    }

    @Override
    public Optional<Aventureiro> findById(Long id) {
        return aventureiros.stream()
                .filter(aventureiro -> aventureiro.getId().equals(id))
                .findFirst();
    }

    @Override
    public Aventureiro save(Aventureiro aventureiro) {
        if (aventureiro.getId() == null) {
            aventureiro.setId(nextId++);
            aventureiros.add(aventureiro);
            return aventureiro;
        }

        for (int i = 0; i < aventureiros.size(); i++) {
            if (aventureiros.get(i).getId().equals(aventureiro.getId())) {
                aventureiros.set(i, aventureiro);
                return aventureiro;
            }
        }

        aventureiros.add(aventureiro);
        return aventureiro;
    }
}