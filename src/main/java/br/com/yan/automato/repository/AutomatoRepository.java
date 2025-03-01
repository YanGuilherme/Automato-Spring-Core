package br.com.yan.automato.repository;

import br.com.yan.automato.model.Automato;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutomatoRepository extends MongoRepository<Automato, String> {
    @Query("{ '_class': { $in: ['br.com.yan.automato.service.AutomatoDeterministico', 'br.com.yan.automato.service.AutomatoNaoDeterministico'] } }")
    List<Automato> findAll();
    List<Automato> findByUserId(String userId);
}

