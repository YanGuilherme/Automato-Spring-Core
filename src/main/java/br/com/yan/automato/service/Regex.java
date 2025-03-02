package br.com.yan.automato.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class Regex {

    @Autowired
    private AutomatoService automatoService;

    public AutomatoNaoDeterministico gerarAFN(String expressaoRegular) {
        // Limpa a expressão removendo quebras de linha, espaços em branco extras e barras invertidas
        String expressaoLimpa = expressaoRegular.replaceAll("\\s+", "").replace("\\", "");
        // Insere concatenação implícita
        String expressaoComConcatenacao = adicionarConcatenacaoImplicita(expressaoLimpa);
        return construirAFN(expressaoComConcatenacao);
    }

    // Este método insere concatenações implícitas na expressão regular
    private String adicionarConcatenacaoImplicita(String expressao) {
        StringBuilder resultado = new StringBuilder();
        for (int i = 0; i < expressao.length(); i++) {
            char atual = expressao.charAt(i);
            resultado.append(atual);

            // Se o caractere atual for uma letra, número ou fechamento de parênteses
            if (i + 1 < expressao.length()) {
                char proximo = expressao.charAt(i + 1);

                if ((Character.isLetterOrDigit(atual) || atual == ')' || atual == ']' || atual == '}') &&
                        (Character.isLetterOrDigit(proximo) || proximo == '(' || proximo == '[' || proximo == '{')) {
                    // Insere um operador de concatenação implícito
                    resultado.append('.');
                }
            }
        }
        return resultado.toString();
    }

    private boolean isOperador(char c) {
        return c == '*' || c == '|' || c == '.' || c == '+';
    }

    private int precedencia(char operador) {
        return switch (operador) {
            case '*' -> 3;
            case '.' -> 2;
            case '|', '+' -> 1; // Trata '+' com a mesma precedência do '|'
            default -> 0;
        };
    }


    private boolean isParentesesCorrespondente(char abertura, char fechamento) {
        return (abertura == '(' && fechamento == ')') ||
                (abertura == '[' && fechamento == ']') ||
                (abertura == '{' && fechamento == '}');
    }


    private AutomatoNaoDeterministico construirAFN(String expressaoRegular) {
        if (expressaoRegular.isEmpty()) {
            String estadoInicial = "q0";
            Set<String> estadosAceitacao = new HashSet<>();
            estadosAceitacao.add(estadoInicial);
            return new AutomatoNaoDeterministico("AFN Vazio", estadoInicial, estadosAceitacao);
        }

        Stack<AutomatoNaoDeterministico> afnStack = new Stack<>();
        Stack<Character> operadorStack = new Stack<>();
        Set<Character> alfabeto = new HashSet<>();

        for (int i = 0; i < expressaoRegular.length(); i++) {
            char simbolo = expressaoRegular.charAt(i);

            if (isOperador(simbolo)) {
                while (!operadorStack.isEmpty() && precedencia(operadorStack.peek()) >= precedencia(simbolo)) {
                    aplicarOperador(afnStack, operadorStack.pop(), alfabeto);
                }
                operadorStack.push(simbolo);
            } else if (simbolo == '(' || simbolo == '[' || simbolo == '{') {
                operadorStack.push(simbolo);
            } else if (simbolo == ')' || simbolo == ']' || simbolo == '}') {
                while (!operadorStack.isEmpty() && !isParentesesCorrespondente(operadorStack.peek(), simbolo)) {
                    aplicarOperador(afnStack, operadorStack.pop(), alfabeto);
                }
                operadorStack.pop(); // Remove o parêntese correspondente
            } else {
                afnStack.push(afnBasico(simbolo,expressaoRegular));
                if (Character.isLetterOrDigit(simbolo)) {
                    alfabeto.add(simbolo);
                }
            }
        }

        while (!operadorStack.isEmpty()) {
            aplicarOperador(afnStack, operadorStack.pop(), alfabeto);
        }

        AutomatoNaoDeterministico resultado = afnStack.pop();
        resultado.setAlfabeto(alfabeto);
        return resultado;
    }

    private void aplicarOperador(Stack<AutomatoNaoDeterministico> afnStack, char operador, Set<Character> alfabeto) {
        switch (operador) {
            case '*': {
                AutomatoNaoDeterministico afn = afnStack.pop();
                afnStack.push(fecharKleene(afn));
                break;
            }
            case '|':
            case '+': { // Trata tanto '|' quanto '+' como alternância
                AutomatoNaoDeterministico afn2 = afnStack.pop();
                AutomatoNaoDeterministico afn1 = afnStack.pop();
                afnStack.push(alternativa(afn1, afn2));
                break;
            }
            case '.': {
                AutomatoNaoDeterministico afn2 = afnStack.pop();
                AutomatoNaoDeterministico afn1 = afnStack.pop();
                afnStack.push(concatenar(afn1, afn2));
                break;
            }
        }
    }


    private AutomatoNaoDeterministico afnBasico(char simbolo, String expressao) {
        String estadoInicial = "q" + (int) (Math.random() * 1000);
        String estadoFinal = "q" + (int) (Math.random() * 1000);
        AutomatoNaoDeterministico afn = new AutomatoNaoDeterministico();
        afn.setEstadoInicial(estadoInicial);
        afn.setNome(expressao);
        // Utilize HashSet para garantir que os conjuntos sejam mutáveis
        afn.setEstados(new HashSet<>(Arrays.asList(estadoInicial, estadoFinal)));
        afn.setEstadosAceitacao(new HashSet<>(Collections.singletonList(estadoFinal)));

        // Adiciona a transição para o símbolo corretamente
        afn.adicionarTransicao(estadoInicial, simbolo, estadoFinal);

        return afn;
    }


    private AutomatoNaoDeterministico fecharKleene(AutomatoNaoDeterministico afn) {
        String novoEstadoInicial = "q" + (int) (Math.random() * 1000);
        String novoEstadoFinal = "q" + (int) (Math.random() * 1000);

        afn.adicionarEstado(novoEstadoInicial);
        afn.adicionarEstado(novoEstadoFinal);

        afn.adicionarTransicao(novoEstadoInicial, 'ε', afn.getEstadoInicial());
        afn.adicionarTransicao(novoEstadoInicial, 'ε', novoEstadoFinal);
        afn.adicionarTransicao(afn.getEstadosAceitacao().iterator().next(), 'ε', novoEstadoFinal);
        afn.adicionarTransicao(afn.getEstadosAceitacao().iterator().next(), 'ε', afn.getEstadoInicial());

        afn.setEstadoInicial(novoEstadoInicial);
        afn.setEstadosAceitacao(new HashSet<>(Collections.singletonList(novoEstadoFinal)));

        return afn;
    }

    private AutomatoNaoDeterministico concatenar(AutomatoNaoDeterministico afn1, AutomatoNaoDeterministico afn2) {
        // Obtenha o estado de aceitação do primeiro AFN
        String estadoAceitacaoAnterior = afn1.getEstadosAceitacao().iterator().next();

        // Adiciona a transição de ε do estado de aceitação de afn1 para o estado inicial de afn2
        afn1.adicionarTransicao(estadoAceitacaoAnterior, 'ε', afn2.getEstadoInicial());

        // Crie novos conjuntos mutáveis para estados de aceitação e estados
        Set<String> novosEstadosAceitacao = new HashSet<>(afn2.getEstadosAceitacao());
        Set<String> novosEstados = new HashSet<>(afn1.getEstados());
        novosEstados.addAll(afn2.getEstados());

        // Atualize os estados e estados de aceitação de afn1
        afn1.setEstadosAceitacao(novosEstadosAceitacao);
        afn1.setEstados(novosEstados);

        // Combine as transições dos dois AFNs
        afn1.getTransicoes().putAll(afn2.getTransicoes());

        return afn1;
    }

    private AutomatoNaoDeterministico alternativa(AutomatoNaoDeterministico afn1, AutomatoNaoDeterministico afn2) {
        String novoEstadoInicial = "q" + (int) (Math.random() * 1000);
        String novoEstadoFinal = "q" + (int) (Math.random() * 1000);

        afn1.adicionarEstado(novoEstadoInicial);
        afn1.adicionarEstado(novoEstadoFinal);

        afn1.adicionarTransicao(novoEstadoInicial, 'ε', afn1.getEstadoInicial());
        afn1.adicionarTransicao(novoEstadoInicial, 'ε', afn2.getEstadoInicial());
        afn1.adicionarTransicao(afn1.getEstadosAceitacao().iterator().next(), 'ε', novoEstadoFinal);
        afn2.adicionarTransicao(afn2.getEstadosAceitacao().iterator().next(), 'ε', novoEstadoFinal);

        // Garanta que os conjuntos sejam mutáveis
        Set<String> novosEstadosAceitacao = new HashSet<>(Collections.singletonList(novoEstadoFinal));
        Set<String> novosEstados = new HashSet<>(afn1.getEstados());
        novosEstados.addAll(afn2.getEstados());

        // Atualize o AFN com os novos estados e transições
        afn1.setEstadoInicial(novoEstadoInicial);
        afn1.setEstadosAceitacao(novosEstadosAceitacao);
        afn1.setEstados(novosEstados);
        afn1.getTransicoes().putAll(afn2.getTransicoes());

        return afn1;
    }




}
