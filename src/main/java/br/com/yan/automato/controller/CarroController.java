package br.com.yan.automato.controller;

import br.com.yan.automato.dto.CarroDto;
import br.com.yan.automato.model.Carro;
import br.com.yan.automato.repository.CarroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class CarroController {

    @Autowired
    private CarroRepository carroRepository;

    @GetMapping("/findAll")
    public ResponseEntity<List<CarroDto>> findAll(){
        List<Carro> carros =  carroRepository.findAll();
        List<CarroDto> response = carros.stream().map(CarroDto::new).toList();
        Map<String, List<CarroDto>> responseMap = Map.of("carro", response);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<CarroDto> create(@RequestBody Map<String, List<CarroDto>> request){
        List<CarroDto> carrosDto = request.get("carro");

        if (carrosDto == null || carrosDto.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        CarroDto carroDto = carrosDto.get(0); // Supondo que sempre haverá apenas um carro no array
        Carro carroPersisto = carroRepository.save(carroDto.toModel());
        return new ResponseEntity<>(new CarroDto(carroPersisto), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CarroDto> update(@PathVariable Long id, @RequestBody CarroDto carroDtoRequest ){
        //verificar se o ID do carro a ser atualizado foi fornecido
        if(id == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        //verificando se o carro fornecido existe no bd
        Optional<Carro> optionalCarro = carroRepository.findById(id);
        if(optionalCarro.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        //atualizar o carro existente com os novos dados
        Carro carroExistente = optionalCarro.get();
        carroExistente.setModelo(carroDtoRequest.getModelo());
        carroExistente.setPreco(carroDtoRequest.getPreco());

        //salvando o carro atualizado no bd
        Carro carroAtualizado = carroRepository.save(carroExistente);

        return new ResponseEntity<>(new CarroDto(carroAtualizado), HttpStatus.OK);
    }

}
