package br.com.exaulra.ScreenMatch.controller;

import br.com.exaulra.ScreenMatch.dto.EpisodioDTO;
import br.com.exaulra.ScreenMatch.dto.SerieDTO;
import br.com.exaulra.ScreenMatch.services.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/series")
public class SerieController {

    @Autowired
    private SerieService serieService;

    @GetMapping
    public List<SerieDTO> obterSeries() {
        return serieService.obterTodasAsSeries();
    }

    @GetMapping("/top5")
    public List<SerieDTO> obterTop5Series() {
        return serieService.obterTop5Series();
    }

    @GetMapping("/lancamentos")
    public List<SerieDTO> obterLancamentos() {
        return serieService.obterLancamentos();
    }

    @GetMapping("/categoria/{nomeCategoria}")
    public List<SerieDTO> obterPorCategoria(@PathVariable String nomeCategoria) {
        return serieService.obterPorCategoria(nomeCategoria);
    }

    @GetMapping("/{id}")
    public SerieDTO obterPorId(@PathVariable Long id) {
        return serieService.obterPorId(id);
    }

    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodioDTO> obterEpisodiosSerie(@PathVariable Long id) {
        return serieService.obterTemporadasSerie(id);
    }

    @GetMapping("/{id}/temporadas/{numeroTemporada}")
    public List<EpisodioDTO> obterEpisodiosSerie(@PathVariable Long id, @PathVariable int numeroTemporada) {
        return serieService.obterTemporadaPorNumero(id, numeroTemporada);
    }

    @GetMapping("/{id}/temporadas/top")
    public List<EpisodioDTO> obterTop5Episodios(@PathVariable Long id) {
        return serieService.obterTop5Episodios(id);
    }
}