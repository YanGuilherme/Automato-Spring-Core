package br.com.yan.automato.service;

import br.com.yan.automato.enums.RespostaExec;
import br.com.yan.automato.model.Automato;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Document(collection = "maquinasDeTuring")
@JsonTypeName("MT")
public class MaquinaDeTuring extends Automato {
    private Character x;
    private Character y;
    private Set<Character> alfabetoFita;
    private Map<String, Map<Character, TransicaoMT>> transicoes = new HashMap<>();

    private char[] fita;
    private int cabecote;



    @Override
    public Set<String> getEstados(){
        return transicoes.keySet();
    }

    @Override
    public Set<Character> getAlfabeto(){
        return this.alfabetoFita;
    }


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

    public MaquinaDeTuring(String estadoInicial, Set<String> estadosAceitacao, String nome, Character x, Character y, Set<Character> alfabetoFita, Map<String, Map<Character, TransicaoMT>> transicoes) {
        this.estadoInicial = estadoInicial;
        this.estadosAceitacao = estadosAceitacao;
        this.nome = nome;
        this.x = x;
        this.y = y;
        this.alfabetoFita = alfabetoFita;
        this.transicoes = transicoes;
    }



    public Set<String> getEstadosAceitacao() {
        return estadosAceitacao;
    }

    public void setEstadosAceitacao(Set<String> estadosAceitacao) {
        this.estadosAceitacao = estadosAceitacao;
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

    @Override
    public RespostaExec testaCadeia(String cadeia){
        return simular(cadeia);
    }

    @Override
    public String processarCadeia(String cadeia) {
        return null;
    }


    public RespostaExec validarProcessar(String cadeia){
        if(!cadeiaValida(cadeia)){
            return RespostaExec.CADEIA_INVALIDA;
        }
        return simular(cadeia);
    }

    private boolean cadeiaValida(String cadeia) {
        Set<Character> alfabeto = this.getAlfabetoFita();
        for(Character caractere : cadeia.toCharArray()){
            if (!alfabeto.contains(caractere)){
                return false;
            }
        }
        return true;
    }


    public RespostaExec simular(String entrada) {
        fita = new char[1000]; // Inicializa uma fita grande
        cabecote = 500; // Posiciona o cabeçote no meio da fita

        // Preenche toda a fita com o símbolo branco
        for (int i = 0; i < fita.length; i++) {
            fita[i] = '-'; // Símbolo de espaço em branco
        }

        // Inicializa a fita com a palavra de entrada
        for (int i = 0; i < entrada.length(); i++) {
            fita[cabecote + i] = entrada.charAt(i);
        }

        return processarSimulacao();
    }

    private RespostaExec processarSimulacao() {
        String estadoAtual = this.getEstadoInicial();

        while (true) {
            char simboloAtual = fita[cabecote];

            TransicaoMT transicao = buscarTransicao(estadoAtual, simboloAtual);

            if (transicao == null) {
                // Não há transição válida, a simulação falha ou para
                return RespostaExec.REJEITA;
            }

            // Aplicar a transição
            fita[cabecote] = transicao.getSimboloEscrito(); // Escreve o novo símbolo na fita
            estadoAtual = transicao.getEstadoDestino(); // Muda para o novo estado
            cabecote += transicao.getMovimento() == 'R' ? 1 : (transicao.getMovimento() == 'L' ? -1 : 0);

            // Checar se atingiu um estado final
            if (this.getEstadosAceitacao().contains(estadoAtual)) {
                return RespostaExec.ACEITA;
            }
        }
    }

    private TransicaoMT buscarTransicao(String estadoAtual, char simboloAtual) {
        Map<Character, TransicaoMT> transicoesEstado = this.getTransicoes().get(estadoAtual);
        if (transicoesEstado != null) {
            return transicoesEstado.get(simboloAtual);
        }
        return null;
    }
}

