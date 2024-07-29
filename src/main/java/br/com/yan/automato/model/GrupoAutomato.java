package br.com.yan.automato.model;

import br.com.yan.automato.service.AutomatoDeterministico;
import br.com.yan.automato.service.AutomatoMinimizado;
import br.com.yan.automato.service.AutomatoNaoDeterministico;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "grupo_automato")
public class GrupoAutomato {
    @Id
    private String id;

    private AutomatoDeterministico automatoDeterminisco;
    private AutomatoNaoDeterministico automatoNaoDeterminisco;
    private AutomatoMinimizado automatoMinimizado;

    public GrupoAutomato(Automato automato) {
        switch (automato){
            case AutomatoMinimizado minimizado -> this.automatoMinimizado = minimizado;
            case AutomatoDeterministico deterministico -> this.automatoDeterminisco = deterministico;
            case AutomatoNaoDeterministico naoDeterministico -> this.automatoNaoDeterminisco = naoDeterministico;
            default -> throw new IllegalStateException("Unexpected value: " + automato);
        }
    }
    public Automato getAutomato(String tipo){
        return switch (tipo){
            case "AFDM" -> this.automatoMinimizado;
            case "AFD" -> this.automatoDeterminisco;
            case "AFN" -> this.automatoNaoDeterminisco;
            default -> throw new IllegalStateException("Unexpected value: " + tipo);
        };
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AutomatoDeterministico getAutomatoDeterminisco() {
        return automatoDeterminisco;
    }

    public void setAutomatoDeterminisco(AutomatoDeterministico automatoDeterminisco) {
        this.automatoDeterminisco = automatoDeterminisco;
    }

    public AutomatoNaoDeterministico getAutomatoNaoDeterminisco() {
        return automatoNaoDeterminisco;
    }

    public void setAutomatoNaoDeterminisco(AutomatoNaoDeterministico automatoNaoDeterminisco) {
        this.automatoNaoDeterminisco = automatoNaoDeterminisco;
    }

    public AutomatoMinimizado getAutomatoDeterminiscoM() {
        return automatoMinimizado;
    }

    public void setAutomatoDeterminiscoM(AutomatoMinimizado automatoDeterminiscoM) {
        this.automatoMinimizado = automatoDeterminiscoM;
    }
}
