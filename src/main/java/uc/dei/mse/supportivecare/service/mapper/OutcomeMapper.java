package uc.dei.mse.supportivecare.service.mapper;

import java.util.Set;
import org.mapstruct.*;
import uc.dei.mse.supportivecare.domain.*;
import uc.dei.mse.supportivecare.service.dto.OutcomeDTO;

/**
 * Mapper for the entity {@link Outcome} and its DTO {@link OutcomeDTO}.
 */
@Mapper(componentModel = "spring", uses = { DocumentMapper.class })
public interface OutcomeMapper extends EntityMapper<OutcomeDTO, Outcome> {
    @Mapping(target = "documents", source = "documents", qualifiedByName = "nameSet")
    OutcomeDTO toDto(Outcome outcome);

    @Mapping(target = "removeDocument", ignore = true)
    Outcome toEntity(OutcomeDTO outcomeDTO);

    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    OutcomeDTO toDtoName(Outcome outcome);

    @Named("nameSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    Set<OutcomeDTO> toDtoNameSet(Set<Outcome> outcome);
}
