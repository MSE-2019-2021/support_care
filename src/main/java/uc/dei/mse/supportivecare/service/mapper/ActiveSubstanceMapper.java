package uc.dei.mse.supportivecare.service.mapper;

import java.util.Set;
import org.mapstruct.*;
import uc.dei.mse.supportivecare.domain.*;
import uc.dei.mse.supportivecare.service.dto.ActiveSubstanceDTO;

/**
 * Mapper for the entity {@link ActiveSubstance} and its DTO {@link ActiveSubstanceDTO}.
 */
@Mapper(componentModel = "spring", uses = { NoticeMapper.class, AdministrationMapper.class })
public interface ActiveSubstanceMapper extends EntityMapper<ActiveSubstanceDTO, ActiveSubstance> {
    @Mapping(target = "notices", source = "notices", qualifiedByName = "nameSet")
    @Mapping(target = "administration", source = "administration", qualifiedByName = "type")
    ActiveSubstanceDTO toDto(ActiveSubstance activeSubstance);

    @Mapping(target = "removeNotice", ignore = true)
    ActiveSubstance toEntity(ActiveSubstanceDTO activeSubstanceDTO);

    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ActiveSubstanceDTO toDtoName(ActiveSubstance activeSubstance);

    @Named("nameSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    Set<ActiveSubstanceDTO> toDtoNameSet(Set<ActiveSubstance> activeSubstance);
}
