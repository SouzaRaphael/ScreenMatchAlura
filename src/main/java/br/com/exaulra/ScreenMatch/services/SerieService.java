package br.com.exaulra.ScreenMatch.services;

import br.com.exaulra.ScreenMatch.dto.SerieDTO;
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
}