package uc.dei.mse.supportivecare.service.mapper;


import uc.dei.mse.supportivecare.domain.*;
import uc.dei.mse.supportivecare.service.dto.TherapeuticRegimeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TherapeuticRegime} and its DTO {@link TherapeuticRegimeDTO}.
 */
@Mapper(componentModel = "spring", uses = {DrugMapper.class, TreatmentMapper.class})
public interface TherapeuticRegimeMapper extends EntityMapper<TherapeuticRegimeDTO, TherapeuticRegime> {

    @Mapping(source = "treatment.id", target = "treatmentId")
    @Mapping(source = "treatment.type", target = "treatmentType")
    TherapeuticRegimeDTO toDto(TherapeuticRegime therapeuticRegime);

    @Mapping(target = "removeDrug", ignore = true)
    @Mapping(source = "treatmentId", target = "treatment")
    TherapeuticRegime toEntity(TherapeuticRegimeDTO therapeuticRegimeDTO);

    default TherapeuticRegime fromId(Long id) {
        if (id == null) {
            return null;
        }
        TherapeuticRegime therapeuticRegime = new TherapeuticRegime();
        therapeuticRegime.setId(id);
        return therapeuticRegime;
    }
}
