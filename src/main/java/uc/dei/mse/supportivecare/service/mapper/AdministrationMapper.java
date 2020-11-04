package uc.dei.mse.supportivecare.service.mapper;


import uc.dei.mse.supportivecare.domain.*;
import uc.dei.mse.supportivecare.service.dto.AdministrationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Administration} and its DTO {@link AdministrationDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AdministrationMapper extends EntityMapper<AdministrationDTO, Administration> {


    @Mapping(target = "drugs", ignore = true)
    @Mapping(target = "removeDrug", ignore = true)
    Administration toEntity(AdministrationDTO administrationDTO);

    default Administration fromId(Long id) {
        if (id == null) {
            return null;
        }
        Administration administration = new Administration();
        administration.setId(id);
        return administration;
    }
}
