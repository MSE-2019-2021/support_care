package uc.dei.mse.supportivecare.service.mapper;

import java.util.Set;
import org.mapstruct.*;
import uc.dei.mse.supportivecare.domain.*;
import uc.dei.mse.supportivecare.service.dto.TherapeuticRegimeDTO;

/**
 * Mapper for the entity {@link TherapeuticRegime} and its DTO {@link TherapeuticRegimeDTO}.
 */
@Mapper(componentModel = "spring", uses = { ActiveSubstanceMapper.class, TreatmentMapper.class })
public interface TherapeuticRegimeMapper extends EntityMapper<TherapeuticRegimeDTO, TherapeuticRegime> {
    @Mapping(target = "activeSubstances", source = "activeSubstances", qualifiedByName = "nameSet")
    @Mapping(target = "treatment", source = "treatment", qualifiedByName = "type")
    TherapeuticRegimeDTO toDto(TherapeuticRegime therapeuticRegime);

    @Mapping(target = "removeActiveSubstance", ignore = true)
    TherapeuticRegime toEntity(TherapeuticRegimeDTO therapeuticRegimeDTO);

    @Named("nameSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    Set<TherapeuticRegimeDTO> toDtoNameSet(Set<TherapeuticRegime> therapeuticRegime);
}
