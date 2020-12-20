package uc.dei.mse.supportivecare.service.mapper;

import java.util.Set;
import org.mapstruct.*;
import uc.dei.mse.supportivecare.domain.*;
import uc.dei.mse.supportivecare.service.dto.SymptomDTO;

/**
 * Mapper for the entity {@link Symptom} and its DTO {@link SymptomDTO}.
 */
@Mapper(componentModel = "spring", uses = { TherapeuticRegimeMapper.class, OutcomeMapper.class })
public interface SymptomMapper extends EntityMapper<SymptomDTO, Symptom> {
    @Mapping(target = "therapeuticRegimes", source = "therapeuticRegimes", qualifiedByName = "nameSet")
    @Mapping(target = "outcomes", source = "outcomes", qualifiedByName = "nameSet")
    SymptomDTO toDto(Symptom symptom);

    @Mapping(target = "removeTherapeuticRegime", ignore = true)
    @Mapping(target = "removeOutcome", ignore = true)
    Symptom toEntity(SymptomDTO symptomDTO);

    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    SymptomDTO toDtoName(Symptom symptom);
}
