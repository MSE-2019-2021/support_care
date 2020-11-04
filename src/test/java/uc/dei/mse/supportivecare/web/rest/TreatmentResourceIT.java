package uc.dei.mse.supportivecare.web.rest;

import uc.dei.mse.supportivecare.SupportivecareApp;
import uc.dei.mse.supportivecare.domain.Treatment;
import uc.dei.mse.supportivecare.domain.TherapeuticRegime;
import uc.dei.mse.supportivecare.repository.TreatmentRepository;
import uc.dei.mse.supportivecare.service.TreatmentService;
import uc.dei.mse.supportivecare.service.dto.TreatmentDTO;
import uc.dei.mse.supportivecare.service.mapper.TreatmentMapper;
import uc.dei.mse.supportivecare.service.dto.TreatmentCriteria;
import uc.dei.mse.supportivecare.service.TreatmentQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TreatmentResource} REST controller.
 */
@SpringBootTest(classes = SupportivecareApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TreatmentResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    @Autowired
    private TreatmentRepository treatmentRepository;

    @Autowired
    private TreatmentMapper treatmentMapper;

    @Autowired
    private TreatmentService treatmentService;

    @Autowired
    private TreatmentQueryService treatmentQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTreatmentMockMvc;

    private Treatment treatment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Treatment createEntity(EntityManager em) {
        Treatment treatment = new Treatment()
            .type(DEFAULT_TYPE);
        return treatment;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Treatment createUpdatedEntity(EntityManager em) {
        Treatment treatment = new Treatment()
            .type(UPDATED_TYPE);
        return treatment;
    }

    @BeforeEach
    public void initTest() {
        treatment = createEntity(em);
    }

    @Test
    @Transactional
    public void createTreatment() throws Exception {
        int databaseSizeBeforeCreate = treatmentRepository.findAll().size();
        // Create the Treatment
        TreatmentDTO treatmentDTO = treatmentMapper.toDto(treatment);
        restTreatmentMockMvc.perform(post("/api/treatments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(treatmentDTO)))
            .andExpect(status().isCreated());

        // Validate the Treatment in the database
        List<Treatment> treatmentList = treatmentRepository.findAll();
        assertThat(treatmentList).hasSize(databaseSizeBeforeCreate + 1);
        Treatment testTreatment = treatmentList.get(treatmentList.size() - 1);
        assertThat(testTreatment.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createTreatmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = treatmentRepository.findAll().size();

        // Create the Treatment with an existing ID
        treatment.setId(1L);
        TreatmentDTO treatmentDTO = treatmentMapper.toDto(treatment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTreatmentMockMvc.perform(post("/api/treatments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(treatmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Treatment in the database
        List<Treatment> treatmentList = treatmentRepository.findAll();
        assertThat(treatmentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = treatmentRepository.findAll().size();
        // set the field null
        treatment.setType(null);

        // Create the Treatment, which fails.
        TreatmentDTO treatmentDTO = treatmentMapper.toDto(treatment);


        restTreatmentMockMvc.perform(post("/api/treatments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(treatmentDTO)))
            .andExpect(status().isBadRequest());

        List<Treatment> treatmentList = treatmentRepository.findAll();
        assertThat(treatmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTreatments() throws Exception {
        // Initialize the database
        treatmentRepository.saveAndFlush(treatment);

        // Get all the treatmentList
        restTreatmentMockMvc.perform(get("/api/treatments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(treatment.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }
    
    @Test
    @Transactional
    public void getTreatment() throws Exception {
        // Initialize the database
        treatmentRepository.saveAndFlush(treatment);

        // Get the treatment
        restTreatmentMockMvc.perform(get("/api/treatments/{id}", treatment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(treatment.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }


    @Test
    @Transactional
    public void getTreatmentsByIdFiltering() throws Exception {
        // Initialize the database
        treatmentRepository.saveAndFlush(treatment);

        Long id = treatment.getId();

        defaultTreatmentShouldBeFound("id.equals=" + id);
        defaultTreatmentShouldNotBeFound("id.notEquals=" + id);

        defaultTreatmentShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTreatmentShouldNotBeFound("id.greaterThan=" + id);

        defaultTreatmentShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTreatmentShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTreatmentsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        treatmentRepository.saveAndFlush(treatment);

        // Get all the treatmentList where type equals to DEFAULT_TYPE
        defaultTreatmentShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the treatmentList where type equals to UPDATED_TYPE
        defaultTreatmentShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllTreatmentsByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        treatmentRepository.saveAndFlush(treatment);

        // Get all the treatmentList where type not equals to DEFAULT_TYPE
        defaultTreatmentShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the treatmentList where type not equals to UPDATED_TYPE
        defaultTreatmentShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllTreatmentsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        treatmentRepository.saveAndFlush(treatment);

        // Get all the treatmentList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultTreatmentShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the treatmentList where type equals to UPDATED_TYPE
        defaultTreatmentShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllTreatmentsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        treatmentRepository.saveAndFlush(treatment);

        // Get all the treatmentList where type is not null
        defaultTreatmentShouldBeFound("type.specified=true");

        // Get all the treatmentList where type is null
        defaultTreatmentShouldNotBeFound("type.specified=false");
    }
                @Test
    @Transactional
    public void getAllTreatmentsByTypeContainsSomething() throws Exception {
        // Initialize the database
        treatmentRepository.saveAndFlush(treatment);

        // Get all the treatmentList where type contains DEFAULT_TYPE
        defaultTreatmentShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the treatmentList where type contains UPDATED_TYPE
        defaultTreatmentShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllTreatmentsByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        treatmentRepository.saveAndFlush(treatment);

        // Get all the treatmentList where type does not contain DEFAULT_TYPE
        defaultTreatmentShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the treatmentList where type does not contain UPDATED_TYPE
        defaultTreatmentShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }


    @Test
    @Transactional
    public void getAllTreatmentsByTherapeuticRegimeIsEqualToSomething() throws Exception {
        // Initialize the database
        treatmentRepository.saveAndFlush(treatment);
        TherapeuticRegime therapeuticRegime = TherapeuticRegimeResourceIT.createEntity(em);
        em.persist(therapeuticRegime);
        em.flush();
        treatment.addTherapeuticRegime(therapeuticRegime);
        treatmentRepository.saveAndFlush(treatment);
        Long therapeuticRegimeId = therapeuticRegime.getId();

        // Get all the treatmentList where therapeuticRegime equals to therapeuticRegimeId
        defaultTreatmentShouldBeFound("therapeuticRegimeId.equals=" + therapeuticRegimeId);

        // Get all the treatmentList where therapeuticRegime equals to therapeuticRegimeId + 1
        defaultTreatmentShouldNotBeFound("therapeuticRegimeId.equals=" + (therapeuticRegimeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTreatmentShouldBeFound(String filter) throws Exception {
        restTreatmentMockMvc.perform(get("/api/treatments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(treatment.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));

        // Check, that the count call also returns 1
        restTreatmentMockMvc.perform(get("/api/treatments/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTreatmentShouldNotBeFound(String filter) throws Exception {
        restTreatmentMockMvc.perform(get("/api/treatments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTreatmentMockMvc.perform(get("/api/treatments/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingTreatment() throws Exception {
        // Get the treatment
        restTreatmentMockMvc.perform(get("/api/treatments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTreatment() throws Exception {
        // Initialize the database
        treatmentRepository.saveAndFlush(treatment);

        int databaseSizeBeforeUpdate = treatmentRepository.findAll().size();

        // Update the treatment
        Treatment updatedTreatment = treatmentRepository.findById(treatment.getId()).get();
        // Disconnect from session so that the updates on updatedTreatment are not directly saved in db
        em.detach(updatedTreatment);
        updatedTreatment
            .type(UPDATED_TYPE);
        TreatmentDTO treatmentDTO = treatmentMapper.toDto(updatedTreatment);

        restTreatmentMockMvc.perform(put("/api/treatments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(treatmentDTO)))
            .andExpect(status().isOk());

        // Validate the Treatment in the database
        List<Treatment> treatmentList = treatmentRepository.findAll();
        assertThat(treatmentList).hasSize(databaseSizeBeforeUpdate);
        Treatment testTreatment = treatmentList.get(treatmentList.size() - 1);
        assertThat(testTreatment.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingTreatment() throws Exception {
        int databaseSizeBeforeUpdate = treatmentRepository.findAll().size();

        // Create the Treatment
        TreatmentDTO treatmentDTO = treatmentMapper.toDto(treatment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTreatmentMockMvc.perform(put("/api/treatments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(treatmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Treatment in the database
        List<Treatment> treatmentList = treatmentRepository.findAll();
        assertThat(treatmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTreatment() throws Exception {
        // Initialize the database
        treatmentRepository.saveAndFlush(treatment);

        int databaseSizeBeforeDelete = treatmentRepository.findAll().size();

        // Delete the treatment
        restTreatmentMockMvc.perform(delete("/api/treatments/{id}", treatment.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Treatment> treatmentList = treatmentRepository.findAll();
        assertThat(treatmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
