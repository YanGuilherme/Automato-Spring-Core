package br.com.yan.automato.controller;

import br.com.yan.automato.dto.ExecucaoDto;
import br.com.yan.automato.dto.TestarMaquinaDTO;
import br.com.yan.automato.service.MaquinaDeTuring;
import br.com.yan.automato.service.MaquinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/maquina")
public class MaquinaController {
    @Autowired
    private MaquinaService maquinaService;

    @PostMapping("/save")
    public MaquinaDeTuring save(@RequestBody MaquinaDeTuring maquinaDeTuring) {
        return maquinaService.save(maquinaDeTuring);

    }

    @PostMapping("/testarCadeia")
    public ResponseEntity<ExecucaoDto> testarCadeia(@RequestBody TestarMaquinaDTO request) {
        // Buscar a Máquina de Turing pelo ID
        MaquinaDeTuring maquinaDeTuring = maquinaService.findById(request.getId());

        if (maquinaDeTuring == null) {
            throw new RuntimeException("Máquina de Turing não encontrada para o ID: " + request.getId());
        }

        // Criar uma instância de MaquinaDeTuringUniversal para simular a cadeia


        ExecucaoDto execucaoDto = new ExecucaoDto(maquinaDeTuring.validarProcessar((request.getCadeia())));

        // Testar a cadeia
        return ResponseEntity.ok(execucaoDto);

    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable String id) {
        maquinaService.deleteById(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaquinaDeTuring> findById(@PathVariable String id) {
        return ResponseEntity.ok(maquinaService.findById(id));
    }

    @GetMapping("/findAll")
    public List<MaquinaDeTuring> findAll() {
        return maquinaService.findAll();
    }
    @GetMapping("/test")
    public String test() {
        return "Controller is working!";
    }

}
