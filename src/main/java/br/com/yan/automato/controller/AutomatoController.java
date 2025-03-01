package br.com.yan.automato.controller;

import br.com.yan.automato.dto.ConverterDTO;
import br.com.yan.automato.dto.ExecucaoDto;
import br.com.yan.automato.equivalencia.EquivalenciaService;
import br.com.yan.automato.equivalencia.dto.TesteEquivalencia;
import br.com.yan.automato.equivalencia.validacoes.LinguagemValidator;
import br.com.yan.automato.model.Automato;
import br.com.yan.automato.model.User;
import br.com.yan.automato.service.*;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;
import br.com.yan.automato.service.Regex;

@RestController
@RequestMapping("api/automatos")
public class AutomatoController {
    private final AutomatoService automatoService;
    private final EquivalenciaService equivalenciaService;
    private static final Logger logger = LoggerFactory.getLogger(AutomatoService.class);
    private LinguagemValidator linguagemValidator;

    @Autowired
    private Regex regex;

    public AutomatoController(AutomatoService automatoService, EquivalenciaService equivalenciaService) {
        this.automatoService = automatoService;
        this.equivalenciaService = equivalenciaService;
    }

    @GetMapping("/findAll")
    public List<Automato> findAll(@AuthenticationPrincipal User user) {
            return automatoService.findByUserId(user.getId());
    }

    @PostMapping("/save")
    public ResponseEntity<Automato> create(@RequestBody Automato automato, @AuthenticationPrincipal User user) {
        automato.setUserId(user.getId());
        Automato automatoSaved = automatoService.save(automato);
        return ResponseEntity.ok(automatoSaved);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable String id, @AuthenticationPrincipal User user) {

        Automato automato = automatoService.findById(id);
        if(automato.getUserId().equals(user.getId())){
            automatoService.deleteById(id);
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    //Ajustar os endpoints daqui pra baixo

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

    @PostMapping("regexToAfn")
    public ResponseEntity<AutomatoNaoDeterministico> converterRegextoAfn(@RequestBody Map<String, String> payload) {
        String expressao = payload.get("expressao");
        AutomatoNaoDeterministico afnGerado = regex.gerarAFN(expressao);
        return ResponseEntity.ok(afnGerado);
    }

}
