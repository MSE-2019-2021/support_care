package uc.dei.mse.supportivecare.service.mapper;

import java.util.Set;
import org.mapstruct.*;
import uc.dei.mse.supportivecare.domain.*;
import uc.dei.mse.supportivecare.service.dto.ToxicityRateDTO;

/**
 * Mapper for the entity {@link ToxicityRate} and its DTO {@link ToxicityRateDTO}.
 */
@Mapper(componentModel = "spring", uses = { SymptomMapper.class })
public interface ToxicityRateMapper extends EntityMapper<ToxicityRateDTO, ToxicityRate> {
    @Mapping(target = "symptom", source = "symptom", qualifiedByName = "name")
    ToxicityRateDTO toDto(ToxicityRate toxicityRate);

    @Named("nameSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    Set<ToxicityRateDTO> toDtoNameSet(Set<ToxicityRate> toxicityRate);
}
