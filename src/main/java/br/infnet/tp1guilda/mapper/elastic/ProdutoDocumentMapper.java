package br.infnet.tp1guilda.mapper.elastic;

import br.infnet.tp1guilda.domain.elastic.ProdutoLoja;
import br.infnet.tp1guilda.dto.elastic.FaixaPreco;
import br.infnet.tp1guilda.dto.elastic.ProdutoResponse;
import co.elastic.clients.elasticsearch._types.aggregations.RangeBucket;

public final class ProdutoDocumentMapper {

    private ProdutoDocumentMapper() {
    }

    public static ProdutoResponse toResponse(ProdutoLoja document) {
        return new ProdutoResponse(
                document.getNome(),
                document.getDescricao(),
                document.getCategoria(),
                document.getRaridade(),
                (double) document.getPreco()
        );
    }

    public static FaixaPreco bucketToFaixaPreco(RangeBucket bucket) {
        return new FaixaPreco(bucket.key(), bucket.docCount());
    }
}
