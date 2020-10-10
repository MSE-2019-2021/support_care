package uc.dei.mse.supportcare.service.mapper;


import uc.dei.mse.supportcare.domain.*;
import uc.dei.mse.supportcare.service.dto.DrugDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Drug} and its DTO {@link DrugDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DrugMapper extends EntityMapper<DrugDTO, Drug> {



    default Drug fromId(Long id) {
        if (id == null) {
            return null;
        }
        Drug drug = new Drug();
        drug.setId(id);
        return drug;
    }
}
