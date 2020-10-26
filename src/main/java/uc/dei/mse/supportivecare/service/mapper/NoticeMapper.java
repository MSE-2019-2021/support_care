package uc.dei.mse.supportivecare.service.mapper;


import uc.dei.mse.supportivecare.domain.*;
import uc.dei.mse.supportivecare.service.dto.NoticeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Notice} and its DTO {@link NoticeDTO}.
 */
@Mapper(componentModel = "spring", uses = {DrugMapper.class})
public interface NoticeMapper extends EntityMapper<NoticeDTO, Notice> {

    @Mapping(source = "drug.id", target = "drugId")
    @Mapping(source = "drug.name", target = "drugName")
    NoticeDTO toDto(Notice notice);

    @Mapping(source = "drugId", target = "drug")
    Notice toEntity(NoticeDTO noticeDTO);

    default Notice fromId(Long id) {
        if (id == null) {
            return null;
        }
        Notice notice = new Notice();
        notice.setId(id);
        return notice;
    }
}
