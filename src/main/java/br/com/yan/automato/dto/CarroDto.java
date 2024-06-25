package br.com.yan.automato.dto;

import br.com.yan.automato.model.Carro;

import java.math.BigDecimal;

public class CarroDto {

    private String modelo;

    private BigDecimal preco;

    private Long id;


    public CarroDto() {
        // construtor padrão
    }


    public CarroDto(Carro carro) {
        this.modelo = carro.getModelo();
        this.preco = carro.getPreco();
        this.id = carro.getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Carro toModel(){
       return new Carro(this);
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

