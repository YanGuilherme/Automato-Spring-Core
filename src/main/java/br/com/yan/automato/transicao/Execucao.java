package br.com.yan.automato.transicao;

public class Execucao {

    private Automato automato;

    private String cadeia;

    public Execucao() {
    }

    Execucao(Automato automato, String cadeia) {
        this.automato = automato;
        this.cadeia = cadeia;
    }

    public Automato getAutomato() {
        return automato;
    }

    public void setAutomato(Automato automatoDto) {
        this.automato = automatoDto;
    }

    public String getCadeia() {
        return cadeia;
    }

    public void setCadeia(String cadeia) {
        this.cadeia = cadeia;
    }

}
