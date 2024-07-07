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
    private Map<String, Set<String >> transicoes;
    private String estado_inicial;
    private Set<String> estados_aceitacao;
    private Long id;
    private Tipo tipo_automato;

    public AutomatoDto() {
        //construtor padrasso
    }

    public AutomatoDto(Automato automato){
        this.estados = automato.getEstados();
        this.alfabeto = automato.getAlfabeto();
        this.transicoes = new HashMap<>();

        for (Map.Entry<Pair<String, Character>, Set<String>> entry : automato.getTransicoes().entrySet()) {
            String estadoOrigem = entry.getKey().getFirst();
            Character simbolo = entry.getKey().getSecond();
            Set<String> estadosDestino = entry.getValue();

            // Verifica se já existe uma entrada para o estado de origem
            if (!this.transicoes.containsKey(estadoOrigem)) {
                this.transicoes.put(estadoOrigem, new HashSet<>());
            }

            // Adiciona o estado de destino à lista de estados para o estado de origem e símbolo
            for(String estadoDestino : estadosDestino){
                this.transicoes.get(estadoOrigem).add(estadoDestino);
            }
        }
        this.estado_inicial = automato.getEstado_inicial();
        this.estados_aceitacao = automato.getEstados_aceitacao();
        this.id = automato.getId();
        this.tipo_automato = automato.getTipo_automato();
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

    public Map<String, Set<String>> getTransicoes() {
        return transicoes;
    }

    public void setTransicoes(Map<String , Set<String>> transicoes) {
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

    public Tipo getTipo_automato() {
        return tipo_automato;
    }

    public void setTipo_automato(Tipo tipo_automato) {
        this.tipo_automato = tipo_automato;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
