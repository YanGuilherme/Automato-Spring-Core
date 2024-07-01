package br.com.yan.automato.model;
import br.com.yan.automato.util.Pair;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Set;
import java.util.Map;

public class Automato {
    private Set<String> estados;
    private Set<Character> alfabeto;
    private Map<Pair<String,Character>, String> transicoes;
    private String estado_inicial;
    private Set<String> estados_aceitacao;

    private String tipo_automato;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Automato(Set<String> estados, Set<Character> alfabeto, Map<Pair<String, Character>, String> transicoes, String estado_inicial, Set<String> estados_aceitacao, String tipo_automato) {
        this.estados = estados;
        this.alfabeto = alfabeto;
        this.transicoes = transicoes;
        this.estado_inicial = estado_inicial;
        this.estados_aceitacao = estados_aceitacao;
        this.tipo_automato = tipo_automato;
    }

    public Automato() {
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

    public Map<Pair<String, Character>, String> getTransicoes() {
        return transicoes;
    }

    public void setTransicoes(Map<Pair<String, Character>, String> transicoes) {
        this.transicoes = transicoes;
    }

    public String getEstado_inicial() {
        return estado_inicial;
    }

    public void setEstado_inicial(String estado_inicial) {
        this.estado_inicial = estado_inicial;
    }

    public Set<String> getEstados_aceitacao() {
        return estados_aceitacao;
    }

    public void setEstados_aceitacao(Set<String> estados_aceitacao) {
        this.estados_aceitacao = estados_aceitacao;
    }

    public String getTipo_automato() {
        return tipo_automato;
    }

    public void setTipo_automato(String tipo_automato) {
        this.tipo_automato = tipo_automato;
    }

    public Long getId(){
        return id;
    }
}

