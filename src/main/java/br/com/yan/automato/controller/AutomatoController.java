package br.com.yan.automato.controller;

import br.com.yan.automato.model.Automato;
import br.com.yan.automato.dto.AutomatoDto;
import br.com.yan.automato.repository.AutomatoRepository;
import br.com.yan.automato.service.AutomatoService;
import br.com.yan.automato.util.Pair;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/automatos")
public class AutomatoController {

    @Autowired
    private AutomatoService automatoService;

    @PostMapping("/create")
    public ResponseEntity<Automato> criarAutomato(@RequestBody AutomatoDto automatoDto){
        Automato novoAutomato = automatoService.salvarAutomato(automatoDto);
        return ResponseEntity.ok(novoAutomato);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Automato>> buscarTodosAutomatos(){
        List<Automato> automatos = automatoService.buscarTodosAutomatos();
        return ResponseEntity.ok(automatos);
    }



}
