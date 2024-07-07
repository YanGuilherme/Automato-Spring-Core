package br.com.yan.automato.transicao;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("automato")
public class ExecutarAutomatoController {

    @PostMapping("exec")
    public ResponseEntity<String> percorrer(@RequestBody Execucao excecucao){
        Automato automato = excecucao.getAutomato();
        return ResponseEntity.ok(automato.processarCadeia(excecucao.getCadeia()));
    }

}
