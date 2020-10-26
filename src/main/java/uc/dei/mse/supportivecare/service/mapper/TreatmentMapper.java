package uc.dei.mse.supportivecare.service.mapper;


import uc.dei.mse.supportivecare.domain.*;
import uc.dei.mse.supportivecare.service.dto.TreatmentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Treatment} and its DTO {@link TreatmentDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TreatmentMapper extends EntityMapper<TreatmentDTO, Treatment> {



    default Treatment fromId(Long id) {
        if (id == null) {
            return null;
        }
        Treatment treatment = new Treatment();
        treatment.setId(id);
        return treatment;
    }
}
