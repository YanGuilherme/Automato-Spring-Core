package br.com.yan.automato.service;

import br.com.yan.automato.enums.RespostaExec;
import br.com.yan.automato.model.Automato;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.*;

@JsonTypeName("AFD")
public class AutomatoDeterministico extends Automato {
    private Map<String, Map<Character, String>> transicoes = new HashMap<>();
    private boolean minimized;

    public boolean isMinimized() {
        return minimized;
    }

    public void setMinimized(boolean minimized) {
        this.minimized = minimized;
    }

    public AutomatoDeterministico(){
        super();
    }

    public AutomatoDeterministico(String nome, String estadoInicial, Set<String> estadosDeAceitacao) {
        super(nome, estadoInicial, estadosDeAceitacao);
        this.transicoes = new HashMap<>();
    }

    public AutomatoDeterministico(String nome, String estadoInicial, Set<String> estadosDeAceitacao, Map<String, Map<Character, String>> transicoes) {
        super(nome, estadoInicial, estadosDeAceitacao);
        this.transicoes = transicoes != null ? new HashMap<>(transicoes) : new HashMap<>();
    }

    public Map<String, Map<Character, String>> getTransicoes() {
        return this.transicoes;
    }

    public void setTransicoes(Map<String, Map<Character, String>> transicoes) {
        this.transicoes = transicoes;
    }

    @Override
    public Set<Character> getAlfabeto() {
        String estado = getEstados().stream().findAny().orElse("Q1");
        return this.transicoes.get(estado).keySet();
    }

    @Override
    public Set<String> getEstados() {
        return new HashSet<>(this.transicoes.keySet());
    }

    public void adicionarTransicao(String estadoOrigem, char simbolo, String estadoDestino) {
        if (!transicoes.containsKey(estadoOrigem)) {
            transicoes.put(estadoOrigem, new HashMap<>());
        }
        transicoes.get(estadoOrigem).put(simbolo, estadoDestino);
    }

    @Override
    public RespostaExec testaCadeia(String cadeia) {
        try {
            String estadoFinal = processarCadeia(cadeia);
            return estadosAceitacao.contains(estadoFinal) ? RespostaExec.ACEITA : RespostaExec.REJEITA;
        } catch (IllegalArgumentException e) {
            return RespostaExec.REJEITA;
        }
    }

    public String processarCadeia(String cadeia) {
        String estadoAtual = this.estadoInicial;
        for (char simbolo : cadeia.toCharArray()) {
            Map<Character, String> transicao = transicoes.get(estadoAtual);
            if (transicao == null) {
                throw new IllegalArgumentException("Estado " + estadoAtual + " não possui transições definidas.");
            }
            estadoAtual = transicao.get(simbolo);
            if (estadoAtual == null) {
                throw new IllegalArgumentException("Não há transição definida para o símbolo " + simbolo + " no estado " + estadoAtual + ".");
            }
        }
        return estadoAtual;
    }



    public String getTransicao(String estado, char simbolo) {
        if (transicoes.containsKey(estado) && transicoes.get(estado).containsKey(simbolo)) {
            return transicoes.get(estado).get(simbolo);
        }
        return null;
    }
}

