package uc.dei.mse.supportcare.service.mapper;


import uc.dei.mse.supportcare.domain.*;
import uc.dei.mse.supportcare.service.dto.TherapeuticRegimeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TherapeuticRegime} and its DTO {@link TherapeuticRegimeDTO}.
 */
@Mapper(componentModel = "spring", uses = {DrugMapper.class})
public interface TherapeuticRegimeMapper extends EntityMapper<TherapeuticRegimeDTO, TherapeuticRegime> {

    @Mapping(source = "drug.id", target = "drugId")
    TherapeuticRegimeDTO toDto(TherapeuticRegime therapeuticRegime);

    @Mapping(source = "drugId", target = "drug")
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
