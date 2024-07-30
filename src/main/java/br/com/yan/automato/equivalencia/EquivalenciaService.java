package br.com.yan.automato.equivalencia;

import br.com.yan.automato.equivalencia.dto.TesteEquivalencia;
import br.com.yan.automato.equivalencia.exception.EquivalenceException;
import br.com.yan.automato.equivalencia.validacoes.EquivalenceInterface;
import br.com.yan.automato.model.Automato;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class EquivalenciaService {

    private final ApplicationContext applicationContext;

    public EquivalenciaService(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public List<TesteEquivalencia> testarEquivalencia(Automato automato1, Automato automato2) {
        Map<String, EquivalenceInterface> validadores = applicationContext.getBeansOfType(EquivalenceInterface.class);
        List<TesteEquivalencia> errors = new ArrayList<>();
        for (EquivalenceInterface validator: validadores.values()){
            try {
                TesteEquivalencia error = validator.validate(automato1, automato2);
                errors.add(error);
            } catch (EquivalenceException ex){
                errors.add(new TesteEquivalencia(false, ex.getMessage(), "Excessão"));
            }
        }
        return errors;
    }

}
