package uc.dei.mse.supportcare.service.mapper;


import uc.dei.mse.supportcare.domain.*;
import uc.dei.mse.supportcare.service.dto.OutcomeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Outcome} and its DTO {@link OutcomeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OutcomeMapper extends EntityMapper<OutcomeDTO, Outcome> {



    default Outcome fromId(Long id) {
        if (id == null) {
            return null;
        }
        Outcome outcome = new Outcome();
        outcome.setId(id);
        return outcome;
    }
}
