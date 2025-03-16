package br.com.exaulra.ScreenMatch.repository;

import br.com.exaulra.ScreenMatch.models.Categoria;
import br.com.exaulra.ScreenMatch.models.Episodio;
import br.com.exaulra.ScreenMatch.models.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {
    Optional<Serie> findByTituloContainingIgnoreCase(String nomeSerie);
    List<Serie> findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(String nomeAtor, double avaliacao);
    List<Serie> findTop5ByOrderByAvaliacaoDesc();
    List<Serie> findByGenero(Categoria categoria);
    List<Serie> findByTotalTemporadasLessThanEqualAndAvaliacaoGreaterThanEqual(int numeroTemporadas, double avaliacao);
    @Query("select s from Serie s where s.totalTemporadas <= :numeroTemporadas and s.avaliacao >= :avaliacao")
    List<Serie> seriesPorTemporadaEAvaliacao(int numeroTemporadas, double avaliacao);
    @Query("select e from Serie s join s.episodios e where e.titulo ilike %:trechoEpisodio%")
    List<Episodio> episodiosPorTrecho(String trechoEpisodio);
    @Query("select e from Serie s join s.episodios e where s = :s order by e.avaliacao limit 5")
    List<Episodio> top5EpisodiosPorSerie(Serie s);
    @Query("select e from Serie s join s.episodios e where s = :serieEncontrada and year(e.dataLancamento) >= :anoLancamento")
    List<Episodio> episodiosPorSerieEAno(Serie serieEncontrada, int anoLancamento);
    @Query("SELECT s FROM Serie s " +
            "JOIN s.episodios e " +
            "GROUP BY s " +
            "ORDER BY MAX(e.dataLancamento) DESC LIMIT 5")
    List<Serie> encontrarEpisodiosMaisRecentes();
    @Query("select e from Serie s join s.episodios e where s.id = :id and e.temporada = :numeroTemporada")
    List<Episodio> episodiosPorTemporada(Long id, int numeroTemporada);
}
//    Palavras relativas à igualdade:
//
//    Is, para ver igualdades
//    Equals, para ver igualdades (essa palavra-chave e a anterior têm os mesmos princípios, e são mais utilizadas para a legibilidade do método).
//    IsNot, para checar desigualdades
//    IsNull, para verificar se um parâmetro é nulo
//    Palavras relativas à similaridade:
//
//    Containing, para palavras que contenham um trecho
//    StartingWith, para palavras que comecem com um trecho
//    EndingWith, para palavras que terminem com um trecho
//    Essas palavras podem ser concatenadas com outras condições, como o ContainingIgnoreCase, para não termos problemas de Case Sensitive.
//    Palavras relacionadas à comparação:
//
//    LessThan, para buscar registros menores que um valor
//    LessThanEqual, para buscar registros menores ou iguais a um valor
//    GreaterThan, para identificar registros maiores que um valor
//    GreaterThanEqual, para identificar registros maiores ou iguais a um valor
//    Between, para saber quais registros estão entre dois valores