package br.com.yan.automato.controller;

import br.com.yan.automato.dto.ConverterDTO;
import br.com.yan.automato.dto.ExecucaoDto;
import br.com.yan.automato.enums.UserRole;
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


    //endpoint nao funciona kkkkkkkkkkkk
    @DeleteMapping("/deleteAll")
    public ResponseEntity deleteAll(@AuthenticationPrincipal User user){
        System.out.println(user.getUserRole());
        if(user.getUserRole().equals(UserRole.ADMIN)){
            automatoService.deleteAll();
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }


    @PostMapping("/exec")
    public ResponseEntity<ExecucaoDto> percorrer(@RequestBody Execucao excecucao, @AuthenticationPrincipal User user) {
        Automato automato = automatoService.findById(excecucao.getAutomatoId());
        if(automato.getUserId().equals(user.getId())){
            ExecucaoDto execucaoDto = new ExecucaoDto(automato.validarProcessar((excecucao.getCadeia())));
            return ResponseEntity.ok(execucaoDto);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

    }

    @GetMapping("/{id}")
    public ResponseEntity<Automato> findById(@PathVariable String id, @AuthenticationPrincipal User user) {
        Automato automato = automatoService.findById(id);
        if(automato.getUserId().equals(user.getId())){
            return ResponseEntity.ok(automato);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    //Ajustar os endpoints daqui pra baixo


    @PostMapping("/convertToAFD")
    public ResponseEntity<AutomatoDeterministico> convertToAFD(@RequestBody ConverterDTO conversao, @AuthenticationPrincipal User user) {
        Automato automato = automatoService.findById(conversao.getId());
        if(automato.getUserId().equals(user.getId())){
            AutomatoDeterministico automatoDeterministico = automatoService.converterAFD(automato);
            automatoDeterministico.setUserId(user.getId());
            automatoService.save(automatoDeterministico);
            return ResponseEntity.ok(automatoDeterministico);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

    }

    @PostMapping("/minimizar")
    public ResponseEntity<AutomatoDeterministico> minimizarAFD(@RequestBody ConverterDTO minimizacao, @AuthenticationPrincipal User user){
        Automato automato = automatoService.findById(minimizacao.getId());
        if(automato.getUserId().equals(user.getId())){
            AutomatoDeterministico automatoDeterministico = automatoService.minimizarAFD(automato);
            automatoDeterministico.setMinimized(true);
            automatoDeterministico.setUserId(user.getId());
            automatoService.save(automatoDeterministico);
            return ResponseEntity.ok(automatoDeterministico);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }


    @GetMapping("testarEquivalencia/{id1}/{id2}")
    public ResponseEntity<List<TesteEquivalencia>> testarEquivalencia(@PathVariable String id1, @PathVariable String id2,  @AuthenticationPrincipal User user) {
        Automato automato1 = automatoService.findById(id1);
        Automato automato2 = automatoService.findById(id2);
        if(automato1.getUserId().equals(user.getId()) && automato2.getUserId().equals(user.getId())){
            List<TesteEquivalencia> equivalente = this.equivalenciaService.testarEquivalencia(automato1, automato2);
            return ResponseEntity.ok(equivalente);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PostMapping("regexToAfn")
    public ResponseEntity<AutomatoNaoDeterministico> converterRegextoAfn(@RequestBody Map<String, String> payload, @AuthenticationPrincipal User user) {
        String expressao = payload.get("expressao");
        AutomatoNaoDeterministico afnGerado = regex.gerarAFN(expressao);
        afnGerado.setUserId(user.getId());
        automatoService.save(afnGerado);
        return ResponseEntity.ok(afnGerado);
    }

    //parece que todos os endpoints esta ok, menos o de deleteAll

}
