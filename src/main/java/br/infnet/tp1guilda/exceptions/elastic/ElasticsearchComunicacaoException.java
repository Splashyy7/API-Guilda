package br.infnet.tp1guilda.exceptions.elastic;

public class ElasticsearchComunicacaoException extends RuntimeException {

    public ElasticsearchComunicacaoException(String message) {
        super(message);
    }
}
