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
    private AutomatoDeterministico converter(AutomatoNaoDeterministico afn) {
        Set<String> estados = new HashSet<>();
        Set<Character> alfabeto = afn.getAlfabeto();
        String estadoInicial = afn.getEstadoInicial();
        Set<String> estadosDeAceitacao = new HashSet<>();

        AutomatoDeterministico afd = new AutomatoDeterministico(estadoInicial, estadosDeAceitacao);

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


