package br.com.yan.automato.repository;

import br.com.yan.automato.model.Automato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface AutomatoRepository extends JpaRepository<Automato, Long>{
}
