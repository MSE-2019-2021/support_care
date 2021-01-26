package uc.dei.mse.supportivecare.service.mapper;

import org.mapstruct.Mapper;
import uc.dei.mse.supportivecare.domain.projection.ThumbCount;
import uc.dei.mse.supportivecare.service.dto.ThumbCountDTO;

/**
 * Mapper for the entity {@link ThumbCount} and its DTO {@link ThumbCountDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ThumbCountMapper {
    ThumbCountDTO toDTO(ThumbCount thumbCount);
}
