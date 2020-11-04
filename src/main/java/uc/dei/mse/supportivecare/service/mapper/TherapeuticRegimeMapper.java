package uc.dei.mse.supportivecare.service.mapper;

import org.mapstruct.*;
import uc.dei.mse.supportivecare.GeneratedByJHipster;
import uc.dei.mse.supportivecare.domain.*;
import uc.dei.mse.supportivecare.service.dto.TherapeuticRegimeDTO;

/**
 * Mapper for the entity {@link TherapeuticRegime} and its DTO {@link TherapeuticRegimeDTO}.
 */
@Mapper(componentModel = "spring", uses = { DrugMapper.class, TreatmentMapper.class })
@GeneratedByJHipster
public interface TherapeuticRegimeMapper extends EntityMapper<TherapeuticRegimeDTO, TherapeuticRegime> {
    @Mapping(target = "treatment", source = "treatment", qualifiedByName = "type")
    TherapeuticRegimeDTO toDto(TherapeuticRegime therapeuticRegime);

    @Mapping(target = "removeDrug", ignore = true)
    TherapeuticRegime toEntity(TherapeuticRegimeDTO therapeuticRegimeDTO);
}
