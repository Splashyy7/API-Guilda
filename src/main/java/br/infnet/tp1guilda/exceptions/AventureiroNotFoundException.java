package br.infnet.tp1guilda.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AventureiroNotFoundException extends RuntimeException {

    public AventureiroNotFoundException(Long id) {
        super("Aventureiro com ID " + id + " não foi encontrado na guilda!");
    }
}