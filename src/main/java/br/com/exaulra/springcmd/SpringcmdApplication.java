package br.com.exaulra.springcmd;

import br.com.exaulra.springcmd.main.Main;
import br.com.exaulra.springcmd.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringcmdApplication implements CommandLineRunner {

	@Autowired
	private SerieRepository repositorioSerie;

	public static void main(String[] args) {
		SpringApplication.run(SpringcmdApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Main main = new Main(repositorioSerie);
		main.exibeMenu();
	}
}