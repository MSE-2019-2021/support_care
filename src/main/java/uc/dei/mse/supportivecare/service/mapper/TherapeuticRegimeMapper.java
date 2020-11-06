package uc.dei.mse.supportivecare.service.mapper;

import org.mapstruct.*;
import uc.dei.mse.supportivecare.domain.*;
import uc.dei.mse.supportivecare.service.dto.TherapeuticRegimeDTO;

/**
 * Mapper for the entity {@link TherapeuticRegime} and its DTO {@link TherapeuticRegimeDTO}.
 */
@Mapper(componentModel = "spring", uses = { DrugMapper.class, TreatmentMapper.class })
public interface TherapeuticRegimeMapper extends EntityMapper<TherapeuticRegimeDTO, TherapeuticRegime> {
    @Mapping(target = "treatment", source = "treatment", qualifiedByName = "type")
    TherapeuticRegimeDTO toDto(TherapeuticRegime therapeuticRegime);

    @Mapping(target = "removeDrug", ignore = true)
    @Mapping(target = "symptoms", ignore = true)
    @Mapping(target = "removeSymptom", ignore = true)
    TherapeuticRegime toEntity(TherapeuticRegimeDTO therapeuticRegimeDTO);

    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    TherapeuticRegimeDTO toDtoName(TherapeuticRegime therapeuticRegime);
}
