package br.com.yan.automato.service;

import br.com.yan.automato.enums.RespostaExec;
import br.com.yan.automato.model.Automato;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.springframework.data.annotation.Transient;

import java.util.*;
import java.util.stream.Collectors;

@JsonTypeName("AFN")
public class AutomatoNaoDeterministico extends Automato {

    @Transient
    private final String ACEITA = "ACEITA";
    @Transient
    private final String REJEITA = "REJEITA";

    private Map<String, Map<Character, Set<String>>> transicoes = new HashMap<>();

    public AutomatoNaoDeterministico() {
        super();
    }

    public AutomatoNaoDeterministico(String nome, String estadoInicial, Set<String> estadosDeAceitacao){
        super(nome, estadoInicial, estadosDeAceitacao);
        this.transicoes = new HashMap<>();
    }


    public AutomatoNaoDeterministico(String nome, String estadoInicial, Set<String> estadosAceitacao, Map<String, Map<Character, Set<String>>> transicoes) {
        super(nome, estadoInicial, estadosAceitacao);
        this.transicoes = transicoes;
    }

    public Map<String, Map<Character, Set<String>>> getTransicoes() {
        return transicoes;
    }

    public void setTransicoes(Map<String, Map<Character, Set<String>>> transicoes) {
        this.transicoes = transicoes;
    }

    @Override
    public Set<String> getEstados() {
        return this.transicoes.keySet();
    }

    @Override
    public RespostaExec testaCadeia(String cadeia) {
        return processarCadeia(cadeia).equals(ACEITA) ? RespostaExec.ACEITA : RespostaExec.REJEITA;
    }

    @Override
    public Set<Character> getAlfabeto() {
        String estado = getEstados().stream().findAny().orElse("Q1");
        // Retorna o alfabeto sem o símbolo ε
        return this.transicoes.get(estado).keySet().stream()
                .filter(simbolo -> simbolo != 'ε')
                .collect(Collectors.toSet());
    }

    public void setEstados(Set<String> estados) {
        // Inicializa o mapa de transições para cada estado
        for (String estado : estados) {
            this.transicoes.putIfAbsent(estado, new HashMap<>());
        }
    }

    public void setAlfabeto(Set<Character> alfabeto) {
        // Remove o símbolo ε se existir
        alfabeto.remove('ε');

        // Atualiza o alfabeto em todas as transições existentes
        for (String estado : this.transicoes.keySet()) {
            Map<Character, Set<String>> transicoesDoEstado = this.transicoes.get(estado);
            for (Character simbolo : alfabeto) {
                transicoesDoEstado.putIfAbsent(simbolo, new HashSet<>());
            }
        }
    }
    public void adicionarTransicao(String estadoOrigem, Character simbolo, String estadoDestino) {
        this.transicoes
                .computeIfAbsent(estadoOrigem, k -> new HashMap<>())
                .computeIfAbsent(simbolo, k -> new HashSet<>())
                .add(estadoDestino);
    }
    public void adicionarEstadoDeAceitacao(String estado) {
        this.getEstadosAceitacao().add(estado);
    }



    public String processarCadeia(String cadeia) {
        return simularAFN(cadeia, estadoInicial, 0) ? ACEITA : REJEITA;
    }

    public boolean simularAFN(String cadeia, String estadoAtual, int index) {
        // Caso base: se o índice alcançou o final da cadeia
        if (index == cadeia.length()) {
            // Verifica se o estado atual é de aceitação
            if (this.estadosAceitacao.contains(estadoAtual)) {
                return true;
            }

            // Verificar se alguma transição epsilon leva a um estado de aceitação
            final Set<String> transicoesEpsilon = transicoes.get(estadoAtual).get('ε');
            if (transicoesEpsilon != null) {
                for (String proximoEstado : transicoesEpsilon) {
                    if (simularAFN(cadeia, proximoEstado, index)) {
                        return true;
                    }
                }
            }

            return false; // Se não for um estado de aceitação e não houver transições epsilon válidas
        }

        // Verificar e processar as transições de epsilon
        final Set<String> transicoesEpsilon = transicoes.get(estadoAtual).get('ε');
        if (transicoesEpsilon != null) {
            for (String proximoEstado : transicoesEpsilon) {
                if (simularAFN(cadeia, proximoEstado, index)) {
                    return true; // Aceita se uma transição epsilon leva a aceitação
                }
            }
        }

        // Caso contrário, processe o símbolo atual
        if (index < cadeia.length()) {
            final Character simbolo = cadeia.charAt(index);
            final Set<String> proximosEstados = transicoes.get(estadoAtual).get(simbolo);

            if (Objects.isNull(proximosEstados) || proximosEstados.isEmpty()) {
                return false; // Não há transição para o símbolo atual
            }

            // Verifica recursivamente cada possível próximo estado
            for (String proximoEstado : proximosEstados) {
                if (simularAFN(cadeia, proximoEstado, index + 1)) {
                    return true; // Aceita se encontrar um caminho válido
                }
            }
        }

        return false; // Se nenhum caminho leva a uma aceitação
    }


    public void adicionarTransicoes(String estadoOrigem, Map<Character, Set<String>> novasTransicoes) {
        // Verifica se o estado de origem já existe, se não, inicializa um novo mapa de transições
        this.transicoes.computeIfAbsent(estadoOrigem, k -> new HashMap<>());

        // Adiciona cada transição ao mapa de transições existente
        for (Map.Entry<Character, Set<String>> entrada : novasTransicoes.entrySet()) {
            Character simbolo = entrada.getKey();
            Set<String> estadosDestino = entrada.getValue();

            // Verifica se já existe uma transição para o símbolo, se não, inicializa um novo conjunto
            this.transicoes.get(estadoOrigem).computeIfAbsent(simbolo, k -> new HashSet<>()).addAll(estadosDestino);
        }
    }

    public void adicionarEstado(String estado) {
        // Inicializa o mapa de transições para o novo estado, se ainda não existir
        this.transicoes.putIfAbsent(estado, new HashMap<>());
    }





}
