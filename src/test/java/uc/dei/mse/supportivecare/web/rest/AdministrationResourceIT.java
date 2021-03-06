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
import uc.dei.mse.supportivecare.repository.AdministrationRepository;
import uc.dei.mse.supportivecare.service.AdministrationQueryService;
import uc.dei.mse.supportivecare.service.dto.AdministrationCriteria;
import uc.dei.mse.supportivecare.service.dto.AdministrationDTO;
import uc.dei.mse.supportivecare.service.mapper.AdministrationMapper;

/**
 * Integration tests for the {@link AdministrationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AdministrationResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    @Autowired
    private AdministrationRepository administrationRepository;

    @Autowired
    private AdministrationMapper administrationMapper;

    @Autowired
    private AdministrationQueryService administrationQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdministrationMockMvc;

    private Administration administration;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Administration createEntity(EntityManager em) {
        Administration administration = new Administration().type(DEFAULT_TYPE);
        return administration;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Administration createUpdatedEntity(EntityManager em) {
        Administration administration = new Administration().type(UPDATED_TYPE);
        return administration;
    }

    @BeforeEach
    public void initTest() {
        administration = createEntity(em);
    }

    @Test
    @Transactional
    void createAdministration() throws Exception {
        int databaseSizeBeforeCreate = administrationRepository.findAll().size();
        // Create the Administration
        AdministrationDTO administrationDTO = administrationMapper.toDto(administration);
        restAdministrationMockMvc
            .perform(
                post("/api/administrations")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(administrationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Administration in the database
        List<Administration> administrationList = administrationRepository.findAll();
        assertThat(administrationList).hasSize(databaseSizeBeforeCreate + 1);
        Administration testAdministration = administrationList.get(administrationList.size() - 1);
        assertThat(testAdministration.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    void createAdministrationWithExistingId() throws Exception {
        // Create the Administration with an existing ID
        administration.setId(1L);
        AdministrationDTO administrationDTO = administrationMapper.toDto(administration);

        int databaseSizeBeforeCreate = administrationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdministrationMockMvc
            .perform(
                post("/api/administrations")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(administrationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Administration in the database
        List<Administration> administrationList = administrationRepository.findAll();
        assertThat(administrationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = administrationRepository.findAll().size();
        // set the field null
        administration.setType(null);

        // Create the Administration, which fails.
        AdministrationDTO administrationDTO = administrationMapper.toDto(administration);

        restAdministrationMockMvc
            .perform(
                post("/api/administrations")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(administrationDTO))
            )
            .andExpect(status().isBadRequest());

        List<Administration> administrationList = administrationRepository.findAll();
        assertThat(administrationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAdministrations() throws Exception {
        // Initialize the database
        administrationRepository.saveAndFlush(administration);

        // Get all the administrationList
        restAdministrationMockMvc
            .perform(get("/api/administrations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(administration.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }

    @Test
    @Transactional
    void getAdministration() throws Exception {
        // Initialize the database
        administrationRepository.saveAndFlush(administration);

        // Get the administration
        restAdministrationMockMvc
            .perform(get("/api/administrations/{id}", administration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(administration.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }

    @Test
    @Transactional
    void getAdministrationsByIdFiltering() throws Exception {
        // Initialize the database
        administrationRepository.saveAndFlush(administration);

        Long id = administration.getId();

        defaultAdministrationShouldBeFound("id.equals=" + id);
        defaultAdministrationShouldNotBeFound("id.notEquals=" + id);

        defaultAdministrationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAdministrationShouldNotBeFound("id.greaterThan=" + id);

        defaultAdministrationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAdministrationShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAdministrationsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        administrationRepository.saveAndFlush(administration);

        // Get all the administrationList where type equals to DEFAULT_TYPE
        defaultAdministrationShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the administrationList where type equals to UPDATED_TYPE
        defaultAdministrationShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllAdministrationsByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        administrationRepository.saveAndFlush(administration);

        // Get all the administrationList where type not equals to DEFAULT_TYPE
        defaultAdministrationShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the administrationList where type not equals to UPDATED_TYPE
        defaultAdministrationShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllAdministrationsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        administrationRepository.saveAndFlush(administration);

        // Get all the administrationList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultAdministrationShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the administrationList where type equals to UPDATED_TYPE
        defaultAdministrationShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllAdministrationsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        administrationRepository.saveAndFlush(administration);

        // Get all the administrationList where type is not null
        defaultAdministrationShouldBeFound("type.specified=true");

        // Get all the administrationList where type is null
        defaultAdministrationShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllAdministrationsByTypeContainsSomething() throws Exception {
        // Initialize the database
        administrationRepository.saveAndFlush(administration);

        // Get all the administrationList where type contains DEFAULT_TYPE
        defaultAdministrationShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the administrationList where type contains UPDATED_TYPE
        defaultAdministrationShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllAdministrationsByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        administrationRepository.saveAndFlush(administration);

        // Get all the administrationList where type does not contain DEFAULT_TYPE
        defaultAdministrationShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the administrationList where type does not contain UPDATED_TYPE
        defaultAdministrationShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllAdministrationsByActiveSubstanceIsEqualToSomething() throws Exception {
        // Initialize the database
        administrationRepository.saveAndFlush(administration);
        ActiveSubstance activeSubstance = ActiveSubstanceResourceIT.createEntity(em);
        em.persist(activeSubstance);
        em.flush();
        administration.addActiveSubstance(activeSubstance);
        administrationRepository.saveAndFlush(administration);
        Long activeSubstanceId = activeSubstance.getId();

        // Get all the administrationList where activeSubstance equals to activeSubstanceId
        defaultAdministrationShouldBeFound("activeSubstanceId.equals=" + activeSubstanceId);

        // Get all the administrationList where activeSubstance equals to activeSubstanceId + 1
        defaultAdministrationShouldNotBeFound("activeSubstanceId.equals=" + (activeSubstanceId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAdministrationShouldBeFound(String filter) throws Exception {
        restAdministrationMockMvc
            .perform(get("/api/administrations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(administration.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));

        // Check, that the count call also returns 1
        restAdministrationMockMvc
            .perform(get("/api/administrations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAdministrationShouldNotBeFound(String filter) throws Exception {
        restAdministrationMockMvc
            .perform(get("/api/administrations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAdministrationMockMvc
            .perform(get("/api/administrations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAdministration() throws Exception {
        // Get the administration
        restAdministrationMockMvc.perform(get("/api/administrations/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void updateAdministration() throws Exception {
        // Initialize the database
        administrationRepository.saveAndFlush(administration);

        int databaseSizeBeforeUpdate = administrationRepository.findAll().size();

        // Update the administration
        Administration updatedAdministration = administrationRepository.findById(administration.getId()).get();
        // Disconnect from session so that the updates on updatedAdministration are not directly saved in db
        em.detach(updatedAdministration);
        updatedAdministration.type(UPDATED_TYPE);
        AdministrationDTO administrationDTO = administrationMapper.toDto(updatedAdministration);

        restAdministrationMockMvc
            .perform(
                put("/api/administrations")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(administrationDTO))
            )
            .andExpect(status().isOk());

        // Validate the Administration in the database
        List<Administration> administrationList = administrationRepository.findAll();
        assertThat(administrationList).hasSize(databaseSizeBeforeUpdate);
        Administration testAdministration = administrationList.get(administrationList.size() - 1);
        assertThat(testAdministration.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void updateNonExistingAdministration() throws Exception {
        int databaseSizeBeforeUpdate = administrationRepository.findAll().size();

        // Create the Administration
        AdministrationDTO administrationDTO = administrationMapper.toDto(administration);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdministrationMockMvc
            .perform(
                put("/api/administrations")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(administrationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Administration in the database
        List<Administration> administrationList = administrationRepository.findAll();
        assertThat(administrationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAdministrationWithPatch() throws Exception {
        // Initialize the database
        administrationRepository.saveAndFlush(administration);

        int databaseSizeBeforeUpdate = administrationRepository.findAll().size();

        // Update the administration using partial update
        Administration partialUpdatedAdministration = new Administration();
        partialUpdatedAdministration.setId(administration.getId());

        restAdministrationMockMvc
            .perform(
                patch("/api/administrations")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdministration))
            )
            .andExpect(status().isOk());

        // Validate the Administration in the database
        List<Administration> administrationList = administrationRepository.findAll();
        assertThat(administrationList).hasSize(databaseSizeBeforeUpdate);
        Administration testAdministration = administrationList.get(administrationList.size() - 1);
        assertThat(testAdministration.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateAdministrationWithPatch() throws Exception {
        // Initialize the database
        administrationRepository.saveAndFlush(administration);

        int databaseSizeBeforeUpdate = administrationRepository.findAll().size();

        // Update the administration using partial update
        Administration partialUpdatedAdministration = new Administration();
        partialUpdatedAdministration.setId(administration.getId());

        partialUpdatedAdministration.type(UPDATED_TYPE);

        restAdministrationMockMvc
            .perform(
                patch("/api/administrations")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdministration))
            )
            .andExpect(status().isOk());

        // Validate the Administration in the database
        List<Administration> administrationList = administrationRepository.findAll();
        assertThat(administrationList).hasSize(databaseSizeBeforeUpdate);
        Administration testAdministration = administrationList.get(administrationList.size() - 1);
        assertThat(testAdministration.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void partialUpdateAdministrationShouldThrown() throws Exception {
        // Update the administration without id should throw
        Administration partialUpdatedAdministration = new Administration();

        restAdministrationMockMvc
            .perform(
                patch("/api/administrations")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdministration))
            )
            .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void deleteAdministration() throws Exception {
        // Initialize the database
        administrationRepository.saveAndFlush(administration);

        int databaseSizeBeforeDelete = administrationRepository.findAll().size();

        // Delete the administration
        restAdministrationMockMvc
            .perform(delete("/api/administrations/{id}", administration.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Administration> administrationList = administrationRepository.findAll();
        assertThat(administrationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
