package uc.dei.mse.supportivecare.service.mapper;


import uc.dei.mse.supportivecare.domain.*;
import uc.dei.mse.supportivecare.service.dto.DrugDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Drug} and its DTO {@link DrugDTO}.
 */
@Mapper(componentModel = "spring", uses = {AdministrationMapper.class, TherapeuticRegimeMapper.class})
public interface DrugMapper extends EntityMapper<DrugDTO, Drug> {

    @Mapping(source = "administration.id", target = "administrationId")
    @Mapping(source = "administration.type", target = "administrationType")
    @Mapping(source = "therapeuticRegime.id", target = "therapeuticRegimeId")
    @Mapping(source = "therapeuticRegime.name", target = "therapeuticRegimeName")
    DrugDTO toDto(Drug drug);

    @Mapping(source = "administrationId", target = "administration")
    @Mapping(target = "notices", ignore = true)
    @Mapping(target = "removeNotice", ignore = true)
    @Mapping(source = "therapeuticRegimeId", target = "therapeuticRegime")
    Drug toEntity(DrugDTO drugDTO);

    default Drug fromId(Long id) {
        if (id == null) {
            return null;
        }
        Drug drug = new Drug();
        drug.setId(id);
        return drug;
    }
}
