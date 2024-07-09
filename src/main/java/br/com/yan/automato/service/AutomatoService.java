package br.com.yan.automato.service;

import br.com.yan.automato.exception.NotFoundException;
import br.com.yan.automato.model.Automato;
import br.com.yan.automato.repository.AutomatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class AutomatoService {

    @Autowired
    public AutomatoRepository repository;

    public void deleteAll() {
        repository.deleteAll();
    }

    public List<Automato> findAll(){
        return repository.findAll();
    }

    public Automato save(Automato automato){
        return repository.save(automato);
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }

    public Automato findById(String automatoId) {
        return repository.findById(automatoId).orElseThrow(
                ()-> new NotFoundException(String.format("Automato com ID %s não encontrado.", automatoId))
        );
    }
}


