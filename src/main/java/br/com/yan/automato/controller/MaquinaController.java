package br.com.yan.automato.controller;

import br.com.yan.automato.dto.ExecucaoDto;
import br.com.yan.automato.dto.TestarMaquinaDTO;
import br.com.yan.automato.model.User;
import br.com.yan.automato.service.MaquinaDeTuring;
import br.com.yan.automato.service.MaquinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/maquina")
public class MaquinaController {
    @Autowired
    private MaquinaService maquinaService;

    @PostMapping("/save")
    public MaquinaDeTuring save(@RequestBody MaquinaDeTuring maquinaDeTuring, @AuthenticationPrincipal User user) {
        maquinaDeTuring.setUserId(user.getId());
        return maquinaService.save(maquinaDeTuring);

    }

    @PostMapping("/testarCadeia")
    public ResponseEntity<ExecucaoDto> testarCadeia(@RequestBody TestarMaquinaDTO request, @AuthenticationPrincipal User user) {
        // Buscar a Máquina de Turing pelo ID
        MaquinaDeTuring maquinaDeTuring = maquinaService.findById(request.getId());
        if(maquinaDeTuring != null && maquinaDeTuring.getUserId().equals(user.getId())){
            // Criar uma instância de MaquinaDeTuringUniversal para simular a cadeia
            ExecucaoDto execucaoDto = new ExecucaoDto(maquinaDeTuring.validarProcessar((request.getCadeia())));
            // Testar a cadeia
            return ResponseEntity.ok(execucaoDto);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();


    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable String id, @AuthenticationPrincipal User user) {
        MaquinaDeTuring maquinaDeTuring = maquinaService.findById(id);
        if(maquinaDeTuring.getUserId().equals(user.getId())){
            maquinaService.deleteById(maquinaDeTuring.getId());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaquinaDeTuring> findById(@PathVariable String id, @AuthenticationPrincipal User user) {
        MaquinaDeTuring maquinaDeTuring = maquinaService.findById(id);
        if(maquinaDeTuring.getUserId().equals(user.getId())){
            return ResponseEntity.ok(maquinaDeTuring);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<MaquinaDeTuring>> findAll(@AuthenticationPrincipal User user) {
        List<MaquinaDeTuring> listMT = maquinaService.findByUserId(user.getId());
        if(listMT != null){
            return ResponseEntity.ok(listMT);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping("/test")
    public String test() {
        return "Controller is working!";
    }

}
