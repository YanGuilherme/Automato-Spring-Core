package br.com.yan.automato.model;
import br.com.yan.automato.enums.RespostaExec;
import br.com.yan.automato.service.AutomatoDeterministico;
import br.com.yan.automato.service.AutomatoNaoDeterministico;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;
import java.util.UUID;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "tipo"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = AutomatoDeterministico.class, name = "AFD"),
        @JsonSubTypes.Type(value = AutomatoNaoDeterministico.class, name = "AFN")
})
@Document(collection = "automato")
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class Automato {
    @Id
    private String id;
    protected String estadoInicial;
    protected Set<String> estadosAceitacao;
    protected String nome;



    public Automato(String nome, String estadoInicial, Set<String> estadosAceitacao) {
        this.nome = nome;
        this.id = UUID.randomUUID().toString();
        this.estadoInicial = estadoInicial;
        this.estadosAceitacao = estadosAceitacao;
    }

    public Automato() {
        this.id = UUID.randomUUID().toString();

    }

    public Automato(Automato automato){
        this.id = UUID.randomUUID().toString();
        this.estadoInicial = automato.estadoInicial;
        this.estadosAceitacao = automato.estadosAceitacao;
    }

    public RespostaExec validarProcessar(String cadeia){
        if(!cadeiaValida(cadeia)){
            return RespostaExec.CADEIA_INVALIDA;
        }
        return testaCadeia(cadeia);
    }

    private boolean cadeiaValida(String cadeia) {
        Set<Character> alfabeto = this.getAlfabeto();
        for(Character caractere : cadeia.toCharArray()){
            if (!alfabeto.contains(caractere)){
                return false;
            }
        }
        return true;
    }

    abstract public Set<String> getEstados();
    abstract public RespostaExec testaCadeia(String cadeia);
    abstract public Set<Character> getAlfabeto();
    abstract public String processarCadeia(String cadeia);

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getEstadoInicial() {
        return estadoInicial;
    }

    public void setEstadoInicial(String estadoInicial) {
        this.estadoInicial = estadoInicial;
    }

    public Set<String> getEstadosAceitacao() {
        return estadosAceitacao;
    }

    public void setEstadosAceitacao(Set<String> estadosAceitacao) {
        this.estadosAceitacao = estadosAceitacao;
    }
}

