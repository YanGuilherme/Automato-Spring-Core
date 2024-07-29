package br.com.yan.automato.repository;

import br.com.yan.automato.model.GrupoAutomato;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GrupoAutomatoRepository extends MongoRepository<GrupoAutomato, String> {
    List<GrupoAutomato> findAll();
}
