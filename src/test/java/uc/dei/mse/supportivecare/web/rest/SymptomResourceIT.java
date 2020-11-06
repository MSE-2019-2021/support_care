package uc.dei.mse.supportivecare.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import uc.dei.mse.supportivecare.SupportivecareApp;
import uc.dei.mse.supportivecare.domain.Outcome;
import uc.dei.mse.supportivecare.domain.Symptom;
import uc.dei.mse.supportivecare.domain.TherapeuticRegime;
import uc.dei.mse.supportivecare.domain.ToxicityRate;
import uc.dei.mse.supportivecare.repository.SymptomRepository;
import uc.dei.mse.supportivecare.service.SymptomQueryService;
import uc.dei.mse.supportivecare.service.SymptomService;
import uc.dei.mse.supportivecare.service.dto.SymptomCriteria;
import uc.dei.mse.supportivecare.service.dto.SymptomDTO;
import uc.dei.mse.supportivecare.service.mapper.SymptomMapper;

/**
 * Integration tests for the {@link SymptomResource} REST controller.
 */
@SpringBootTest(classes = SupportivecareApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class SymptomResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_NOTICE = "AAAAAAAAAA";
    private static final String UPDATED_NOTICE = "BBBBBBBBBB";

    @Autowired
    private SymptomRepository symptomRepository;

    @Mock
    private SymptomRepository symptomRepositoryMock;

    @Autowired
    private SymptomMapper symptomMapper;

    @Mock
    private SymptomService symptomServiceMock;

    @Autowired
    private SymptomQueryService symptomQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSymptomMockMvc;

    private Symptom symptom;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Symptom createEntity(EntityManager em) {
        Symptom symptom = new Symptom().name(DEFAULT_NAME).notice(DEFAULT_NOTICE);
        return symptom;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Symptom createUpdatedEntity(EntityManager em) {
        Symptom symptom = new Symptom().name(UPDATED_NAME).notice(UPDATED_NOTICE);
        return symptom;
    }

    @BeforeEach
    public void initTest() {
        symptom = createEntity(em);
    }

    @Test
    @Transactional
    void createSymptom() throws Exception {
        int databaseSizeBeforeCreate = symptomRepository.findAll().size();
        // Create the Symptom
        SymptomDTO symptomDTO = symptomMapper.toDto(symptom);
        restSymptomMockMvc
            .perform(post("/api/symptoms").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(symptomDTO)))
            .andExpect(status().isCreated());

        // Validate the Symptom in the database
        List<Symptom> symptomList = symptomRepository.findAll();
        assertThat(symptomList).hasSize(databaseSizeBeforeCreate + 1);
        Symptom testSymptom = symptomList.get(symptomList.size() - 1);
        assertThat(testSymptom.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSymptom.getNotice()).isEqualTo(DEFAULT_NOTICE);
    }

    @Test
    @Transactional
    void createSymptomWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = symptomRepository.findAll().size();

        // Create the Symptom with an existing ID
        symptom.setId(1L);
        SymptomDTO symptomDTO = symptomMapper.toDto(symptom);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSymptomMockMvc
            .perform(post("/api/symptoms").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(symptomDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Symptom in the database
        List<Symptom> symptomList = symptomRepository.findAll();
        assertThat(symptomList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = symptomRepository.findAll().size();
        // set the field null
        symptom.setName(null);

        // Create the Symptom, which fails.
        SymptomDTO symptomDTO = symptomMapper.toDto(symptom);

        restSymptomMockMvc
            .perform(post("/api/symptoms").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(symptomDTO)))
            .andExpect(status().isBadRequest());

        List<Symptom> symptomList = symptomRepository.findAll();
        assertThat(symptomList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSymptoms() throws Exception {
        // Initialize the database
        symptomRepository.saveAndFlush(symptom);

        // Get all the symptomList
        restSymptomMockMvc
            .perform(get("/api/symptoms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(symptom.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].notice").value(hasItem(DEFAULT_NOTICE)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSymptomsWithEagerRelationshipsIsEnabled() throws Exception {
        when(symptomServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSymptomMockMvc.perform(get("/api/symptoms?eagerload=true")).andExpect(status().isOk());

        verify(symptomServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSymptomsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(symptomServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSymptomMockMvc.perform(get("/api/symptoms?eagerload=true")).andExpect(status().isOk());

        verify(symptomServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getSymptom() throws Exception {
        // Initialize the database
        symptomRepository.saveAndFlush(symptom);

        // Get the symptom
        restSymptomMockMvc
            .perform(get("/api/symptoms/{id}", symptom.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(symptom.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.notice").value(DEFAULT_NOTICE));
    }

    @Test
    @Transactional
    void getSymptomsByIdFiltering() throws Exception {
        // Initialize the database
        symptomRepository.saveAndFlush(symptom);

        Long id = symptom.getId();

        defaultSymptomShouldBeFound("id.equals=" + id);
        defaultSymptomShouldNotBeFound("id.notEquals=" + id);

        defaultSymptomShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSymptomShouldNotBeFound("id.greaterThan=" + id);

        defaultSymptomShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSymptomShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSymptomsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        symptomRepository.saveAndFlush(symptom);

        // Get all the symptomList where name equals to DEFAULT_NAME
        defaultSymptomShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the symptomList where name equals to UPDATED_NAME
        defaultSymptomShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSymptomsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        symptomRepository.saveAndFlush(symptom);

        // Get all the symptomList where name not equals to DEFAULT_NAME
        defaultSymptomShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the symptomList where name not equals to UPDATED_NAME
        defaultSymptomShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSymptomsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        symptomRepository.saveAndFlush(symptom);

        // Get all the symptomList where name in DEFAULT_NAME or UPDATED_NAME
        defaultSymptomShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the symptomList where name equals to UPDATED_NAME
        defaultSymptomShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSymptomsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        symptomRepository.saveAndFlush(symptom);

        // Get all the symptomList where name is not null
        defaultSymptomShouldBeFound("name.specified=true");

        // Get all the symptomList where name is null
        defaultSymptomShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllSymptomsByNameContainsSomething() throws Exception {
        // Initialize the database
        symptomRepository.saveAndFlush(symptom);

        // Get all the symptomList where name contains DEFAULT_NAME
        defaultSymptomShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the symptomList where name contains UPDATED_NAME
        defaultSymptomShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSymptomsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        symptomRepository.saveAndFlush(symptom);

        // Get all the symptomList where name does not contain DEFAULT_NAME
        defaultSymptomShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the symptomList where name does not contain UPDATED_NAME
        defaultSymptomShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSymptomsByNoticeIsEqualToSomething() throws Exception {
        // Initialize the database
        symptomRepository.saveAndFlush(symptom);

        // Get all the symptomList where notice equals to DEFAULT_NOTICE
        defaultSymptomShouldBeFound("notice.equals=" + DEFAULT_NOTICE);

        // Get all the symptomList where notice equals to UPDATED_NOTICE
        defaultSymptomShouldNotBeFound("notice.equals=" + UPDATED_NOTICE);
    }

    @Test
    @Transactional
    void getAllSymptomsByNoticeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        symptomRepository.saveAndFlush(symptom);

        // Get all the symptomList where notice not equals to DEFAULT_NOTICE
        defaultSymptomShouldNotBeFound("notice.notEquals=" + DEFAULT_NOTICE);

        // Get all the symptomList where notice not equals to UPDATED_NOTICE
        defaultSymptomShouldBeFound("notice.notEquals=" + UPDATED_NOTICE);
    }

    @Test
    @Transactional
    void getAllSymptomsByNoticeIsInShouldWork() throws Exception {
        // Initialize the database
        symptomRepository.saveAndFlush(symptom);

        // Get all the symptomList where notice in DEFAULT_NOTICE or UPDATED_NOTICE
        defaultSymptomShouldBeFound("notice.in=" + DEFAULT_NOTICE + "," + UPDATED_NOTICE);

        // Get all the symptomList where notice equals to UPDATED_NOTICE
        defaultSymptomShouldNotBeFound("notice.in=" + UPDATED_NOTICE);
    }

    @Test
    @Transactional
    void getAllSymptomsByNoticeIsNullOrNotNull() throws Exception {
        // Initialize the database
        symptomRepository.saveAndFlush(symptom);

        // Get all the symptomList where notice is not null
        defaultSymptomShouldBeFound("notice.specified=true");

        // Get all the symptomList where notice is null
        defaultSymptomShouldNotBeFound("notice.specified=false");
    }

    @Test
    @Transactional
    void getAllSymptomsByNoticeContainsSomething() throws Exception {
        // Initialize the database
        symptomRepository.saveAndFlush(symptom);

        // Get all the symptomList where notice contains DEFAULT_NOTICE
        defaultSymptomShouldBeFound("notice.contains=" + DEFAULT_NOTICE);

        // Get all the symptomList where notice contains UPDATED_NOTICE
        defaultSymptomShouldNotBeFound("notice.contains=" + UPDATED_NOTICE);
    }

    @Test
    @Transactional
    void getAllSymptomsByNoticeNotContainsSomething() throws Exception {
        // Initialize the database
        symptomRepository.saveAndFlush(symptom);

        // Get all the symptomList where notice does not contain DEFAULT_NOTICE
        defaultSymptomShouldNotBeFound("notice.doesNotContain=" + DEFAULT_NOTICE);

        // Get all the symptomList where notice does not contain UPDATED_NOTICE
        defaultSymptomShouldBeFound("notice.doesNotContain=" + UPDATED_NOTICE);
    }

    @Test
    @Transactional
    void getAllSymptomsByTherapeuticRegimeIsEqualToSomething() throws Exception {
        // Initialize the database
        symptomRepository.saveAndFlush(symptom);
        TherapeuticRegime therapeuticRegime = TherapeuticRegimeResourceIT.createEntity(em);
        em.persist(therapeuticRegime);
        em.flush();
        symptom.addTherapeuticRegime(therapeuticRegime);
        symptomRepository.saveAndFlush(symptom);
        Long therapeuticRegimeId = therapeuticRegime.getId();

        // Get all the symptomList where therapeuticRegime equals to therapeuticRegimeId
        defaultSymptomShouldBeFound("therapeuticRegimeId.equals=" + therapeuticRegimeId);

        // Get all the symptomList where therapeuticRegime equals to therapeuticRegimeId + 1
        defaultSymptomShouldNotBeFound("therapeuticRegimeId.equals=" + (therapeuticRegimeId + 1));
    }

    @Test
    @Transactional
    void getAllSymptomsByOutcomeIsEqualToSomething() throws Exception {
        // Initialize the database
        symptomRepository.saveAndFlush(symptom);
        Outcome outcome = OutcomeResourceIT.createEntity(em);
        em.persist(outcome);
        em.flush();
        symptom.addOutcome(outcome);
        symptomRepository.saveAndFlush(symptom);
        Long outcomeId = outcome.getId();

        // Get all the symptomList where outcome equals to outcomeId
        defaultSymptomShouldBeFound("outcomeId.equals=" + outcomeId);

        // Get all the symptomList where outcome equals to outcomeId + 1
        defaultSymptomShouldNotBeFound("outcomeId.equals=" + (outcomeId + 1));
    }

    @Test
    @Transactional
    void getAllSymptomsByToxicityRateIsEqualToSomething() throws Exception {
        // Initialize the database
        symptomRepository.saveAndFlush(symptom);
        ToxicityRate toxicityRate = ToxicityRateResourceIT.createEntity(em);
        em.persist(toxicityRate);
        em.flush();
        symptom.addToxicityRate(toxicityRate);
        symptomRepository.saveAndFlush(symptom);
        Long toxicityRateId = toxicityRate.getId();

        // Get all the symptomList where toxicityRate equals to toxicityRateId
        defaultSymptomShouldBeFound("toxicityRateId.equals=" + toxicityRateId);

        // Get all the symptomList where toxicityRate equals to toxicityRateId + 1
        defaultSymptomShouldNotBeFound("toxicityRateId.equals=" + (toxicityRateId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSymptomShouldBeFound(String filter) throws Exception {
        restSymptomMockMvc
            .perform(get("/api/symptoms?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(symptom.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].notice").value(hasItem(DEFAULT_NOTICE)));

        // Check, that the count call also returns 1
        restSymptomMockMvc
            .perform(get("/api/symptoms/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSymptomShouldNotBeFound(String filter) throws Exception {
        restSymptomMockMvc
            .perform(get("/api/symptoms?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSymptomMockMvc
            .perform(get("/api/symptoms/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSymptom() throws Exception {
        // Get the symptom
        restSymptomMockMvc.perform(get("/api/symptoms/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void updateSymptom() throws Exception {
        // Initialize the database
        symptomRepository.saveAndFlush(symptom);

        int databaseSizeBeforeUpdate = symptomRepository.findAll().size();

        // Update the symptom
        Symptom updatedSymptom = symptomRepository.findById(symptom.getId()).get();
        // Disconnect from session so that the updates on updatedSymptom are not directly saved in db
        em.detach(updatedSymptom);
        updatedSymptom.name(UPDATED_NAME).notice(UPDATED_NOTICE);
        SymptomDTO symptomDTO = symptomMapper.toDto(updatedSymptom);

        restSymptomMockMvc
            .perform(put("/api/symptoms").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(symptomDTO)))
            .andExpect(status().isOk());

        // Validate the Symptom in the database
        List<Symptom> symptomList = symptomRepository.findAll();
        assertThat(symptomList).hasSize(databaseSizeBeforeUpdate);
        Symptom testSymptom = symptomList.get(symptomList.size() - 1);
        assertThat(testSymptom.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSymptom.getNotice()).isEqualTo(UPDATED_NOTICE);
    }

    @Test
    @Transactional
    void updateNonExistingSymptom() throws Exception {
        int databaseSizeBeforeUpdate = symptomRepository.findAll().size();

        // Create the Symptom
        SymptomDTO symptomDTO = symptomMapper.toDto(symptom);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSymptomMockMvc
            .perform(put("/api/symptoms").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(symptomDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Symptom in the database
        List<Symptom> symptomList = symptomRepository.findAll();
        assertThat(symptomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSymptomWithPatch() throws Exception {
        // Initialize the database
        symptomRepository.saveAndFlush(symptom);

        int databaseSizeBeforeUpdate = symptomRepository.findAll().size();

        // Update the symptom using partial update
        Symptom partialUpdatedSymptom = new Symptom();
        partialUpdatedSymptom.setId(symptom.getId());

        partialUpdatedSymptom.name(UPDATED_NAME).notice(UPDATED_NOTICE);

        restSymptomMockMvc
            .perform(
                patch("/api/symptoms")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSymptom))
            )
            .andExpect(status().isOk());

        // Validate the Symptom in the database
        List<Symptom> symptomList = symptomRepository.findAll();
        assertThat(symptomList).hasSize(databaseSizeBeforeUpdate);
        Symptom testSymptom = symptomList.get(symptomList.size() - 1);
        assertThat(testSymptom.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSymptom.getNotice()).isEqualTo(UPDATED_NOTICE);
    }

    @Test
    @Transactional
    void fullUpdateSymptomWithPatch() throws Exception {
        // Initialize the database
        symptomRepository.saveAndFlush(symptom);

        int databaseSizeBeforeUpdate = symptomRepository.findAll().size();

        // Update the symptom using partial update
        Symptom partialUpdatedSymptom = new Symptom();
        partialUpdatedSymptom.setId(symptom.getId());

        partialUpdatedSymptom.name(UPDATED_NAME).notice(UPDATED_NOTICE);

        restSymptomMockMvc
            .perform(
                patch("/api/symptoms")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSymptom))
            )
            .andExpect(status().isOk());

        // Validate the Symptom in the database
        List<Symptom> symptomList = symptomRepository.findAll();
        assertThat(symptomList).hasSize(databaseSizeBeforeUpdate);
        Symptom testSymptom = symptomList.get(symptomList.size() - 1);
        assertThat(testSymptom.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSymptom.getNotice()).isEqualTo(UPDATED_NOTICE);
    }

    @Test
    @Transactional
    void partialUpdateSymptomShouldThrown() throws Exception {
        // Update the symptom without id should throw
        Symptom partialUpdatedSymptom = new Symptom();

        restSymptomMockMvc
            .perform(
                patch("/api/symptoms")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSymptom))
            )
            .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void deleteSymptom() throws Exception {
        // Initialize the database
        symptomRepository.saveAndFlush(symptom);

        int databaseSizeBeforeDelete = symptomRepository.findAll().size();

        // Delete the symptom
        restSymptomMockMvc
            .perform(delete("/api/symptoms/{id}", symptom.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Symptom> symptomList = symptomRepository.findAll();
        assertThat(symptomList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
