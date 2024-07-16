package br.com.yan.automato.controller;

import br.com.yan.automato.dto.ConverterDTO;
import br.com.yan.automato.dto.ExecucaoDto;
import br.com.yan.automato.model.Automato;
import br.com.yan.automato.service.*;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/automatos")
public class AutomatoController {

    @Autowired
    private AutomatoService automatoService;
    private static final Logger logger = LoggerFactory.getLogger(AutomatoService.class);

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
}
