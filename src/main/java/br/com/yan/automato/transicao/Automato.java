package br.com.yan.automato.transicao;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Set;


@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "tipo")
@JsonSubTypes({
        @JsonSubTypes.Type(value = AutomatoDeterministico.class, name = "AFD"),
        @JsonSubTypes.Type(value = AutomatoNaoDeterministico.class, name = "AFN")
})
public abstract class Automato {

    private Tipo tipo;
    protected String estadoInicial;
    protected Set<String> estadosAceitacao;

    public Automato(String estadoInicial, Set<String> estadosAceitacao) {
        this.estadoInicial = estadoInicial;
        this.estadosAceitacao = estadosAceitacao;
    }

    public Automato() {

    }

    public abstract boolean aceitaCadeia(String cadeia);

    public abstract String processarCadeia(String cadeia);

    // Getters e Setters
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

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }
}
