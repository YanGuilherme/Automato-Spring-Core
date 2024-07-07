package br.com.yan.automato.controller;

import br.com.yan.automato.model.Automato;
import br.com.yan.automato.dto.AutomatoDto;
import br.com.yan.automato.repository.AutomatoRepository;
import br.com.yan.automato.service.AutomatoService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/automatos")
public class AutomatoController {

    @Autowired
    private AutomatoService automatoService;
    @Autowired
    private AutomatoRepository automatoRepository;

    @PostMapping("/create")
    public ResponseEntity<Automato> criarAutomato(@RequestBody AutomatoDto automatoDto){
        Automato novoAutomato = automatoService.salvarAutomato(automatoDto);
        return new ResponseEntity<>(new Automato(novoAutomato), HttpStatus.CREATED);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Automato>> buscarTodosAutomatos(){
        List<Automato> automatos = automatoService.buscarTodosAutomatos();
        return ResponseEntity.ok(automatos);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        Optional<Automato> optionalAutomato = automatoRepository.findById(id);
        if(optionalAutomato.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        automatoRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/convert/{id}")
    public ResponseEntity<Automato> converterAutomato(@PathVariable Long id) {
        try {
            Automato novoAutomato = automatoService.converterParaAFD(id);
            novoAutomato = automatoService.salvarAutomato(new AutomatoDto(novoAutomato));
            return new ResponseEntity<>(novoAutomato, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



}
