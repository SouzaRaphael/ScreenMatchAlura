package br.com.exaulra.springcmd.models;

public enum Categoria {
    ACAO("Action", "Ação"),
    ANIMACAO("Animation", "Animação"),
    COMEDIA("Comedy", "Comédia"),
    CRIME("Crime", "Crime"),
    DRAMA("Drama", "Drama"),
    ROMANCE("Romance", "Romance");

    private String categoriaOmdb;
    private String categoria;

    Categoria(String categoriaOmdb, String categoria){
        this.categoriaOmdb = categoriaOmdb;
        this.categoria = categoria;
    }

    public static Categoria fromString(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaOmdb.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
    }

    public static Categoria fromPortugues(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoria.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
    }
}