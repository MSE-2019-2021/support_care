package uc.dei.mse.supportivecare.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uc.dei.mse.supportivecare.domain.Content;
import uc.dei.mse.supportivecare.repository.ContentRepository;
import uc.dei.mse.supportivecare.service.dto.ContentDTO;
import uc.dei.mse.supportivecare.service.mapper.ContentMapper;

/**
 * Service Implementation for managing {@link Content}.
 */
@Service
@Transactional
public class ContentService {

    private final Logger log = LoggerFactory.getLogger(ContentService.class);

    private final ContentRepository contentRepository;

    private final ContentMapper contentMapper;

    public ContentService(ContentRepository contentRepository, ContentMapper contentMapper) {
        this.contentRepository = contentRepository;
        this.contentMapper = contentMapper;
    }

    /**
     * Save a content.
     *
     * @param contentDTO the entity to save.
     * @return the persisted entity.
     */
    public ContentDTO save(ContentDTO contentDTO) {
        log.debug("Request to save Content : {}", contentDTO);
        Content content = contentMapper.toEntity(contentDTO);
        content = contentRepository.save(content);
        return contentMapper.toDto(content);
    }

    /**
     * Partially udpates a content.
     *
     * @param contentDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ContentDTO> partialUpdate(ContentDTO contentDTO) {
        log.debug("Request to partially update Content : {}", contentDTO);

        return contentRepository
            .findById(contentDTO.getId())
            .map(
                existingContent -> {
                    if (contentDTO.getData() != null) {
                        existingContent.setData(contentDTO.getData());
                    }
                    if (contentDTO.getDataContentType() != null) {
                        existingContent.setDataContentType(contentDTO.getDataContentType());
                    }

                    return existingContent;
                }
            )
            .map(contentRepository::save)
            .map(contentMapper::toDto);
    }

    /**
     * Get all the contents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ContentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Contents");
        return contentRepository.findAll(pageable).map(contentMapper::toDto);
    }

    /**
     *  Get all the contents where Document is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ContentDTO> findAllWhereDocumentIsNull() {
        log.debug("Request to get all contents where Document is null");
        return StreamSupport
            .stream(contentRepository.findAll().spliterator(), false)
            .filter(content -> content.getDocument() == null)
            .map(contentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one content by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ContentDTO> findOne(Long id) {
        log.debug("Request to get Content : {}", id);
        return contentRepository.findById(id).map(contentMapper::toDto);
    }

    /**
     * Delete the content by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Content : {}", id);
        contentRepository.deleteById(id);
    }
}
