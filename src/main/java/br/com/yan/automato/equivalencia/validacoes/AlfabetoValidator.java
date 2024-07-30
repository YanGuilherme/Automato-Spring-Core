package br.com.yan.automato.equivalencia.validacoes;

import br.com.yan.automato.equivalencia.dto.TesteEquivalencia;
import br.com.yan.automato.model.Automato;
import org.springframework.stereotype.Component;

@Component
public class AlfabetoValidator implements EquivalenceInterface {

    @Override
    public TesteEquivalencia validate(Automato automato1, Automato automato2) {
        boolean alfabetoIgual = automato1.getAlfabeto().equals(automato2.getAlfabeto());
        String message = alfabetoIgual ? "Sim": "Alfabetos diferentes";
        return new TesteEquivalencia(alfabetoIgual, message, "Alfabetos são iguais?");
    }

}
