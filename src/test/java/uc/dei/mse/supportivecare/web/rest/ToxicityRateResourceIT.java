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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import uc.dei.mse.supportivecare.SupportivecareApp;
import uc.dei.mse.supportivecare.domain.Symptom;
import uc.dei.mse.supportivecare.domain.ToxicityRate;
import uc.dei.mse.supportivecare.repository.ToxicityRateRepository;
import uc.dei.mse.supportivecare.service.ToxicityRateQueryService;
import uc.dei.mse.supportivecare.service.dto.ToxicityRateCriteria;
import uc.dei.mse.supportivecare.service.dto.ToxicityRateDTO;
import uc.dei.mse.supportivecare.service.mapper.ToxicityRateMapper;

/**
 * Integration tests for the {@link ToxicityRateResource} REST controller.
 */
@SpringBootTest(classes = SupportivecareApp.class)
@AutoConfigureMockMvc
@WithMockUser
class ToxicityRateResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_NOTICE = "AAAAAAAAAA";
    private static final String UPDATED_NOTICE = "BBBBBBBBBB";

    private static final String DEFAULT_AUTONOMOUS_INTERVENTION = "AAAAAAAAAA";
    private static final String UPDATED_AUTONOMOUS_INTERVENTION = "BBBBBBBBBB";

    private static final String DEFAULT_INTERDEPENDENT_INTERVENTION = "AAAAAAAAAA";
    private static final String UPDATED_INTERDEPENDENT_INTERVENTION = "BBBBBBBBBB";

    private static final String DEFAULT_SELF_MANAGEMENT = "AAAAAAAAAA";
    private static final String UPDATED_SELF_MANAGEMENT = "BBBBBBBBBB";

    @Autowired
    private ToxicityRateRepository toxicityRateRepository;

    @Autowired
    private ToxicityRateMapper toxicityRateMapper;

    @Autowired
    private ToxicityRateQueryService toxicityRateQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restToxicityRateMockMvc;

    private ToxicityRate toxicityRate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ToxicityRate createEntity(EntityManager em) {
        ToxicityRate toxicityRate = new ToxicityRate()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .notice(DEFAULT_NOTICE)
            .autonomousIntervention(DEFAULT_AUTONOMOUS_INTERVENTION)
            .interdependentIntervention(DEFAULT_INTERDEPENDENT_INTERVENTION)
            .selfManagement(DEFAULT_SELF_MANAGEMENT);
        return toxicityRate;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ToxicityRate createUpdatedEntity(EntityManager em) {
        ToxicityRate toxicityRate = new ToxicityRate()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .notice(UPDATED_NOTICE)
            .autonomousIntervention(UPDATED_AUTONOMOUS_INTERVENTION)
            .interdependentIntervention(UPDATED_INTERDEPENDENT_INTERVENTION)
            .selfManagement(UPDATED_SELF_MANAGEMENT);
        return toxicityRate;
    }

    @BeforeEach
    public void initTest() {
        toxicityRate = createEntity(em);
    }

    @Test
    @Transactional
    void createToxicityRate() throws Exception {
        int databaseSizeBeforeCreate = toxicityRateRepository.findAll().size();
        // Create the ToxicityRate
        ToxicityRateDTO toxicityRateDTO = toxicityRateMapper.toDto(toxicityRate);
        restToxicityRateMockMvc
            .perform(
                post("/api/toxicity-rates")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(toxicityRateDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ToxicityRate in the database
        List<ToxicityRate> toxicityRateList = toxicityRateRepository.findAll();
        assertThat(toxicityRateList).hasSize(databaseSizeBeforeCreate + 1);
        ToxicityRate testToxicityRate = toxicityRateList.get(toxicityRateList.size() - 1);
        assertThat(testToxicityRate.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testToxicityRate.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testToxicityRate.getNotice()).isEqualTo(DEFAULT_NOTICE);
        assertThat(testToxicityRate.getAutonomousIntervention()).isEqualTo(DEFAULT_AUTONOMOUS_INTERVENTION);
        assertThat(testToxicityRate.getInterdependentIntervention()).isEqualTo(DEFAULT_INTERDEPENDENT_INTERVENTION);
        assertThat(testToxicityRate.getSelfManagement()).isEqualTo(DEFAULT_SELF_MANAGEMENT);
    }

    @Test
    @Transactional
    void createToxicityRateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = toxicityRateRepository.findAll().size();

        // Create the ToxicityRate with an existing ID
        toxicityRate.setId(1L);
        ToxicityRateDTO toxicityRateDTO = toxicityRateMapper.toDto(toxicityRate);

        // An entity with an existing ID cannot be created, so this API call must fail
        restToxicityRateMockMvc
            .perform(
                post("/api/toxicity-rates")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(toxicityRateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ToxicityRate in the database
        List<ToxicityRate> toxicityRateList = toxicityRateRepository.findAll();
        assertThat(toxicityRateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = toxicityRateRepository.findAll().size();
        // set the field null
        toxicityRate.setName(null);

        // Create the ToxicityRate, which fails.
        ToxicityRateDTO toxicityRateDTO = toxicityRateMapper.toDto(toxicityRate);

        restToxicityRateMockMvc
            .perform(
                post("/api/toxicity-rates")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(toxicityRateDTO))
            )
            .andExpect(status().isBadRequest());

        List<ToxicityRate> toxicityRateList = toxicityRateRepository.findAll();
        assertThat(toxicityRateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllToxicityRates() throws Exception {
        // Initialize the database
        toxicityRateRepository.saveAndFlush(toxicityRate);

        // Get all the toxicityRateList
        restToxicityRateMockMvc
            .perform(get("/api/toxicity-rates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(toxicityRate.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].notice").value(hasItem(DEFAULT_NOTICE)))
            .andExpect(jsonPath("$.[*].autonomousIntervention").value(hasItem(DEFAULT_AUTONOMOUS_INTERVENTION)))
            .andExpect(jsonPath("$.[*].interdependentIntervention").value(hasItem(DEFAULT_INTERDEPENDENT_INTERVENTION)))
            .andExpect(jsonPath("$.[*].selfManagement").value(hasItem(DEFAULT_SELF_MANAGEMENT)));
    }

    @Test
    @Transactional
    void getToxicityRate() throws Exception {
        // Initialize the database
        toxicityRateRepository.saveAndFlush(toxicityRate);

        // Get the toxicityRate
        restToxicityRateMockMvc
            .perform(get("/api/toxicity-rates/{id}", toxicityRate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(toxicityRate.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.notice").value(DEFAULT_NOTICE))
            .andExpect(jsonPath("$.autonomousIntervention").value(DEFAULT_AUTONOMOUS_INTERVENTION))
            .andExpect(jsonPath("$.interdependentIntervention").value(DEFAULT_INTERDEPENDENT_INTERVENTION))
            .andExpect(jsonPath("$.selfManagement").value(DEFAULT_SELF_MANAGEMENT));
    }

    @Test
    @Transactional
    void getToxicityRatesByIdFiltering() throws Exception {
        // Initialize the database
        toxicityRateRepository.saveAndFlush(toxicityRate);

        Long id = toxicityRate.getId();

        defaultToxicityRateShouldBeFound("id.equals=" + id);
        defaultToxicityRateShouldNotBeFound("id.notEquals=" + id);

        defaultToxicityRateShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultToxicityRateShouldNotBeFound("id.greaterThan=" + id);

        defaultToxicityRateShouldBeFound("id.lessThanOrEqual=" + id);
        defaultToxicityRateShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllToxicityRatesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        toxicityRateRepository.saveAndFlush(toxicityRate);

        // Get all the toxicityRateList where name equals to DEFAULT_NAME
        defaultToxicityRateShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the toxicityRateList where name equals to UPDATED_NAME
        defaultToxicityRateShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllToxicityRatesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        toxicityRateRepository.saveAndFlush(toxicityRate);

        // Get all the toxicityRateList where name not equals to DEFAULT_NAME
        defaultToxicityRateShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the toxicityRateList where name not equals to UPDATED_NAME
        defaultToxicityRateShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllToxicityRatesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        toxicityRateRepository.saveAndFlush(toxicityRate);

        // Get all the toxicityRateList where name in DEFAULT_NAME or UPDATED_NAME
        defaultToxicityRateShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the toxicityRateList where name equals to UPDATED_NAME
        defaultToxicityRateShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllToxicityRatesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        toxicityRateRepository.saveAndFlush(toxicityRate);

        // Get all the toxicityRateList where name is not null
        defaultToxicityRateShouldBeFound("name.specified=true");

        // Get all the toxicityRateList where name is null
        defaultToxicityRateShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllToxicityRatesByNameContainsSomething() throws Exception {
        // Initialize the database
        toxicityRateRepository.saveAndFlush(toxicityRate);

        // Get all the toxicityRateList where name contains DEFAULT_NAME
        defaultToxicityRateShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the toxicityRateList where name contains UPDATED_NAME
        defaultToxicityRateShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllToxicityRatesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        toxicityRateRepository.saveAndFlush(toxicityRate);

        // Get all the toxicityRateList where name does not contain DEFAULT_NAME
        defaultToxicityRateShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the toxicityRateList where name does not contain UPDATED_NAME
        defaultToxicityRateShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllToxicityRatesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        toxicityRateRepository.saveAndFlush(toxicityRate);

        // Get all the toxicityRateList where description equals to DEFAULT_DESCRIPTION
        defaultToxicityRateShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the toxicityRateList where description equals to UPDATED_DESCRIPTION
        defaultToxicityRateShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllToxicityRatesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        toxicityRateRepository.saveAndFlush(toxicityRate);

        // Get all the toxicityRateList where description not equals to DEFAULT_DESCRIPTION
        defaultToxicityRateShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the toxicityRateList where description not equals to UPDATED_DESCRIPTION
        defaultToxicityRateShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllToxicityRatesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        toxicityRateRepository.saveAndFlush(toxicityRate);

        // Get all the toxicityRateList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultToxicityRateShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the toxicityRateList where description equals to UPDATED_DESCRIPTION
        defaultToxicityRateShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllToxicityRatesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        toxicityRateRepository.saveAndFlush(toxicityRate);

        // Get all the toxicityRateList where description is not null
        defaultToxicityRateShouldBeFound("description.specified=true");

        // Get all the toxicityRateList where description is null
        defaultToxicityRateShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllToxicityRatesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        toxicityRateRepository.saveAndFlush(toxicityRate);

        // Get all the toxicityRateList where description contains DEFAULT_DESCRIPTION
        defaultToxicityRateShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the toxicityRateList where description contains UPDATED_DESCRIPTION
        defaultToxicityRateShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllToxicityRatesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        toxicityRateRepository.saveAndFlush(toxicityRate);

        // Get all the toxicityRateList where description does not contain DEFAULT_DESCRIPTION
        defaultToxicityRateShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the toxicityRateList where description does not contain UPDATED_DESCRIPTION
        defaultToxicityRateShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllToxicityRatesByNoticeIsEqualToSomething() throws Exception {
        // Initialize the database
        toxicityRateRepository.saveAndFlush(toxicityRate);

        // Get all the toxicityRateList where notice equals to DEFAULT_NOTICE
        defaultToxicityRateShouldBeFound("notice.equals=" + DEFAULT_NOTICE);

        // Get all the toxicityRateList where notice equals to UPDATED_NOTICE
        defaultToxicityRateShouldNotBeFound("notice.equals=" + UPDATED_NOTICE);
    }

    @Test
    @Transactional
    void getAllToxicityRatesByNoticeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        toxicityRateRepository.saveAndFlush(toxicityRate);

        // Get all the toxicityRateList where notice not equals to DEFAULT_NOTICE
        defaultToxicityRateShouldNotBeFound("notice.notEquals=" + DEFAULT_NOTICE);

        // Get all the toxicityRateList where notice not equals to UPDATED_NOTICE
        defaultToxicityRateShouldBeFound("notice.notEquals=" + UPDATED_NOTICE);
    }

    @Test
    @Transactional
    void getAllToxicityRatesByNoticeIsInShouldWork() throws Exception {
        // Initialize the database
        toxicityRateRepository.saveAndFlush(toxicityRate);

        // Get all the toxicityRateList where notice in DEFAULT_NOTICE or UPDATED_NOTICE
        defaultToxicityRateShouldBeFound("notice.in=" + DEFAULT_NOTICE + "," + UPDATED_NOTICE);

        // Get all the toxicityRateList where notice equals to UPDATED_NOTICE
        defaultToxicityRateShouldNotBeFound("notice.in=" + UPDATED_NOTICE);
    }

    @Test
    @Transactional
    void getAllToxicityRatesByNoticeIsNullOrNotNull() throws Exception {
        // Initialize the database
        toxicityRateRepository.saveAndFlush(toxicityRate);

        // Get all the toxicityRateList where notice is not null
        defaultToxicityRateShouldBeFound("notice.specified=true");

        // Get all the toxicityRateList where notice is null
        defaultToxicityRateShouldNotBeFound("notice.specified=false");
    }

    @Test
    @Transactional
    void getAllToxicityRatesByNoticeContainsSomething() throws Exception {
        // Initialize the database
        toxicityRateRepository.saveAndFlush(toxicityRate);

        // Get all the toxicityRateList where notice contains DEFAULT_NOTICE
        defaultToxicityRateShouldBeFound("notice.contains=" + DEFAULT_NOTICE);

        // Get all the toxicityRateList where notice contains UPDATED_NOTICE
        defaultToxicityRateShouldNotBeFound("notice.contains=" + UPDATED_NOTICE);
    }

    @Test
    @Transactional
    void getAllToxicityRatesByNoticeNotContainsSomething() throws Exception {
        // Initialize the database
        toxicityRateRepository.saveAndFlush(toxicityRate);

        // Get all the toxicityRateList where notice does not contain DEFAULT_NOTICE
        defaultToxicityRateShouldNotBeFound("notice.doesNotContain=" + DEFAULT_NOTICE);

        // Get all the toxicityRateList where notice does not contain UPDATED_NOTICE
        defaultToxicityRateShouldBeFound("notice.doesNotContain=" + UPDATED_NOTICE);
    }

    @Test
    @Transactional
    void getAllToxicityRatesByAutonomousInterventionIsEqualToSomething() throws Exception {
        // Initialize the database
        toxicityRateRepository.saveAndFlush(toxicityRate);

        // Get all the toxicityRateList where autonomousIntervention equals to DEFAULT_AUTONOMOUS_INTERVENTION
        defaultToxicityRateShouldBeFound("autonomousIntervention.equals=" + DEFAULT_AUTONOMOUS_INTERVENTION);

        // Get all the toxicityRateList where autonomousIntervention equals to UPDATED_AUTONOMOUS_INTERVENTION
        defaultToxicityRateShouldNotBeFound("autonomousIntervention.equals=" + UPDATED_AUTONOMOUS_INTERVENTION);
    }

    @Test
    @Transactional
    void getAllToxicityRatesByAutonomousInterventionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        toxicityRateRepository.saveAndFlush(toxicityRate);

        // Get all the toxicityRateList where autonomousIntervention not equals to DEFAULT_AUTONOMOUS_INTERVENTION
        defaultToxicityRateShouldNotBeFound("autonomousIntervention.notEquals=" + DEFAULT_AUTONOMOUS_INTERVENTION);

        // Get all the toxicityRateList where autonomousIntervention not equals to UPDATED_AUTONOMOUS_INTERVENTION
        defaultToxicityRateShouldBeFound("autonomousIntervention.notEquals=" + UPDATED_AUTONOMOUS_INTERVENTION);
    }

    @Test
    @Transactional
    void getAllToxicityRatesByAutonomousInterventionIsInShouldWork() throws Exception {
        // Initialize the database
        toxicityRateRepository.saveAndFlush(toxicityRate);

        // Get all the toxicityRateList where autonomousIntervention in DEFAULT_AUTONOMOUS_INTERVENTION or UPDATED_AUTONOMOUS_INTERVENTION
        defaultToxicityRateShouldBeFound(
            "autonomousIntervention.in=" + DEFAULT_AUTONOMOUS_INTERVENTION + "," + UPDATED_AUTONOMOUS_INTERVENTION
        );

        // Get all the toxicityRateList where autonomousIntervention equals to UPDATED_AUTONOMOUS_INTERVENTION
        defaultToxicityRateShouldNotBeFound("autonomousIntervention.in=" + UPDATED_AUTONOMOUS_INTERVENTION);
    }

    @Test
    @Transactional
    void getAllToxicityRatesByAutonomousInterventionIsNullOrNotNull() throws Exception {
        // Initialize the database
        toxicityRateRepository.saveAndFlush(toxicityRate);

        // Get all the toxicityRateList where autonomousIntervention is not null
        defaultToxicityRateShouldBeFound("autonomousIntervention.specified=true");

        // Get all the toxicityRateList where autonomousIntervention is null
        defaultToxicityRateShouldNotBeFound("autonomousIntervention.specified=false");
    }

    @Test
    @Transactional
    void getAllToxicityRatesByAutonomousInterventionContainsSomething() throws Exception {
        // Initialize the database
        toxicityRateRepository.saveAndFlush(toxicityRate);

        // Get all the toxicityRateList where autonomousIntervention contains DEFAULT_AUTONOMOUS_INTERVENTION
        defaultToxicityRateShouldBeFound("autonomousIntervention.contains=" + DEFAULT_AUTONOMOUS_INTERVENTION);

        // Get all the toxicityRateList where autonomousIntervention contains UPDATED_AUTONOMOUS_INTERVENTION
        defaultToxicityRateShouldNotBeFound("autonomousIntervention.contains=" + UPDATED_AUTONOMOUS_INTERVENTION);
    }

    @Test
    @Transactional
    void getAllToxicityRatesByAutonomousInterventionNotContainsSomething() throws Exception {
        // Initialize the database
        toxicityRateRepository.saveAndFlush(toxicityRate);

        // Get all the toxicityRateList where autonomousIntervention does not contain DEFAULT_AUTONOMOUS_INTERVENTION
        defaultToxicityRateShouldNotBeFound("autonomousIntervention.doesNotContain=" + DEFAULT_AUTONOMOUS_INTERVENTION);

        // Get all the toxicityRateList where autonomousIntervention does not contain UPDATED_AUTONOMOUS_INTERVENTION
        defaultToxicityRateShouldBeFound("autonomousIntervention.doesNotContain=" + UPDATED_AUTONOMOUS_INTERVENTION);
    }

    @Test
    @Transactional
    void getAllToxicityRatesByInterdependentInterventionIsEqualToSomething() throws Exception {
        // Initialize the database
        toxicityRateRepository.saveAndFlush(toxicityRate);

        // Get all the toxicityRateList where interdependentIntervention equals to DEFAULT_INTERDEPENDENT_INTERVENTION
        defaultToxicityRateShouldBeFound("interdependentIntervention.equals=" + DEFAULT_INTERDEPENDENT_INTERVENTION);

        // Get all the toxicityRateList where interdependentIntervention equals to UPDATED_INTERDEPENDENT_INTERVENTION
        defaultToxicityRateShouldNotBeFound("interdependentIntervention.equals=" + UPDATED_INTERDEPENDENT_INTERVENTION);
    }

    @Test
    @Transactional
    void getAllToxicityRatesByInterdependentInterventionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        toxicityRateRepository.saveAndFlush(toxicityRate);

        // Get all the toxicityRateList where interdependentIntervention not equals to DEFAULT_INTERDEPENDENT_INTERVENTION
        defaultToxicityRateShouldNotBeFound("interdependentIntervention.notEquals=" + DEFAULT_INTERDEPENDENT_INTERVENTION);

        // Get all the toxicityRateList where interdependentIntervention not equals to UPDATED_INTERDEPENDENT_INTERVENTION
        defaultToxicityRateShouldBeFound("interdependentIntervention.notEquals=" + UPDATED_INTERDEPENDENT_INTERVENTION);
    }

    @Test
    @Transactional
    void getAllToxicityRatesByInterdependentInterventionIsInShouldWork() throws Exception {
        // Initialize the database
        toxicityRateRepository.saveAndFlush(toxicityRate);

        // Get all the toxicityRateList where interdependentIntervention in DEFAULT_INTERDEPENDENT_INTERVENTION or UPDATED_INTERDEPENDENT_INTERVENTION
        defaultToxicityRateShouldBeFound(
            "interdependentIntervention.in=" + DEFAULT_INTERDEPENDENT_INTERVENTION + "," + UPDATED_INTERDEPENDENT_INTERVENTION
        );

        // Get all the toxicityRateList where interdependentIntervention equals to UPDATED_INTERDEPENDENT_INTERVENTION
        defaultToxicityRateShouldNotBeFound("interdependentIntervention.in=" + UPDATED_INTERDEPENDENT_INTERVENTION);
    }

    @Test
    @Transactional
    void getAllToxicityRatesByInterdependentInterventionIsNullOrNotNull() throws Exception {
        // Initialize the database
        toxicityRateRepository.saveAndFlush(toxicityRate);

        // Get all the toxicityRateList where interdependentIntervention is not null
        defaultToxicityRateShouldBeFound("interdependentIntervention.specified=true");

        // Get all the toxicityRateList where interdependentIntervention is null
        defaultToxicityRateShouldNotBeFound("interdependentIntervention.specified=false");
    }

    @Test
    @Transactional
    void getAllToxicityRatesByInterdependentInterventionContainsSomething() throws Exception {
        // Initialize the database
        toxicityRateRepository.saveAndFlush(toxicityRate);

        // Get all the toxicityRateList where interdependentIntervention contains DEFAULT_INTERDEPENDENT_INTERVENTION
        defaultToxicityRateShouldBeFound("interdependentIntervention.contains=" + DEFAULT_INTERDEPENDENT_INTERVENTION);

        // Get all the toxicityRateList where interdependentIntervention contains UPDATED_INTERDEPENDENT_INTERVENTION
        defaultToxicityRateShouldNotBeFound("interdependentIntervention.contains=" + UPDATED_INTERDEPENDENT_INTERVENTION);
    }

    @Test
    @Transactional
    void getAllToxicityRatesByInterdependentInterventionNotContainsSomething() throws Exception {
        // Initialize the database
        toxicityRateRepository.saveAndFlush(toxicityRate);

        // Get all the toxicityRateList where interdependentIntervention does not contain DEFAULT_INTERDEPENDENT_INTERVENTION
        defaultToxicityRateShouldNotBeFound("interdependentIntervention.doesNotContain=" + DEFAULT_INTERDEPENDENT_INTERVENTION);

        // Get all the toxicityRateList where interdependentIntervention does not contain UPDATED_INTERDEPENDENT_INTERVENTION
        defaultToxicityRateShouldBeFound("interdependentIntervention.doesNotContain=" + UPDATED_INTERDEPENDENT_INTERVENTION);
    }

    @Test
    @Transactional
    void getAllToxicityRatesBySelfManagementIsEqualToSomething() throws Exception {
        // Initialize the database
        toxicityRateRepository.saveAndFlush(toxicityRate);

        // Get all the toxicityRateList where selfManagement equals to DEFAULT_SELF_MANAGEMENT
        defaultToxicityRateShouldBeFound("selfManagement.equals=" + DEFAULT_SELF_MANAGEMENT);

        // Get all the toxicityRateList where selfManagement equals to UPDATED_SELF_MANAGEMENT
        defaultToxicityRateShouldNotBeFound("selfManagement.equals=" + UPDATED_SELF_MANAGEMENT);
    }

    @Test
    @Transactional
    void getAllToxicityRatesBySelfManagementIsNotEqualToSomething() throws Exception {
        // Initialize the database
        toxicityRateRepository.saveAndFlush(toxicityRate);

        // Get all the toxicityRateList where selfManagement not equals to DEFAULT_SELF_MANAGEMENT
        defaultToxicityRateShouldNotBeFound("selfManagement.notEquals=" + DEFAULT_SELF_MANAGEMENT);

        // Get all the toxicityRateList where selfManagement not equals to UPDATED_SELF_MANAGEMENT
        defaultToxicityRateShouldBeFound("selfManagement.notEquals=" + UPDATED_SELF_MANAGEMENT);
    }

    @Test
    @Transactional
    void getAllToxicityRatesBySelfManagementIsInShouldWork() throws Exception {
        // Initialize the database
        toxicityRateRepository.saveAndFlush(toxicityRate);

        // Get all the toxicityRateList where selfManagement in DEFAULT_SELF_MANAGEMENT or UPDATED_SELF_MANAGEMENT
        defaultToxicityRateShouldBeFound("selfManagement.in=" + DEFAULT_SELF_MANAGEMENT + "," + UPDATED_SELF_MANAGEMENT);

        // Get all the toxicityRateList where selfManagement equals to UPDATED_SELF_MANAGEMENT
        defaultToxicityRateShouldNotBeFound("selfManagement.in=" + UPDATED_SELF_MANAGEMENT);
    }

    @Test
    @Transactional
    void getAllToxicityRatesBySelfManagementIsNullOrNotNull() throws Exception {
        // Initialize the database
        toxicityRateRepository.saveAndFlush(toxicityRate);

        // Get all the toxicityRateList where selfManagement is not null
        defaultToxicityRateShouldBeFound("selfManagement.specified=true");

        // Get all the toxicityRateList where selfManagement is null
        defaultToxicityRateShouldNotBeFound("selfManagement.specified=false");
    }

    @Test
    @Transactional
    void getAllToxicityRatesBySelfManagementContainsSomething() throws Exception {
        // Initialize the database
        toxicityRateRepository.saveAndFlush(toxicityRate);

        // Get all the toxicityRateList where selfManagement contains DEFAULT_SELF_MANAGEMENT
        defaultToxicityRateShouldBeFound("selfManagement.contains=" + DEFAULT_SELF_MANAGEMENT);

        // Get all the toxicityRateList where selfManagement contains UPDATED_SELF_MANAGEMENT
        defaultToxicityRateShouldNotBeFound("selfManagement.contains=" + UPDATED_SELF_MANAGEMENT);
    }

    @Test
    @Transactional
    void getAllToxicityRatesBySelfManagementNotContainsSomething() throws Exception {
        // Initialize the database
        toxicityRateRepository.saveAndFlush(toxicityRate);

        // Get all the toxicityRateList where selfManagement does not contain DEFAULT_SELF_MANAGEMENT
        defaultToxicityRateShouldNotBeFound("selfManagement.doesNotContain=" + DEFAULT_SELF_MANAGEMENT);

        // Get all the toxicityRateList where selfManagement does not contain UPDATED_SELF_MANAGEMENT
        defaultToxicityRateShouldBeFound("selfManagement.doesNotContain=" + UPDATED_SELF_MANAGEMENT);
    }

    @Test
    @Transactional
    void getAllToxicityRatesBySymptomIsEqualToSomething() throws Exception {
        // Initialize the database
        toxicityRateRepository.saveAndFlush(toxicityRate);
        Symptom symptom = SymptomResourceIT.createEntity(em);
        em.persist(symptom);
        em.flush();
        toxicityRate.addSymptom(symptom);
        toxicityRateRepository.saveAndFlush(toxicityRate);
        Long symptomId = symptom.getId();

        // Get all the toxicityRateList where symptom equals to symptomId
        defaultToxicityRateShouldBeFound("symptomId.equals=" + symptomId);

        // Get all the toxicityRateList where symptom equals to symptomId + 1
        defaultToxicityRateShouldNotBeFound("symptomId.equals=" + (symptomId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultToxicityRateShouldBeFound(String filter) throws Exception {
        restToxicityRateMockMvc
            .perform(get("/api/toxicity-rates?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(toxicityRate.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].notice").value(hasItem(DEFAULT_NOTICE)))
            .andExpect(jsonPath("$.[*].autonomousIntervention").value(hasItem(DEFAULT_AUTONOMOUS_INTERVENTION)))
            .andExpect(jsonPath("$.[*].interdependentIntervention").value(hasItem(DEFAULT_INTERDEPENDENT_INTERVENTION)))
            .andExpect(jsonPath("$.[*].selfManagement").value(hasItem(DEFAULT_SELF_MANAGEMENT)));

        // Check, that the count call also returns 1
        restToxicityRateMockMvc
            .perform(get("/api/toxicity-rates/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultToxicityRateShouldNotBeFound(String filter) throws Exception {
        restToxicityRateMockMvc
            .perform(get("/api/toxicity-rates?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restToxicityRateMockMvc
            .perform(get("/api/toxicity-rates/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingToxicityRate() throws Exception {
        // Get the toxicityRate
        restToxicityRateMockMvc.perform(get("/api/toxicity-rates/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void updateToxicityRate() throws Exception {
        // Initialize the database
        toxicityRateRepository.saveAndFlush(toxicityRate);

        int databaseSizeBeforeUpdate = toxicityRateRepository.findAll().size();

        // Update the toxicityRate
        ToxicityRate updatedToxicityRate = toxicityRateRepository.findById(toxicityRate.getId()).get();
        // Disconnect from session so that the updates on updatedToxicityRate are not directly saved in db
        em.detach(updatedToxicityRate);
        updatedToxicityRate
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .notice(UPDATED_NOTICE)
            .autonomousIntervention(UPDATED_AUTONOMOUS_INTERVENTION)
            .interdependentIntervention(UPDATED_INTERDEPENDENT_INTERVENTION)
            .selfManagement(UPDATED_SELF_MANAGEMENT);
        ToxicityRateDTO toxicityRateDTO = toxicityRateMapper.toDto(updatedToxicityRate);

        restToxicityRateMockMvc
            .perform(
                put("/api/toxicity-rates")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(toxicityRateDTO))
            )
            .andExpect(status().isOk());

        // Validate the ToxicityRate in the database
        List<ToxicityRate> toxicityRateList = toxicityRateRepository.findAll();
        assertThat(toxicityRateList).hasSize(databaseSizeBeforeUpdate);
        ToxicityRate testToxicityRate = toxicityRateList.get(toxicityRateList.size() - 1);
        assertThat(testToxicityRate.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testToxicityRate.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testToxicityRate.getNotice()).isEqualTo(UPDATED_NOTICE);
        assertThat(testToxicityRate.getAutonomousIntervention()).isEqualTo(UPDATED_AUTONOMOUS_INTERVENTION);
        assertThat(testToxicityRate.getInterdependentIntervention()).isEqualTo(UPDATED_INTERDEPENDENT_INTERVENTION);
        assertThat(testToxicityRate.getSelfManagement()).isEqualTo(UPDATED_SELF_MANAGEMENT);
    }

    @Test
    @Transactional
    void updateNonExistingToxicityRate() throws Exception {
        int databaseSizeBeforeUpdate = toxicityRateRepository.findAll().size();

        // Create the ToxicityRate
        ToxicityRateDTO toxicityRateDTO = toxicityRateMapper.toDto(toxicityRate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restToxicityRateMockMvc
            .perform(
                put("/api/toxicity-rates")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(toxicityRateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ToxicityRate in the database
        List<ToxicityRate> toxicityRateList = toxicityRateRepository.findAll();
        assertThat(toxicityRateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateToxicityRateWithPatch() throws Exception {
        // Initialize the database
        toxicityRateRepository.saveAndFlush(toxicityRate);

        int databaseSizeBeforeUpdate = toxicityRateRepository.findAll().size();

        // Update the toxicityRate using partial update
        ToxicityRate partialUpdatedToxicityRate = new ToxicityRate();
        partialUpdatedToxicityRate.setId(toxicityRate.getId());

        partialUpdatedToxicityRate.name(UPDATED_NAME).description(UPDATED_DESCRIPTION).selfManagement(UPDATED_SELF_MANAGEMENT);

        restToxicityRateMockMvc
            .perform(
                patch("/api/toxicity-rates")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedToxicityRate))
            )
            .andExpect(status().isOk());

        // Validate the ToxicityRate in the database
        List<ToxicityRate> toxicityRateList = toxicityRateRepository.findAll();
        assertThat(toxicityRateList).hasSize(databaseSizeBeforeUpdate);
        ToxicityRate testToxicityRate = toxicityRateList.get(toxicityRateList.size() - 1);
        assertThat(testToxicityRate.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testToxicityRate.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testToxicityRate.getNotice()).isEqualTo(DEFAULT_NOTICE);
        assertThat(testToxicityRate.getAutonomousIntervention()).isEqualTo(DEFAULT_AUTONOMOUS_INTERVENTION);
        assertThat(testToxicityRate.getInterdependentIntervention()).isEqualTo(DEFAULT_INTERDEPENDENT_INTERVENTION);
        assertThat(testToxicityRate.getSelfManagement()).isEqualTo(UPDATED_SELF_MANAGEMENT);
    }

    @Test
    @Transactional
    void fullUpdateToxicityRateWithPatch() throws Exception {
        // Initialize the database
        toxicityRateRepository.saveAndFlush(toxicityRate);

        int databaseSizeBeforeUpdate = toxicityRateRepository.findAll().size();

        // Update the toxicityRate using partial update
        ToxicityRate partialUpdatedToxicityRate = new ToxicityRate();
        partialUpdatedToxicityRate.setId(toxicityRate.getId());

        partialUpdatedToxicityRate
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .notice(UPDATED_NOTICE)
            .autonomousIntervention(UPDATED_AUTONOMOUS_INTERVENTION)
            .interdependentIntervention(UPDATED_INTERDEPENDENT_INTERVENTION)
            .selfManagement(UPDATED_SELF_MANAGEMENT);

        restToxicityRateMockMvc
            .perform(
                patch("/api/toxicity-rates")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedToxicityRate))
            )
            .andExpect(status().isOk());

        // Validate the ToxicityRate in the database
        List<ToxicityRate> toxicityRateList = toxicityRateRepository.findAll();
        assertThat(toxicityRateList).hasSize(databaseSizeBeforeUpdate);
        ToxicityRate testToxicityRate = toxicityRateList.get(toxicityRateList.size() - 1);
        assertThat(testToxicityRate.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testToxicityRate.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testToxicityRate.getNotice()).isEqualTo(UPDATED_NOTICE);
        assertThat(testToxicityRate.getAutonomousIntervention()).isEqualTo(UPDATED_AUTONOMOUS_INTERVENTION);
        assertThat(testToxicityRate.getInterdependentIntervention()).isEqualTo(UPDATED_INTERDEPENDENT_INTERVENTION);
        assertThat(testToxicityRate.getSelfManagement()).isEqualTo(UPDATED_SELF_MANAGEMENT);
    }

    @Test
    @Transactional
    void partialUpdateToxicityRateShouldThrown() throws Exception {
        // Update the toxicityRate without id should throw
        ToxicityRate partialUpdatedToxicityRate = new ToxicityRate();

        restToxicityRateMockMvc
            .perform(
                patch("/api/toxicity-rates")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedToxicityRate))
            )
            .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void deleteToxicityRate() throws Exception {
        // Initialize the database
        toxicityRateRepository.saveAndFlush(toxicityRate);

        int databaseSizeBeforeDelete = toxicityRateRepository.findAll().size();

        // Delete the toxicityRate
        restToxicityRateMockMvc
            .perform(delete("/api/toxicity-rates/{id}", toxicityRate.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ToxicityRate> toxicityRateList = toxicityRateRepository.findAll();
        assertThat(toxicityRateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
