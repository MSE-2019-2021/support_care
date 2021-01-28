package uc.dei.mse.supportivecare.service.mapper;

import org.mapstruct.*;
import uc.dei.mse.supportivecare.domain.*;
import uc.dei.mse.supportivecare.service.dto.ThumbDTO;

/**
 * Mapper for the entity {@link Thumb} and its DTO {@link ThumbDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ThumbMapper extends EntityMapper<ThumbDTO, Thumb> {}
