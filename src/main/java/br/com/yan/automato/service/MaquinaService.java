package br.com.yan.automato.service;

import br.com.yan.automato.enums.RespostaExec;
import br.com.yan.automato.repository.MaquinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaquinaService {

    @Autowired
    MaquinaRepository maquinaRepository;

    public List<MaquinaDeTuring> findAll(){
        return maquinaRepository.findAll();
    }
    public List<MaquinaDeTuring> findByUserId(String userId) {return maquinaRepository.findByUserId(userId);}

    public MaquinaDeTuring save(MaquinaDeTuring maquinaDeTuring){
        return maquinaRepository.save(maquinaDeTuring);
    }
    public MaquinaDeTuring findById(String id){
        return maquinaRepository.findById(id).orElse(null);
    }

    public void deleteById(String id) {
        maquinaRepository.deleteById(id);
    }

}
