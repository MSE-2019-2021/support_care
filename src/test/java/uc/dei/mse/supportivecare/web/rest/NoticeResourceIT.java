package uc.dei.mse.supportivecare.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import uc.dei.mse.supportivecare.IntegrationTest;
import uc.dei.mse.supportivecare.domain.ActiveSubstance;
import uc.dei.mse.supportivecare.domain.Notice;
import uc.dei.mse.supportivecare.repository.NoticeRepository;
import uc.dei.mse.supportivecare.service.NoticeQueryService;
import uc.dei.mse.supportivecare.service.dto.NoticeCriteria;
import uc.dei.mse.supportivecare.service.dto.NoticeDTO;
import uc.dei.mse.supportivecare.service.mapper.NoticeMapper;

/**
 * Integration tests for the {@link NoticeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NoticeResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_EVALUATION = "AAAAAAAAAA";
    private static final String UPDATED_EVALUATION = "BBBBBBBBBB";

    private static final String DEFAULT_INTERVENTION = "AAAAAAAAAA";
    private static final String UPDATED_INTERVENTION = "BBBBBBBBBB";

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private NoticeMapper noticeMapper;

    @Autowired
    private NoticeQueryService noticeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNoticeMockMvc;

    private Notice notice;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Notice createEntity(EntityManager em) {
        Notice notice = new Notice().description(DEFAULT_DESCRIPTION).evaluation(DEFAULT_EVALUATION).intervention(DEFAULT_INTERVENTION);
        // Add required entity
        ActiveSubstance activeSubstance;
        if (TestUtil.findAll(em, ActiveSubstance.class).isEmpty()) {
            activeSubstance = ActiveSubstanceResourceIT.createEntity(em);
            em.persist(activeSubstance);
            em.flush();
        } else {
            activeSubstance = TestUtil.findAll(em, ActiveSubstance.class).get(0);
        }
        notice.setActiveSubstance(activeSubstance);
        return notice;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Notice createUpdatedEntity(EntityManager em) {
        Notice notice = new Notice().description(UPDATED_DESCRIPTION).evaluation(UPDATED_EVALUATION).intervention(UPDATED_INTERVENTION);
        // Add required entity
        ActiveSubstance activeSubstance;
        if (TestUtil.findAll(em, ActiveSubstance.class).isEmpty()) {
            activeSubstance = ActiveSubstanceResourceIT.createUpdatedEntity(em);
            em.persist(activeSubstance);
            em.flush();
        } else {
            activeSubstance = TestUtil.findAll(em, ActiveSubstance.class).get(0);
        }
        notice.setActiveSubstance(activeSubstance);
        return notice;
    }

    @BeforeEach
    public void initTest() {
        notice = createEntity(em);
    }

    @Test
    @Transactional
    void createNotice() throws Exception {
        int databaseSizeBeforeCreate = noticeRepository.findAll().size();
        // Create the Notice
        NoticeDTO noticeDTO = noticeMapper.toDto(notice);
        restNoticeMockMvc
            .perform(post("/api/notices").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(noticeDTO)))
            .andExpect(status().isCreated());

        // Validate the Notice in the database
        List<Notice> noticeList = noticeRepository.findAll();
        assertThat(noticeList).hasSize(databaseSizeBeforeCreate + 1);
        Notice testNotice = noticeList.get(noticeList.size() - 1);
        assertThat(testNotice.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testNotice.getEvaluation()).isEqualTo(DEFAULT_EVALUATION);
        assertThat(testNotice.getIntervention()).isEqualTo(DEFAULT_INTERVENTION);
    }

    @Test
    @Transactional
    void createNoticeWithExistingId() throws Exception {
        // Create the Notice with an existing ID
        notice.setId(1L);
        NoticeDTO noticeDTO = noticeMapper.toDto(notice);

        int databaseSizeBeforeCreate = noticeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNoticeMockMvc
            .perform(post("/api/notices").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(noticeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Notice in the database
        List<Notice> noticeList = noticeRepository.findAll();
        assertThat(noticeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = noticeRepository.findAll().size();
        // set the field null
        notice.setDescription(null);

        // Create the Notice, which fails.
        NoticeDTO noticeDTO = noticeMapper.toDto(notice);

        restNoticeMockMvc
            .perform(post("/api/notices").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(noticeDTO)))
            .andExpect(status().isBadRequest());

        List<Notice> noticeList = noticeRepository.findAll();
        assertThat(noticeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEvaluationIsRequired() throws Exception {
        int databaseSizeBeforeTest = noticeRepository.findAll().size();
        // set the field null
        notice.setEvaluation(null);

        // Create the Notice, which fails.
        NoticeDTO noticeDTO = noticeMapper.toDto(notice);

        restNoticeMockMvc
            .perform(post("/api/notices").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(noticeDTO)))
            .andExpect(status().isBadRequest());

        List<Notice> noticeList = noticeRepository.findAll();
        assertThat(noticeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkInterventionIsRequired() throws Exception {
        int databaseSizeBeforeTest = noticeRepository.findAll().size();
        // set the field null
        notice.setIntervention(null);

        // Create the Notice, which fails.
        NoticeDTO noticeDTO = noticeMapper.toDto(notice);

        restNoticeMockMvc
            .perform(post("/api/notices").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(noticeDTO)))
            .andExpect(status().isBadRequest());

        List<Notice> noticeList = noticeRepository.findAll();
        assertThat(noticeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllNotices() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList
        restNoticeMockMvc
            .perform(get("/api/notices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notice.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].evaluation").value(hasItem(DEFAULT_EVALUATION)))
            .andExpect(jsonPath("$.[*].intervention").value(hasItem(DEFAULT_INTERVENTION)));
    }

    @Test
    @Transactional
    void getNotice() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get the notice
        restNoticeMockMvc
            .perform(get("/api/notices/{id}", notice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(notice.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.evaluation").value(DEFAULT_EVALUATION))
            .andExpect(jsonPath("$.intervention").value(DEFAULT_INTERVENTION));
    }

    @Test
    @Transactional
    void getNoticesByIdFiltering() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        Long id = notice.getId();

        defaultNoticeShouldBeFound("id.equals=" + id);
        defaultNoticeShouldNotBeFound("id.notEquals=" + id);

        defaultNoticeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultNoticeShouldNotBeFound("id.greaterThan=" + id);

        defaultNoticeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultNoticeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllNoticesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where description equals to DEFAULT_DESCRIPTION
        defaultNoticeShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the noticeList where description equals to UPDATED_DESCRIPTION
        defaultNoticeShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllNoticesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where description not equals to DEFAULT_DESCRIPTION
        defaultNoticeShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the noticeList where description not equals to UPDATED_DESCRIPTION
        defaultNoticeShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllNoticesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultNoticeShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the noticeList where description equals to UPDATED_DESCRIPTION
        defaultNoticeShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllNoticesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where description is not null
        defaultNoticeShouldBeFound("description.specified=true");

        // Get all the noticeList where description is null
        defaultNoticeShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllNoticesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where description contains DEFAULT_DESCRIPTION
        defaultNoticeShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the noticeList where description contains UPDATED_DESCRIPTION
        defaultNoticeShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllNoticesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where description does not contain DEFAULT_DESCRIPTION
        defaultNoticeShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the noticeList where description does not contain UPDATED_DESCRIPTION
        defaultNoticeShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllNoticesByEvaluationIsEqualToSomething() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where evaluation equals to DEFAULT_EVALUATION
        defaultNoticeShouldBeFound("evaluation.equals=" + DEFAULT_EVALUATION);

        // Get all the noticeList where evaluation equals to UPDATED_EVALUATION
        defaultNoticeShouldNotBeFound("evaluation.equals=" + UPDATED_EVALUATION);
    }

    @Test
    @Transactional
    void getAllNoticesByEvaluationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where evaluation not equals to DEFAULT_EVALUATION
        defaultNoticeShouldNotBeFound("evaluation.notEquals=" + DEFAULT_EVALUATION);

        // Get all the noticeList where evaluation not equals to UPDATED_EVALUATION
        defaultNoticeShouldBeFound("evaluation.notEquals=" + UPDATED_EVALUATION);
    }

    @Test
    @Transactional
    void getAllNoticesByEvaluationIsInShouldWork() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where evaluation in DEFAULT_EVALUATION or UPDATED_EVALUATION
        defaultNoticeShouldBeFound("evaluation.in=" + DEFAULT_EVALUATION + "," + UPDATED_EVALUATION);

        // Get all the noticeList where evaluation equals to UPDATED_EVALUATION
        defaultNoticeShouldNotBeFound("evaluation.in=" + UPDATED_EVALUATION);
    }

    @Test
    @Transactional
    void getAllNoticesByEvaluationIsNullOrNotNull() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where evaluation is not null
        defaultNoticeShouldBeFound("evaluation.specified=true");

        // Get all the noticeList where evaluation is null
        defaultNoticeShouldNotBeFound("evaluation.specified=false");
    }

    @Test
    @Transactional
    void getAllNoticesByEvaluationContainsSomething() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where evaluation contains DEFAULT_EVALUATION
        defaultNoticeShouldBeFound("evaluation.contains=" + DEFAULT_EVALUATION);

        // Get all the noticeList where evaluation contains UPDATED_EVALUATION
        defaultNoticeShouldNotBeFound("evaluation.contains=" + UPDATED_EVALUATION);
    }

    @Test
    @Transactional
    void getAllNoticesByEvaluationNotContainsSomething() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where evaluation does not contain DEFAULT_EVALUATION
        defaultNoticeShouldNotBeFound("evaluation.doesNotContain=" + DEFAULT_EVALUATION);

        // Get all the noticeList where evaluation does not contain UPDATED_EVALUATION
        defaultNoticeShouldBeFound("evaluation.doesNotContain=" + UPDATED_EVALUATION);
    }

    @Test
    @Transactional
    void getAllNoticesByInterventionIsEqualToSomething() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where intervention equals to DEFAULT_INTERVENTION
        defaultNoticeShouldBeFound("intervention.equals=" + DEFAULT_INTERVENTION);

        // Get all the noticeList where intervention equals to UPDATED_INTERVENTION
        defaultNoticeShouldNotBeFound("intervention.equals=" + UPDATED_INTERVENTION);
    }

    @Test
    @Transactional
    void getAllNoticesByInterventionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where intervention not equals to DEFAULT_INTERVENTION
        defaultNoticeShouldNotBeFound("intervention.notEquals=" + DEFAULT_INTERVENTION);

        // Get all the noticeList where intervention not equals to UPDATED_INTERVENTION
        defaultNoticeShouldBeFound("intervention.notEquals=" + UPDATED_INTERVENTION);
    }

    @Test
    @Transactional
    void getAllNoticesByInterventionIsInShouldWork() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where intervention in DEFAULT_INTERVENTION or UPDATED_INTERVENTION
        defaultNoticeShouldBeFound("intervention.in=" + DEFAULT_INTERVENTION + "," + UPDATED_INTERVENTION);

        // Get all the noticeList where intervention equals to UPDATED_INTERVENTION
        defaultNoticeShouldNotBeFound("intervention.in=" + UPDATED_INTERVENTION);
    }

    @Test
    @Transactional
    void getAllNoticesByInterventionIsNullOrNotNull() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where intervention is not null
        defaultNoticeShouldBeFound("intervention.specified=true");

        // Get all the noticeList where intervention is null
        defaultNoticeShouldNotBeFound("intervention.specified=false");
    }

    @Test
    @Transactional
    void getAllNoticesByInterventionContainsSomething() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where intervention contains DEFAULT_INTERVENTION
        defaultNoticeShouldBeFound("intervention.contains=" + DEFAULT_INTERVENTION);

        // Get all the noticeList where intervention contains UPDATED_INTERVENTION
        defaultNoticeShouldNotBeFound("intervention.contains=" + UPDATED_INTERVENTION);
    }

    @Test
    @Transactional
    void getAllNoticesByInterventionNotContainsSomething() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where intervention does not contain DEFAULT_INTERVENTION
        defaultNoticeShouldNotBeFound("intervention.doesNotContain=" + DEFAULT_INTERVENTION);

        // Get all the noticeList where intervention does not contain UPDATED_INTERVENTION
        defaultNoticeShouldBeFound("intervention.doesNotContain=" + UPDATED_INTERVENTION);
    }

    @Test
    @Transactional
    void getAllNoticesByActiveSubstanceIsEqualToSomething() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);
        ActiveSubstance activeSubstance = ActiveSubstanceResourceIT.createEntity(em);
        em.persist(activeSubstance);
        em.flush();
        notice.setActiveSubstance(activeSubstance);
        noticeRepository.saveAndFlush(notice);
        Long activeSubstanceId = activeSubstance.getId();

        // Get all the noticeList where activeSubstance equals to activeSubstanceId
        defaultNoticeShouldBeFound("activeSubstanceId.equals=" + activeSubstanceId);

        // Get all the noticeList where activeSubstance equals to activeSubstanceId + 1
        defaultNoticeShouldNotBeFound("activeSubstanceId.equals=" + (activeSubstanceId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNoticeShouldBeFound(String filter) throws Exception {
        restNoticeMockMvc
            .perform(get("/api/notices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notice.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].evaluation").value(hasItem(DEFAULT_EVALUATION)))
            .andExpect(jsonPath("$.[*].intervention").value(hasItem(DEFAULT_INTERVENTION)));

        // Check, that the count call also returns 1
        restNoticeMockMvc
            .perform(get("/api/notices/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNoticeShouldNotBeFound(String filter) throws Exception {
        restNoticeMockMvc
            .perform(get("/api/notices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNoticeMockMvc
            .perform(get("/api/notices/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingNotice() throws Exception {
        // Get the notice
        restNoticeMockMvc.perform(get("/api/notices/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void updateNotice() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        int databaseSizeBeforeUpdate = noticeRepository.findAll().size();

        // Update the notice
        Notice updatedNotice = noticeRepository.findById(notice.getId()).get();
        // Disconnect from session so that the updates on updatedNotice are not directly saved in db
        em.detach(updatedNotice);
        updatedNotice.description(UPDATED_DESCRIPTION).evaluation(UPDATED_EVALUATION).intervention(UPDATED_INTERVENTION);
        NoticeDTO noticeDTO = noticeMapper.toDto(updatedNotice);

        restNoticeMockMvc
            .perform(put("/api/notices").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(noticeDTO)))
            .andExpect(status().isOk());

        // Validate the Notice in the database
        List<Notice> noticeList = noticeRepository.findAll();
        assertThat(noticeList).hasSize(databaseSizeBeforeUpdate);
        Notice testNotice = noticeList.get(noticeList.size() - 1);
        assertThat(testNotice.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testNotice.getEvaluation()).isEqualTo(UPDATED_EVALUATION);
        assertThat(testNotice.getIntervention()).isEqualTo(UPDATED_INTERVENTION);
    }

    @Test
    @Transactional
    void updateNonExistingNotice() throws Exception {
        int databaseSizeBeforeUpdate = noticeRepository.findAll().size();

        // Create the Notice
        NoticeDTO noticeDTO = noticeMapper.toDto(notice);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNoticeMockMvc
            .perform(put("/api/notices").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(noticeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Notice in the database
        List<Notice> noticeList = noticeRepository.findAll();
        assertThat(noticeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNoticeWithPatch() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        int databaseSizeBeforeUpdate = noticeRepository.findAll().size();

        // Update the notice using partial update
        Notice partialUpdatedNotice = new Notice();
        partialUpdatedNotice.setId(notice.getId());

        partialUpdatedNotice.description(UPDATED_DESCRIPTION).evaluation(UPDATED_EVALUATION).intervention(UPDATED_INTERVENTION);

        restNoticeMockMvc
            .perform(
                patch("/api/notices")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNotice))
            )
            .andExpect(status().isOk());

        // Validate the Notice in the database
        List<Notice> noticeList = noticeRepository.findAll();
        assertThat(noticeList).hasSize(databaseSizeBeforeUpdate);
        Notice testNotice = noticeList.get(noticeList.size() - 1);
        assertThat(testNotice.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testNotice.getEvaluation()).isEqualTo(UPDATED_EVALUATION);
        assertThat(testNotice.getIntervention()).isEqualTo(UPDATED_INTERVENTION);
    }

    @Test
    @Transactional
    void fullUpdateNoticeWithPatch() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        int databaseSizeBeforeUpdate = noticeRepository.findAll().size();

        // Update the notice using partial update
        Notice partialUpdatedNotice = new Notice();
        partialUpdatedNotice.setId(notice.getId());

        partialUpdatedNotice.description(UPDATED_DESCRIPTION).evaluation(UPDATED_EVALUATION).intervention(UPDATED_INTERVENTION);

        restNoticeMockMvc
            .perform(
                patch("/api/notices")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNotice))
            )
            .andExpect(status().isOk());

        // Validate the Notice in the database
        List<Notice> noticeList = noticeRepository.findAll();
        assertThat(noticeList).hasSize(databaseSizeBeforeUpdate);
        Notice testNotice = noticeList.get(noticeList.size() - 1);
        assertThat(testNotice.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testNotice.getEvaluation()).isEqualTo(UPDATED_EVALUATION);
        assertThat(testNotice.getIntervention()).isEqualTo(UPDATED_INTERVENTION);
    }

    @Test
    @Transactional
    void partialUpdateNoticeShouldThrown() throws Exception {
        // Update the notice without id should throw
        Notice partialUpdatedNotice = new Notice();

        restNoticeMockMvc
            .perform(
                patch("/api/notices")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNotice))
            )
            .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void deleteNotice() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        int databaseSizeBeforeDelete = noticeRepository.findAll().size();

        // Delete the notice
        restNoticeMockMvc
            .perform(delete("/api/notices/{id}", notice.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Notice> noticeList = noticeRepository.findAll();
        assertThat(noticeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
