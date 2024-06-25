package br.com.yan.automato.model;

import br.com.yan.automato.dto.CarroDto;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Carro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String modelo;

    private BigDecimal preco;

    public Carro() {
    }

    public Carro(String modelo, BigDecimal preco) {
        this.modelo = modelo;
        this.preco = preco;
    }

    public Carro(CarroDto carroDto) {
        this.modelo = carroDto.getModelo();
        this.preco = carroDto.getPreco();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }
}
