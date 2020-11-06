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
import uc.dei.mse.supportivecare.domain.Administration;
import uc.dei.mse.supportivecare.domain.Drug;
import uc.dei.mse.supportivecare.domain.Notice;
import uc.dei.mse.supportivecare.domain.TherapeuticRegime;
import uc.dei.mse.supportivecare.repository.DrugRepository;
import uc.dei.mse.supportivecare.service.DrugQueryService;
import uc.dei.mse.supportivecare.service.DrugService;
import uc.dei.mse.supportivecare.service.dto.DrugCriteria;
import uc.dei.mse.supportivecare.service.dto.DrugDTO;
import uc.dei.mse.supportivecare.service.mapper.DrugMapper;

/**
 * Integration tests for the {@link DrugResource} REST controller.
 */
@SpringBootTest(classes = SupportivecareApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DrugResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private DrugRepository drugRepository;

    @Mock
    private DrugRepository drugRepositoryMock;

    @Autowired
    private DrugMapper drugMapper;

    @Mock
    private DrugService drugServiceMock;

    @Autowired
    private DrugQueryService drugQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDrugMockMvc;

    private Drug drug;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Drug createEntity(EntityManager em) {
        Drug drug = new Drug().name(DEFAULT_NAME).description(DEFAULT_DESCRIPTION);
        // Add required entity
        Administration administration;
        if (TestUtil.findAll(em, Administration.class).isEmpty()) {
            administration = AdministrationResourceIT.createEntity(em);
            em.persist(administration);
            em.flush();
        } else {
            administration = TestUtil.findAll(em, Administration.class).get(0);
        }
        drug.setAdministration(administration);
        return drug;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Drug createUpdatedEntity(EntityManager em) {
        Drug drug = new Drug().name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
        // Add required entity
        Administration administration;
        if (TestUtil.findAll(em, Administration.class).isEmpty()) {
            administration = AdministrationResourceIT.createUpdatedEntity(em);
            em.persist(administration);
            em.flush();
        } else {
            administration = TestUtil.findAll(em, Administration.class).get(0);
        }
        drug.setAdministration(administration);
        return drug;
    }

    @BeforeEach
    public void initTest() {
        drug = createEntity(em);
    }

    @Test
    @Transactional
    void createDrug() throws Exception {
        int databaseSizeBeforeCreate = drugRepository.findAll().size();
        // Create the Drug
        DrugDTO drugDTO = drugMapper.toDto(drug);
        restDrugMockMvc
            .perform(post("/api/drugs").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(drugDTO)))
            .andExpect(status().isCreated());

        // Validate the Drug in the database
        List<Drug> drugList = drugRepository.findAll();
        assertThat(drugList).hasSize(databaseSizeBeforeCreate + 1);
        Drug testDrug = drugList.get(drugList.size() - 1);
        assertThat(testDrug.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDrug.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createDrugWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = drugRepository.findAll().size();

        // Create the Drug with an existing ID
        drug.setId(1L);
        DrugDTO drugDTO = drugMapper.toDto(drug);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDrugMockMvc
            .perform(post("/api/drugs").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(drugDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Drug in the database
        List<Drug> drugList = drugRepository.findAll();
        assertThat(drugList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = drugRepository.findAll().size();
        // set the field null
        drug.setName(null);

        // Create the Drug, which fails.
        DrugDTO drugDTO = drugMapper.toDto(drug);

        restDrugMockMvc
            .perform(post("/api/drugs").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(drugDTO)))
            .andExpect(status().isBadRequest());

        List<Drug> drugList = drugRepository.findAll();
        assertThat(drugList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDrugs() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList
        restDrugMockMvc
            .perform(get("/api/drugs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(drug.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDrugsWithEagerRelationshipsIsEnabled() throws Exception {
        when(drugServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDrugMockMvc.perform(get("/api/drugs?eagerload=true")).andExpect(status().isOk());

        verify(drugServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDrugsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(drugServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDrugMockMvc.perform(get("/api/drugs?eagerload=true")).andExpect(status().isOk());

        verify(drugServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getDrug() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get the drug
        restDrugMockMvc
            .perform(get("/api/drugs/{id}", drug.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(drug.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getDrugsByIdFiltering() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        Long id = drug.getId();

        defaultDrugShouldBeFound("id.equals=" + id);
        defaultDrugShouldNotBeFound("id.notEquals=" + id);

        defaultDrugShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDrugShouldNotBeFound("id.greaterThan=" + id);

        defaultDrugShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDrugShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDrugsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where name equals to DEFAULT_NAME
        defaultDrugShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the drugList where name equals to UPDATED_NAME
        defaultDrugShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllDrugsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where name not equals to DEFAULT_NAME
        defaultDrugShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the drugList where name not equals to UPDATED_NAME
        defaultDrugShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllDrugsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where name in DEFAULT_NAME or UPDATED_NAME
        defaultDrugShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the drugList where name equals to UPDATED_NAME
        defaultDrugShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllDrugsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where name is not null
        defaultDrugShouldBeFound("name.specified=true");

        // Get all the drugList where name is null
        defaultDrugShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllDrugsByNameContainsSomething() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where name contains DEFAULT_NAME
        defaultDrugShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the drugList where name contains UPDATED_NAME
        defaultDrugShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllDrugsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where name does not contain DEFAULT_NAME
        defaultDrugShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the drugList where name does not contain UPDATED_NAME
        defaultDrugShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllDrugsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where description equals to DEFAULT_DESCRIPTION
        defaultDrugShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the drugList where description equals to UPDATED_DESCRIPTION
        defaultDrugShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllDrugsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where description not equals to DEFAULT_DESCRIPTION
        defaultDrugShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the drugList where description not equals to UPDATED_DESCRIPTION
        defaultDrugShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllDrugsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultDrugShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the drugList where description equals to UPDATED_DESCRIPTION
        defaultDrugShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllDrugsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where description is not null
        defaultDrugShouldBeFound("description.specified=true");

        // Get all the drugList where description is null
        defaultDrugShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllDrugsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where description contains DEFAULT_DESCRIPTION
        defaultDrugShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the drugList where description contains UPDATED_DESCRIPTION
        defaultDrugShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllDrugsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where description does not contain DEFAULT_DESCRIPTION
        defaultDrugShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the drugList where description does not contain UPDATED_DESCRIPTION
        defaultDrugShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllDrugsByNoticeIsEqualToSomething() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);
        Notice notice = NoticeResourceIT.createEntity(em);
        em.persist(notice);
        em.flush();
        drug.addNotice(notice);
        drugRepository.saveAndFlush(drug);
        Long noticeId = notice.getId();

        // Get all the drugList where notice equals to noticeId
        defaultDrugShouldBeFound("noticeId.equals=" + noticeId);

        // Get all the drugList where notice equals to noticeId + 1
        defaultDrugShouldNotBeFound("noticeId.equals=" + (noticeId + 1));
    }

    @Test
    @Transactional
    void getAllDrugsByAdministrationIsEqualToSomething() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);
        Administration administration = AdministrationResourceIT.createEntity(em);
        em.persist(administration);
        em.flush();
        drug.setAdministration(administration);
        drugRepository.saveAndFlush(drug);
        Long administrationId = administration.getId();

        // Get all the drugList where administration equals to administrationId
        defaultDrugShouldBeFound("administrationId.equals=" + administrationId);

        // Get all the drugList where administration equals to administrationId + 1
        defaultDrugShouldNotBeFound("administrationId.equals=" + (administrationId + 1));
    }

    @Test
    @Transactional
    void getAllDrugsByTherapeuticRegimeIsEqualToSomething() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);
        TherapeuticRegime therapeuticRegime = TherapeuticRegimeResourceIT.createEntity(em);
        em.persist(therapeuticRegime);
        em.flush();
        drug.addTherapeuticRegime(therapeuticRegime);
        drugRepository.saveAndFlush(drug);
        Long therapeuticRegimeId = therapeuticRegime.getId();

        // Get all the drugList where therapeuticRegime equals to therapeuticRegimeId
        defaultDrugShouldBeFound("therapeuticRegimeId.equals=" + therapeuticRegimeId);

        // Get all the drugList where therapeuticRegime equals to therapeuticRegimeId + 1
        defaultDrugShouldNotBeFound("therapeuticRegimeId.equals=" + (therapeuticRegimeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDrugShouldBeFound(String filter) throws Exception {
        restDrugMockMvc
            .perform(get("/api/drugs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(drug.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restDrugMockMvc
            .perform(get("/api/drugs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDrugShouldNotBeFound(String filter) throws Exception {
        restDrugMockMvc
            .perform(get("/api/drugs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDrugMockMvc
            .perform(get("/api/drugs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDrug() throws Exception {
        // Get the drug
        restDrugMockMvc.perform(get("/api/drugs/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void updateDrug() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        int databaseSizeBeforeUpdate = drugRepository.findAll().size();

        // Update the drug
        Drug updatedDrug = drugRepository.findById(drug.getId()).get();
        // Disconnect from session so that the updates on updatedDrug are not directly saved in db
        em.detach(updatedDrug);
        updatedDrug.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
        DrugDTO drugDTO = drugMapper.toDto(updatedDrug);

        restDrugMockMvc
            .perform(put("/api/drugs").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(drugDTO)))
            .andExpect(status().isOk());

        // Validate the Drug in the database
        List<Drug> drugList = drugRepository.findAll();
        assertThat(drugList).hasSize(databaseSizeBeforeUpdate);
        Drug testDrug = drugList.get(drugList.size() - 1);
        assertThat(testDrug.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDrug.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void updateNonExistingDrug() throws Exception {
        int databaseSizeBeforeUpdate = drugRepository.findAll().size();

        // Create the Drug
        DrugDTO drugDTO = drugMapper.toDto(drug);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDrugMockMvc
            .perform(put("/api/drugs").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(drugDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Drug in the database
        List<Drug> drugList = drugRepository.findAll();
        assertThat(drugList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDrugWithPatch() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        int databaseSizeBeforeUpdate = drugRepository.findAll().size();

        // Update the drug using partial update
        Drug partialUpdatedDrug = new Drug();
        partialUpdatedDrug.setId(drug.getId());

        partialUpdatedDrug.description(UPDATED_DESCRIPTION);

        restDrugMockMvc
            .perform(
                patch("/api/drugs")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDrug))
            )
            .andExpect(status().isOk());

        // Validate the Drug in the database
        List<Drug> drugList = drugRepository.findAll();
        assertThat(drugList).hasSize(databaseSizeBeforeUpdate);
        Drug testDrug = drugList.get(drugList.size() - 1);
        assertThat(testDrug.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDrug.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateDrugWithPatch() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        int databaseSizeBeforeUpdate = drugRepository.findAll().size();

        // Update the drug using partial update
        Drug partialUpdatedDrug = new Drug();
        partialUpdatedDrug.setId(drug.getId());

        partialUpdatedDrug.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restDrugMockMvc
            .perform(
                patch("/api/drugs")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDrug))
            )
            .andExpect(status().isOk());

        // Validate the Drug in the database
        List<Drug> drugList = drugRepository.findAll();
        assertThat(drugList).hasSize(databaseSizeBeforeUpdate);
        Drug testDrug = drugList.get(drugList.size() - 1);
        assertThat(testDrug.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDrug.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void partialUpdateDrugShouldThrown() throws Exception {
        // Update the drug without id should throw
        Drug partialUpdatedDrug = new Drug();

        restDrugMockMvc
            .perform(
                patch("/api/drugs")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDrug))
            )
            .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void deleteDrug() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        int databaseSizeBeforeDelete = drugRepository.findAll().size();

        // Delete the drug
        restDrugMockMvc
            .perform(delete("/api/drugs/{id}", drug.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Drug> drugList = drugRepository.findAll();
        assertThat(drugList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
