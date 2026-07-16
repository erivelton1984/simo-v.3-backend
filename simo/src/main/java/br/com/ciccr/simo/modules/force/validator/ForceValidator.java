package br.com.ciccr.simo.modules.force.validator;

import br.com.ciccr.simo.common.exception.ConflictException;
import br.com.ciccr.simo.modules.force.constants.ForceMessages;
import br.com.ciccr.simo.modules.force.model.Force;
import br.com.ciccr.simo.modules.force.repository.ForceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ForceValidator {

    private final ForceRepository repository;

    public void validateCreate(String name) {

        if (repository.existsByNameIgnoreCase(name)) {
            throw new ConflictException(ForceMessages.NAME_ALREADY_EXISTS);
        }

    }

    public void validateUpdate(Long id, String name) {

        repository.findByNameIgnoreCase(name)
                .filter(force -> !force.getId().equals(id))
                .ifPresent(force -> {
                    throw new ConflictException(ForceMessages.NAME_ALREADY_EXISTS);
                });

    }

    public void validateDelete(Force force) {

        // Futuramente:
        // verificar se existem atendimentos vinculados
        // if (...) {
        //     throw new BusinessException(...);
        // }

    }

}