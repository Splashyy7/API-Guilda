package br.infnet.tp1guilda.dto;

import java.util.List;

public record PageResult<T>(
        int page,
        int size,
        int total,
        int totalPages,
        List<T> content
) {
    public PageResult(int page, int size, int total, List<T> content) {
        this(
                page,
                Math.max(size, 1),
                total,
                (int) Math.ceil((double) total / Math.max(size, 1)),
                content
        );
    }
}