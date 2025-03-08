package br.com.exaulra.springcmd.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "episodios")
public class Episodio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private int temporada;
    private int numeroEpisodio;
    private LocalDate dataLancamento;
    private double avaliacao;
    @ManyToOne
    private Serie serie;

    public Episodio() {}

    public Episodio(int numeroTemporada, DadosEpisodio dadosEpisodio) {
        this.temporada = numeroTemporada;

        this.titulo = dadosEpisodio.titulo();
        this.numeroEpisodio = dadosEpisodio.numeroEpisodio();

        if (!dadosEpisodio.dataLancamento().equalsIgnoreCase("N/A"))
            this.dataLancamento = LocalDate.parse(dadosEpisodio.dataLancamento());

        if (!dadosEpisodio.avaliacao().equalsIgnoreCase("N/A"))
            this.avaliacao = Double.parseDouble(dadosEpisodio.avaliacao());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getTemporada() {
        return temporada;
    }

    public void setTemporada(int temporada) {
        this.temporada = temporada;
    }

    public int getNumeroEpisodio() {
        return numeroEpisodio;
    }

    public void setNumeroEpisodio(int numeroEpisodio) {
        this.numeroEpisodio = numeroEpisodio;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    @Override
    public String toString() {
        return "Episodio{" +
                "titulo='" + titulo + '\'' +
                ", temporada=" + temporada +
                ", numeroEpisodio=" + numeroEpisodio +
                ", dataLancamento=" + dataLancamento +
                ", avaliacao=" + avaliacao +
                '}';
    }
}