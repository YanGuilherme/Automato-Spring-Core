package br.com.yan.automato.equivalencia.validacoes;

import br.com.yan.automato.enums.RespostaExec;
import br.com.yan.automato.equivalencia.dto.TesteEquivalencia;
import br.com.yan.automato.model.Automato;
import br.com.yan.automato.service.AutomatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class LinguagemValidator implements EquivalenceInterface{
    @Autowired
    AutomatoService automatoService;

    public Set<String> gerarLinguagem(String id1, String id2){
        Automato automato = automatoService.findById(id1);
        Set<Character> alfabeto = automato.getAlfabeto();
        Integer comprimento = automato.getEstados().size();
        Set<String> linguagem = new HashSet<>();

        gerarCombinacoes("", alfabeto, comprimento, linguagem);

        return linguagem;



    }

    private void gerarCombinacoes(String prefixo, Set<Character> alfabeto, int comprimento, Set<String> linguagem) {
        if (comprimento == 0) {
            linguagem.add(prefixo);
            return;
        }

        for (char c : alfabeto) {
            gerarCombinacoes(prefixo + c, alfabeto, comprimento - 1, linguagem);
        }
    }
    @Override
    public TesteEquivalencia validate(Automato automato1, Automato automato2) {
        Set<String> linguagem = gerarLinguagem(automato1.getId(), automato2.getId());
        for(String cadeia: linguagem){
            if(!automato1.validarProcessar(cadeia).equals(automato2.validarProcessar(cadeia))){
                return new TesteEquivalencia(false, "Falhou na cadeia: " + cadeia, "Reconhecem mesma linguagem?");
            }
        }
        return new TesteEquivalencia(true, "Sim", "Reconhecem mesma linguagem?");
    }
}
