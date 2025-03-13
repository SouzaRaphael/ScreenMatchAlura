package br.com.exaulra.ScreenMatch.repository;

import br.com.exaulra.ScreenMatch.models.Episodio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpisodioRepository extends JpaRepository<Episodio, Long> {}