package br.com.yan.automato.equivalencia.dto;

public class TesteEquivalencia {

    private String nome;

    private boolean sucesso;

    private String message;

    public TesteEquivalencia(boolean sucesso, String message, String nome) {
        this.sucesso = sucesso;
        this.message = message;
        this.nome = nome;
    }

    public void setSucesso(boolean sucesso) {
        this.sucesso = sucesso;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSucesso() {
        return sucesso;
    }

    public String getMessage() {
        return message;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
