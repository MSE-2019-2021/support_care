package uc.dei.mse.supportivecare.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uc.dei.mse.supportivecare.domain.Notice;
import uc.dei.mse.supportivecare.repository.NoticeRepository;
import uc.dei.mse.supportivecare.service.dto.NoticeDTO;
import uc.dei.mse.supportivecare.service.mapper.NoticeMapper;

/**
 * Service Implementation for managing {@link Notice}.
 */
@Service
@Transactional
public class NoticeService {

    private final Logger log = LoggerFactory.getLogger(NoticeService.class);

    private final NoticeRepository noticeRepository;

    private final NoticeMapper noticeMapper;

    public NoticeService(NoticeRepository noticeRepository, NoticeMapper noticeMapper) {
        this.noticeRepository = noticeRepository;
        this.noticeMapper = noticeMapper;
    }

    /**
     * Save a notice.
     *
     * @param noticeDTO the entity to save.
     * @return the persisted entity.
     */
    public NoticeDTO save(NoticeDTO noticeDTO) {
        log.debug("Request to save Notice : {}", noticeDTO);
        Notice notice = noticeMapper.toEntity(noticeDTO);
        notice = noticeRepository.save(notice);
        return noticeMapper.toDto(notice);
    }

    /**
     * Partially udpates a notice.
     *
     * @param noticeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public NoticeDTO partialUpdate(NoticeDTO noticeDTO) {
        log.debug("Request to partially update Notice : {}", noticeDTO);

        return noticeRepository
            .findById(noticeDTO.getId())
            .map(
                existingNotice -> {
                    if (noticeDTO.getDescription() != null) {
                        existingNotice.setDescription(noticeDTO.getDescription());
                    }

                    if (noticeDTO.getEvaluation() != null) {
                        existingNotice.setEvaluation(noticeDTO.getEvaluation());
                    }

                    if (noticeDTO.getIntervention() != null) {
                        existingNotice.setIntervention(noticeDTO.getIntervention());
                    }

                    return existingNotice;
                }
            )
            .map(noticeRepository::save)
            .map(noticeMapper::toDto)
            .get();
    }

    /**
     * Get all the notices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<NoticeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Notices");
        return noticeRepository.findAll(pageable).map(noticeMapper::toDto);
    }

    /**
     * Get one notice by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<NoticeDTO> findOne(Long id) {
        log.debug("Request to get Notice : {}", id);
        return noticeRepository.findById(id).map(noticeMapper::toDto);
    }

    /**
     * Delete the notice by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Notice : {}", id);
        noticeRepository.deleteById(id);
    }
}
