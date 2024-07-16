package br.com.yan.automato.enums;

public enum RespostaExec {
    ACEITA("Aceita"), REJEITA("Rejeita"), CADEIA_INVALIDA("Cadeia inválida");

    private String name;

    RespostaExec(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}