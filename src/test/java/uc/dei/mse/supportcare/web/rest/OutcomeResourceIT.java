package uc.dei.mse.supportcare.web.rest;

import uc.dei.mse.supportcare.SupportcareApp;
import uc.dei.mse.supportcare.domain.Outcome;
import uc.dei.mse.supportcare.repository.OutcomeRepository;
import uc.dei.mse.supportcare.service.OutcomeService;
import uc.dei.mse.supportcare.service.dto.OutcomeDTO;
import uc.dei.mse.supportcare.service.mapper.OutcomeMapper;
import uc.dei.mse.supportcare.service.dto.OutcomeCriteria;
import uc.dei.mse.supportcare.service.OutcomeQueryService;

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
 * Integration tests for the {@link OutcomeResource} REST controller.
 */
@SpringBootTest(classes = SupportcareApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class OutcomeResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_SCORE = 0;
    private static final Integer UPDATED_SCORE = 1;
    private static final Integer SMALLER_SCORE = 0 - 1;

    private static final String DEFAULT_CREATE_USER = "AAAAAAAAAA";
    private static final String UPDATED_CREATE_USER = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATE_USER = "AAAAAAAAAA";
    private static final String UPDATED_UPDATE_USER = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private OutcomeRepository outcomeRepository;

    @Autowired
    private OutcomeMapper outcomeMapper;

    @Autowired
    private OutcomeService outcomeService;

    @Autowired
    private OutcomeQueryService outcomeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOutcomeMockMvc;

    private Outcome outcome;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Outcome createEntity(EntityManager em) {
        Outcome outcome = new Outcome()
            .description(DEFAULT_DESCRIPTION)
            .score(DEFAULT_SCORE)
            .createUser(DEFAULT_CREATE_USER)
            .createDate(DEFAULT_CREATE_DATE)
            .updateUser(DEFAULT_UPDATE_USER)
            .updateDate(DEFAULT_UPDATE_DATE);
        return outcome;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Outcome createUpdatedEntity(EntityManager em) {
        Outcome outcome = new Outcome()
            .description(UPDATED_DESCRIPTION)
            .score(UPDATED_SCORE)
            .createUser(UPDATED_CREATE_USER)
            .createDate(UPDATED_CREATE_DATE)
            .updateUser(UPDATED_UPDATE_USER)
            .updateDate(UPDATED_UPDATE_DATE);
        return outcome;
    }

    @BeforeEach
    public void initTest() {
        outcome = createEntity(em);
    }

    @Test
    @Transactional
    public void createOutcome() throws Exception {
        int databaseSizeBeforeCreate = outcomeRepository.findAll().size();
        // Create the Outcome
        OutcomeDTO outcomeDTO = outcomeMapper.toDto(outcome);
        restOutcomeMockMvc.perform(post("/api/outcomes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(outcomeDTO)))
            .andExpect(status().isCreated());

        // Validate the Outcome in the database
        List<Outcome> outcomeList = outcomeRepository.findAll();
        assertThat(outcomeList).hasSize(databaseSizeBeforeCreate + 1);
        Outcome testOutcome = outcomeList.get(outcomeList.size() - 1);
        assertThat(testOutcome.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testOutcome.getScore()).isEqualTo(DEFAULT_SCORE);
        assertThat(testOutcome.getCreateUser()).isEqualTo(DEFAULT_CREATE_USER);
        assertThat(testOutcome.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testOutcome.getUpdateUser()).isEqualTo(DEFAULT_UPDATE_USER);
        assertThat(testOutcome.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void createOutcomeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = outcomeRepository.findAll().size();

        // Create the Outcome with an existing ID
        outcome.setId(1L);
        OutcomeDTO outcomeDTO = outcomeMapper.toDto(outcome);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOutcomeMockMvc.perform(post("/api/outcomes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(outcomeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Outcome in the database
        List<Outcome> outcomeList = outcomeRepository.findAll();
        assertThat(outcomeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = outcomeRepository.findAll().size();
        // set the field null
        outcome.setDescription(null);

        // Create the Outcome, which fails.
        OutcomeDTO outcomeDTO = outcomeMapper.toDto(outcome);


        restOutcomeMockMvc.perform(post("/api/outcomes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(outcomeDTO)))
            .andExpect(status().isBadRequest());

        List<Outcome> outcomeList = outcomeRepository.findAll();
        assertThat(outcomeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateUserIsRequired() throws Exception {
        int databaseSizeBeforeTest = outcomeRepository.findAll().size();
        // set the field null
        outcome.setCreateUser(null);

        // Create the Outcome, which fails.
        OutcomeDTO outcomeDTO = outcomeMapper.toDto(outcome);


        restOutcomeMockMvc.perform(post("/api/outcomes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(outcomeDTO)))
            .andExpect(status().isBadRequest());

        List<Outcome> outcomeList = outcomeRepository.findAll();
        assertThat(outcomeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = outcomeRepository.findAll().size();
        // set the field null
        outcome.setCreateDate(null);

        // Create the Outcome, which fails.
        OutcomeDTO outcomeDTO = outcomeMapper.toDto(outcome);


        restOutcomeMockMvc.perform(post("/api/outcomes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(outcomeDTO)))
            .andExpect(status().isBadRequest());

        List<Outcome> outcomeList = outcomeRepository.findAll();
        assertThat(outcomeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateUserIsRequired() throws Exception {
        int databaseSizeBeforeTest = outcomeRepository.findAll().size();
        // set the field null
        outcome.setUpdateUser(null);

        // Create the Outcome, which fails.
        OutcomeDTO outcomeDTO = outcomeMapper.toDto(outcome);


        restOutcomeMockMvc.perform(post("/api/outcomes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(outcomeDTO)))
            .andExpect(status().isBadRequest());

        List<Outcome> outcomeList = outcomeRepository.findAll();
        assertThat(outcomeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = outcomeRepository.findAll().size();
        // set the field null
        outcome.setUpdateDate(null);

        // Create the Outcome, which fails.
        OutcomeDTO outcomeDTO = outcomeMapper.toDto(outcome);


        restOutcomeMockMvc.perform(post("/api/outcomes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(outcomeDTO)))
            .andExpect(status().isBadRequest());

        List<Outcome> outcomeList = outcomeRepository.findAll();
        assertThat(outcomeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOutcomes() throws Exception {
        // Initialize the database
        outcomeRepository.saveAndFlush(outcome);

        // Get all the outcomeList
        restOutcomeMockMvc.perform(get("/api/outcomes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(outcome.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE)))
            .andExpect(jsonPath("$.[*].createUser").value(hasItem(DEFAULT_CREATE_USER)))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].updateUser").value(hasItem(DEFAULT_UPDATE_USER)))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getOutcome() throws Exception {
        // Initialize the database
        outcomeRepository.saveAndFlush(outcome);

        // Get the outcome
        restOutcomeMockMvc.perform(get("/api/outcomes/{id}", outcome.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(outcome.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE))
            .andExpect(jsonPath("$.createUser").value(DEFAULT_CREATE_USER))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.updateUser").value(DEFAULT_UPDATE_USER))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()));
    }


    @Test
    @Transactional
    public void getOutcomesByIdFiltering() throws Exception {
        // Initialize the database
        outcomeRepository.saveAndFlush(outcome);

        Long id = outcome.getId();

        defaultOutcomeShouldBeFound("id.equals=" + id);
        defaultOutcomeShouldNotBeFound("id.notEquals=" + id);

        defaultOutcomeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOutcomeShouldNotBeFound("id.greaterThan=" + id);

        defaultOutcomeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOutcomeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllOutcomesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        outcomeRepository.saveAndFlush(outcome);

        // Get all the outcomeList where description equals to DEFAULT_DESCRIPTION
        defaultOutcomeShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the outcomeList where description equals to UPDATED_DESCRIPTION
        defaultOutcomeShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllOutcomesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        outcomeRepository.saveAndFlush(outcome);

        // Get all the outcomeList where description not equals to DEFAULT_DESCRIPTION
        defaultOutcomeShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the outcomeList where description not equals to UPDATED_DESCRIPTION
        defaultOutcomeShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllOutcomesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        outcomeRepository.saveAndFlush(outcome);

        // Get all the outcomeList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultOutcomeShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the outcomeList where description equals to UPDATED_DESCRIPTION
        defaultOutcomeShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllOutcomesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        outcomeRepository.saveAndFlush(outcome);

        // Get all the outcomeList where description is not null
        defaultOutcomeShouldBeFound("description.specified=true");

        // Get all the outcomeList where description is null
        defaultOutcomeShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllOutcomesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        outcomeRepository.saveAndFlush(outcome);

        // Get all the outcomeList where description contains DEFAULT_DESCRIPTION
        defaultOutcomeShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the outcomeList where description contains UPDATED_DESCRIPTION
        defaultOutcomeShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllOutcomesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        outcomeRepository.saveAndFlush(outcome);

        // Get all the outcomeList where description does not contain DEFAULT_DESCRIPTION
        defaultOutcomeShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the outcomeList where description does not contain UPDATED_DESCRIPTION
        defaultOutcomeShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllOutcomesByScoreIsEqualToSomething() throws Exception {
        // Initialize the database
        outcomeRepository.saveAndFlush(outcome);

        // Get all the outcomeList where score equals to DEFAULT_SCORE
        defaultOutcomeShouldBeFound("score.equals=" + DEFAULT_SCORE);

        // Get all the outcomeList where score equals to UPDATED_SCORE
        defaultOutcomeShouldNotBeFound("score.equals=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void getAllOutcomesByScoreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        outcomeRepository.saveAndFlush(outcome);

        // Get all the outcomeList where score not equals to DEFAULT_SCORE
        defaultOutcomeShouldNotBeFound("score.notEquals=" + DEFAULT_SCORE);

        // Get all the outcomeList where score not equals to UPDATED_SCORE
        defaultOutcomeShouldBeFound("score.notEquals=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void getAllOutcomesByScoreIsInShouldWork() throws Exception {
        // Initialize the database
        outcomeRepository.saveAndFlush(outcome);

        // Get all the outcomeList where score in DEFAULT_SCORE or UPDATED_SCORE
        defaultOutcomeShouldBeFound("score.in=" + DEFAULT_SCORE + "," + UPDATED_SCORE);

        // Get all the outcomeList where score equals to UPDATED_SCORE
        defaultOutcomeShouldNotBeFound("score.in=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void getAllOutcomesByScoreIsNullOrNotNull() throws Exception {
        // Initialize the database
        outcomeRepository.saveAndFlush(outcome);

        // Get all the outcomeList where score is not null
        defaultOutcomeShouldBeFound("score.specified=true");

        // Get all the outcomeList where score is null
        defaultOutcomeShouldNotBeFound("score.specified=false");
    }

    @Test
    @Transactional
    public void getAllOutcomesByScoreIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        outcomeRepository.saveAndFlush(outcome);

        // Get all the outcomeList where score is greater than or equal to DEFAULT_SCORE
        defaultOutcomeShouldBeFound("score.greaterThanOrEqual=" + DEFAULT_SCORE);

        // Get all the outcomeList where score is greater than or equal to (DEFAULT_SCORE + 1)
        defaultOutcomeShouldNotBeFound("score.greaterThanOrEqual=" + (DEFAULT_SCORE + 1));
    }

    @Test
    @Transactional
    public void getAllOutcomesByScoreIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        outcomeRepository.saveAndFlush(outcome);

        // Get all the outcomeList where score is less than or equal to DEFAULT_SCORE
        defaultOutcomeShouldBeFound("score.lessThanOrEqual=" + DEFAULT_SCORE);

        // Get all the outcomeList where score is less than or equal to SMALLER_SCORE
        defaultOutcomeShouldNotBeFound("score.lessThanOrEqual=" + SMALLER_SCORE);
    }

    @Test
    @Transactional
    public void getAllOutcomesByScoreIsLessThanSomething() throws Exception {
        // Initialize the database
        outcomeRepository.saveAndFlush(outcome);

        // Get all the outcomeList where score is less than DEFAULT_SCORE
        defaultOutcomeShouldNotBeFound("score.lessThan=" + DEFAULT_SCORE);

        // Get all the outcomeList where score is less than (DEFAULT_SCORE + 1)
        defaultOutcomeShouldBeFound("score.lessThan=" + (DEFAULT_SCORE + 1));
    }

    @Test
    @Transactional
    public void getAllOutcomesByScoreIsGreaterThanSomething() throws Exception {
        // Initialize the database
        outcomeRepository.saveAndFlush(outcome);

        // Get all the outcomeList where score is greater than DEFAULT_SCORE
        defaultOutcomeShouldNotBeFound("score.greaterThan=" + DEFAULT_SCORE);

        // Get all the outcomeList where score is greater than SMALLER_SCORE
        defaultOutcomeShouldBeFound("score.greaterThan=" + SMALLER_SCORE);
    }


    @Test
    @Transactional
    public void getAllOutcomesByCreateUserIsEqualToSomething() throws Exception {
        // Initialize the database
        outcomeRepository.saveAndFlush(outcome);

        // Get all the outcomeList where createUser equals to DEFAULT_CREATE_USER
        defaultOutcomeShouldBeFound("createUser.equals=" + DEFAULT_CREATE_USER);

        // Get all the outcomeList where createUser equals to UPDATED_CREATE_USER
        defaultOutcomeShouldNotBeFound("createUser.equals=" + UPDATED_CREATE_USER);
    }

    @Test
    @Transactional
    public void getAllOutcomesByCreateUserIsNotEqualToSomething() throws Exception {
        // Initialize the database
        outcomeRepository.saveAndFlush(outcome);

        // Get all the outcomeList where createUser not equals to DEFAULT_CREATE_USER
        defaultOutcomeShouldNotBeFound("createUser.notEquals=" + DEFAULT_CREATE_USER);

        // Get all the outcomeList where createUser not equals to UPDATED_CREATE_USER
        defaultOutcomeShouldBeFound("createUser.notEquals=" + UPDATED_CREATE_USER);
    }

    @Test
    @Transactional
    public void getAllOutcomesByCreateUserIsInShouldWork() throws Exception {
        // Initialize the database
        outcomeRepository.saveAndFlush(outcome);

        // Get all the outcomeList where createUser in DEFAULT_CREATE_USER or UPDATED_CREATE_USER
        defaultOutcomeShouldBeFound("createUser.in=" + DEFAULT_CREATE_USER + "," + UPDATED_CREATE_USER);

        // Get all the outcomeList where createUser equals to UPDATED_CREATE_USER
        defaultOutcomeShouldNotBeFound("createUser.in=" + UPDATED_CREATE_USER);
    }

    @Test
    @Transactional
    public void getAllOutcomesByCreateUserIsNullOrNotNull() throws Exception {
        // Initialize the database
        outcomeRepository.saveAndFlush(outcome);

        // Get all the outcomeList where createUser is not null
        defaultOutcomeShouldBeFound("createUser.specified=true");

        // Get all the outcomeList where createUser is null
        defaultOutcomeShouldNotBeFound("createUser.specified=false");
    }
                @Test
    @Transactional
    public void getAllOutcomesByCreateUserContainsSomething() throws Exception {
        // Initialize the database
        outcomeRepository.saveAndFlush(outcome);

        // Get all the outcomeList where createUser contains DEFAULT_CREATE_USER
        defaultOutcomeShouldBeFound("createUser.contains=" + DEFAULT_CREATE_USER);

        // Get all the outcomeList where createUser contains UPDATED_CREATE_USER
        defaultOutcomeShouldNotBeFound("createUser.contains=" + UPDATED_CREATE_USER);
    }

    @Test
    @Transactional
    public void getAllOutcomesByCreateUserNotContainsSomething() throws Exception {
        // Initialize the database
        outcomeRepository.saveAndFlush(outcome);

        // Get all the outcomeList where createUser does not contain DEFAULT_CREATE_USER
        defaultOutcomeShouldNotBeFound("createUser.doesNotContain=" + DEFAULT_CREATE_USER);

        // Get all the outcomeList where createUser does not contain UPDATED_CREATE_USER
        defaultOutcomeShouldBeFound("createUser.doesNotContain=" + UPDATED_CREATE_USER);
    }


    @Test
    @Transactional
    public void getAllOutcomesByCreateDateIsEqualToSomething() throws Exception {
        // Initialize the database
        outcomeRepository.saveAndFlush(outcome);

        // Get all the outcomeList where createDate equals to DEFAULT_CREATE_DATE
        defaultOutcomeShouldBeFound("createDate.equals=" + DEFAULT_CREATE_DATE);

        // Get all the outcomeList where createDate equals to UPDATED_CREATE_DATE
        defaultOutcomeShouldNotBeFound("createDate.equals=" + UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void getAllOutcomesByCreateDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        outcomeRepository.saveAndFlush(outcome);

        // Get all the outcomeList where createDate not equals to DEFAULT_CREATE_DATE
        defaultOutcomeShouldNotBeFound("createDate.notEquals=" + DEFAULT_CREATE_DATE);

        // Get all the outcomeList where createDate not equals to UPDATED_CREATE_DATE
        defaultOutcomeShouldBeFound("createDate.notEquals=" + UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void getAllOutcomesByCreateDateIsInShouldWork() throws Exception {
        // Initialize the database
        outcomeRepository.saveAndFlush(outcome);

        // Get all the outcomeList where createDate in DEFAULT_CREATE_DATE or UPDATED_CREATE_DATE
        defaultOutcomeShouldBeFound("createDate.in=" + DEFAULT_CREATE_DATE + "," + UPDATED_CREATE_DATE);

        // Get all the outcomeList where createDate equals to UPDATED_CREATE_DATE
        defaultOutcomeShouldNotBeFound("createDate.in=" + UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void getAllOutcomesByCreateDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        outcomeRepository.saveAndFlush(outcome);

        // Get all the outcomeList where createDate is not null
        defaultOutcomeShouldBeFound("createDate.specified=true");

        // Get all the outcomeList where createDate is null
        defaultOutcomeShouldNotBeFound("createDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllOutcomesByUpdateUserIsEqualToSomething() throws Exception {
        // Initialize the database
        outcomeRepository.saveAndFlush(outcome);

        // Get all the outcomeList where updateUser equals to DEFAULT_UPDATE_USER
        defaultOutcomeShouldBeFound("updateUser.equals=" + DEFAULT_UPDATE_USER);

        // Get all the outcomeList where updateUser equals to UPDATED_UPDATE_USER
        defaultOutcomeShouldNotBeFound("updateUser.equals=" + UPDATED_UPDATE_USER);
    }

    @Test
    @Transactional
    public void getAllOutcomesByUpdateUserIsNotEqualToSomething() throws Exception {
        // Initialize the database
        outcomeRepository.saveAndFlush(outcome);

        // Get all the outcomeList where updateUser not equals to DEFAULT_UPDATE_USER
        defaultOutcomeShouldNotBeFound("updateUser.notEquals=" + DEFAULT_UPDATE_USER);

        // Get all the outcomeList where updateUser not equals to UPDATED_UPDATE_USER
        defaultOutcomeShouldBeFound("updateUser.notEquals=" + UPDATED_UPDATE_USER);
    }

    @Test
    @Transactional
    public void getAllOutcomesByUpdateUserIsInShouldWork() throws Exception {
        // Initialize the database
        outcomeRepository.saveAndFlush(outcome);

        // Get all the outcomeList where updateUser in DEFAULT_UPDATE_USER or UPDATED_UPDATE_USER
        defaultOutcomeShouldBeFound("updateUser.in=" + DEFAULT_UPDATE_USER + "," + UPDATED_UPDATE_USER);

        // Get all the outcomeList where updateUser equals to UPDATED_UPDATE_USER
        defaultOutcomeShouldNotBeFound("updateUser.in=" + UPDATED_UPDATE_USER);
    }

    @Test
    @Transactional
    public void getAllOutcomesByUpdateUserIsNullOrNotNull() throws Exception {
        // Initialize the database
        outcomeRepository.saveAndFlush(outcome);

        // Get all the outcomeList where updateUser is not null
        defaultOutcomeShouldBeFound("updateUser.specified=true");

        // Get all the outcomeList where updateUser is null
        defaultOutcomeShouldNotBeFound("updateUser.specified=false");
    }
                @Test
    @Transactional
    public void getAllOutcomesByUpdateUserContainsSomething() throws Exception {
        // Initialize the database
        outcomeRepository.saveAndFlush(outcome);

        // Get all the outcomeList where updateUser contains DEFAULT_UPDATE_USER
        defaultOutcomeShouldBeFound("updateUser.contains=" + DEFAULT_UPDATE_USER);

        // Get all the outcomeList where updateUser contains UPDATED_UPDATE_USER
        defaultOutcomeShouldNotBeFound("updateUser.contains=" + UPDATED_UPDATE_USER);
    }

    @Test
    @Transactional
    public void getAllOutcomesByUpdateUserNotContainsSomething() throws Exception {
        // Initialize the database
        outcomeRepository.saveAndFlush(outcome);

        // Get all the outcomeList where updateUser does not contain DEFAULT_UPDATE_USER
        defaultOutcomeShouldNotBeFound("updateUser.doesNotContain=" + DEFAULT_UPDATE_USER);

        // Get all the outcomeList where updateUser does not contain UPDATED_UPDATE_USER
        defaultOutcomeShouldBeFound("updateUser.doesNotContain=" + UPDATED_UPDATE_USER);
    }


    @Test
    @Transactional
    public void getAllOutcomesByUpdateDateIsEqualToSomething() throws Exception {
        // Initialize the database
        outcomeRepository.saveAndFlush(outcome);

        // Get all the outcomeList where updateDate equals to DEFAULT_UPDATE_DATE
        defaultOutcomeShouldBeFound("updateDate.equals=" + DEFAULT_UPDATE_DATE);

        // Get all the outcomeList where updateDate equals to UPDATED_UPDATE_DATE
        defaultOutcomeShouldNotBeFound("updateDate.equals=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void getAllOutcomesByUpdateDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        outcomeRepository.saveAndFlush(outcome);

        // Get all the outcomeList where updateDate not equals to DEFAULT_UPDATE_DATE
        defaultOutcomeShouldNotBeFound("updateDate.notEquals=" + DEFAULT_UPDATE_DATE);

        // Get all the outcomeList where updateDate not equals to UPDATED_UPDATE_DATE
        defaultOutcomeShouldBeFound("updateDate.notEquals=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void getAllOutcomesByUpdateDateIsInShouldWork() throws Exception {
        // Initialize the database
        outcomeRepository.saveAndFlush(outcome);

        // Get all the outcomeList where updateDate in DEFAULT_UPDATE_DATE or UPDATED_UPDATE_DATE
        defaultOutcomeShouldBeFound("updateDate.in=" + DEFAULT_UPDATE_DATE + "," + UPDATED_UPDATE_DATE);

        // Get all the outcomeList where updateDate equals to UPDATED_UPDATE_DATE
        defaultOutcomeShouldNotBeFound("updateDate.in=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void getAllOutcomesByUpdateDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        outcomeRepository.saveAndFlush(outcome);

        // Get all the outcomeList where updateDate is not null
        defaultOutcomeShouldBeFound("updateDate.specified=true");

        // Get all the outcomeList where updateDate is null
        defaultOutcomeShouldNotBeFound("updateDate.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOutcomeShouldBeFound(String filter) throws Exception {
        restOutcomeMockMvc.perform(get("/api/outcomes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(outcome.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE)))
            .andExpect(jsonPath("$.[*].createUser").value(hasItem(DEFAULT_CREATE_USER)))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].updateUser").value(hasItem(DEFAULT_UPDATE_USER)))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())));

        // Check, that the count call also returns 1
        restOutcomeMockMvc.perform(get("/api/outcomes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOutcomeShouldNotBeFound(String filter) throws Exception {
        restOutcomeMockMvc.perform(get("/api/outcomes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOutcomeMockMvc.perform(get("/api/outcomes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingOutcome() throws Exception {
        // Get the outcome
        restOutcomeMockMvc.perform(get("/api/outcomes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOutcome() throws Exception {
        // Initialize the database
        outcomeRepository.saveAndFlush(outcome);

        int databaseSizeBeforeUpdate = outcomeRepository.findAll().size();

        // Update the outcome
        Outcome updatedOutcome = outcomeRepository.findById(outcome.getId()).get();
        // Disconnect from session so that the updates on updatedOutcome are not directly saved in db
        em.detach(updatedOutcome);
        updatedOutcome
            .description(UPDATED_DESCRIPTION)
            .score(UPDATED_SCORE)
            .createUser(UPDATED_CREATE_USER)
            .createDate(UPDATED_CREATE_DATE)
            .updateUser(UPDATED_UPDATE_USER)
            .updateDate(UPDATED_UPDATE_DATE);
        OutcomeDTO outcomeDTO = outcomeMapper.toDto(updatedOutcome);

        restOutcomeMockMvc.perform(put("/api/outcomes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(outcomeDTO)))
            .andExpect(status().isOk());

        // Validate the Outcome in the database
        List<Outcome> outcomeList = outcomeRepository.findAll();
        assertThat(outcomeList).hasSize(databaseSizeBeforeUpdate);
        Outcome testOutcome = outcomeList.get(outcomeList.size() - 1);
        assertThat(testOutcome.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testOutcome.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testOutcome.getCreateUser()).isEqualTo(UPDATED_CREATE_USER);
        assertThat(testOutcome.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testOutcome.getUpdateUser()).isEqualTo(UPDATED_UPDATE_USER);
        assertThat(testOutcome.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingOutcome() throws Exception {
        int databaseSizeBeforeUpdate = outcomeRepository.findAll().size();

        // Create the Outcome
        OutcomeDTO outcomeDTO = outcomeMapper.toDto(outcome);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOutcomeMockMvc.perform(put("/api/outcomes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(outcomeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Outcome in the database
        List<Outcome> outcomeList = outcomeRepository.findAll();
        assertThat(outcomeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOutcome() throws Exception {
        // Initialize the database
        outcomeRepository.saveAndFlush(outcome);

        int databaseSizeBeforeDelete = outcomeRepository.findAll().size();

        // Delete the outcome
        restOutcomeMockMvc.perform(delete("/api/outcomes/{id}", outcome.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Outcome> outcomeList = outcomeRepository.findAll();
        assertThat(outcomeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
