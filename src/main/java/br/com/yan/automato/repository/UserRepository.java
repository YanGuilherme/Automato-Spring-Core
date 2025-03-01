package br.com.yan.automato.repository;

import br.com.yan.automato.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends MongoRepository<User, String> { //tipo da entidade e tipo da chave primaria
    UserDetails findByEmail(String email);
}
