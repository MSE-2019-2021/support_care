package uc.dei.mse.supportivecare.service.mapper;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uc.dei.mse.supportivecare.service.dto.DocumentDTO;

@Service
public class DocumentContentMapper {

    private final Logger log = LoggerFactory.getLogger(DocumentMapper.class);

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
}
