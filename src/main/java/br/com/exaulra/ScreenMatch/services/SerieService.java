package br.com.exaulra.ScreenMatch.services;

import br.com.exaulra.ScreenMatch.dto.EpisodioDTO;
import br.com.exaulra.ScreenMatch.dto.SerieDTO;
import br.com.exaulra.ScreenMatch.models.Categoria;
import br.com.exaulra.ScreenMatch.models.Serie;
import br.com.exaulra.ScreenMatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SerieService {
    @Autowired
    private SerieRepository serieRepository;

    public List<SerieDTO> obterTodasAsSeries() {
        return converteDados(serieRepository.findAll());
    }

    public List<SerieDTO> obterTop5Series() {
        return converteDados(serieRepository.findTop5ByOrderByAvaliacaoDesc());
    }

    private List<SerieDTO> converteDados(List<Serie> series) {
        return series.stream()
                .map(s -> new SerieDTO(
                                s.getId(),
                                s.getTitulo(),
                                s.getTotalTemporadas(),
                                s.getAvaliacao(),
                                s.getGenero(),
                                s.getAtores(),
                                s.getPoster()
                        )
                )
                .toList();
    }

    public List<SerieDTO> obterLancamentos() {
        return converteDados(serieRepository.encontrarEpisodiosMaisRecentes());
    }

    public SerieDTO obterPorId(Long id) {
        Optional<Serie> serieBuscada = serieRepository.findById(id);
        if (serieBuscada.isPresent()) {
            Serie serieEncontrada = serieBuscada.get();
            return new SerieDTO(
                    serieEncontrada.getId(),
                    serieEncontrada.getTitulo(),
                    serieEncontrada.getTotalTemporadas(),
                    serieEncontrada.getAvaliacao(),
                    serieEncontrada.getGenero(),
                    serieEncontrada.getAtores(),
                    serieEncontrada.getPoster()  
            );
        }
        return null;
    }

    public List<SerieDTO> obterPorCategoria(String nomeCategoria) {
        Categoria categoria = Categoria.fromPortugues(nomeCategoria);
        return converteDados(serieRepository.findByGenero(categoria));
    }

    public List<EpisodioDTO> obterTemporadasSerie(Long id) {
        Optional<Serie> serieBuscada = serieRepository.findById(id);
        if (serieBuscada.isPresent()) {
            Serie serieEncontrada = serieBuscada.get();
            return serieEncontrada.getEpisodios().stream()
                    .map(sE -> new EpisodioDTO(sE.getTitulo(), sE.getTemporada(), sE.getNumeroEpisodio()))
                    .toList();
        }
        return null;
    }

    public List<EpisodioDTO> obterTemporadaPorNumero(Long id, int numeroTemporada) {
        return serieRepository.episodiosPorTemporada(id, numeroTemporada).stream()
                .map(e -> new EpisodioDTO(e.getTitulo(), e.getTemporada(), e.getNumeroEpisodio()))
                .toList();
    }

    public List<EpisodioDTO> obterTop5Episodios(Long id) {
        return serieRepository.top5EpisodiosPorSerie(serieRepository.findById(id).get()).stream()
                .map(e -> new EpisodioDTO(e.getTitulo(), e.getTemporada(), e.getNumeroEpisodio()))
                .toList();
    }
}