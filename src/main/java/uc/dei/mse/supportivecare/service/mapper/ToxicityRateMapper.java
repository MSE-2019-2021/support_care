package uc.dei.mse.supportivecare.service.mapper;

import org.mapstruct.*;
import uc.dei.mse.supportivecare.domain.*;
import uc.dei.mse.supportivecare.service.dto.ToxicityRateDTO;

/**
 * Mapper for the entity {@link ToxicityRate} and its DTO {@link ToxicityRateDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ToxicityRateMapper extends EntityMapper<ToxicityRateDTO, ToxicityRate> {
    @Mapping(target = "symptoms", ignore = true)
    @Mapping(target = "removeSymptom", ignore = true)
    ToxicityRate toEntity(ToxicityRateDTO toxicityRateDTO);

    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ToxicityRateDTO toDtoName(ToxicityRate toxicityRate);
}
