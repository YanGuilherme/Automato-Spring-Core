package br.com.yan.automato.service;

import br.com.yan.automato.dto.AutomatoDto;
import br.com.yan.automato.model.Automato;
import br.com.yan.automato.repository.AutomatoRepository;
import br.com.yan.automato.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static br.com.yan.automato.dto.Tipo.AFD;


@Service
public class AutomatoService {

    @Autowired
    private AutomatoRepository automatoRepository;

    public Automato salvarAutomato(AutomatoDto automatoDto) {
        Automato automato = new Automato();
        automato.setEstados(automatoDto.getEstados());
        automato.setAlfabeto(automatoDto.getAlfabeto());
        automato.setEstado_inicial(automatoDto.getEstado_inicial());
        automato.setEstados_aceitacao(automatoDto.getEstados_aceitacao());
        automato.setId(automatoDto.getId());
        automato.setTipo_automato(automatoDto.getTipo_automato());

        Map<Pair<String, Character>, Set<String>> transicoesMap = new HashMap<>();
        for (Map.Entry<String, Set<String>> entry : automatoDto.getTransicoes().entrySet()) {
            String key = entry.getKey();
            if (key.contains("_")) { // Verifica se a chave contém o delimitador esperado
                String[] parts = key.split("_");
                String estadoOrigem = parts[0];
                Character simbolo = parts[1].charAt(0); // Acessa o primeiro caractere após o underscore
                Set<String> estadoDestinos = entry.getValue();
                transicoesMap.put(new Pair<>(estadoOrigem, simbolo), estadoDestinos);
            } else {
                // Tratar caso em que a chave não está no formato esperado
                // Pode lançar uma exceção, ignorar a entrada ou tratá-la de outra forma adequada
            }
        }
        automato.setTransicoes(transicoesMap);

        return automatoRepository.save(automato);
    }


    public List<Automato> buscarTodosAutomatos() {
        return automatoRepository.findAll();
    }

    //classe nao funcionando
    public Automato converterParaAFD(Long id) {
        Optional<Automato> optionalAutomato = automatoRepository.findById(id);
        if (optionalAutomato.isEmpty()) {
            throw new IllegalArgumentException("Autômato não encontrado para o ID: " + id);
        }
        Automato afn = optionalAutomato.get();

        Automato afd = new Automato();
        afd.setAlfabeto(new HashSet<>(afn.getAlfabeto())); // Inicializa uma nova instância de Set<Character>
        afd.setEstado_inicial(afn.getEstado_inicial());
        afd.setEstados(new HashSet<>(afn.getEstados())); // Inicializa uma nova instância de Set<String>
        afd.setEstados_aceitacao(new HashSet<>(afn.getEstados_aceitacao())); // Inicializa uma nova instância de Set<String>
        afd.setTipo_automato(AFD);

        afd.setAlfabeto(afn.getAlfabeto());
        afd.setEstado_inicial(afn.getEstado_inicial());

        // Inicializar coleções do AFD conforme necessário
        afd.setEstados(new HashSet<>());
        // Utiliza uma fila para processar os novos estados do AFD
        Queue<Set<String>> fila = new LinkedList<>();
        Set<Set<String>> visitados = new HashSet<>();

        // Adiciona o estado inicial do AFN à fila
        Set<String> estadoInicialAFD = new HashSet<>(Collections.singletonList(afn.getEstado_inicial()));
        fila.offer(estadoInicialAFD);
        visitados.add(estadoInicialAFD);
        afd.getEstados().add(setToString(estadoInicialAFD));

        while (!fila.isEmpty()) {
            Set<String> estadosAtuais = fila.poll();

            for (char simbolo : afn.getAlfabeto()) {
                Set<String> novosEstados = new HashSet<>();

                for (String estado : estadosAtuais) {
                    Pair<String, Character> chave = new Pair<>(estado, simbolo);
                    if (afn.getTransicoes().containsKey(chave)) {
                        novosEstados.addAll(afn.getTransicoes().get(chave));
                    }
                }

                if (!novosEstados.isEmpty()) {
                    if (!visitados.contains(novosEstados)) {
                        visitados.add(novosEstados);
                        fila.offer(novosEstados);
                        afd.getEstados().add(setToString(novosEstados));
                    }

                    Pair<String, Character> chave = new Pair<>(setToString(estadosAtuais), simbolo);
                    afd.getTransicoes().put(chave, new HashSet<>(Collections.singletonList(setToString(novosEstados))));
                }
            }
        }

        // Definir estados de aceitação do AFD
        for (Set<String> estadosAFD : visitados) {
            for (String estado : estadosAFD) {
                if (afn.getEstados_aceitacao().contains(estado)) {
                    afd.getEstados_aceitacao().add(setToString(estadosAFD));
                    break;
                }
            }
        }

        // Adicionar o estado D (estado de morte) e as transições que faltam
        String estadoMorte = "D";
        afd.getEstados().add(estadoMorte);

        for (String estadoAFD : afd.getEstados()) {
            for (char simbolo : afd.getAlfabeto()) {
                Pair<String, Character> chave = new Pair<>(estadoAFD, simbolo);
                if (!afd.getTransicoes().containsKey(chave)) {
                    afd.getTransicoes().put(chave, new HashSet<>(Collections.singletonList(estadoMorte)));
                }
            }
        }

        // Definir transições de auto-loop para o estado de morte
        for (char simbolo : afd.getAlfabeto()) {
            Pair<String, Character> chave = new Pair<>(estadoMorte, simbolo);
            afd.getTransicoes().put(chave, new HashSet<>(Collections.singletonList(estadoMorte)));
        }

        afd.setTipo_automato(AFD);
        return afd;
    }


    private String setToString(Set<String> estados) {
        StringBuilder resultado = new StringBuilder();
        for (String estado : estados) {
            resultado.append(estado);
        }
        return resultado.toString();
    }
}


