package br.com.yan.automato.service;

import br.com.yan.automato.enums.RespostaExec;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Document(collection = "maquinasDeTuring")
public class MaquinaDeTuring{
    @Id
    private String id;
    private String estadoInicial;
    private Set<String> estadosAceitacao = new HashSet<>();
    private String nome;
    private Character x;
    private Character y;
    private Set<Character> alfabetoFita;
    private Map<String, Map<Character, TransicaoMT>> transicoes = new HashMap<>();




    public static class TransicaoMT {
        private String estadoDestino;
        private char simboloEscrito;
        private char movimento;

        public TransicaoMT(String estadoDestino, char simboloEscrito, char movimento) {
            this.estadoDestino = estadoDestino;
            this.simboloEscrito = simboloEscrito;
            this.movimento = movimento;
        }
        public TransicaoMT(){

        }

        public String getEstadoDestino() {
            return estadoDestino;
        }

        public void setEstadoDestino(String estadoDestino) {
            this.estadoDestino = estadoDestino;
        }

        public char getSimboloEscrito() {
            return simboloEscrito;
        }

        public void setSimboloEscrito(char simboloEscrito) {
            this.simboloEscrito = simboloEscrito;
        }

        public char getMovimento() {
            return movimento;
        }

        public void setMovimento(char movimento) {
            this.movimento= movimento;
        }
    }

    public MaquinaDeTuring() {
    }

    public MaquinaDeTuring(String id, String estadoInicial, Set<String> estadosAceitacao, String nome, Character x, Character y, Set<Character> alfabetoFita, Map<String, Map<Character, TransicaoMT>> transicoes) {
        this.id = id;
        this.estadoInicial = estadoInicial;
        this.estadosAceitacao = estadosAceitacao;
        this.nome = nome;
        this.x = x;
        this.y = y;
        this.alfabetoFita = alfabetoFita;
        this.transicoes = transicoes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Character getX() {
        return x;
    }

    public void setX(Character x) {
        this.x = x;
    }

    public Character getY() {
        return y;
    }

    public void setY(Character y) {
        this.y = y;
    }

    public Set<Character> getAlfabetoFita() {
        return alfabetoFita;
    }

    public void setAlfabetoFita(Set<Character> alfabetoFita) {
        this.alfabetoFita = alfabetoFita;
    }

    public Map<String, Map<Character, TransicaoMT>> getTransicoes() {
        return transicoes;
    }

    public void setTransicoes(Map<String, Map<Character, TransicaoMT>> transicoes) {
        this.transicoes = transicoes;
    }
}

