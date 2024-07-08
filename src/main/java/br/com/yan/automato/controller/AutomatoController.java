package br.com.yan.automato.controller;

import br.com.yan.automato.model.Automato;
import br.com.yan.automato.dto.AutomatoDto;
import br.com.yan.automato.service.AutomatoService;


import br.com.yan.automato.service.Execucao;
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



    @PostMapping("/create")
    public ResponseEntity<Automato> criarAutomato(@RequestBody AutomatoDto automatoDto){
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Automato>> buscarTodosAutomatos(){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(){
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/exec")
    public ResponseEntity<String> percorrer(@RequestBody Execucao excecucao) {
        Automato automato = excecucao.getAutomato();
        return ResponseEntity.ok(automato.processarCadeia(excecucao.getCadeia()));
    }


}
