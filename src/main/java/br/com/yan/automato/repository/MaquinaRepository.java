package br.com.yan.automato.repository;

import br.com.yan.automato.service.MaquinaDeTuring;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaquinaRepository extends MongoRepository<MaquinaDeTuring, String> {
}
