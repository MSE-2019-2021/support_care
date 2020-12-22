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
import uc.dei.mse.supportivecare.domain.Administration;
import uc.dei.mse.supportivecare.domain.Notice;
import uc.dei.mse.supportivecare.domain.TherapeuticRegime;
import uc.dei.mse.supportivecare.repository.ActiveSubstanceRepository;
import uc.dei.mse.supportivecare.service.ActiveSubstanceQueryService;
import uc.dei.mse.supportivecare.service.dto.ActiveSubstanceCriteria;
import uc.dei.mse.supportivecare.service.dto.ActiveSubstanceDTO;
import uc.dei.mse.supportivecare.service.mapper.ActiveSubstanceMapper;

/**
 * Integration tests for the {@link ActiveSubstanceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ActiveSubstanceResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DOSAGE = "AAAAAAAAAA";
    private static final String UPDATED_DOSAGE = "BBBBBBBBBB";

    private static final String DEFAULT_FORM = "AAAAAAAAAA";
    private static final String UPDATED_FORM = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ActiveSubstanceRepository activeSubstanceRepository;

    @Autowired
    private ActiveSubstanceMapper activeSubstanceMapper;

    @Autowired
    private ActiveSubstanceQueryService activeSubstanceQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restActiveSubstanceMockMvc;

    private ActiveSubstance activeSubstance;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ActiveSubstance createEntity(EntityManager em) {
        ActiveSubstance activeSubstance = new ActiveSubstance()
            .name(DEFAULT_NAME)
            .dosage(DEFAULT_DOSAGE)
            .form(DEFAULT_FORM)
            .description(DEFAULT_DESCRIPTION);
        return activeSubstance;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ActiveSubstance createUpdatedEntity(EntityManager em) {
        ActiveSubstance activeSubstance = new ActiveSubstance()
            .name(UPDATED_NAME)
            .dosage(UPDATED_DOSAGE)
            .form(UPDATED_FORM)
            .description(UPDATED_DESCRIPTION);
        return activeSubstance;
    }

    @BeforeEach
    public void initTest() {
        activeSubstance = createEntity(em);
    }

    @Test
    @Transactional
    void createActiveSubstance() throws Exception {
        int databaseSizeBeforeCreate = activeSubstanceRepository.findAll().size();
        // Create the ActiveSubstance
        ActiveSubstanceDTO activeSubstanceDTO = activeSubstanceMapper.toDto(activeSubstance);
        restActiveSubstanceMockMvc
            .perform(
                post("/api/active-substances")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(activeSubstanceDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ActiveSubstance in the database
        List<ActiveSubstance> activeSubstanceList = activeSubstanceRepository.findAll();
        assertThat(activeSubstanceList).hasSize(databaseSizeBeforeCreate + 1);
        ActiveSubstance testActiveSubstance = activeSubstanceList.get(activeSubstanceList.size() - 1);
        assertThat(testActiveSubstance.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testActiveSubstance.getDosage()).isEqualTo(DEFAULT_DOSAGE);
        assertThat(testActiveSubstance.getForm()).isEqualTo(DEFAULT_FORM);
        assertThat(testActiveSubstance.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createActiveSubstanceWithExistingId() throws Exception {
        // Create the ActiveSubstance with an existing ID
        activeSubstance.setId(1L);
        ActiveSubstanceDTO activeSubstanceDTO = activeSubstanceMapper.toDto(activeSubstance);

        int databaseSizeBeforeCreate = activeSubstanceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restActiveSubstanceMockMvc
            .perform(
                post("/api/active-substances")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(activeSubstanceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ActiveSubstance in the database
        List<ActiveSubstance> activeSubstanceList = activeSubstanceRepository.findAll();
        assertThat(activeSubstanceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = activeSubstanceRepository.findAll().size();
        // set the field null
        activeSubstance.setName(null);

        // Create the ActiveSubstance, which fails.
        ActiveSubstanceDTO activeSubstanceDTO = activeSubstanceMapper.toDto(activeSubstance);

        restActiveSubstanceMockMvc
            .perform(
                post("/api/active-substances")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(activeSubstanceDTO))
            )
            .andExpect(status().isBadRequest());

        List<ActiveSubstance> activeSubstanceList = activeSubstanceRepository.findAll();
        assertThat(activeSubstanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDosageIsRequired() throws Exception {
        int databaseSizeBeforeTest = activeSubstanceRepository.findAll().size();
        // set the field null
        activeSubstance.setDosage(null);

        // Create the ActiveSubstance, which fails.
        ActiveSubstanceDTO activeSubstanceDTO = activeSubstanceMapper.toDto(activeSubstance);

        restActiveSubstanceMockMvc
            .perform(
                post("/api/active-substances")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(activeSubstanceDTO))
            )
            .andExpect(status().isBadRequest());

        List<ActiveSubstance> activeSubstanceList = activeSubstanceRepository.findAll();
        assertThat(activeSubstanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFormIsRequired() throws Exception {
        int databaseSizeBeforeTest = activeSubstanceRepository.findAll().size();
        // set the field null
        activeSubstance.setForm(null);

        // Create the ActiveSubstance, which fails.
        ActiveSubstanceDTO activeSubstanceDTO = activeSubstanceMapper.toDto(activeSubstance);

        restActiveSubstanceMockMvc
            .perform(
                post("/api/active-substances")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(activeSubstanceDTO))
            )
            .andExpect(status().isBadRequest());

        List<ActiveSubstance> activeSubstanceList = activeSubstanceRepository.findAll();
        assertThat(activeSubstanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllActiveSubstances() throws Exception {
        // Initialize the database
        activeSubstanceRepository.saveAndFlush(activeSubstance);

        // Get all the activeSubstanceList
        restActiveSubstanceMockMvc
            .perform(get("/api/active-substances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activeSubstance.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].dosage").value(hasItem(DEFAULT_DOSAGE)))
            .andExpect(jsonPath("$.[*].form").value(hasItem(DEFAULT_FORM)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getActiveSubstance() throws Exception {
        // Initialize the database
        activeSubstanceRepository.saveAndFlush(activeSubstance);

        // Get the activeSubstance
        restActiveSubstanceMockMvc
            .perform(get("/api/active-substances/{id}", activeSubstance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(activeSubstance.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.dosage").value(DEFAULT_DOSAGE))
            .andExpect(jsonPath("$.form").value(DEFAULT_FORM))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getActiveSubstancesByIdFiltering() throws Exception {
        // Initialize the database
        activeSubstanceRepository.saveAndFlush(activeSubstance);

        Long id = activeSubstance.getId();

        defaultActiveSubstanceShouldBeFound("id.equals=" + id);
        defaultActiveSubstanceShouldNotBeFound("id.notEquals=" + id);

        defaultActiveSubstanceShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultActiveSubstanceShouldNotBeFound("id.greaterThan=" + id);

        defaultActiveSubstanceShouldBeFound("id.lessThanOrEqual=" + id);
        defaultActiveSubstanceShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllActiveSubstancesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        activeSubstanceRepository.saveAndFlush(activeSubstance);

        // Get all the activeSubstanceList where name equals to DEFAULT_NAME
        defaultActiveSubstanceShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the activeSubstanceList where name equals to UPDATED_NAME
        defaultActiveSubstanceShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllActiveSubstancesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        activeSubstanceRepository.saveAndFlush(activeSubstance);

        // Get all the activeSubstanceList where name not equals to DEFAULT_NAME
        defaultActiveSubstanceShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the activeSubstanceList where name not equals to UPDATED_NAME
        defaultActiveSubstanceShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllActiveSubstancesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        activeSubstanceRepository.saveAndFlush(activeSubstance);

        // Get all the activeSubstanceList where name in DEFAULT_NAME or UPDATED_NAME
        defaultActiveSubstanceShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the activeSubstanceList where name equals to UPDATED_NAME
        defaultActiveSubstanceShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllActiveSubstancesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        activeSubstanceRepository.saveAndFlush(activeSubstance);

        // Get all the activeSubstanceList where name is not null
        defaultActiveSubstanceShouldBeFound("name.specified=true");

        // Get all the activeSubstanceList where name is null
        defaultActiveSubstanceShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllActiveSubstancesByNameContainsSomething() throws Exception {
        // Initialize the database
        activeSubstanceRepository.saveAndFlush(activeSubstance);

        // Get all the activeSubstanceList where name contains DEFAULT_NAME
        defaultActiveSubstanceShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the activeSubstanceList where name contains UPDATED_NAME
        defaultActiveSubstanceShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllActiveSubstancesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        activeSubstanceRepository.saveAndFlush(activeSubstance);

        // Get all the activeSubstanceList where name does not contain DEFAULT_NAME
        defaultActiveSubstanceShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the activeSubstanceList where name does not contain UPDATED_NAME
        defaultActiveSubstanceShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllActiveSubstancesByDosageIsEqualToSomething() throws Exception {
        // Initialize the database
        activeSubstanceRepository.saveAndFlush(activeSubstance);

        // Get all the activeSubstanceList where dosage equals to DEFAULT_DOSAGE
        defaultActiveSubstanceShouldBeFound("dosage.equals=" + DEFAULT_DOSAGE);

        // Get all the activeSubstanceList where dosage equals to UPDATED_DOSAGE
        defaultActiveSubstanceShouldNotBeFound("dosage.equals=" + UPDATED_DOSAGE);
    }

    @Test
    @Transactional
    void getAllActiveSubstancesByDosageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        activeSubstanceRepository.saveAndFlush(activeSubstance);

        // Get all the activeSubstanceList where dosage not equals to DEFAULT_DOSAGE
        defaultActiveSubstanceShouldNotBeFound("dosage.notEquals=" + DEFAULT_DOSAGE);

        // Get all the activeSubstanceList where dosage not equals to UPDATED_DOSAGE
        defaultActiveSubstanceShouldBeFound("dosage.notEquals=" + UPDATED_DOSAGE);
    }

    @Test
    @Transactional
    void getAllActiveSubstancesByDosageIsInShouldWork() throws Exception {
        // Initialize the database
        activeSubstanceRepository.saveAndFlush(activeSubstance);

        // Get all the activeSubstanceList where dosage in DEFAULT_DOSAGE or UPDATED_DOSAGE
        defaultActiveSubstanceShouldBeFound("dosage.in=" + DEFAULT_DOSAGE + "," + UPDATED_DOSAGE);

        // Get all the activeSubstanceList where dosage equals to UPDATED_DOSAGE
        defaultActiveSubstanceShouldNotBeFound("dosage.in=" + UPDATED_DOSAGE);
    }

    @Test
    @Transactional
    void getAllActiveSubstancesByDosageIsNullOrNotNull() throws Exception {
        // Initialize the database
        activeSubstanceRepository.saveAndFlush(activeSubstance);

        // Get all the activeSubstanceList where dosage is not null
        defaultActiveSubstanceShouldBeFound("dosage.specified=true");

        // Get all the activeSubstanceList where dosage is null
        defaultActiveSubstanceShouldNotBeFound("dosage.specified=false");
    }

    @Test
    @Transactional
    void getAllActiveSubstancesByDosageContainsSomething() throws Exception {
        // Initialize the database
        activeSubstanceRepository.saveAndFlush(activeSubstance);

        // Get all the activeSubstanceList where dosage contains DEFAULT_DOSAGE
        defaultActiveSubstanceShouldBeFound("dosage.contains=" + DEFAULT_DOSAGE);

        // Get all the activeSubstanceList where dosage contains UPDATED_DOSAGE
        defaultActiveSubstanceShouldNotBeFound("dosage.contains=" + UPDATED_DOSAGE);
    }

    @Test
    @Transactional
    void getAllActiveSubstancesByDosageNotContainsSomething() throws Exception {
        // Initialize the database
        activeSubstanceRepository.saveAndFlush(activeSubstance);

        // Get all the activeSubstanceList where dosage does not contain DEFAULT_DOSAGE
        defaultActiveSubstanceShouldNotBeFound("dosage.doesNotContain=" + DEFAULT_DOSAGE);

        // Get all the activeSubstanceList where dosage does not contain UPDATED_DOSAGE
        defaultActiveSubstanceShouldBeFound("dosage.doesNotContain=" + UPDATED_DOSAGE);
    }

    @Test
    @Transactional
    void getAllActiveSubstancesByFormIsEqualToSomething() throws Exception {
        // Initialize the database
        activeSubstanceRepository.saveAndFlush(activeSubstance);

        // Get all the activeSubstanceList where form equals to DEFAULT_FORM
        defaultActiveSubstanceShouldBeFound("form.equals=" + DEFAULT_FORM);

        // Get all the activeSubstanceList where form equals to UPDATED_FORM
        defaultActiveSubstanceShouldNotBeFound("form.equals=" + UPDATED_FORM);
    }

    @Test
    @Transactional
    void getAllActiveSubstancesByFormIsNotEqualToSomething() throws Exception {
        // Initialize the database
        activeSubstanceRepository.saveAndFlush(activeSubstance);

        // Get all the activeSubstanceList where form not equals to DEFAULT_FORM
        defaultActiveSubstanceShouldNotBeFound("form.notEquals=" + DEFAULT_FORM);

        // Get all the activeSubstanceList where form not equals to UPDATED_FORM
        defaultActiveSubstanceShouldBeFound("form.notEquals=" + UPDATED_FORM);
    }

    @Test
    @Transactional
    void getAllActiveSubstancesByFormIsInShouldWork() throws Exception {
        // Initialize the database
        activeSubstanceRepository.saveAndFlush(activeSubstance);

        // Get all the activeSubstanceList where form in DEFAULT_FORM or UPDATED_FORM
        defaultActiveSubstanceShouldBeFound("form.in=" + DEFAULT_FORM + "," + UPDATED_FORM);

        // Get all the activeSubstanceList where form equals to UPDATED_FORM
        defaultActiveSubstanceShouldNotBeFound("form.in=" + UPDATED_FORM);
    }

    @Test
    @Transactional
    void getAllActiveSubstancesByFormIsNullOrNotNull() throws Exception {
        // Initialize the database
        activeSubstanceRepository.saveAndFlush(activeSubstance);

        // Get all the activeSubstanceList where form is not null
        defaultActiveSubstanceShouldBeFound("form.specified=true");

        // Get all the activeSubstanceList where form is null
        defaultActiveSubstanceShouldNotBeFound("form.specified=false");
    }

    @Test
    @Transactional
    void getAllActiveSubstancesByFormContainsSomething() throws Exception {
        // Initialize the database
        activeSubstanceRepository.saveAndFlush(activeSubstance);

        // Get all the activeSubstanceList where form contains DEFAULT_FORM
        defaultActiveSubstanceShouldBeFound("form.contains=" + DEFAULT_FORM);

        // Get all the activeSubstanceList where form contains UPDATED_FORM
        defaultActiveSubstanceShouldNotBeFound("form.contains=" + UPDATED_FORM);
    }

    @Test
    @Transactional
    void getAllActiveSubstancesByFormNotContainsSomething() throws Exception {
        // Initialize the database
        activeSubstanceRepository.saveAndFlush(activeSubstance);

        // Get all the activeSubstanceList where form does not contain DEFAULT_FORM
        defaultActiveSubstanceShouldNotBeFound("form.doesNotContain=" + DEFAULT_FORM);

        // Get all the activeSubstanceList where form does not contain UPDATED_FORM
        defaultActiveSubstanceShouldBeFound("form.doesNotContain=" + UPDATED_FORM);
    }

    @Test
    @Transactional
    void getAllActiveSubstancesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        activeSubstanceRepository.saveAndFlush(activeSubstance);

        // Get all the activeSubstanceList where description equals to DEFAULT_DESCRIPTION
        defaultActiveSubstanceShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the activeSubstanceList where description equals to UPDATED_DESCRIPTION
        defaultActiveSubstanceShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllActiveSubstancesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        activeSubstanceRepository.saveAndFlush(activeSubstance);

        // Get all the activeSubstanceList where description not equals to DEFAULT_DESCRIPTION
        defaultActiveSubstanceShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the activeSubstanceList where description not equals to UPDATED_DESCRIPTION
        defaultActiveSubstanceShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllActiveSubstancesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        activeSubstanceRepository.saveAndFlush(activeSubstance);

        // Get all the activeSubstanceList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultActiveSubstanceShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the activeSubstanceList where description equals to UPDATED_DESCRIPTION
        defaultActiveSubstanceShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllActiveSubstancesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        activeSubstanceRepository.saveAndFlush(activeSubstance);

        // Get all the activeSubstanceList where description is not null
        defaultActiveSubstanceShouldBeFound("description.specified=true");

        // Get all the activeSubstanceList where description is null
        defaultActiveSubstanceShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllActiveSubstancesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        activeSubstanceRepository.saveAndFlush(activeSubstance);

        // Get all the activeSubstanceList where description contains DEFAULT_DESCRIPTION
        defaultActiveSubstanceShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the activeSubstanceList where description contains UPDATED_DESCRIPTION
        defaultActiveSubstanceShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllActiveSubstancesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        activeSubstanceRepository.saveAndFlush(activeSubstance);

        // Get all the activeSubstanceList where description does not contain DEFAULT_DESCRIPTION
        defaultActiveSubstanceShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the activeSubstanceList where description does not contain UPDATED_DESCRIPTION
        defaultActiveSubstanceShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllActiveSubstancesByNoticeIsEqualToSomething() throws Exception {
        // Initialize the database
        activeSubstanceRepository.saveAndFlush(activeSubstance);
        Notice notice = NoticeResourceIT.createEntity(em);
        em.persist(notice);
        em.flush();
        activeSubstance.addNotice(notice);
        activeSubstanceRepository.saveAndFlush(activeSubstance);
        Long noticeId = notice.getId();

        // Get all the activeSubstanceList where notice equals to noticeId
        defaultActiveSubstanceShouldBeFound("noticeId.equals=" + noticeId);

        // Get all the activeSubstanceList where notice equals to noticeId + 1
        defaultActiveSubstanceShouldNotBeFound("noticeId.equals=" + (noticeId + 1));
    }

    @Test
    @Transactional
    void getAllActiveSubstancesByAdministrationIsEqualToSomething() throws Exception {
        // Initialize the database
        activeSubstanceRepository.saveAndFlush(activeSubstance);
        Administration administration = AdministrationResourceIT.createEntity(em);
        em.persist(administration);
        em.flush();
        activeSubstance.setAdministration(administration);
        activeSubstanceRepository.saveAndFlush(activeSubstance);
        Long administrationId = administration.getId();

        // Get all the activeSubstanceList where administration equals to administrationId
        defaultActiveSubstanceShouldBeFound("administrationId.equals=" + administrationId);

        // Get all the activeSubstanceList where administration equals to administrationId + 1
        defaultActiveSubstanceShouldNotBeFound("administrationId.equals=" + (administrationId + 1));
    }

    @Test
    @Transactional
    void getAllActiveSubstancesByTherapeuticRegimeIsEqualToSomething() throws Exception {
        // Initialize the database
        activeSubstanceRepository.saveAndFlush(activeSubstance);
        TherapeuticRegime therapeuticRegime = TherapeuticRegimeResourceIT.createEntity(em);
        em.persist(therapeuticRegime);
        em.flush();
        activeSubstance.addTherapeuticRegime(therapeuticRegime);
        activeSubstanceRepository.saveAndFlush(activeSubstance);
        Long therapeuticRegimeId = therapeuticRegime.getId();

        // Get all the activeSubstanceList where therapeuticRegime equals to therapeuticRegimeId
        defaultActiveSubstanceShouldBeFound("therapeuticRegimeId.equals=" + therapeuticRegimeId);

        // Get all the activeSubstanceList where therapeuticRegime equals to therapeuticRegimeId + 1
        defaultActiveSubstanceShouldNotBeFound("therapeuticRegimeId.equals=" + (therapeuticRegimeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultActiveSubstanceShouldBeFound(String filter) throws Exception {
        restActiveSubstanceMockMvc
            .perform(get("/api/active-substances?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activeSubstance.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].dosage").value(hasItem(DEFAULT_DOSAGE)))
            .andExpect(jsonPath("$.[*].form").value(hasItem(DEFAULT_FORM)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restActiveSubstanceMockMvc
            .perform(get("/api/active-substances/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultActiveSubstanceShouldNotBeFound(String filter) throws Exception {
        restActiveSubstanceMockMvc
            .perform(get("/api/active-substances?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restActiveSubstanceMockMvc
            .perform(get("/api/active-substances/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingActiveSubstance() throws Exception {
        // Get the activeSubstance
        restActiveSubstanceMockMvc.perform(get("/api/active-substances/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void updateActiveSubstance() throws Exception {
        // Initialize the database
        activeSubstanceRepository.saveAndFlush(activeSubstance);

        int databaseSizeBeforeUpdate = activeSubstanceRepository.findAll().size();

        // Update the activeSubstance
        ActiveSubstance updatedActiveSubstance = activeSubstanceRepository.findById(activeSubstance.getId()).get();
        // Disconnect from session so that the updates on updatedActiveSubstance are not directly saved in db
        em.detach(updatedActiveSubstance);
        updatedActiveSubstance.name(UPDATED_NAME).dosage(UPDATED_DOSAGE).form(UPDATED_FORM).description(UPDATED_DESCRIPTION);
        ActiveSubstanceDTO activeSubstanceDTO = activeSubstanceMapper.toDto(updatedActiveSubstance);

        restActiveSubstanceMockMvc
            .perform(
                put("/api/active-substances")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(activeSubstanceDTO))
            )
            .andExpect(status().isOk());

        // Validate the ActiveSubstance in the database
        List<ActiveSubstance> activeSubstanceList = activeSubstanceRepository.findAll();
        assertThat(activeSubstanceList).hasSize(databaseSizeBeforeUpdate);
        ActiveSubstance testActiveSubstance = activeSubstanceList.get(activeSubstanceList.size() - 1);
        assertThat(testActiveSubstance.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testActiveSubstance.getDosage()).isEqualTo(UPDATED_DOSAGE);
        assertThat(testActiveSubstance.getForm()).isEqualTo(UPDATED_FORM);
        assertThat(testActiveSubstance.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void updateNonExistingActiveSubstance() throws Exception {
        int databaseSizeBeforeUpdate = activeSubstanceRepository.findAll().size();

        // Create the ActiveSubstance
        ActiveSubstanceDTO activeSubstanceDTO = activeSubstanceMapper.toDto(activeSubstance);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActiveSubstanceMockMvc
            .perform(
                put("/api/active-substances")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(activeSubstanceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ActiveSubstance in the database
        List<ActiveSubstance> activeSubstanceList = activeSubstanceRepository.findAll();
        assertThat(activeSubstanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateActiveSubstanceWithPatch() throws Exception {
        // Initialize the database
        activeSubstanceRepository.saveAndFlush(activeSubstance);

        int databaseSizeBeforeUpdate = activeSubstanceRepository.findAll().size();

        // Update the activeSubstance using partial update
        ActiveSubstance partialUpdatedActiveSubstance = new ActiveSubstance();
        partialUpdatedActiveSubstance.setId(activeSubstance.getId());

        partialUpdatedActiveSubstance.name(UPDATED_NAME).form(UPDATED_FORM).description(UPDATED_DESCRIPTION);

        restActiveSubstanceMockMvc
            .perform(
                patch("/api/active-substances")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedActiveSubstance))
            )
            .andExpect(status().isOk());

        // Validate the ActiveSubstance in the database
        List<ActiveSubstance> activeSubstanceList = activeSubstanceRepository.findAll();
        assertThat(activeSubstanceList).hasSize(databaseSizeBeforeUpdate);
        ActiveSubstance testActiveSubstance = activeSubstanceList.get(activeSubstanceList.size() - 1);
        assertThat(testActiveSubstance.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testActiveSubstance.getDosage()).isEqualTo(DEFAULT_DOSAGE);
        assertThat(testActiveSubstance.getForm()).isEqualTo(UPDATED_FORM);
        assertThat(testActiveSubstance.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateActiveSubstanceWithPatch() throws Exception {
        // Initialize the database
        activeSubstanceRepository.saveAndFlush(activeSubstance);

        int databaseSizeBeforeUpdate = activeSubstanceRepository.findAll().size();

        // Update the activeSubstance using partial update
        ActiveSubstance partialUpdatedActiveSubstance = new ActiveSubstance();
        partialUpdatedActiveSubstance.setId(activeSubstance.getId());

        partialUpdatedActiveSubstance.name(UPDATED_NAME).dosage(UPDATED_DOSAGE).form(UPDATED_FORM).description(UPDATED_DESCRIPTION);

        restActiveSubstanceMockMvc
            .perform(
                patch("/api/active-substances")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedActiveSubstance))
            )
            .andExpect(status().isOk());

        // Validate the ActiveSubstance in the database
        List<ActiveSubstance> activeSubstanceList = activeSubstanceRepository.findAll();
        assertThat(activeSubstanceList).hasSize(databaseSizeBeforeUpdate);
        ActiveSubstance testActiveSubstance = activeSubstanceList.get(activeSubstanceList.size() - 1);
        assertThat(testActiveSubstance.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testActiveSubstance.getDosage()).isEqualTo(UPDATED_DOSAGE);
        assertThat(testActiveSubstance.getForm()).isEqualTo(UPDATED_FORM);
        assertThat(testActiveSubstance.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void partialUpdateActiveSubstanceShouldThrown() throws Exception {
        // Update the activeSubstance without id should throw
        ActiveSubstance partialUpdatedActiveSubstance = new ActiveSubstance();

        restActiveSubstanceMockMvc
            .perform(
                patch("/api/active-substances")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedActiveSubstance))
            )
            .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void deleteActiveSubstance() throws Exception {
        // Initialize the database
        activeSubstanceRepository.saveAndFlush(activeSubstance);

        int databaseSizeBeforeDelete = activeSubstanceRepository.findAll().size();

        // Delete the activeSubstance
        restActiveSubstanceMockMvc
            .perform(delete("/api/active-substances/{id}", activeSubstance.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ActiveSubstance> activeSubstanceList = activeSubstanceRepository.findAll();
        assertThat(activeSubstanceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
