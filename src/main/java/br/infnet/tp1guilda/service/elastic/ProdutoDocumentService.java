package br.infnet.tp1guilda.service.elastic;

import br.infnet.tp1guilda.domain.elastic.ProdutoLoja;
import br.infnet.tp1guilda.dto.elastic.ContagemCampoAggregation;
import br.infnet.tp1guilda.dto.elastic.FaixaPreco;
import br.infnet.tp1guilda.dto.elastic.PrecoMedioAggregation;
import br.infnet.tp1guilda.exceptions.elastic.ElasticsearchComunicacaoException;
import br.infnet.tp1guilda.mapper.elastic.ProdutoDocumentMapper;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.aggregations.AggregationRange;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.NumberRangeQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProdutoDocumentService {

    private static final String INDEX_NAME = "guilda_loja";

    private final ElasticsearchClient client;

    public List<ProdutoLoja> buscarPorNome(String nome) {
        return search(matchQuery("nome", nome)._toQuery());
    }

    public List<ProdutoLoja> buscarPorDescricao(String descricao) {
        return search(matchQuery("descricao", descricao)._toQuery());
    }

    public List<ProdutoLoja> buscarPorFraseExata(String descricaoExata) {
        return search(Query.of(q -> q.matchPhrase(m -> m
                .field("descricao")
                .query(descricaoExata)
        )));
    }

    public List<ProdutoLoja> buscarPorNomeComTolerancia(String nome) {
        return search(MatchQuery.of(q -> q
                .field("nome")
                .query(nome)
                .fuzziness("AUTO")
                .fuzzyTranspositions(true)
        )._toQuery());
    }

    public List<ProdutoLoja> buscarPorNomeEDescricao(String termo) {
        return search(Query.of(q -> q.multiMatch(m -> m
                .fields("nome", "descricao")
                .query(termo)
        )));
    }

    public List<ProdutoLoja> buscarPorDescricaoECategoria(String descricao, String categoria) {
        return search(Query.of(q -> q.bool(b -> b
                .must(matchQuery("descricao", descricao)._toQuery())
                .filter(termQuery("categoria", categoria)._toQuery())
        )));
    }

    public List<ProdutoLoja> buscarPorFaixaPreco(double min, double max) {
        return search(Query.of(q -> q.range(r -> r
                .number(numberRangeQuery("preco", min, max))
        )));
    }

    public List<ProdutoLoja> buscaCombinada(String categoria, String raridade, double min, double max) {
        return search(Query.of(q -> q.bool(b -> b.filter(
                termQuery("categoria", categoria)._toQuery(),
                termQuery("raridade", raridade)._toQuery(),
                Query.of(rq -> rq.range(r -> r.number(numberRangeQuery("preco", min, max))))
        ))));
    }

    public List<ContagemCampoAggregation> quantidadeProdutosPorCampo(String field) {
        String key = String.format("por_%s", field);

        try {
            SearchResponse<ProdutoLoja> response = client.search(s -> s
                    .index(INDEX_NAME)
                    .size(0)
                    .aggregations(key, a -> a.terms(t -> t.field(field))), ProdutoLoja.class
            );

            return response.aggregations().get(key).sterms().buckets().array().stream()
                    .map(bucket -> new ContagemCampoAggregation(field, bucket.key().stringValue(), bucket.docCount()))
                    .toList();
        } catch (IOException e) {
            throw new ElasticsearchComunicacaoException("Erro ao executar busca no Elasticsearch");
        }
    }

    public PrecoMedioAggregation precoMedioProdutos() {
        String key = "preco_medio";

        try {
            SearchResponse<ProdutoLoja> response = client.search(s -> s
                    .index(INDEX_NAME)
                    .size(0)
                    .aggregations(key, a -> a.avg(avg -> avg.field("preco"))), ProdutoLoja.class
            );

            Double value = response.aggregations().get(key).avg().value();
            return new PrecoMedioAggregation(value == null ? 0.0 : value);
        } catch (IOException e) {
            throw new ElasticsearchComunicacaoException("Erro ao executar busca no Elasticsearch");
        }
    }

    public List<FaixaPreco> agruparEmFaixaPreco() {
        String aggregationKey = "faixa_preco";
        List<AggregationRange> ranges = List.of(
                AggregationRange.of(a -> a.to(100.0).key("Abaixo de 100")),
                AggregationRange.of(a -> a.from(100.0).to(300.0).key("De 100 a 300")),
                AggregationRange.of(a -> a.from(300.0).to(700.0).key("De 300 a 700")),
                AggregationRange.of(a -> a.from(700.0).key("Acima de 700"))
        );

        try {
            SearchResponse<ProdutoLoja> response = client.search(s -> s
                    .index(INDEX_NAME)
                    .size(0)
                    .aggregations(aggregationKey, a -> a.range(r -> r.field("preco").ranges(ranges))), ProdutoLoja.class
            );

            return response.aggregations().get(aggregationKey).range().buckets().array().stream()
                    .map(ProdutoDocumentMapper::bucketToFaixaPreco)
                    .toList();
        } catch (IOException e) {
            throw new ElasticsearchComunicacaoException("Erro ao executar busca no Elasticsearch");
        }
    }

    private List<ProdutoLoja> search(Query query) {
        try {
            SearchResponse<ProdutoLoja> response = client.search(s -> s.index(INDEX_NAME).query(query), ProdutoLoja.class);
            return response.hits().hits().stream()
                    .map(Hit::source)
                    .filter(Objects::nonNull)
                    .toList();
        } catch (IOException e) {
            throw new ElasticsearchComunicacaoException("Erro ao executar busca no Elasticsearch");
        }
    }

    private MatchQuery matchQuery(String field, String value) {
        return MatchQuery.of(q -> q.field(field).query(value));
    }

    private NumberRangeQuery numberRangeQuery(String field, double min, double max) {
        return NumberRangeQuery.of(q -> q.field(field).gte(min).lte(max));
    }

    private TermQuery termQuery(String field, String value) {
        return TermQuery.of(q -> q.field(field).value(value));
    }
}
