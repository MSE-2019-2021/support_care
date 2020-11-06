package uc.dei.mse.supportivecare.service.mapper;

import org.mapstruct.*;
import uc.dei.mse.supportivecare.domain.*;
import uc.dei.mse.supportivecare.service.dto.ContentDTO;

/**
 * Mapper for the entity {@link Content} and its DTO {@link ContentDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ContentMapper extends EntityMapper<ContentDTO, Content> {
    @Mapping(target = "document", ignore = true)
    Content toEntity(ContentDTO contentDTO);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ContentDTO toDtoId(Content content);
}
