package br.com.yan.automato.equivalencia.validacoes;

import br.com.yan.automato.equivalencia.dto.TesteEquivalencia;
import br.com.yan.automato.model.Automato;

public interface EquivalenceInterface {

    TesteEquivalencia validate(Automato automato1, Automato automato2);

}
