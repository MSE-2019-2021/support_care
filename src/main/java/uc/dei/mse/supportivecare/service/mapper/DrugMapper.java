package uc.dei.mse.supportivecare.service.mapper;


import uc.dei.mse.supportivecare.domain.*;
import uc.dei.mse.supportivecare.service.dto.DrugDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Drug} and its DTO {@link DrugDTO}.
 */
@Mapper(componentModel = "spring", uses = {AdministrationMapper.class})
public interface DrugMapper extends EntityMapper<DrugDTO, Drug> {

    @Mapping(source = "administration.id", target = "administrationId")
    @Mapping(source = "administration.type", target = "administrationType")
    DrugDTO toDto(Drug drug);

    @Mapping(target = "notices", ignore = true)
    @Mapping(target = "removeNotice", ignore = true)
    @Mapping(source = "administrationId", target = "administration")
    @Mapping(target = "therapeuticRegimes", ignore = true)
    @Mapping(target = "removeTherapeuticRegime", ignore = true)
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
