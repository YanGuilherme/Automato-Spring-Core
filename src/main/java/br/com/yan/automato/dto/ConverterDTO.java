package br.com.yan.automato.dto;

import br.com.yan.automato.service.AutomatoService;

public class ConverterDTO {

    AutomatoService automatoService;


    private String id;


    public String getId() {
        return id;
    }

    public void setId(String id){
        this.id = id;
    }
}
