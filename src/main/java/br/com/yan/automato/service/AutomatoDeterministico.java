package br.com.yan.automato.service;

import br.com.yan.automato.model.Automato;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@JsonTypeName("AFD")
public class AutomatoDeterministico extends Automato {
    private Map<String, Map<Character, String>> transicoes = new HashMap<>();

    public AutomatoDeterministico(){
        super();

    }

    public AutomatoDeterministico(String estadoInicial, Set<String> estadoAceitacao, Map<String, Map<Character, String>> transicoes) {
        super(estadoInicial, estadoAceitacao);
        this.transicoes = transicoes;
    }

    public AutomatoDeterministico(String estadoInicial, Set<String> estadosDeAceitacao) {
        super(estadoInicial, estadosDeAceitacao);

    }

    public Map<String, Map<Character, String>> getTransicoes() {
        return transicoes;
    }

    public void setTransicoes(Map<String, Map<Character, String>> transicoes) {
        this.transicoes = transicoes;
    }


    public String processarCadeia(String cadeia) {
        String estadoAtual = this.estadoInicial;
        for (char simbolo : cadeia.toCharArray()) {
            estadoAtual = transicoes.get(estadoAtual).get(simbolo);
        }
        return estadoAtual;
    }

    @Override
    public Set<Character> getAlfabeto() {
        String estado = getEstados().stream().findAny().orElse("Q1");
        return this.transicoes.get(estado).keySet();
    }
    @Override
    public boolean aceitaCadeia(String cadeia) {
        String estadoFinal = processarCadeia(cadeia);
        return estadosAceitacao.contains(estadoFinal);
    }

    @Override
    public Set<String> getEstados() {
        return this.transicoes.keySet();
    }

    public void adicionarTransicao(String estadoOrigem, char simbolo, String estadoDestino) {
        if (!transicoes.containsKey(estadoOrigem)) {
            transicoes.put(estadoOrigem, new HashMap<>());
        }

        transicoes.get(estadoOrigem).put(simbolo, estadoDestino);
    }
}
