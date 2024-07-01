package br.com.yan.automato.dto;

import br.com.yan.automato.model.Automato;
import br.com.yan.automato.util.Pair;

import java.util.Map;
import java.util.Set;

public class AutomatoDto {
    private Set<String> estados;
    private Set<Character> alfabeto;
    private Map<Pair<String,Character>, String> transicoes;
    private String estado_inicial;
    private Set<String> estados_aceitacao;

    private String tipo_automato;
    private Long id;

    public AutomatoDto() {
        //construtor padrasso
    }

    public AutomatoDto(Automato automato){
        this.estados = automato.getEstados();
        this.alfabeto = automato.getAlfabeto();
        this.transicoes = automato.getTransicoes();
        this.estado_inicial = automato.getEstado_inicial();
        this.estados_aceitacao = automato.getEstados_aceitacao();
        this.tipo_automato = automato.getTipo_automato();
        this.id = automato.getId();
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
