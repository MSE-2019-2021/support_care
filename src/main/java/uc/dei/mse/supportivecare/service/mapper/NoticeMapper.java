package uc.dei.mse.supportivecare.service.mapper;

import java.util.Set;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import uc.dei.mse.supportivecare.domain.Notice;
import uc.dei.mse.supportivecare.service.dto.NoticeDTO;

/**
 * Mapper for the entity {@link Notice} and its DTO {@link NoticeDTO}.
 */
@Mapper(componentModel = "spring", uses = { ActiveSubstanceMapper.class })
public interface NoticeMapper extends EntityMapper<NoticeDTO, Notice> {
    @Mapping(target = "activeSubstance", source = "activeSubstance", qualifiedByName = "name")
    NoticeDTO toDto(Notice notice);

    @Named("nameSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "description", source = "description")
    Set<NoticeDTO> toDtoNameSet(Set<Notice> notice);
}
