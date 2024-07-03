package br.com.yan.automato.service;

import br.com.yan.automato.dto.AutomatoDto;
import br.com.yan.automato.model.Automato;
import br.com.yan.automato.repository.AutomatoRepository;
import br.com.yan.automato.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class AutomatoService {

    @Autowired
    private AutomatoRepository automatoRepository;

    public Automato salvarAutomato(AutomatoDto automatoDto){

        Automato automato = new Automato();
        automato.setEstados(automatoDto.getEstados());
        automato.setAlfabeto(automatoDto.getAlfabeto());
        automato.setEstado_inicial(automatoDto.getEstado_inicial());
        automato.setEstados_aceitacao(automatoDto.getEstados_aceitacao());
        automato.setTipo_automato(automatoDto.getTipo_automato());

        Map<Pair<String, Character>, Set<String>> transicoesMap = new HashMap<>();
        for(Map.Entry<String, Set<String>> entry: automatoDto.getTransicoes().entrySet()){
            String[] parts = entry.getKey().split("_");
            String estadoOrigem = parts[0];
            Character simbolo = parts[1].charAt(0);
            Set<String> estadoDestinos = entry.getValue();
            transicoesMap.put(new Pair<>(estadoOrigem,simbolo), estadoDestinos);
        }
        automato.setTransicoes(transicoesMap);

        return automatoRepository.save(automato);
    }

    public List<Automato> buscarTodosAutomatos(){
        return automatoRepository.findAll();
    }
}
