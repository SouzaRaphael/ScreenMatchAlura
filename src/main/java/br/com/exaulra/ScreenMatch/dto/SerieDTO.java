package br.com.exaulra.ScreenMatch.dto;

import br.com.exaulra.ScreenMatch.models.Categoria;

public record SerieDTO(
        Long id,
        String titulo,
        Integer totalTemporadas,
        Double avaliacao,
        Categoria genero,
        String atores,
        String poster
) {}