package br.com.exaulra.springcmd.repository;

import br.com.exaulra.springcmd.models.Episodio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpisodioRepository extends JpaRepository<Episodio, Long> {}