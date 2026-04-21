package br.infnet.tp1guilda.controllers;

import br.infnet.tp1guilda.dto.elastic.ContagemCampoAggregation;
import br.infnet.tp1guilda.dto.elastic.FaixaPreco;
import br.infnet.tp1guilda.dto.elastic.PrecoMedioAggregation;
import br.infnet.tp1guilda.dto.elastic.ProdutoResponse;
import br.infnet.tp1guilda.mapper.elastic.ProdutoDocumentMapper;
import br.infnet.tp1guilda.service.elastic.ProdutoDocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoElasticController {

    private final ProdutoDocumentService produtoDocumentService;

    @GetMapping("/busca/nome")
    public List<ProdutoResponse> buscarPorNome(@RequestParam("termo") String termo) {
        return produtoDocumentService.buscarPorNome(termo).stream()
                .map(ProdutoDocumentMapper::toResponse)
                .toList();
    }

    @GetMapping("/busca/descricao")
    public List<ProdutoResponse> buscarPorDescricao(@RequestParam("termo") String termo) {
        return produtoDocumentService.buscarPorDescricao(termo).stream()
                .map(ProdutoDocumentMapper::toResponse)
                .toList();
    }

    @GetMapping("/busca/frase")
    public List<ProdutoResponse> buscarPorFrase(@RequestParam("termo") String termo) {
        return produtoDocumentService.buscarPorFraseExata(termo).stream()
                .map(ProdutoDocumentMapper::toResponse)
                .toList();
    }

    @GetMapping("/busca/fuzzy")
    public List<ProdutoResponse> buscarPorFuzzy(@RequestParam("termo") String termo) {
        return produtoDocumentService.buscarPorNomeComTolerancia(termo).stream()
                .map(ProdutoDocumentMapper::toResponse)
                .toList();
    }

    @GetMapping("/busca/multicampos")
    public List<ProdutoResponse> buscarMulticampos(@RequestParam("termo") String termo) {
        return produtoDocumentService.buscarPorNomeEDescricao(termo).stream()
                .map(ProdutoDocumentMapper::toResponse)
                .toList();
    }

    @GetMapping("/busca/com-filtro")
    public List<ProdutoResponse> buscarComFiltro(
            @RequestParam("termo") String termo,
            @RequestParam("categoria") String categoria
    ) {
        return produtoDocumentService.buscarPorDescricaoECategoria(termo, categoria).stream()
                .map(ProdutoDocumentMapper::toResponse)
                .toList();
    }

    @GetMapping("/busca/faixa-preco")
    public List<ProdutoResponse> buscarPorFaixaPreco(
            @RequestParam("min") double min,
            @RequestParam("max") double max
    ) {
        return produtoDocumentService.buscarPorFaixaPreco(min, max).stream()
                .map(ProdutoDocumentMapper::toResponse)
                .toList();
    }

    @GetMapping("/busca/avancada")
    public List<ProdutoResponse> buscaAvancada(
            @RequestParam("categoria") String categoria,
            @RequestParam("raridade") String raridade,
            @RequestParam("min") double min,
            @RequestParam("max") double max
    ) {
        return produtoDocumentService.buscaCombinada(categoria, raridade, min, max).stream()
                .map(ProdutoDocumentMapper::toResponse)
                .toList();
    }

    @GetMapping("/agregacoes/por-categoria")
    public List<ContagemCampoAggregation> porCategoria() {
        return produtoDocumentService.quantidadeProdutosPorCampo("categoria");
    }

    @GetMapping("/agregacoes/por-raridade")
    public List<ContagemCampoAggregation> porRaridade() {
        return produtoDocumentService.quantidadeProdutosPorCampo("raridade");
    }

    @GetMapping("/agregacoes/preco-medio")
    public PrecoMedioAggregation precoMedio() {
        return produtoDocumentService.precoMedioProdutos();
    }

    @GetMapping("/agregacoes/faixas-preco")
    public List<FaixaPreco> faixasPreco() {
        return produtoDocumentService.agruparEmFaixaPreco();
    }
}
