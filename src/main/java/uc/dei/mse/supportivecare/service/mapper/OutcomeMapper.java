package uc.dei.mse.supportivecare.service.mapper;

import org.mapstruct.*;
import uc.dei.mse.supportivecare.domain.*;
import uc.dei.mse.supportivecare.service.dto.OutcomeDTO;

/**
 * Mapper for the entity {@link Outcome} and its DTO {@link OutcomeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OutcomeMapper extends EntityMapper<OutcomeDTO, Outcome> {
    @Mapping(target = "documents", ignore = true)
    @Mapping(target = "removeDocument", ignore = true)
    @Mapping(target = "symptoms", ignore = true)
    @Mapping(target = "removeSymptom", ignore = true)
    Outcome toEntity(OutcomeDTO outcomeDTO);

    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    OutcomeDTO toDtoName(Outcome outcome);
}
