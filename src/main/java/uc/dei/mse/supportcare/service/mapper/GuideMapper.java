package uc.dei.mse.supportcare.service.mapper;


import uc.dei.mse.supportcare.domain.*;
import uc.dei.mse.supportcare.service.dto.GuideDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Guide} and its DTO {@link GuideDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GuideMapper extends EntityMapper<GuideDTO, Guide> {



    default Guide fromId(Long id) {
        if (id == null) {
            return null;
        }
        Guide guide = new Guide();
        guide.setId(id);
        return guide;
    }
}
