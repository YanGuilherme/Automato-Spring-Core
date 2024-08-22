package br.com.yan.automato.controller;

import br.com.yan.automato.dto.ConverterDTO;
import br.com.yan.automato.dto.ExecucaoDto;
import br.com.yan.automato.equivalencia.EquivalenciaService;
import br.com.yan.automato.equivalencia.dto.TesteEquivalencia;
import br.com.yan.automato.equivalencia.validacoes.LinguagemValidator;
import br.com.yan.automato.model.Automato;
import br.com.yan.automato.service.*;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Set;

@RestController
@RequestMapping("api/automatos")
public class AutomatoController {

    private final AutomatoService automatoService;
    private final EquivalenciaService equivalenciaService;
    private static final Logger logger = LoggerFactory.getLogger(AutomatoService.class);
    private LinguagemValidator linguagemValidator;

    public AutomatoController(AutomatoService automatoService, EquivalenciaService equivalenciaService) {
        this.automatoService = automatoService;
        this.equivalenciaService = equivalenciaService;
    }

    @GetMapping("/findAll")
    public List<Automato> findAll() {
        List<Automato> automatos = automatoService.findAll();
        return automatos;
    }

    @PostMapping("/save")
    public Automato create(@RequestBody Automato automato) {
        return automatoService.save(automato);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable String id) {
        automatoService.deleteById(id);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteAll() {
        automatoService.deleteAll();
        return ResponseEntity.status(HttpStatus.OK).body("Todos os autômatos foram deletados com sucesso.");
    }

    @PostMapping("/exec")
    public ResponseEntity<ExecucaoDto> percorrer(@RequestBody Execucao excecucao) {
        Automato automato = automatoService.findById(excecucao.getAutomatoId());
        ExecucaoDto execucaoDto = new ExecucaoDto(automato.validarProcessar((excecucao.getCadeia())));
        return ResponseEntity.ok(execucaoDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Automato> findById(@PathVariable String id) {
        return ResponseEntity.ok(automatoService.findById(id));
    }

    @PostMapping("/convertToAFD")
    public ResponseEntity<AutomatoDeterministico> convertToAFD(@RequestBody ConverterDTO conversao) {

        AutomatoDeterministico automato = automatoService.converterAFD(conversao.getId());
        automatoService.save(automato);
        return ResponseEntity.ok(automato);
    }

    @PostMapping("/minimizar")
    public ResponseEntity<AutomatoDeterministico> minimizarAFD(@RequestBody ConverterDTO minimizacao){
        AutomatoDeterministico automato = automatoService.minimizarAFD(minimizacao.getId());
        automato.setMinimized(true);
        automatoService.save(automato);
        return ResponseEntity.ok(automato);
    }

    @PostMapping("gerarLinguagem/{id1}/{id2}")
    public ResponseEntity<Set<String>> gerarLinguagem(@PathVariable String id1, @PathVariable String id2) {
        Set<String> linguagem = linguagemValidator.gerarLinguagem(id1,id2);
        return ResponseEntity.ok(linguagem);
    }

        @GetMapping("testarEquivalencia/{id1}/{id2}")
    public ResponseEntity<List<TesteEquivalencia>> testarEquivalencia(@PathVariable String id1, @PathVariable String id2) {
        Automato automato1 = automatoService.findById(id1);
        Automato automato2 = automatoService.findById(id2);
        List<TesteEquivalencia> equivalente = this.equivalenciaService.testarEquivalencia(automato1, automato2);
        return ResponseEntity.ok(equivalente);
    }
}
