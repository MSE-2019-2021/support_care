package uc.dei.mse.supportivecare.service.mapper;

import org.mapstruct.*;
import uc.dei.mse.supportivecare.domain.*;
import uc.dei.mse.supportivecare.service.dto.SymptomDTO;

/**
 * Mapper for the entity {@link Symptom} and its DTO {@link SymptomDTO}.
 */
@Mapper(componentModel = "spring", uses = { TherapeuticRegimeMapper.class, OutcomeMapper.class, ToxicityRateMapper.class })
public interface SymptomMapper extends EntityMapper<SymptomDTO, Symptom> {
    @Mapping(target = "removeTherapeuticRegime", ignore = true)
    @Mapping(target = "removeOutcome", ignore = true)
    @Mapping(target = "removeToxicityRate", ignore = true)
    Symptom toEntity(SymptomDTO symptomDTO);
}
