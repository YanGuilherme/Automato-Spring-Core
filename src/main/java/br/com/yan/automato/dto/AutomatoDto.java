package br.com.yan.automato.dto;

import br.com.yan.automato.model.Automato;
import br.com.yan.automato.util.Pair;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AutomatoDto {
    private Set<String> estados;
    private Set<Character> alfabeto;
    private Map<String, Map<Character, Set<String>>> transicoes;
    private String estadoInicial;
    private Set<String> estadosAceitacao;
    private Tipo tipo_automato;

    public AutomatoDto(String estadoInicial, Set<String> estadosAceitacao) {
        //construtor padrasso
        this.estadoInicial = estadoInicial;
        this.estadosAceitacao = estadosAceitacao;
    }

    public AutomatoDto(Automato automato, String estadoInicial, Set<String> estadosAceitacao){
        this.estados = automato.getEstados();
        this.alfabeto = automato.getAlfabeto();
        this.estadoInicial = estadoInicial;
        this.estadosAceitacao = estadosAceitacao;
        this.tipo_automato = automato.getTipo_automato();
    }

    public String getEstadoInicial() {
        return estadoInicial;
    }

    public void setEstadoInicial(String estadoInicial) {
        this.estadoInicial = estadoInicial;
    }

    public Set<String> getEstadosAceitacao() {
        return estadosAceitacao;
    }

    public void setEstadosAceitacao(Set<String> estadosAceitacao) {
        this.estadosAceitacao = estadosAceitacao;
    }

    public Set<String> getEstados() {
        return estados;
    }

    public void setEstados(Set<String> estados) {
        this.estados = estados;
    }

    public Set<Character> getAlfabeto() {
        return alfabeto;
    }

    public void setAlfabeto(Set<Character> alfabeto) {
        this.alfabeto = alfabeto;
    }

    public Map<String, Map<Character, Set<String>>> getTransicoes() {
        return transicoes;
    }

    public void setTransicoes(Map<String, Map<Character, Set<String>>> transicoes) {
        this.transicoes = transicoes;
    }

    public Tipo getTipo_automato() {
        return tipo_automato;
    }

    public void setTipo_automato(Tipo tipo_automato) {
        this.tipo_automato = tipo_automato;
    }


}
