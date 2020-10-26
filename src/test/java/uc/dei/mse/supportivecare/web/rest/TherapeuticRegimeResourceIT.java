package uc.dei.mse.supportivecare.web.rest;

import uc.dei.mse.supportivecare.SupportivecareApp;
import uc.dei.mse.supportivecare.domain.TherapeuticRegime;
import uc.dei.mse.supportivecare.domain.Treatment;
import uc.dei.mse.supportivecare.domain.Drug;
import uc.dei.mse.supportivecare.domain.Diagnostic;
import uc.dei.mse.supportivecare.repository.TherapeuticRegimeRepository;
import uc.dei.mse.supportivecare.service.TherapeuticRegimeService;
import uc.dei.mse.supportivecare.service.dto.TherapeuticRegimeDTO;
import uc.dei.mse.supportivecare.service.mapper.TherapeuticRegimeMapper;
import uc.dei.mse.supportivecare.service.dto.TherapeuticRegimeCriteria;
import uc.dei.mse.supportivecare.service.TherapeuticRegimeQueryService;

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
 * Integration tests for the {@link TherapeuticRegimeResource} REST controller.
 */
@SpringBootTest(classes = SupportivecareApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TherapeuticRegimeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ACRONYM = "AAAAAAAAAA";
    private static final String UPDATED_ACRONYM = "BBBBBBBBBB";

    private static final String DEFAULT_PURPOSE = "AAAAAAAAAA";
    private static final String UPDATED_PURPOSE = "BBBBBBBBBB";

    private static final String DEFAULT_CONDITION = "AAAAAAAAAA";
    private static final String UPDATED_CONDITION = "BBBBBBBBBB";

    private static final String DEFAULT_TIMING = "AAAAAAAAAA";
    private static final String UPDATED_TIMING = "BBBBBBBBBB";

    private static final String DEFAULT_INDICATION = "AAAAAAAAAA";
    private static final String UPDATED_INDICATION = "BBBBBBBBBB";

    private static final String DEFAULT_CRITERIA = "AAAAAAAAAA";
    private static final String UPDATED_CRITERIA = "BBBBBBBBBB";

    private static final String DEFAULT_NOTICE = "AAAAAAAAAA";
    private static final String UPDATED_NOTICE = "BBBBBBBBBB";

    @Autowired
    private TherapeuticRegimeRepository therapeuticRegimeRepository;

    @Autowired
    private TherapeuticRegimeMapper therapeuticRegimeMapper;

    @Autowired
    private TherapeuticRegimeService therapeuticRegimeService;

    @Autowired
    private TherapeuticRegimeQueryService therapeuticRegimeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTherapeuticRegimeMockMvc;

    private TherapeuticRegime therapeuticRegime;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TherapeuticRegime createEntity(EntityManager em) {
        TherapeuticRegime therapeuticRegime = new TherapeuticRegime()
            .name(DEFAULT_NAME)
            .acronym(DEFAULT_ACRONYM)
            .purpose(DEFAULT_PURPOSE)
            .condition(DEFAULT_CONDITION)
            .timing(DEFAULT_TIMING)
            .indication(DEFAULT_INDICATION)
            .criteria(DEFAULT_CRITERIA)
            .notice(DEFAULT_NOTICE);
        // Add required entity
        Treatment treatment;
        if (TestUtil.findAll(em, Treatment.class).isEmpty()) {
            treatment = TreatmentResourceIT.createEntity(em);
            em.persist(treatment);
            em.flush();
        } else {
            treatment = TestUtil.findAll(em, Treatment.class).get(0);
        }
        therapeuticRegime.setTreatment(treatment);
        return therapeuticRegime;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TherapeuticRegime createUpdatedEntity(EntityManager em) {
        TherapeuticRegime therapeuticRegime = new TherapeuticRegime()
            .name(UPDATED_NAME)
            .acronym(UPDATED_ACRONYM)
            .purpose(UPDATED_PURPOSE)
            .condition(UPDATED_CONDITION)
            .timing(UPDATED_TIMING)
            .indication(UPDATED_INDICATION)
            .criteria(UPDATED_CRITERIA)
            .notice(UPDATED_NOTICE);
        // Add required entity
        Treatment treatment;
        if (TestUtil.findAll(em, Treatment.class).isEmpty()) {
            treatment = TreatmentResourceIT.createUpdatedEntity(em);
            em.persist(treatment);
            em.flush();
        } else {
            treatment = TestUtil.findAll(em, Treatment.class).get(0);
        }
        therapeuticRegime.setTreatment(treatment);
        return therapeuticRegime;
    }

    @BeforeEach
    public void initTest() {
        therapeuticRegime = createEntity(em);
    }

    @Test
    @Transactional
    public void createTherapeuticRegime() throws Exception {
        int databaseSizeBeforeCreate = therapeuticRegimeRepository.findAll().size();
        // Create the TherapeuticRegime
        TherapeuticRegimeDTO therapeuticRegimeDTO = therapeuticRegimeMapper.toDto(therapeuticRegime);
        restTherapeuticRegimeMockMvc.perform(post("/api/therapeutic-regimes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(therapeuticRegimeDTO)))
            .andExpect(status().isCreated());

        // Validate the TherapeuticRegime in the database
        List<TherapeuticRegime> therapeuticRegimeList = therapeuticRegimeRepository.findAll();
        assertThat(therapeuticRegimeList).hasSize(databaseSizeBeforeCreate + 1);
        TherapeuticRegime testTherapeuticRegime = therapeuticRegimeList.get(therapeuticRegimeList.size() - 1);
        assertThat(testTherapeuticRegime.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTherapeuticRegime.getAcronym()).isEqualTo(DEFAULT_ACRONYM);
        assertThat(testTherapeuticRegime.getPurpose()).isEqualTo(DEFAULT_PURPOSE);
        assertThat(testTherapeuticRegime.getCondition()).isEqualTo(DEFAULT_CONDITION);
        assertThat(testTherapeuticRegime.getTiming()).isEqualTo(DEFAULT_TIMING);
        assertThat(testTherapeuticRegime.getIndication()).isEqualTo(DEFAULT_INDICATION);
        assertThat(testTherapeuticRegime.getCriteria()).isEqualTo(DEFAULT_CRITERIA);
        assertThat(testTherapeuticRegime.getNotice()).isEqualTo(DEFAULT_NOTICE);
    }

    @Test
    @Transactional
    public void createTherapeuticRegimeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = therapeuticRegimeRepository.findAll().size();

        // Create the TherapeuticRegime with an existing ID
        therapeuticRegime.setId(1L);
        TherapeuticRegimeDTO therapeuticRegimeDTO = therapeuticRegimeMapper.toDto(therapeuticRegime);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTherapeuticRegimeMockMvc.perform(post("/api/therapeutic-regimes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(therapeuticRegimeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TherapeuticRegime in the database
        List<TherapeuticRegime> therapeuticRegimeList = therapeuticRegimeRepository.findAll();
        assertThat(therapeuticRegimeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = therapeuticRegimeRepository.findAll().size();
        // set the field null
        therapeuticRegime.setName(null);

        // Create the TherapeuticRegime, which fails.
        TherapeuticRegimeDTO therapeuticRegimeDTO = therapeuticRegimeMapper.toDto(therapeuticRegime);


        restTherapeuticRegimeMockMvc.perform(post("/api/therapeutic-regimes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(therapeuticRegimeDTO)))
            .andExpect(status().isBadRequest());

        List<TherapeuticRegime> therapeuticRegimeList = therapeuticRegimeRepository.findAll();
        assertThat(therapeuticRegimeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPurposeIsRequired() throws Exception {
        int databaseSizeBeforeTest = therapeuticRegimeRepository.findAll().size();
        // set the field null
        therapeuticRegime.setPurpose(null);

        // Create the TherapeuticRegime, which fails.
        TherapeuticRegimeDTO therapeuticRegimeDTO = therapeuticRegimeMapper.toDto(therapeuticRegime);


        restTherapeuticRegimeMockMvc.perform(post("/api/therapeutic-regimes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(therapeuticRegimeDTO)))
            .andExpect(status().isBadRequest());

        List<TherapeuticRegime> therapeuticRegimeList = therapeuticRegimeRepository.findAll();
        assertThat(therapeuticRegimeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkConditionIsRequired() throws Exception {
        int databaseSizeBeforeTest = therapeuticRegimeRepository.findAll().size();
        // set the field null
        therapeuticRegime.setCondition(null);

        // Create the TherapeuticRegime, which fails.
        TherapeuticRegimeDTO therapeuticRegimeDTO = therapeuticRegimeMapper.toDto(therapeuticRegime);


        restTherapeuticRegimeMockMvc.perform(post("/api/therapeutic-regimes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(therapeuticRegimeDTO)))
            .andExpect(status().isBadRequest());

        List<TherapeuticRegime> therapeuticRegimeList = therapeuticRegimeRepository.findAll();
        assertThat(therapeuticRegimeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIndicationIsRequired() throws Exception {
        int databaseSizeBeforeTest = therapeuticRegimeRepository.findAll().size();
        // set the field null
        therapeuticRegime.setIndication(null);

        // Create the TherapeuticRegime, which fails.
        TherapeuticRegimeDTO therapeuticRegimeDTO = therapeuticRegimeMapper.toDto(therapeuticRegime);


        restTherapeuticRegimeMockMvc.perform(post("/api/therapeutic-regimes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(therapeuticRegimeDTO)))
            .andExpect(status().isBadRequest());

        List<TherapeuticRegime> therapeuticRegimeList = therapeuticRegimeRepository.findAll();
        assertThat(therapeuticRegimeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCriteriaIsRequired() throws Exception {
        int databaseSizeBeforeTest = therapeuticRegimeRepository.findAll().size();
        // set the field null
        therapeuticRegime.setCriteria(null);

        // Create the TherapeuticRegime, which fails.
        TherapeuticRegimeDTO therapeuticRegimeDTO = therapeuticRegimeMapper.toDto(therapeuticRegime);


        restTherapeuticRegimeMockMvc.perform(post("/api/therapeutic-regimes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(therapeuticRegimeDTO)))
            .andExpect(status().isBadRequest());

        List<TherapeuticRegime> therapeuticRegimeList = therapeuticRegimeRepository.findAll();
        assertThat(therapeuticRegimeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTherapeuticRegimes() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList
        restTherapeuticRegimeMockMvc.perform(get("/api/therapeutic-regimes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(therapeuticRegime.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].acronym").value(hasItem(DEFAULT_ACRONYM)))
            .andExpect(jsonPath("$.[*].purpose").value(hasItem(DEFAULT_PURPOSE)))
            .andExpect(jsonPath("$.[*].condition").value(hasItem(DEFAULT_CONDITION)))
            .andExpect(jsonPath("$.[*].timing").value(hasItem(DEFAULT_TIMING)))
            .andExpect(jsonPath("$.[*].indication").value(hasItem(DEFAULT_INDICATION)))
            .andExpect(jsonPath("$.[*].criteria").value(hasItem(DEFAULT_CRITERIA)))
            .andExpect(jsonPath("$.[*].notice").value(hasItem(DEFAULT_NOTICE)));
    }
    
    @Test
    @Transactional
    public void getTherapeuticRegime() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get the therapeuticRegime
        restTherapeuticRegimeMockMvc.perform(get("/api/therapeutic-regimes/{id}", therapeuticRegime.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(therapeuticRegime.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.acronym").value(DEFAULT_ACRONYM))
            .andExpect(jsonPath("$.purpose").value(DEFAULT_PURPOSE))
            .andExpect(jsonPath("$.condition").value(DEFAULT_CONDITION))
            .andExpect(jsonPath("$.timing").value(DEFAULT_TIMING))
            .andExpect(jsonPath("$.indication").value(DEFAULT_INDICATION))
            .andExpect(jsonPath("$.criteria").value(DEFAULT_CRITERIA))
            .andExpect(jsonPath("$.notice").value(DEFAULT_NOTICE));
    }


    @Test
    @Transactional
    public void getTherapeuticRegimesByIdFiltering() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        Long id = therapeuticRegime.getId();

        defaultTherapeuticRegimeShouldBeFound("id.equals=" + id);
        defaultTherapeuticRegimeShouldNotBeFound("id.notEquals=" + id);

        defaultTherapeuticRegimeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTherapeuticRegimeShouldNotBeFound("id.greaterThan=" + id);

        defaultTherapeuticRegimeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTherapeuticRegimeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTherapeuticRegimesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where name equals to DEFAULT_NAME
        defaultTherapeuticRegimeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the therapeuticRegimeList where name equals to UPDATED_NAME
        defaultTherapeuticRegimeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTherapeuticRegimesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where name not equals to DEFAULT_NAME
        defaultTherapeuticRegimeShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the therapeuticRegimeList where name not equals to UPDATED_NAME
        defaultTherapeuticRegimeShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTherapeuticRegimesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultTherapeuticRegimeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the therapeuticRegimeList where name equals to UPDATED_NAME
        defaultTherapeuticRegimeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTherapeuticRegimesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where name is not null
        defaultTherapeuticRegimeShouldBeFound("name.specified=true");

        // Get all the therapeuticRegimeList where name is null
        defaultTherapeuticRegimeShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllTherapeuticRegimesByNameContainsSomething() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where name contains DEFAULT_NAME
        defaultTherapeuticRegimeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the therapeuticRegimeList where name contains UPDATED_NAME
        defaultTherapeuticRegimeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTherapeuticRegimesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where name does not contain DEFAULT_NAME
        defaultTherapeuticRegimeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the therapeuticRegimeList where name does not contain UPDATED_NAME
        defaultTherapeuticRegimeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllTherapeuticRegimesByAcronymIsEqualToSomething() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where acronym equals to DEFAULT_ACRONYM
        defaultTherapeuticRegimeShouldBeFound("acronym.equals=" + DEFAULT_ACRONYM);

        // Get all the therapeuticRegimeList where acronym equals to UPDATED_ACRONYM
        defaultTherapeuticRegimeShouldNotBeFound("acronym.equals=" + UPDATED_ACRONYM);
    }

    @Test
    @Transactional
    public void getAllTherapeuticRegimesByAcronymIsNotEqualToSomething() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where acronym not equals to DEFAULT_ACRONYM
        defaultTherapeuticRegimeShouldNotBeFound("acronym.notEquals=" + DEFAULT_ACRONYM);

        // Get all the therapeuticRegimeList where acronym not equals to UPDATED_ACRONYM
        defaultTherapeuticRegimeShouldBeFound("acronym.notEquals=" + UPDATED_ACRONYM);
    }

    @Test
    @Transactional
    public void getAllTherapeuticRegimesByAcronymIsInShouldWork() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where acronym in DEFAULT_ACRONYM or UPDATED_ACRONYM
        defaultTherapeuticRegimeShouldBeFound("acronym.in=" + DEFAULT_ACRONYM + "," + UPDATED_ACRONYM);

        // Get all the therapeuticRegimeList where acronym equals to UPDATED_ACRONYM
        defaultTherapeuticRegimeShouldNotBeFound("acronym.in=" + UPDATED_ACRONYM);
    }

    @Test
    @Transactional
    public void getAllTherapeuticRegimesByAcronymIsNullOrNotNull() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where acronym is not null
        defaultTherapeuticRegimeShouldBeFound("acronym.specified=true");

        // Get all the therapeuticRegimeList where acronym is null
        defaultTherapeuticRegimeShouldNotBeFound("acronym.specified=false");
    }
                @Test
    @Transactional
    public void getAllTherapeuticRegimesByAcronymContainsSomething() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where acronym contains DEFAULT_ACRONYM
        defaultTherapeuticRegimeShouldBeFound("acronym.contains=" + DEFAULT_ACRONYM);

        // Get all the therapeuticRegimeList where acronym contains UPDATED_ACRONYM
        defaultTherapeuticRegimeShouldNotBeFound("acronym.contains=" + UPDATED_ACRONYM);
    }

    @Test
    @Transactional
    public void getAllTherapeuticRegimesByAcronymNotContainsSomething() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where acronym does not contain DEFAULT_ACRONYM
        defaultTherapeuticRegimeShouldNotBeFound("acronym.doesNotContain=" + DEFAULT_ACRONYM);

        // Get all the therapeuticRegimeList where acronym does not contain UPDATED_ACRONYM
        defaultTherapeuticRegimeShouldBeFound("acronym.doesNotContain=" + UPDATED_ACRONYM);
    }


    @Test
    @Transactional
    public void getAllTherapeuticRegimesByPurposeIsEqualToSomething() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where purpose equals to DEFAULT_PURPOSE
        defaultTherapeuticRegimeShouldBeFound("purpose.equals=" + DEFAULT_PURPOSE);

        // Get all the therapeuticRegimeList where purpose equals to UPDATED_PURPOSE
        defaultTherapeuticRegimeShouldNotBeFound("purpose.equals=" + UPDATED_PURPOSE);
    }

    @Test
    @Transactional
    public void getAllTherapeuticRegimesByPurposeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where purpose not equals to DEFAULT_PURPOSE
        defaultTherapeuticRegimeShouldNotBeFound("purpose.notEquals=" + DEFAULT_PURPOSE);

        // Get all the therapeuticRegimeList where purpose not equals to UPDATED_PURPOSE
        defaultTherapeuticRegimeShouldBeFound("purpose.notEquals=" + UPDATED_PURPOSE);
    }

    @Test
    @Transactional
    public void getAllTherapeuticRegimesByPurposeIsInShouldWork() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where purpose in DEFAULT_PURPOSE or UPDATED_PURPOSE
        defaultTherapeuticRegimeShouldBeFound("purpose.in=" + DEFAULT_PURPOSE + "," + UPDATED_PURPOSE);

        // Get all the therapeuticRegimeList where purpose equals to UPDATED_PURPOSE
        defaultTherapeuticRegimeShouldNotBeFound("purpose.in=" + UPDATED_PURPOSE);
    }

    @Test
    @Transactional
    public void getAllTherapeuticRegimesByPurposeIsNullOrNotNull() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where purpose is not null
        defaultTherapeuticRegimeShouldBeFound("purpose.specified=true");

        // Get all the therapeuticRegimeList where purpose is null
        defaultTherapeuticRegimeShouldNotBeFound("purpose.specified=false");
    }
                @Test
    @Transactional
    public void getAllTherapeuticRegimesByPurposeContainsSomething() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where purpose contains DEFAULT_PURPOSE
        defaultTherapeuticRegimeShouldBeFound("purpose.contains=" + DEFAULT_PURPOSE);

        // Get all the therapeuticRegimeList where purpose contains UPDATED_PURPOSE
        defaultTherapeuticRegimeShouldNotBeFound("purpose.contains=" + UPDATED_PURPOSE);
    }

    @Test
    @Transactional
    public void getAllTherapeuticRegimesByPurposeNotContainsSomething() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where purpose does not contain DEFAULT_PURPOSE
        defaultTherapeuticRegimeShouldNotBeFound("purpose.doesNotContain=" + DEFAULT_PURPOSE);

        // Get all the therapeuticRegimeList where purpose does not contain UPDATED_PURPOSE
        defaultTherapeuticRegimeShouldBeFound("purpose.doesNotContain=" + UPDATED_PURPOSE);
    }


    @Test
    @Transactional
    public void getAllTherapeuticRegimesByConditionIsEqualToSomething() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where condition equals to DEFAULT_CONDITION
        defaultTherapeuticRegimeShouldBeFound("condition.equals=" + DEFAULT_CONDITION);

        // Get all the therapeuticRegimeList where condition equals to UPDATED_CONDITION
        defaultTherapeuticRegimeShouldNotBeFound("condition.equals=" + UPDATED_CONDITION);
    }

    @Test
    @Transactional
    public void getAllTherapeuticRegimesByConditionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where condition not equals to DEFAULT_CONDITION
        defaultTherapeuticRegimeShouldNotBeFound("condition.notEquals=" + DEFAULT_CONDITION);

        // Get all the therapeuticRegimeList where condition not equals to UPDATED_CONDITION
        defaultTherapeuticRegimeShouldBeFound("condition.notEquals=" + UPDATED_CONDITION);
    }

    @Test
    @Transactional
    public void getAllTherapeuticRegimesByConditionIsInShouldWork() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where condition in DEFAULT_CONDITION or UPDATED_CONDITION
        defaultTherapeuticRegimeShouldBeFound("condition.in=" + DEFAULT_CONDITION + "," + UPDATED_CONDITION);

        // Get all the therapeuticRegimeList where condition equals to UPDATED_CONDITION
        defaultTherapeuticRegimeShouldNotBeFound("condition.in=" + UPDATED_CONDITION);
    }

    @Test
    @Transactional
    public void getAllTherapeuticRegimesByConditionIsNullOrNotNull() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where condition is not null
        defaultTherapeuticRegimeShouldBeFound("condition.specified=true");

        // Get all the therapeuticRegimeList where condition is null
        defaultTherapeuticRegimeShouldNotBeFound("condition.specified=false");
    }
                @Test
    @Transactional
    public void getAllTherapeuticRegimesByConditionContainsSomething() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where condition contains DEFAULT_CONDITION
        defaultTherapeuticRegimeShouldBeFound("condition.contains=" + DEFAULT_CONDITION);

        // Get all the therapeuticRegimeList where condition contains UPDATED_CONDITION
        defaultTherapeuticRegimeShouldNotBeFound("condition.contains=" + UPDATED_CONDITION);
    }

    @Test
    @Transactional
    public void getAllTherapeuticRegimesByConditionNotContainsSomething() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where condition does not contain DEFAULT_CONDITION
        defaultTherapeuticRegimeShouldNotBeFound("condition.doesNotContain=" + DEFAULT_CONDITION);

        // Get all the therapeuticRegimeList where condition does not contain UPDATED_CONDITION
        defaultTherapeuticRegimeShouldBeFound("condition.doesNotContain=" + UPDATED_CONDITION);
    }


    @Test
    @Transactional
    public void getAllTherapeuticRegimesByTimingIsEqualToSomething() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where timing equals to DEFAULT_TIMING
        defaultTherapeuticRegimeShouldBeFound("timing.equals=" + DEFAULT_TIMING);

        // Get all the therapeuticRegimeList where timing equals to UPDATED_TIMING
        defaultTherapeuticRegimeShouldNotBeFound("timing.equals=" + UPDATED_TIMING);
    }

    @Test
    @Transactional
    public void getAllTherapeuticRegimesByTimingIsNotEqualToSomething() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where timing not equals to DEFAULT_TIMING
        defaultTherapeuticRegimeShouldNotBeFound("timing.notEquals=" + DEFAULT_TIMING);

        // Get all the therapeuticRegimeList where timing not equals to UPDATED_TIMING
        defaultTherapeuticRegimeShouldBeFound("timing.notEquals=" + UPDATED_TIMING);
    }

    @Test
    @Transactional
    public void getAllTherapeuticRegimesByTimingIsInShouldWork() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where timing in DEFAULT_TIMING or UPDATED_TIMING
        defaultTherapeuticRegimeShouldBeFound("timing.in=" + DEFAULT_TIMING + "," + UPDATED_TIMING);

        // Get all the therapeuticRegimeList where timing equals to UPDATED_TIMING
        defaultTherapeuticRegimeShouldNotBeFound("timing.in=" + UPDATED_TIMING);
    }

    @Test
    @Transactional
    public void getAllTherapeuticRegimesByTimingIsNullOrNotNull() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where timing is not null
        defaultTherapeuticRegimeShouldBeFound("timing.specified=true");

        // Get all the therapeuticRegimeList where timing is null
        defaultTherapeuticRegimeShouldNotBeFound("timing.specified=false");
    }
                @Test
    @Transactional
    public void getAllTherapeuticRegimesByTimingContainsSomething() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where timing contains DEFAULT_TIMING
        defaultTherapeuticRegimeShouldBeFound("timing.contains=" + DEFAULT_TIMING);

        // Get all the therapeuticRegimeList where timing contains UPDATED_TIMING
        defaultTherapeuticRegimeShouldNotBeFound("timing.contains=" + UPDATED_TIMING);
    }

    @Test
    @Transactional
    public void getAllTherapeuticRegimesByTimingNotContainsSomething() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where timing does not contain DEFAULT_TIMING
        defaultTherapeuticRegimeShouldNotBeFound("timing.doesNotContain=" + DEFAULT_TIMING);

        // Get all the therapeuticRegimeList where timing does not contain UPDATED_TIMING
        defaultTherapeuticRegimeShouldBeFound("timing.doesNotContain=" + UPDATED_TIMING);
    }


    @Test
    @Transactional
    public void getAllTherapeuticRegimesByIndicationIsEqualToSomething() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where indication equals to DEFAULT_INDICATION
        defaultTherapeuticRegimeShouldBeFound("indication.equals=" + DEFAULT_INDICATION);

        // Get all the therapeuticRegimeList where indication equals to UPDATED_INDICATION
        defaultTherapeuticRegimeShouldNotBeFound("indication.equals=" + UPDATED_INDICATION);
    }

    @Test
    @Transactional
    public void getAllTherapeuticRegimesByIndicationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where indication not equals to DEFAULT_INDICATION
        defaultTherapeuticRegimeShouldNotBeFound("indication.notEquals=" + DEFAULT_INDICATION);

        // Get all the therapeuticRegimeList where indication not equals to UPDATED_INDICATION
        defaultTherapeuticRegimeShouldBeFound("indication.notEquals=" + UPDATED_INDICATION);
    }

    @Test
    @Transactional
    public void getAllTherapeuticRegimesByIndicationIsInShouldWork() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where indication in DEFAULT_INDICATION or UPDATED_INDICATION
        defaultTherapeuticRegimeShouldBeFound("indication.in=" + DEFAULT_INDICATION + "," + UPDATED_INDICATION);

        // Get all the therapeuticRegimeList where indication equals to UPDATED_INDICATION
        defaultTherapeuticRegimeShouldNotBeFound("indication.in=" + UPDATED_INDICATION);
    }

    @Test
    @Transactional
    public void getAllTherapeuticRegimesByIndicationIsNullOrNotNull() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where indication is not null
        defaultTherapeuticRegimeShouldBeFound("indication.specified=true");

        // Get all the therapeuticRegimeList where indication is null
        defaultTherapeuticRegimeShouldNotBeFound("indication.specified=false");
    }
                @Test
    @Transactional
    public void getAllTherapeuticRegimesByIndicationContainsSomething() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where indication contains DEFAULT_INDICATION
        defaultTherapeuticRegimeShouldBeFound("indication.contains=" + DEFAULT_INDICATION);

        // Get all the therapeuticRegimeList where indication contains UPDATED_INDICATION
        defaultTherapeuticRegimeShouldNotBeFound("indication.contains=" + UPDATED_INDICATION);
    }

    @Test
    @Transactional
    public void getAllTherapeuticRegimesByIndicationNotContainsSomething() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where indication does not contain DEFAULT_INDICATION
        defaultTherapeuticRegimeShouldNotBeFound("indication.doesNotContain=" + DEFAULT_INDICATION);

        // Get all the therapeuticRegimeList where indication does not contain UPDATED_INDICATION
        defaultTherapeuticRegimeShouldBeFound("indication.doesNotContain=" + UPDATED_INDICATION);
    }


    @Test
    @Transactional
    public void getAllTherapeuticRegimesByCriteriaIsEqualToSomething() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where criteria equals to DEFAULT_CRITERIA
        defaultTherapeuticRegimeShouldBeFound("criteria.equals=" + DEFAULT_CRITERIA);

        // Get all the therapeuticRegimeList where criteria equals to UPDATED_CRITERIA
        defaultTherapeuticRegimeShouldNotBeFound("criteria.equals=" + UPDATED_CRITERIA);
    }

    @Test
    @Transactional
    public void getAllTherapeuticRegimesByCriteriaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where criteria not equals to DEFAULT_CRITERIA
        defaultTherapeuticRegimeShouldNotBeFound("criteria.notEquals=" + DEFAULT_CRITERIA);

        // Get all the therapeuticRegimeList where criteria not equals to UPDATED_CRITERIA
        defaultTherapeuticRegimeShouldBeFound("criteria.notEquals=" + UPDATED_CRITERIA);
    }

    @Test
    @Transactional
    public void getAllTherapeuticRegimesByCriteriaIsInShouldWork() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where criteria in DEFAULT_CRITERIA or UPDATED_CRITERIA
        defaultTherapeuticRegimeShouldBeFound("criteria.in=" + DEFAULT_CRITERIA + "," + UPDATED_CRITERIA);

        // Get all the therapeuticRegimeList where criteria equals to UPDATED_CRITERIA
        defaultTherapeuticRegimeShouldNotBeFound("criteria.in=" + UPDATED_CRITERIA);
    }

    @Test
    @Transactional
    public void getAllTherapeuticRegimesByCriteriaIsNullOrNotNull() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where criteria is not null
        defaultTherapeuticRegimeShouldBeFound("criteria.specified=true");

        // Get all the therapeuticRegimeList where criteria is null
        defaultTherapeuticRegimeShouldNotBeFound("criteria.specified=false");
    }
                @Test
    @Transactional
    public void getAllTherapeuticRegimesByCriteriaContainsSomething() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where criteria contains DEFAULT_CRITERIA
        defaultTherapeuticRegimeShouldBeFound("criteria.contains=" + DEFAULT_CRITERIA);

        // Get all the therapeuticRegimeList where criteria contains UPDATED_CRITERIA
        defaultTherapeuticRegimeShouldNotBeFound("criteria.contains=" + UPDATED_CRITERIA);
    }

    @Test
    @Transactional
    public void getAllTherapeuticRegimesByCriteriaNotContainsSomething() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where criteria does not contain DEFAULT_CRITERIA
        defaultTherapeuticRegimeShouldNotBeFound("criteria.doesNotContain=" + DEFAULT_CRITERIA);

        // Get all the therapeuticRegimeList where criteria does not contain UPDATED_CRITERIA
        defaultTherapeuticRegimeShouldBeFound("criteria.doesNotContain=" + UPDATED_CRITERIA);
    }


    @Test
    @Transactional
    public void getAllTherapeuticRegimesByNoticeIsEqualToSomething() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where notice equals to DEFAULT_NOTICE
        defaultTherapeuticRegimeShouldBeFound("notice.equals=" + DEFAULT_NOTICE);

        // Get all the therapeuticRegimeList where notice equals to UPDATED_NOTICE
        defaultTherapeuticRegimeShouldNotBeFound("notice.equals=" + UPDATED_NOTICE);
    }

    @Test
    @Transactional
    public void getAllTherapeuticRegimesByNoticeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where notice not equals to DEFAULT_NOTICE
        defaultTherapeuticRegimeShouldNotBeFound("notice.notEquals=" + DEFAULT_NOTICE);

        // Get all the therapeuticRegimeList where notice not equals to UPDATED_NOTICE
        defaultTherapeuticRegimeShouldBeFound("notice.notEquals=" + UPDATED_NOTICE);
    }

    @Test
    @Transactional
    public void getAllTherapeuticRegimesByNoticeIsInShouldWork() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where notice in DEFAULT_NOTICE or UPDATED_NOTICE
        defaultTherapeuticRegimeShouldBeFound("notice.in=" + DEFAULT_NOTICE + "," + UPDATED_NOTICE);

        // Get all the therapeuticRegimeList where notice equals to UPDATED_NOTICE
        defaultTherapeuticRegimeShouldNotBeFound("notice.in=" + UPDATED_NOTICE);
    }

    @Test
    @Transactional
    public void getAllTherapeuticRegimesByNoticeIsNullOrNotNull() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where notice is not null
        defaultTherapeuticRegimeShouldBeFound("notice.specified=true");

        // Get all the therapeuticRegimeList where notice is null
        defaultTherapeuticRegimeShouldNotBeFound("notice.specified=false");
    }
                @Test
    @Transactional
    public void getAllTherapeuticRegimesByNoticeContainsSomething() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where notice contains DEFAULT_NOTICE
        defaultTherapeuticRegimeShouldBeFound("notice.contains=" + DEFAULT_NOTICE);

        // Get all the therapeuticRegimeList where notice contains UPDATED_NOTICE
        defaultTherapeuticRegimeShouldNotBeFound("notice.contains=" + UPDATED_NOTICE);
    }

    @Test
    @Transactional
    public void getAllTherapeuticRegimesByNoticeNotContainsSomething() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where notice does not contain DEFAULT_NOTICE
        defaultTherapeuticRegimeShouldNotBeFound("notice.doesNotContain=" + DEFAULT_NOTICE);

        // Get all the therapeuticRegimeList where notice does not contain UPDATED_NOTICE
        defaultTherapeuticRegimeShouldBeFound("notice.doesNotContain=" + UPDATED_NOTICE);
    }


    @Test
    @Transactional
    public void getAllTherapeuticRegimesByTreatmentIsEqualToSomething() throws Exception {
        // Get already existing entity
        Treatment treatment = therapeuticRegime.getTreatment();
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);
        Long treatmentId = treatment.getId();

        // Get all the therapeuticRegimeList where treatment equals to treatmentId
        defaultTherapeuticRegimeShouldBeFound("treatmentId.equals=" + treatmentId);

        // Get all the therapeuticRegimeList where treatment equals to treatmentId + 1
        defaultTherapeuticRegimeShouldNotBeFound("treatmentId.equals=" + (treatmentId + 1));
    }


    @Test
    @Transactional
    public void getAllTherapeuticRegimesByDrugIsEqualToSomething() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);
        Drug drug = DrugResourceIT.createEntity(em);
        em.persist(drug);
        em.flush();
        therapeuticRegime.addDrug(drug);
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);
        Long drugId = drug.getId();

        // Get all the therapeuticRegimeList where drug equals to drugId
        defaultTherapeuticRegimeShouldBeFound("drugId.equals=" + drugId);

        // Get all the therapeuticRegimeList where drug equals to drugId + 1
        defaultTherapeuticRegimeShouldNotBeFound("drugId.equals=" + (drugId + 1));
    }


    @Test
    @Transactional
    public void getAllTherapeuticRegimesByDiagnosticIsEqualToSomething() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);
        Diagnostic diagnostic = DiagnosticResourceIT.createEntity(em);
        em.persist(diagnostic);
        em.flush();
        therapeuticRegime.setDiagnostic(diagnostic);
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);
        Long diagnosticId = diagnostic.getId();

        // Get all the therapeuticRegimeList where diagnostic equals to diagnosticId
        defaultTherapeuticRegimeShouldBeFound("diagnosticId.equals=" + diagnosticId);

        // Get all the therapeuticRegimeList where diagnostic equals to diagnosticId + 1
        defaultTherapeuticRegimeShouldNotBeFound("diagnosticId.equals=" + (diagnosticId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTherapeuticRegimeShouldBeFound(String filter) throws Exception {
        restTherapeuticRegimeMockMvc.perform(get("/api/therapeutic-regimes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(therapeuticRegime.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].acronym").value(hasItem(DEFAULT_ACRONYM)))
            .andExpect(jsonPath("$.[*].purpose").value(hasItem(DEFAULT_PURPOSE)))
            .andExpect(jsonPath("$.[*].condition").value(hasItem(DEFAULT_CONDITION)))
            .andExpect(jsonPath("$.[*].timing").value(hasItem(DEFAULT_TIMING)))
            .andExpect(jsonPath("$.[*].indication").value(hasItem(DEFAULT_INDICATION)))
            .andExpect(jsonPath("$.[*].criteria").value(hasItem(DEFAULT_CRITERIA)))
            .andExpect(jsonPath("$.[*].notice").value(hasItem(DEFAULT_NOTICE)));

        // Check, that the count call also returns 1
        restTherapeuticRegimeMockMvc.perform(get("/api/therapeutic-regimes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTherapeuticRegimeShouldNotBeFound(String filter) throws Exception {
        restTherapeuticRegimeMockMvc.perform(get("/api/therapeutic-regimes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTherapeuticRegimeMockMvc.perform(get("/api/therapeutic-regimes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingTherapeuticRegime() throws Exception {
        // Get the therapeuticRegime
        restTherapeuticRegimeMockMvc.perform(get("/api/therapeutic-regimes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTherapeuticRegime() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        int databaseSizeBeforeUpdate = therapeuticRegimeRepository.findAll().size();

        // Update the therapeuticRegime
        TherapeuticRegime updatedTherapeuticRegime = therapeuticRegimeRepository.findById(therapeuticRegime.getId()).get();
        // Disconnect from session so that the updates on updatedTherapeuticRegime are not directly saved in db
        em.detach(updatedTherapeuticRegime);
        updatedTherapeuticRegime
            .name(UPDATED_NAME)
            .acronym(UPDATED_ACRONYM)
            .purpose(UPDATED_PURPOSE)
            .condition(UPDATED_CONDITION)
            .timing(UPDATED_TIMING)
            .indication(UPDATED_INDICATION)
            .criteria(UPDATED_CRITERIA)
            .notice(UPDATED_NOTICE);
        TherapeuticRegimeDTO therapeuticRegimeDTO = therapeuticRegimeMapper.toDto(updatedTherapeuticRegime);

        restTherapeuticRegimeMockMvc.perform(put("/api/therapeutic-regimes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(therapeuticRegimeDTO)))
            .andExpect(status().isOk());

        // Validate the TherapeuticRegime in the database
        List<TherapeuticRegime> therapeuticRegimeList = therapeuticRegimeRepository.findAll();
        assertThat(therapeuticRegimeList).hasSize(databaseSizeBeforeUpdate);
        TherapeuticRegime testTherapeuticRegime = therapeuticRegimeList.get(therapeuticRegimeList.size() - 1);
        assertThat(testTherapeuticRegime.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTherapeuticRegime.getAcronym()).isEqualTo(UPDATED_ACRONYM);
        assertThat(testTherapeuticRegime.getPurpose()).isEqualTo(UPDATED_PURPOSE);
        assertThat(testTherapeuticRegime.getCondition()).isEqualTo(UPDATED_CONDITION);
        assertThat(testTherapeuticRegime.getTiming()).isEqualTo(UPDATED_TIMING);
        assertThat(testTherapeuticRegime.getIndication()).isEqualTo(UPDATED_INDICATION);
        assertThat(testTherapeuticRegime.getCriteria()).isEqualTo(UPDATED_CRITERIA);
        assertThat(testTherapeuticRegime.getNotice()).isEqualTo(UPDATED_NOTICE);
    }

    @Test
    @Transactional
    public void updateNonExistingTherapeuticRegime() throws Exception {
        int databaseSizeBeforeUpdate = therapeuticRegimeRepository.findAll().size();

        // Create the TherapeuticRegime
        TherapeuticRegimeDTO therapeuticRegimeDTO = therapeuticRegimeMapper.toDto(therapeuticRegime);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTherapeuticRegimeMockMvc.perform(put("/api/therapeutic-regimes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(therapeuticRegimeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TherapeuticRegime in the database
        List<TherapeuticRegime> therapeuticRegimeList = therapeuticRegimeRepository.findAll();
        assertThat(therapeuticRegimeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTherapeuticRegime() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        int databaseSizeBeforeDelete = therapeuticRegimeRepository.findAll().size();

        // Delete the therapeuticRegime
        restTherapeuticRegimeMockMvc.perform(delete("/api/therapeutic-regimes/{id}", therapeuticRegime.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TherapeuticRegime> therapeuticRegimeList = therapeuticRegimeRepository.findAll();
        assertThat(therapeuticRegimeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
