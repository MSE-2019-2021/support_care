package uc.dei.mse.supportcare.web.rest;

import uc.dei.mse.supportcare.SupportcareApp;
import uc.dei.mse.supportcare.domain.Drug;
import uc.dei.mse.supportcare.repository.DrugRepository;
import uc.dei.mse.supportcare.service.DrugService;
import uc.dei.mse.supportcare.service.dto.DrugDTO;
import uc.dei.mse.supportcare.service.mapper.DrugMapper;
import uc.dei.mse.supportcare.service.dto.DrugCriteria;
import uc.dei.mse.supportcare.service.DrugQueryService;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DrugResource} REST controller.
 */
@SpringBootTest(classes = SupportcareApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DrugResourceIT {

    private static final String DEFAULT_DRUG_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DRUG_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CREATE_USER = "AAAAAAAAAA";
    private static final String UPDATED_CREATE_USER = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATE_USER = "AAAAAAAAAA";
    private static final String UPDATED_UPDATE_USER = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private DrugRepository drugRepository;

    @Autowired
    private DrugMapper drugMapper;

    @Autowired
    private DrugService drugService;

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
        Drug drug = new Drug()
            .drugName(DEFAULT_DRUG_NAME)
            .createUser(DEFAULT_CREATE_USER)
            .createDate(DEFAULT_CREATE_DATE)
            .updateUser(DEFAULT_UPDATE_USER)
            .updateDate(DEFAULT_UPDATE_DATE);
        return drug;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Drug createUpdatedEntity(EntityManager em) {
        Drug drug = new Drug()
            .drugName(UPDATED_DRUG_NAME)
            .createUser(UPDATED_CREATE_USER)
            .createDate(UPDATED_CREATE_DATE)
            .updateUser(UPDATED_UPDATE_USER)
            .updateDate(UPDATED_UPDATE_DATE);
        return drug;
    }

    @BeforeEach
    public void initTest() {
        drug = createEntity(em);
    }

    @Test
    @Transactional
    public void createDrug() throws Exception {
        int databaseSizeBeforeCreate = drugRepository.findAll().size();
        // Create the Drug
        DrugDTO drugDTO = drugMapper.toDto(drug);
        restDrugMockMvc.perform(post("/api/drugs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(drugDTO)))
            .andExpect(status().isCreated());

        // Validate the Drug in the database
        List<Drug> drugList = drugRepository.findAll();
        assertThat(drugList).hasSize(databaseSizeBeforeCreate + 1);
        Drug testDrug = drugList.get(drugList.size() - 1);
        assertThat(testDrug.getDrugName()).isEqualTo(DEFAULT_DRUG_NAME);
        assertThat(testDrug.getCreateUser()).isEqualTo(DEFAULT_CREATE_USER);
        assertThat(testDrug.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testDrug.getUpdateUser()).isEqualTo(DEFAULT_UPDATE_USER);
        assertThat(testDrug.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void createDrugWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = drugRepository.findAll().size();

        // Create the Drug with an existing ID
        drug.setId(1L);
        DrugDTO drugDTO = drugMapper.toDto(drug);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDrugMockMvc.perform(post("/api/drugs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(drugDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Drug in the database
        List<Drug> drugList = drugRepository.findAll();
        assertThat(drugList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDrugNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = drugRepository.findAll().size();
        // set the field null
        drug.setDrugName(null);

        // Create the Drug, which fails.
        DrugDTO drugDTO = drugMapper.toDto(drug);


        restDrugMockMvc.perform(post("/api/drugs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(drugDTO)))
            .andExpect(status().isBadRequest());

        List<Drug> drugList = drugRepository.findAll();
        assertThat(drugList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateUserIsRequired() throws Exception {
        int databaseSizeBeforeTest = drugRepository.findAll().size();
        // set the field null
        drug.setCreateUser(null);

        // Create the Drug, which fails.
        DrugDTO drugDTO = drugMapper.toDto(drug);


        restDrugMockMvc.perform(post("/api/drugs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(drugDTO)))
            .andExpect(status().isBadRequest());

        List<Drug> drugList = drugRepository.findAll();
        assertThat(drugList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = drugRepository.findAll().size();
        // set the field null
        drug.setCreateDate(null);

        // Create the Drug, which fails.
        DrugDTO drugDTO = drugMapper.toDto(drug);


        restDrugMockMvc.perform(post("/api/drugs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(drugDTO)))
            .andExpect(status().isBadRequest());

        List<Drug> drugList = drugRepository.findAll();
        assertThat(drugList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateUserIsRequired() throws Exception {
        int databaseSizeBeforeTest = drugRepository.findAll().size();
        // set the field null
        drug.setUpdateUser(null);

        // Create the Drug, which fails.
        DrugDTO drugDTO = drugMapper.toDto(drug);


        restDrugMockMvc.perform(post("/api/drugs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(drugDTO)))
            .andExpect(status().isBadRequest());

        List<Drug> drugList = drugRepository.findAll();
        assertThat(drugList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = drugRepository.findAll().size();
        // set the field null
        drug.setUpdateDate(null);

        // Create the Drug, which fails.
        DrugDTO drugDTO = drugMapper.toDto(drug);


        restDrugMockMvc.perform(post("/api/drugs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(drugDTO)))
            .andExpect(status().isBadRequest());

        List<Drug> drugList = drugRepository.findAll();
        assertThat(drugList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDrugs() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList
        restDrugMockMvc.perform(get("/api/drugs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(drug.getId().intValue())))
            .andExpect(jsonPath("$.[*].drugName").value(hasItem(DEFAULT_DRUG_NAME)))
            .andExpect(jsonPath("$.[*].createUser").value(hasItem(DEFAULT_CREATE_USER)))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].updateUser").value(hasItem(DEFAULT_UPDATE_USER)))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getDrug() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get the drug
        restDrugMockMvc.perform(get("/api/drugs/{id}", drug.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(drug.getId().intValue()))
            .andExpect(jsonPath("$.drugName").value(DEFAULT_DRUG_NAME))
            .andExpect(jsonPath("$.createUser").value(DEFAULT_CREATE_USER))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.updateUser").value(DEFAULT_UPDATE_USER))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()));
    }


    @Test
    @Transactional
    public void getDrugsByIdFiltering() throws Exception {
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
    public void getAllDrugsByDrugNameIsEqualToSomething() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where drugName equals to DEFAULT_DRUG_NAME
        defaultDrugShouldBeFound("drugName.equals=" + DEFAULT_DRUG_NAME);

        // Get all the drugList where drugName equals to UPDATED_DRUG_NAME
        defaultDrugShouldNotBeFound("drugName.equals=" + UPDATED_DRUG_NAME);
    }

    @Test
    @Transactional
    public void getAllDrugsByDrugNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where drugName not equals to DEFAULT_DRUG_NAME
        defaultDrugShouldNotBeFound("drugName.notEquals=" + DEFAULT_DRUG_NAME);

        // Get all the drugList where drugName not equals to UPDATED_DRUG_NAME
        defaultDrugShouldBeFound("drugName.notEquals=" + UPDATED_DRUG_NAME);
    }

    @Test
    @Transactional
    public void getAllDrugsByDrugNameIsInShouldWork() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where drugName in DEFAULT_DRUG_NAME or UPDATED_DRUG_NAME
        defaultDrugShouldBeFound("drugName.in=" + DEFAULT_DRUG_NAME + "," + UPDATED_DRUG_NAME);

        // Get all the drugList where drugName equals to UPDATED_DRUG_NAME
        defaultDrugShouldNotBeFound("drugName.in=" + UPDATED_DRUG_NAME);
    }

    @Test
    @Transactional
    public void getAllDrugsByDrugNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where drugName is not null
        defaultDrugShouldBeFound("drugName.specified=true");

        // Get all the drugList where drugName is null
        defaultDrugShouldNotBeFound("drugName.specified=false");
    }
                @Test
    @Transactional
    public void getAllDrugsByDrugNameContainsSomething() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where drugName contains DEFAULT_DRUG_NAME
        defaultDrugShouldBeFound("drugName.contains=" + DEFAULT_DRUG_NAME);

        // Get all the drugList where drugName contains UPDATED_DRUG_NAME
        defaultDrugShouldNotBeFound("drugName.contains=" + UPDATED_DRUG_NAME);
    }

    @Test
    @Transactional
    public void getAllDrugsByDrugNameNotContainsSomething() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where drugName does not contain DEFAULT_DRUG_NAME
        defaultDrugShouldNotBeFound("drugName.doesNotContain=" + DEFAULT_DRUG_NAME);

        // Get all the drugList where drugName does not contain UPDATED_DRUG_NAME
        defaultDrugShouldBeFound("drugName.doesNotContain=" + UPDATED_DRUG_NAME);
    }


    @Test
    @Transactional
    public void getAllDrugsByCreateUserIsEqualToSomething() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where createUser equals to DEFAULT_CREATE_USER
        defaultDrugShouldBeFound("createUser.equals=" + DEFAULT_CREATE_USER);

        // Get all the drugList where createUser equals to UPDATED_CREATE_USER
        defaultDrugShouldNotBeFound("createUser.equals=" + UPDATED_CREATE_USER);
    }

    @Test
    @Transactional
    public void getAllDrugsByCreateUserIsNotEqualToSomething() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where createUser not equals to DEFAULT_CREATE_USER
        defaultDrugShouldNotBeFound("createUser.notEquals=" + DEFAULT_CREATE_USER);

        // Get all the drugList where createUser not equals to UPDATED_CREATE_USER
        defaultDrugShouldBeFound("createUser.notEquals=" + UPDATED_CREATE_USER);
    }

    @Test
    @Transactional
    public void getAllDrugsByCreateUserIsInShouldWork() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where createUser in DEFAULT_CREATE_USER or UPDATED_CREATE_USER
        defaultDrugShouldBeFound("createUser.in=" + DEFAULT_CREATE_USER + "," + UPDATED_CREATE_USER);

        // Get all the drugList where createUser equals to UPDATED_CREATE_USER
        defaultDrugShouldNotBeFound("createUser.in=" + UPDATED_CREATE_USER);
    }

    @Test
    @Transactional
    public void getAllDrugsByCreateUserIsNullOrNotNull() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where createUser is not null
        defaultDrugShouldBeFound("createUser.specified=true");

        // Get all the drugList where createUser is null
        defaultDrugShouldNotBeFound("createUser.specified=false");
    }
                @Test
    @Transactional
    public void getAllDrugsByCreateUserContainsSomething() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where createUser contains DEFAULT_CREATE_USER
        defaultDrugShouldBeFound("createUser.contains=" + DEFAULT_CREATE_USER);

        // Get all the drugList where createUser contains UPDATED_CREATE_USER
        defaultDrugShouldNotBeFound("createUser.contains=" + UPDATED_CREATE_USER);
    }

    @Test
    @Transactional
    public void getAllDrugsByCreateUserNotContainsSomething() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where createUser does not contain DEFAULT_CREATE_USER
        defaultDrugShouldNotBeFound("createUser.doesNotContain=" + DEFAULT_CREATE_USER);

        // Get all the drugList where createUser does not contain UPDATED_CREATE_USER
        defaultDrugShouldBeFound("createUser.doesNotContain=" + UPDATED_CREATE_USER);
    }


    @Test
    @Transactional
    public void getAllDrugsByCreateDateIsEqualToSomething() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where createDate equals to DEFAULT_CREATE_DATE
        defaultDrugShouldBeFound("createDate.equals=" + DEFAULT_CREATE_DATE);

        // Get all the drugList where createDate equals to UPDATED_CREATE_DATE
        defaultDrugShouldNotBeFound("createDate.equals=" + UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void getAllDrugsByCreateDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where createDate not equals to DEFAULT_CREATE_DATE
        defaultDrugShouldNotBeFound("createDate.notEquals=" + DEFAULT_CREATE_DATE);

        // Get all the drugList where createDate not equals to UPDATED_CREATE_DATE
        defaultDrugShouldBeFound("createDate.notEquals=" + UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void getAllDrugsByCreateDateIsInShouldWork() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where createDate in DEFAULT_CREATE_DATE or UPDATED_CREATE_DATE
        defaultDrugShouldBeFound("createDate.in=" + DEFAULT_CREATE_DATE + "," + UPDATED_CREATE_DATE);

        // Get all the drugList where createDate equals to UPDATED_CREATE_DATE
        defaultDrugShouldNotBeFound("createDate.in=" + UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void getAllDrugsByCreateDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where createDate is not null
        defaultDrugShouldBeFound("createDate.specified=true");

        // Get all the drugList where createDate is null
        defaultDrugShouldNotBeFound("createDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllDrugsByUpdateUserIsEqualToSomething() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where updateUser equals to DEFAULT_UPDATE_USER
        defaultDrugShouldBeFound("updateUser.equals=" + DEFAULT_UPDATE_USER);

        // Get all the drugList where updateUser equals to UPDATED_UPDATE_USER
        defaultDrugShouldNotBeFound("updateUser.equals=" + UPDATED_UPDATE_USER);
    }

    @Test
    @Transactional
    public void getAllDrugsByUpdateUserIsNotEqualToSomething() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where updateUser not equals to DEFAULT_UPDATE_USER
        defaultDrugShouldNotBeFound("updateUser.notEquals=" + DEFAULT_UPDATE_USER);

        // Get all the drugList where updateUser not equals to UPDATED_UPDATE_USER
        defaultDrugShouldBeFound("updateUser.notEquals=" + UPDATED_UPDATE_USER);
    }

    @Test
    @Transactional
    public void getAllDrugsByUpdateUserIsInShouldWork() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where updateUser in DEFAULT_UPDATE_USER or UPDATED_UPDATE_USER
        defaultDrugShouldBeFound("updateUser.in=" + DEFAULT_UPDATE_USER + "," + UPDATED_UPDATE_USER);

        // Get all the drugList where updateUser equals to UPDATED_UPDATE_USER
        defaultDrugShouldNotBeFound("updateUser.in=" + UPDATED_UPDATE_USER);
    }

    @Test
    @Transactional
    public void getAllDrugsByUpdateUserIsNullOrNotNull() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where updateUser is not null
        defaultDrugShouldBeFound("updateUser.specified=true");

        // Get all the drugList where updateUser is null
        defaultDrugShouldNotBeFound("updateUser.specified=false");
    }
                @Test
    @Transactional
    public void getAllDrugsByUpdateUserContainsSomething() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where updateUser contains DEFAULT_UPDATE_USER
        defaultDrugShouldBeFound("updateUser.contains=" + DEFAULT_UPDATE_USER);

        // Get all the drugList where updateUser contains UPDATED_UPDATE_USER
        defaultDrugShouldNotBeFound("updateUser.contains=" + UPDATED_UPDATE_USER);
    }

    @Test
    @Transactional
    public void getAllDrugsByUpdateUserNotContainsSomething() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where updateUser does not contain DEFAULT_UPDATE_USER
        defaultDrugShouldNotBeFound("updateUser.doesNotContain=" + DEFAULT_UPDATE_USER);

        // Get all the drugList where updateUser does not contain UPDATED_UPDATE_USER
        defaultDrugShouldBeFound("updateUser.doesNotContain=" + UPDATED_UPDATE_USER);
    }


    @Test
    @Transactional
    public void getAllDrugsByUpdateDateIsEqualToSomething() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where updateDate equals to DEFAULT_UPDATE_DATE
        defaultDrugShouldBeFound("updateDate.equals=" + DEFAULT_UPDATE_DATE);

        // Get all the drugList where updateDate equals to UPDATED_UPDATE_DATE
        defaultDrugShouldNotBeFound("updateDate.equals=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void getAllDrugsByUpdateDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where updateDate not equals to DEFAULT_UPDATE_DATE
        defaultDrugShouldNotBeFound("updateDate.notEquals=" + DEFAULT_UPDATE_DATE);

        // Get all the drugList where updateDate not equals to UPDATED_UPDATE_DATE
        defaultDrugShouldBeFound("updateDate.notEquals=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void getAllDrugsByUpdateDateIsInShouldWork() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where updateDate in DEFAULT_UPDATE_DATE or UPDATED_UPDATE_DATE
        defaultDrugShouldBeFound("updateDate.in=" + DEFAULT_UPDATE_DATE + "," + UPDATED_UPDATE_DATE);

        // Get all the drugList where updateDate equals to UPDATED_UPDATE_DATE
        defaultDrugShouldNotBeFound("updateDate.in=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void getAllDrugsByUpdateDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where updateDate is not null
        defaultDrugShouldBeFound("updateDate.specified=true");

        // Get all the drugList where updateDate is null
        defaultDrugShouldNotBeFound("updateDate.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDrugShouldBeFound(String filter) throws Exception {
        restDrugMockMvc.perform(get("/api/drugs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(drug.getId().intValue())))
            .andExpect(jsonPath("$.[*].drugName").value(hasItem(DEFAULT_DRUG_NAME)))
            .andExpect(jsonPath("$.[*].createUser").value(hasItem(DEFAULT_CREATE_USER)))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].updateUser").value(hasItem(DEFAULT_UPDATE_USER)))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())));

        // Check, that the count call also returns 1
        restDrugMockMvc.perform(get("/api/drugs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDrugShouldNotBeFound(String filter) throws Exception {
        restDrugMockMvc.perform(get("/api/drugs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDrugMockMvc.perform(get("/api/drugs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingDrug() throws Exception {
        // Get the drug
        restDrugMockMvc.perform(get("/api/drugs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDrug() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        int databaseSizeBeforeUpdate = drugRepository.findAll().size();

        // Update the drug
        Drug updatedDrug = drugRepository.findById(drug.getId()).get();
        // Disconnect from session so that the updates on updatedDrug are not directly saved in db
        em.detach(updatedDrug);
        updatedDrug
            .drugName(UPDATED_DRUG_NAME)
            .createUser(UPDATED_CREATE_USER)
            .createDate(UPDATED_CREATE_DATE)
            .updateUser(UPDATED_UPDATE_USER)
            .updateDate(UPDATED_UPDATE_DATE);
        DrugDTO drugDTO = drugMapper.toDto(updatedDrug);

        restDrugMockMvc.perform(put("/api/drugs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(drugDTO)))
            .andExpect(status().isOk());

        // Validate the Drug in the database
        List<Drug> drugList = drugRepository.findAll();
        assertThat(drugList).hasSize(databaseSizeBeforeUpdate);
        Drug testDrug = drugList.get(drugList.size() - 1);
        assertThat(testDrug.getDrugName()).isEqualTo(UPDATED_DRUG_NAME);
        assertThat(testDrug.getCreateUser()).isEqualTo(UPDATED_CREATE_USER);
        assertThat(testDrug.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testDrug.getUpdateUser()).isEqualTo(UPDATED_UPDATE_USER);
        assertThat(testDrug.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingDrug() throws Exception {
        int databaseSizeBeforeUpdate = drugRepository.findAll().size();

        // Create the Drug
        DrugDTO drugDTO = drugMapper.toDto(drug);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDrugMockMvc.perform(put("/api/drugs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(drugDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Drug in the database
        List<Drug> drugList = drugRepository.findAll();
        assertThat(drugList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDrug() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        int databaseSizeBeforeDelete = drugRepository.findAll().size();

        // Delete the drug
        restDrugMockMvc.perform(delete("/api/drugs/{id}", drug.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Drug> drugList = drugRepository.findAll();
        assertThat(drugList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
