package br.com.yan.automato.equivalencia.exception;

public class EquivalenceException extends RuntimeException{

    private final String message;


    public EquivalenceException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
