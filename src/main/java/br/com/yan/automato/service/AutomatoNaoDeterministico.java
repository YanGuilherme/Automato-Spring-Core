package br.com.yan.automato.service;

import br.com.yan.automato.model.Automato;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AutomatoNaoDeterministico extends Automato {

    private Map<String, Map<Character, Set<String>>> transicoes;

    public AutomatoNaoDeterministico() {
        super();
    }

    public AutomatoNaoDeterministico(String estadoInicial, Set<String> estadosAceitacao, Map<String, Map<Character, Set<String>>> transicoes) {
        super(estadoInicial, estadosAceitacao);
        this.transicoes = transicoes;
    }

    public Map<String, Map<Character, Set<String>>> getTransicoes() {
        return transicoes;
    }

    public void setTransicoes(Map<String, Map<Character, Set<String>>> transicoes) {
        this.transicoes = transicoes;
    }


    @Override
    public boolean aceitaCadeia(String cadeia) {
        String estadoFinal = processarCadeia(cadeia);
        return estadosAceitacao.contains(estadoFinal);
    }

    public String processarCadeia(String cadeia){
        boolean aceita = simularAFN(cadeia, estadoInicial, 0);
        return aceita ? "aceita" : "rejeita";
    }

    public boolean simularAFN(String cadeia, String estadoAtual, int index) {
        // Caso base: chegamos ao fim da cadeia
        if (cadeia.length() == index) {
            return this.estadosAceitacao.contains(estadoAtual);
        }

        final Character simbolo = cadeia.charAt(index);
        final Set<String> proximosEstados = transicoes.get(estadoAtual).get(simbolo);

        if (proximosEstados.isEmpty()) {
            return false; // Não há transição para o símbolo atual
        }

        // Verifica recursivamente cada possível próximo estado
        for (String proximoEstado : proximosEstados) {
            if (simularAFN(cadeia, proximoEstado, index + 1)) {
                return true; // Aceita se encontrar um caminho válido
            }
        }

        return false; // Se nenhum caminho leva a uma aceitação
    }



}
