package uc.dei.mse.supportivecare.service.mapper;

import org.mapstruct.*;
import uc.dei.mse.supportivecare.domain.*;
import uc.dei.mse.supportivecare.service.dto.AdministrationDTO;

/**
 * Mapper for the entity {@link Administration} and its DTO {@link AdministrationDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AdministrationMapper extends EntityMapper<AdministrationDTO, Administration> {
    @Named("type")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "type", source = "type")
    AdministrationDTO toDtoType(Administration administration);
}
