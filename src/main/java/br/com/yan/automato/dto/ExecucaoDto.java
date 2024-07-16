package br.com.yan.automato.dto;

import br.com.yan.automato.enums.RespostaExec;

public class ExecucaoDto {
    private Boolean aceita;
    private String message;

    private String getMessageText(RespostaExec resposta){
        return switch (resposta){
            case REJEITA -> "Não parou em algum estado final";
            case CADEIA_INVALIDA -> "Cadeia  inválida!";
            default -> null;
        };

    }
    public ExecucaoDto(RespostaExec exec) {
        this.aceita = RespostaExec.ACEITA.equals(exec);
        this.message = getMessageText(exec);
    }

    public ExecucaoDto() {
    }

    public Boolean getAceita() {
        return aceita;
    }

    public void setAceita(Boolean aceita) {
        this.aceita = aceita;
    }

    public String getMessage() {
        return message;
    }
}
