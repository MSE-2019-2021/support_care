package uc.dei.mse.supportivecare.service.mapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uc.dei.mse.supportivecare.domain.*;
import uc.dei.mse.supportivecare.service.dto.DocumentDTO;

/**
 * Mapper for the entity {@link Document} and its DTO {@link DocumentDTO}.
 */

@Service
public class DocumentMapper {

    private final Logger log = LoggerFactory.getLogger(DocumentMapper.class);

    @Autowired
    private ContentMapper contentMapper;

    @Autowired
    private OutcomeMapper outcomeMapper;

    public Set<DocumentDTO> multiPartFilesToDocuments(List<MultipartFile> files) {
        return files.stream().map((this::multiPartFileToDocument)).collect(Collectors.toSet());
    }

    public DocumentDTO multiPartFileToDocument(MultipartFile file) {
        DocumentDTO document = new DocumentDTO();
        document.setTitle(file.getOriginalFilename());
        document.setSize(file.getSize());
        document.setMimeType(file.getContentType());
        try {
            document.addContent(file.getBytes());
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return document;
    }

    @Mapping(target = "content", source = "content", qualifiedByName = "id")
    @Mapping(target = "outcome", source = "outcome", qualifiedByName = "name")
    public DocumentDTO toDto(Document document) {
        if (document == null) {
            return null;
        }

        DocumentDTO documentDTO = new DocumentDTO();

        documentDTO.setContent(contentMapper.toDtoId(document.getContent()));
        documentDTO.setOutcome(outcomeMapper.toDtoName(document.getOutcome()));
        documentDTO.setCreatedBy(document.getCreatedBy());
        documentDTO.setCreatedDate(document.getCreatedDate());
        documentDTO.setLastModifiedBy(document.getLastModifiedBy());
        documentDTO.setLastModifiedDate(document.getLastModifiedDate());
        documentDTO.setId(document.getId());
        documentDTO.setTitle(document.getTitle());
        documentDTO.setSize(document.getSize());
        documentDTO.setMimeType(document.getMimeType());

        return documentDTO;
    }

    public List<DocumentDTO> toDto(List<Document> entityList) {
        if (entityList == null) {
            return null;
        }

        List<DocumentDTO> list = new ArrayList<DocumentDTO>(entityList.size());
        for (Document document : entityList) {
            list.add(toDto(document));
        }

        return list;
    }

    public Document toEntity(DocumentDTO dto) {
        if (dto == null) {
            return null;
        }

        Document document = new Document();

        document.setCreatedBy(dto.getCreatedBy());
        document.setCreatedDate(dto.getCreatedDate());
        document.setLastModifiedBy(dto.getLastModifiedBy());
        document.setLastModifiedDate(dto.getLastModifiedDate());
        document.id(dto.getId());
        document.setTitle(dto.getTitle());
        document.setSize(dto.getSize());
        document.setMimeType(dto.getMimeType());
        document.setContent(contentMapper.toEntity(dto.getContent()));
        document.setOutcome(outcomeMapper.toEntity(dto.getOutcome()));

        return document;
    }
}
