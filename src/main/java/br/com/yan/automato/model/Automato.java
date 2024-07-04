package br.com.yan.automato.model;
import br.com.yan.automato.util.Pair;
import jakarta.persistence.*;

import java.util.Set;
import java.util.Map;

@Entity
@Table(name = "automatos")
public class Automato {

    @ElementCollection
    @CollectionTable(name = "automato_estados", joinColumns = @JoinColumn(name = "automato_id"))
    @Column(name = "estado")
    private Set<String> estados;
    @ElementCollection
    @CollectionTable(name = "automato_alfabeto", joinColumns = @JoinColumn(name = "automato_id"))
    @Column(name = "simbolo")
    private Set<Character> alfabeto;
    @ElementCollection
    @CollectionTable(name = "automato_transicoes", joinColumns = @JoinColumn(name = "automato_id"))
    @MapKeyClass(Pair.class)
    @MapKeyColumn(name = "estado_simbolo")
    @Column(name = "estado_destino")
    private Map<Pair<String,Character>, Set<String>> transicoes;
    @Column(name = "estado_inicial")
    private String estado_inicial;
    @ElementCollection
    @CollectionTable(name = "automato_estados_aceitacao", joinColumns = @JoinColumn(name = "automato_id"))
    @Column(name = "estado_aceitacao")
    private Set<String> estados_aceitacao;
    @Column(name = "tipo_automato")
    private String tipo_automato;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Automato(Set<String> estados, Set<Character> alfabeto, Map<Pair<String, Character>, Set<String>> transicoes, String estado_inicial, Set<String> estados_aceitacao, String tipo_automato) {
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

    public Map<Pair<String, Character>, Set<String>> getTransicoes() {
        return transicoes;
    }

    public void setTransicoes(Map<Pair<String, Character>, Set<String>> transicoes) {
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

