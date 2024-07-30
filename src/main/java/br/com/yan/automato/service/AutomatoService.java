package br.com.yan.automato.service;

import br.com.yan.automato.exception.NotFoundException;
import br.com.yan.automato.model.Automato;
import br.com.yan.automato.repository.AutomatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class AutomatoService {

    @Autowired
    public AutomatoRepository repository;




    public AutomatoDeterministico converterAFD(String id){
        Automato automato = this.findById(id);
        if(!(automato instanceof AutomatoNaoDeterministico)){
            throw new IllegalArgumentException("Automato não é do tipo AFN");
        }
        AutomatoNaoDeterministico afn = (AutomatoNaoDeterministico) automato;
        return converter(afn);
    }




    public AutomatoDeterministico minimizarAFD(String id){
        Automato automato = this.findById((id));
        if(!(automato instanceof AutomatoDeterministico)){
            throw new IllegalArgumentException("Automato não é do tipo AFD");
        }
        AutomatoDeterministico afd = (AutomatoDeterministico) automato;
        return minimizacaoHopCroft(afd);
    }

    public AutomatoDeterministico minimizacaoHopCroft(AutomatoDeterministico afd) {
        Set<String> estados = afd.getEstados();
        Set<Character> alfabeto = afd.getAlfabeto();
        String estadoInicial = afd.getEstadoInicial();
        Set<String> estadosDeAceitacao = afd.getEstadosAceitacao();
        Set<String> estadosNaoAceitacao = new HashSet<>(estados);
        estadosNaoAceitacao.removeAll(estadosDeAceitacao);

        Set<Set<String>> P = new HashSet<>();
        P.add(estadosDeAceitacao);
        P.add(estadosNaoAceitacao);

        Set<Set<String>> W = new HashSet<>(P);

        while (!W.isEmpty()) {
            Set<String> A = W.iterator().next();
            W.remove(A);

            for (char c : alfabeto) {
                Set<String> X = new HashSet<>();
                for (String s : estados) {
                    if (A.contains(afd.getTransicao(s, c))) {
                        X.add(s);
                    }
                }

                Set<Set<String>> P_new = new HashSet<>();
                for (Set<String> Y : P) {
                    Set<String> intersection = new HashSet<>(Y);
                    intersection.retainAll(X);
                    Set<String> difference = new HashSet<>(Y);
                    difference.removeAll(X);

                    if (!intersection.isEmpty() && !difference.isEmpty()) {
                        P_new.add(intersection);
                        P_new.add(difference);

                        if (W.contains(Y)) {
                            W.remove(Y);
                            W.add(intersection);
                            W.add(difference);
                        } else {
                            if (intersection.size() <= difference.size()) {
                                W.add(intersection);
                            } else {
                                W.add(difference);
                            }
                        }
                    } else {
                        P_new.add(Y);
                    }
                }
                P = P_new;
            }
        }

        Map<Set<String>, String> representativos = new HashMap<>();
        for (Set<String> grupo : P) {
            String representativo = grupo.iterator().next();
            representativos.put(grupo, representativo);
        }

        AutomatoDeterministico afd_minimizado = new AutomatoDeterministico(afd.getNome()+" MINIMIZADO", estadoInicial, new HashSet<>());
        for (String estado : representativos.values()) {
            afd_minimizado.getEstados().add(estado);
            if (estadosDeAceitacao.contains(estado)) {
                afd_minimizado.getEstadosAceitacao().add(estado);
            }
        }

        for (Set<String> grupo : P) {
            String representativo = representativos.get(grupo);
            for (char c : alfabeto) {
                String transicao = afd.getTransicao(grupo.iterator().next(), c);
                if (transicao != null) {
                    for (Set<String> destinoGrupo : P) {
                        if (destinoGrupo.contains(transicao)) {
                            String destinoRepresentativo = representativos.get(destinoGrupo);
                            afd_minimizado.adicionarTransicao(representativo, c, destinoRepresentativo);
                        }
                    }
                }
            }
        }

        return afd_minimizado;
    }

    private AutomatoDeterministico removerEstadosInacessiveis(AutomatoDeterministico afd) {
        Set<String> acessiveis = new HashSet<>();
        Queue<String> fila = new LinkedList<>();

        String estadoInicial = afd.getEstadoInicial();
        Set<Character> alfabeto = afd.getAlfabeto();

        // Adicionar o estado inicial à fila e ao conjunto de estados acessíveis
        fila.offer(estadoInicial);
        acessiveis.add(estadoInicial);

        while (!fila.isEmpty()) {
            String estadoAtual = fila.poll();

            for (char simbolo : alfabeto) {
                String proximoEstado = afd.getTransicao(estadoAtual, simbolo);
                if (proximoEstado != null && !acessiveis.contains(proximoEstado)) {
                    acessiveis.add(proximoEstado);
                    fila.offer(proximoEstado);
                }
            }
        }

        // Criar um novo AFD com apenas os estados acessíveis
        AutomatoDeterministico afdLimpo = new AutomatoDeterministico(afd.getNome(), estadoInicial, new HashSet<>());
        for (String estado : acessiveis) {
            afdLimpo.getEstados().add(estado);
            if (afd.getEstadosAceitacao().contains(estado)) {
                afdLimpo.getEstadosAceitacao().add(estado);
            }
        }

        for (String estado : acessiveis) {
            for (char simbolo : alfabeto) {
                String proximoEstado = afd.getTransicao(estado, simbolo);
                if (proximoEstado != null && acessiveis.contains(proximoEstado)) {
                    afdLimpo.adicionarTransicao(estado, simbolo, proximoEstado);
                }
            }
        }

        return afdLimpo;
    }


    private AutomatoDeterministico converter(AutomatoNaoDeterministico afn) {
        Set<String> estados = new HashSet<>();
        Set<Character> alfabeto = afn.getAlfabeto();
        String estadoInicial = afn.getEstadoInicial();
        Set<String> estadosDeAceitacao = new HashSet<>();

        AutomatoDeterministico afd = new AutomatoDeterministico(afn.getNome()+" CONVERTIDO", estadoInicial, estadosDeAceitacao);

        Map<Set<String>, String> novosEstados = new HashMap<>();
        Queue<Set<String>> fila = new LinkedList<>();
        Set<Set<String>> visitados = new HashSet<>();

        Set<String> estadoInicialAFD = new HashSet<>(Collections.singletonList(estadoInicial));
        fila.offer(estadoInicialAFD);
        visitados.add(estadoInicialAFD);
        novosEstados.put(estadoInicialAFD, setToString(estadoInicialAFD));
        estados.add(setToString(estadoInicialAFD));

        while (!fila.isEmpty()) {
            Set<String> estadosAtuais = fila.poll();

            for (char simbolo : alfabeto) {
                Set<String> novosEstadosAFD = new HashSet<>();

                for (String estado : estadosAtuais) {
                    Map<Character, Set<String>> transicoesDoEstado = afn.getTransicoes().get(estado);
                    if (transicoesDoEstado != null && transicoesDoEstado.containsKey(simbolo)) {
                        novosEstadosAFD.addAll(transicoesDoEstado.get(simbolo));
                    }
                }

                if (!novosEstadosAFD.isEmpty() && !visitados.contains(novosEstadosAFD)) {
                    visitados.add(novosEstadosAFD);
                    fila.offer(novosEstadosAFD);
                    String novoEstadoAFD = setToString(novosEstadosAFD);
                    novosEstados.put(novosEstadosAFD, novoEstadoAFD);
                    estados.add(novoEstadoAFD);
                }

                String estadoAtualAFD = novosEstados.get(estadosAtuais);
                String novoEstadoAFDString = novosEstados.getOrDefault(novosEstadosAFD, "D");
                afd.adicionarTransicao(estadoAtualAFD, simbolo, novoEstadoAFDString);
            }
        }

        // Definir estados de aceitação do AFD
        for (Set<String> estadosAFD : visitados) {
            for (String estado : estadosAFD) {
                if (afn.getEstadosAceitacao().contains(estado)) {
                    estadosDeAceitacao.add(novosEstados.get(estadosAFD));
                    break;
                }
            }
        }

        // Adicionar o estado de morte e suas transições
        String estadoMorte = "D";
        estados.add(estadoMorte);

        for (String estadoAFD : estados) {
            for (char simbolo : alfabeto) {
                if (!transicaoExiste(afd, estadoAFD, simbolo)) {
                    afd.adicionarTransicao(estadoAFD, simbolo, estadoMorte);
                }
            }
        }

        afd = removerEstadosInacessiveis(afd);
        return afd;
    }

    private boolean transicaoExiste(AutomatoDeterministico afd, String estadoOrigem, char simbolo) {
        Map<String, Map<Character, String>> transicoes = afd.getTransicoes();
        if (transicoes.containsKey(estadoOrigem)) {
            Map<Character, String> transicoesDoEstado = transicoes.get(estadoOrigem);
            return transicoesDoEstado.containsKey(simbolo);
        }
        return false;
    }


    private String setToString(Set<String> estados) {
        return String.join("", estados);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public List<Automato> findAll(){
        return repository.findAll();
    }

    public Automato save(Automato automato){
        return repository.save(automato);
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }

    public Automato findById(String automatoId) {
        return repository.findById(automatoId).orElseThrow(
                ()-> new NotFoundException(String.format("Automato com ID %s não encontrado.", automatoId))
        );
    }
}


