package uc.dei.mse.supportivecare.service.mapper;

import org.mapstruct.*;
import uc.dei.mse.supportivecare.domain.*;
import uc.dei.mse.supportivecare.service.dto.NoticeDTO;

/**
 * Mapper for the entity {@link Notice} and its DTO {@link NoticeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface NoticeMapper extends EntityMapper<NoticeDTO, Notice> {
    @Mapping(target = "drugs", ignore = true)
    @Mapping(target = "removeDrug", ignore = true)
    Notice toEntity(NoticeDTO noticeDTO);

    @Named("description")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "description", source = "description")
    NoticeDTO toDtoDescription(Notice notice);
}
