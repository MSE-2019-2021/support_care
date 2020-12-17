package uc.dei.mse.supportivecare.service.mapper;

import java.util.Set;
import org.mapstruct.*;
import uc.dei.mse.supportivecare.domain.*;
import uc.dei.mse.supportivecare.service.dto.DrugDTO;

/**
 * Mapper for the entity {@link Drug} and its DTO {@link DrugDTO}.
 */
@Mapper(componentModel = "spring", uses = { AdministrationMapper.class })
public interface DrugMapper extends EntityMapper<DrugDTO, Drug> {
    @Mapping(target = "administration", source = "administration", qualifiedByName = "type")
    DrugDTO toDto(Drug drug);

    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    DrugDTO toDtoName(Drug drug);

    @Named("nameSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    Set<DrugDTO> toDtoNameSet(Set<Drug> drug);
}
