package br.infnet.tp1guilda.service;

import br.infnet.tp1guilda.domain.aventura.Aventureiro;
import br.infnet.tp1guilda.repository.aventura.AventureiroRepository;
import br.infnet.tp1guilda.exceptions.AventureiroNotFoundException;
import br.infnet.tp1guilda.exceptions.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import br.infnet.tp1guilda.dto.aventureiro.AtualizarAventureiro;
import br.infnet.tp1guilda.dto.aventureiro.FilterRequestAventureiro;
import br.infnet.tp1guilda.dto.PaginatedView;
import br.infnet.tp1guilda.dto.companheiro.DefinirCompanheiro;
import br.infnet.tp1guilda.domain.aventura.Companheiro;

@Service
@RequiredArgsConstructor
public class AventureiroService {

    private final AventureiroRepository repositoryAventureiro;

    public Aventureiro criar(Aventureiro aventureiro) {
        return repositoryAventureiro.save(aventureiro);
    }

    public Aventureiro buscarPorId(Long id) {
        return repositoryAventureiro.findById(id)
                .orElseThrow(() -> new AventureiroNotFoundException(id));
    }

    public Aventureiro atualizar(Long id, AtualizarAventureiro update) {

        Aventureiro aventureiro = buscarPorId(id);

        if (update.nome() != null) {
            if (update.nome().isBlank()) {
                throw new BusinessException("O nome do aventureiro não pode ser vazio.");
            }
            aventureiro.alterarNome(update.nome());
        }

        if (update.classe() != null) {
            aventureiro.alterarClasse(update.classe());
        }

        if (update.nivel() != null) {
            aventureiro.alterarNivel(update.nivel());
        }

        return repositoryAventureiro.save(aventureiro);
    }

    public Aventureiro encerrarVinculo(Long id) {
        Aventureiro aventureiro = buscarPorId(id);

        if (!aventureiro.getAtivo()) {
            throw new BusinessException("O aventureiro já está inativo.");
        }

        aventureiro.encerrarVinculo();
        return repositoryAventureiro.save(aventureiro);
    }

    public Aventureiro recrutarNovamente(Long id) {
        Aventureiro aventureiro = buscarPorId(id);

        if (aventureiro.getAtivo()) {
            throw new BusinessException("O aventureiro já está ativo.");
        }

        aventureiro.recrutar();
        return repositoryAventureiro.save(aventureiro);
    }

    public Aventureiro removerCompanheiro(Long id) {
        Aventureiro aventureiro = buscarPorId(id);

        if (aventureiro.getCompanheiro() == null) {
            throw new BusinessException("O aventureiro não possui companheiro para remover.");
        }

        aventureiro.removerCompanheiro();
        return repositoryAventureiro.save(aventureiro);
    }

    public PaginatedView<Aventureiro> listar(FilterRequestAventureiro filtro, int page, int size) {
        return repositoryAventureiro.findWithFilter(filtro, page, size);
    }

    public Aventureiro definirCompanheiro(Long id, DefinirCompanheiro dto) {
        Aventureiro aventureiro = buscarPorId(id);

        Companheiro companheiro = new Companheiro(
                dto.nome(),
                dto.especie(),
                dto.lealdade()
        );

        aventureiro.definirCompanheiro(companheiro);

        return repositoryAventureiro.save(aventureiro);
    }
}