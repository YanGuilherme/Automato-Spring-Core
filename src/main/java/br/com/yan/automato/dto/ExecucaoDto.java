package br.com.yan.automato.dto;

public class ExecucaoDto {
    private Boolean aceita;

    public ExecucaoDto(Boolean aceita) {
        this.aceita = aceita;
    }

    public ExecucaoDto() {
    }

    public Boolean getAceita() {
        return aceita;
    }

    public void setAceita(Boolean aceita) {
        this.aceita = aceita;
    }
}
