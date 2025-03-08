package br.com.exaulra.springcmd.main;

import br.com.exaulra.springcmd.models.*;
import br.com.exaulra.springcmd.repository.SerieRepository;
import br.com.exaulra.springcmd.services.ConsumoApi;
import br.com.exaulra.springcmd.services.ConverteDados;

import java.util.*;

public class Main {

    private final Scanner in = new Scanner(System.in);
    private final ConsumoApi consumo = new ConsumoApi();
    private final ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=6585022c";
    private final SerieRepository repositorioSerie;
    private List<Serie> series = new ArrayList<>();

    public Main(SerieRepository repositorioSerie) {
        this.repositorioSerie = repositorioSerie;
    }

    public void exibeMenu() {
        var opcao = -1;
        while(opcao != 0) {
            var menu = """
                    1 - Buscar séries
                    2 - Buscar episódios
                    3 - Listar séries buscadas
                    4 - Buscar série por nome
                    5 - Buscar séries por ator
                    6 - Top 5 séries
                    7 - Buscar séries por categoria
                    8 - Filtrar séries
                    9 - Buscar episódios por trecho
                    10 - Top 5 episódios por série
                    11 - Buscar episódios por data
                                    
                    0 - Sair                                 
                    """;

            System.out.println(menu);
            opcao = in.nextInt();
            in.nextLine();

            switch (opcao) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    listarSeriesBuscadas();
                    break;
                case 4:
                    buscarSeriePorTitulo();
                    break;
                case 5:
                    buscarSeriesPorAtor();
                    break;
                case 6:
                    buscarTop5Series();
                    break;
                case 7:
                    buscarSeriesPorCategoria();
                    break;
                case 8:
                    filtrarSeriesPorTemporadaEAvaliacao();
                    break;
                case 9:
                    buscarEpisodioPorTrecho();
                    break;
                case 10:
                    buscarTop5EpisodiosPorSerie();
                    break;
                case 11:
                    buscarEpisodiosPorData();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void buscarSerieWeb() {
        DadosSerie dados = getDadosSerie();
        Serie serie = new Serie(dados);
        // dadosSeries.add(dados);
        repositorioSerie.save(serie);
        System.out.println(dados);
    }

    private DadosSerie getDadosSerie() {
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = in.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        return conversor.obterDados(json, DadosSerie.class);
    }

    private void buscarEpisodioPorSerie() {
        listarSeriesBuscadas();
        System.out.print("Digite o nome de uma serie ja buscada: ");
        String nomeSerie = in.nextLine();

        Optional<Serie> serie = repositorioSerie.findByTituloContainingIgnoreCase(nomeSerie);

        if (serie.isPresent()) {
            Serie serieEncontrada = serie.get();
            List<DadosTemporada> temporadas = new ArrayList<>();

            for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++) {
                var json = consumo.obterDados(ENDERECO + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
                DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
                temporadas.add(dadosTemporada);
            }

            temporadas.forEach(System.out::println);

            List<Episodio> episodios = temporadas.stream()
                    .flatMap(d -> d.episodios().stream()
                            .map(e -> new Episodio(e.numeroEpisodio(), e)))
                    .toList();

            serieEncontrada.setEpisodios(episodios);
            repositorioSerie.save(serieEncontrada);
        } else {
            System.out.println("Serie nao localizada.");
        }
    }

    private void listarSeriesBuscadas(){
        this.series = repositorioSerie.findAll();
//        series = dadosSeries.stream()
//                .map(Serie::new)
//                .toList();
        this.series.stream()
                .sorted(Comparator.comparing(Serie::getTitulo))
                .forEach(System.out::println);
    }

    private void buscarSeriePorTitulo() {
        System.out.print("Digite o nome de uma serie ja buscada: ");
        String nomeSerie = in.nextLine();

        Optional<Serie> serieBuscada = repositorioSerie.findByTituloContainingIgnoreCase(nomeSerie);

        if (serieBuscada.isPresent()) {
            System.out.println("Dados da série: " + serieBuscada.get());
        } else {
            System.out.println("Série não encontrada.");
        }
    }

    private void buscarSeriesPorAtor() {
        System.out.print("Digite o nome de um ator: ");
        String nomeAtor = in.nextLine();
        System.out.print("Digite o número da avaliação: ");
        double avaliacao = in.nextDouble();

        List<Serie> seriesEncontradas = repositorioSerie.findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(nomeAtor, avaliacao);

        seriesEncontradas.forEach(System.out::println);
    }

    private void buscarTop5Series() {
        List<Serie> topSeries = repositorioSerie.findTop5ByOrderByAvaliacaoDesc();
        topSeries.forEach(System.out::println);
    }

    private void buscarSeriesPorCategoria() {
        System.out.print("Digite uma categoria: ");
        String nomeCategoria = in.nextLine();
        Categoria categoria = Categoria.fromPortugues(nomeCategoria);
        List<Serie> seriesCategoria = repositorioSerie.findByGenero(categoria);
        seriesCategoria.forEach(System.out::println);
    }

    private void filtrarSeriesPorTemporadaEAvaliacao(){
        System.out.println("Filtrar séries até quantas temporadas? ");
        var totalTemporadas = in.nextInt();
        in.nextLine();
        System.out.println("Com avaliação a partir de que valor? ");
        var avaliacao = in.nextDouble();
        in.nextLine();
//        List<Serie> filtroSeries = repositorioSerie.findByTotalTemporadasLessThanEqualAndAvaliacaoGreaterThanEqual(totalTemporadas, avaliacao);
        List<Serie> filtroSeries = repositorioSerie.seriesPorTemporadaEAvaliacao(totalTemporadas, avaliacao);
        System.out.println("*** Séries filtradas ***");
        filtroSeries.forEach(s ->
                System.out.println(s.getTitulo() + "  - avaliação: " + s.getAvaliacao()));
    }

    private void buscarEpisodioPorTrecho() {
        System.out.print("Digite o nome de uma episódio já buscado: ");
        String trechoEpisodio = in.nextLine();
        List<Episodio> episodiosEncontrados = repositorioSerie.episodiosPorTrecho(trechoEpisodio);
        episodiosEncontrados.forEach(System.out::println);
    }

    private void buscarTop5EpisodiosPorSerie() {
        System.out.print("Digite o nome de uma serie ja buscada: ");
        String nomeSerie = in.nextLine();
        Optional<Serie> serieBuscada = repositorioSerie.findByTituloContainingIgnoreCase(nomeSerie);

        if (serieBuscada.isPresent()) {
            Serie serieEncontrada = serieBuscada.get();
            List<Episodio> topEpisodios = repositorioSerie.top5EpisodiosPorSerie(serieEncontrada);
            topEpisodios.forEach(System.out::println);
        } else {
            System.out.println("Série não encontrada.");
        }
    }

    private void buscarEpisodiosPorData() {
        System.out.print("Digite o nome de uma serie ja buscada: ");
        String nomeSerie = in.nextLine();
        Optional<Serie> serieBuscada = repositorioSerie.findByTituloContainingIgnoreCase(nomeSerie);

        if (serieBuscada.isPresent()) {
            Serie serieEncontrada = serieBuscada.get();

            System.out.print("Digite o limite do ano de lançamento: ");
            int anoLancamento = in.nextInt();

            List<Episodio> episodiosAno = repositorioSerie.episodiosPorSerieEAno(serieEncontrada, anoLancamento);
            episodiosAno.forEach(System.out::println);
        } else {
            System.out.println("Série não encontrada.");
        }
    }
}