package uc.dei.mse.supportivecare.service.mapper;

import java.util.Set;
import org.mapstruct.*;
import uc.dei.mse.supportivecare.domain.*;
import uc.dei.mse.supportivecare.service.dto.DocumentDTO;

/**
 * Mapper for the entity {@link Document} and its DTO {@link DocumentDTO}.
 */
@Mapper(componentModel = "spring", uses = { ContentMapper.class, OutcomeMapper.class })
public interface DocumentMapper extends EntityMapper<DocumentDTO, Document> {
    @Mapping(target = "content", source = "content", qualifiedByName = "id")
    @Mapping(target = "outcome", source = "outcome", qualifiedByName = "name")
    DocumentDTO toDto(Document document);

    @Named("nameSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "name", source = "name")
    Set<DocumentDTO> toDtoNameSet(Set<Document> document);
}
