package br.com.yan.automato.model;
import br.com.yan.automato.dto.AutomatoDto;
import br.com.yan.automato.dto.Tipo;
import br.com.yan.automato.service.AutomatoDeterministico;
import br.com.yan.automato.service.AutomatoNaoDeterministico;
import br.com.yan.automato.util.Pair;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "tipo")
@JsonSubTypes({
        @JsonSubTypes.Type(value = AutomatoDeterministico.class, name = "AFD"),
        @JsonSubTypes.Type(value = AutomatoNaoDeterministico.class, name = "AFN")
})
public abstract class Automato {

    protected String estadoInicial;
    private Tipo tipo_automato;
    private Set<String> estados;
    private Set<Character> alfabeto;
    protected Set<String> estadosAceitacao;



    public abstract boolean aceitaCadeia(String cadeia);

    public abstract String processarCadeia(String cadeia);

    public Automato(Set<String> estados, Set<Character> alfabeto,  String estadoInicial, Set<String> estadosAceitacao, Tipo tipo_automato) {
        this.estados = estados;
        this.alfabeto = alfabeto;
        this.estadoInicial = estadoInicial;
        this.estadosAceitacao = estadosAceitacao;
        this.tipo_automato = tipo_automato;
    }

    public Automato() {
    }

    public Automato(Automato automato){
        this.estados = automato.estados;
        this.alfabeto = automato.alfabeto;
        this.estadoInicial = automato.estadoInicial;
        this.estadosAceitacao = automato.estadosAceitacao;
        this.tipo_automato = automato.tipo_automato;
    }

    public Automato(AutomatoDto automatoDto) {
        this.estados = automatoDto.getEstados();
        this.alfabeto = automatoDto.getAlfabeto();
        this.estadoInicial = automatoDto.getEstadoInicial();
        this.estadosAceitacao = automatoDto.getEstadosAceitacao();
        this.tipo_automato = automatoDto.getTipo_automato();
    }

    public Automato(String estadoInicial, Set<String> estadoAceitacao) {
        this.estadoInicial = estadoInicial;
        this.estadosAceitacao = estadoAceitacao;
    }

    public String getEstadoInicial() {
        return estadoInicial;
    }

    public void setEstadoInicial(String estadoInicial) {
        this.estadoInicial = estadoInicial;
    }

    public Tipo getTipo_automato() {
        return tipo_automato;
    }

    public void setTipo_automato(Tipo tipo_automato) {
        this.tipo_automato = tipo_automato;
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

    public Set<String> getEstadosAceitacao() {
        return estadosAceitacao;
    }

    public void setEstadosAceitacao(Set<String> estadosAceitacao) {
        this.estadosAceitacao = estadosAceitacao;
    }
}

