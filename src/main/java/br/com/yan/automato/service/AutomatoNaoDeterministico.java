package br.com.yan.automato.service;

import br.com.yan.automato.enums.RespostaExec;
import br.com.yan.automato.model.Automato;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.springframework.data.annotation.Transient;

import java.util.*;

@JsonTypeName("AFN")
public class AutomatoNaoDeterministico extends Automato {

    @Transient
    private final String ACEITA = "ACEITA";
    @Transient
    private final String REJEITA = "REJEITA";

    private Map<String, Map<Character, Set<String>>> transicoes = new HashMap<>();

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
    public Set<String> getEstados() {
        return this.transicoes.keySet();
    }

    @Override
    public RespostaExec testaCadeia(String cadeia) {
        return processarCadeia(cadeia).equals(ACEITA) ? RespostaExec.ACEITA : RespostaExec.REJEITA;
    }

    @Override
    public Set<Character> getAlfabeto() {
        String estado = getEstados().stream().findAny().orElse("Q1");
        return this.transicoes.get(estado).keySet();
    }

    public String processarCadeia(String cadeia){

        return simularAFN(cadeia, estadoInicial, 0) ? ACEITA : REJEITA;
    }

    public boolean simularAFN(String cadeia, String estadoAtual, int index) {
        // Caso base: chegamos ao fim da cadeia
        if (cadeia.length() == index) {
            return this.estadosAceitacao.contains(estadoAtual);
        }

        final Character simbolo = cadeia.charAt(index);
        final Set<String> proximosEstados = transicoes.get(estadoAtual).get(simbolo);

        if (Objects.isNull(proximosEstados) || proximosEstados.isEmpty()) {
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
