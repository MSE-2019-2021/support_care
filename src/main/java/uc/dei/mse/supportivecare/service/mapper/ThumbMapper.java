package uc.dei.mse.supportivecare.service.mapper;

import org.mapstruct.Mapper;
import uc.dei.mse.supportivecare.domain.Thumb;
import uc.dei.mse.supportivecare.service.dto.ThumbDTO;

/**
 * Mapper for the entity {@link Thumb} and its DTO {@link ThumbDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ThumbMapper {
    ThumbDTO toDTO(Thumb thumb);
}
