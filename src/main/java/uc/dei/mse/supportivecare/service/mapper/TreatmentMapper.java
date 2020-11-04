package uc.dei.mse.supportivecare.service.mapper;

import org.mapstruct.*;
import uc.dei.mse.supportivecare.GeneratedByJHipster;
import uc.dei.mse.supportivecare.domain.*;
import uc.dei.mse.supportivecare.service.dto.TreatmentDTO;

/**
 * Mapper for the entity {@link Treatment} and its DTO {@link TreatmentDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
@GeneratedByJHipster
public interface TreatmentMapper extends EntityMapper<TreatmentDTO, Treatment> {
    @Mapping(target = "therapeuticRegimes", ignore = true)
    @Mapping(target = "removeTherapeuticRegime", ignore = true)
    Treatment toEntity(TreatmentDTO treatmentDTO);

    @Named("type")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "type", source = "type")
    TreatmentDTO toDtoType(Treatment treatment);
}
