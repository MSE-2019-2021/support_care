package uc.dei.mse.supportivecare.service.mapper;

import org.mapstruct.*;
import uc.dei.mse.supportivecare.domain.*;
import uc.dei.mse.supportivecare.service.dto.NoticeDTO;

/**
 * Mapper for the entity {@link Notice} and its DTO {@link NoticeDTO}.
 */
@Mapper(componentModel = "spring", uses = { DrugMapper.class })
public interface NoticeMapper extends EntityMapper<NoticeDTO, Notice> {
    @Mapping(target = "drug", source = "drug", qualifiedByName = "name")
    NoticeDTO toDto(Notice notice);
}
