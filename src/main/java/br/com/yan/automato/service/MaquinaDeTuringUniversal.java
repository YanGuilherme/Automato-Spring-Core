package br.com.yan.automato.service;

import br.com.yan.automato.enums.RespostaExec;

import java.util.Map;
import java.util.Set;

public class MaquinaDeTuringUniversal extends MaquinaDeTuring {

    private char[] fita;
    private int cabecote;

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
