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
import uc.dei.mse.supportivecare.config.Constants;
import uc.dei.mse.supportivecare.domain.Feedback;
import uc.dei.mse.supportivecare.domain.enumeration.EntityFeedback;
import uc.dei.mse.supportivecare.repository.FeedbackRepository;
import uc.dei.mse.supportivecare.service.FeedbackQueryService;
import uc.dei.mse.supportivecare.service.dto.FeedbackCriteria;
import uc.dei.mse.supportivecare.service.dto.FeedbackDTO;
import uc.dei.mse.supportivecare.service.mapper.FeedbackMapper;

/**
 * Integration tests for the {@link FeedbackResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FeedbackResourceIT {

    private static final EntityFeedback DEFAULT_ENTITY_NAME = EntityFeedback.ACTIVE_SUBSTANCE;
    private static final EntityFeedback UPDATED_ENTITY_NAME = EntityFeedback.THERAPEUTIC_REGIME;

    private static final Long DEFAULT_ENTITY_ID = 1L;
    private static final Long UPDATED_ENTITY_ID = 2L;
    private static final Long SMALLER_ENTITY_ID = 1L - 1L;

    private static final Boolean DEFAULT_THUMB = false;
    private static final Boolean UPDATED_THUMB = true;

    private static final String DEFAULT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REASON = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SOLVED = false;
    private static final Boolean UPDATED_SOLVED = true;

    private static final Boolean DEFAULT_ANONYM = false;
    private static final Boolean UPDATED_ANONYM = true;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private FeedbackMapper feedbackMapper;

    @Autowired
    private FeedbackQueryService feedbackQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFeedbackMockMvc;

    private Feedback feedback;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Feedback createEntity(EntityManager em) {
        Feedback feedback = new Feedback()
            .entityName(DEFAULT_ENTITY_NAME)
            .entityId(DEFAULT_ENTITY_ID)
            .thumb(DEFAULT_THUMB)
            .reason(DEFAULT_REASON)
            .solved(DEFAULT_SOLVED)
            .anonym(DEFAULT_ANONYM);
        return feedback;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Feedback createUpdatedEntity(EntityManager em) {
        Feedback feedback = new Feedback()
            .entityName(UPDATED_ENTITY_NAME)
            .entityId(UPDATED_ENTITY_ID)
            .thumb(UPDATED_THUMB)
            .reason(UPDATED_REASON)
            .solved(UPDATED_SOLVED)
            .anonym(UPDATED_ANONYM);
        return feedback;
    }

    @BeforeEach
    public void initTest() {
        feedback = createEntity(em);
    }

    @Test
    @Transactional
    void createFeedback() throws Exception {
        int databaseSizeBeforeCreate = feedbackRepository.findAll().size();
        // Create the Feedback
        FeedbackDTO feedbackDTO = feedbackMapper.toDto(feedback);
        restFeedbackMockMvc
            .perform(post("/api/feedbacks").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feedbackDTO)))
            .andExpect(status().isCreated());

        // Validate the Feedback in the database
        List<Feedback> feedbackList = feedbackRepository.findAll();
        assertThat(feedbackList).hasSize(databaseSizeBeforeCreate + 1);
        Feedback testFeedback = feedbackList.get(feedbackList.size() - 1);
        assertThat(testFeedback.getEntityName()).isEqualTo(DEFAULT_ENTITY_NAME);
        assertThat(testFeedback.getEntityId()).isEqualTo(DEFAULT_ENTITY_ID);
        assertThat(testFeedback.getThumb()).isEqualTo(DEFAULT_THUMB);
        assertThat(testFeedback.getReason()).isEqualTo(DEFAULT_REASON);
        assertThat(testFeedback.getSolved()).isEqualTo(DEFAULT_SOLVED);
        assertThat(testFeedback.getAnonym()).isEqualTo(DEFAULT_ANONYM);
    }

    @Test
    @Transactional
    void createFeedbackWithExistingId() throws Exception {
        // Create the Feedback with an existing ID
        feedback.setId(1L);
        FeedbackDTO feedbackDTO = feedbackMapper.toDto(feedback);

        int databaseSizeBeforeCreate = feedbackRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFeedbackMockMvc
            .perform(post("/api/feedbacks").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feedbackDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Feedback in the database
        List<Feedback> feedbackList = feedbackRepository.findAll();
        assertThat(feedbackList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEntityNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedbackRepository.findAll().size();
        // set the field null
        feedback.setEntityName(null);

        // Create the Feedback, which fails.
        FeedbackDTO feedbackDTO = feedbackMapper.toDto(feedback);

        restFeedbackMockMvc
            .perform(post("/api/feedbacks").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feedbackDTO)))
            .andExpect(status().isBadRequest());

        List<Feedback> feedbackList = feedbackRepository.findAll();
        assertThat(feedbackList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEntityIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedbackRepository.findAll().size();
        // set the field null
        feedback.setEntityId(null);

        // Create the Feedback, which fails.
        FeedbackDTO feedbackDTO = feedbackMapper.toDto(feedback);

        restFeedbackMockMvc
            .perform(post("/api/feedbacks").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feedbackDTO)))
            .andExpect(status().isBadRequest());

        List<Feedback> feedbackList = feedbackRepository.findAll();
        assertThat(feedbackList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkThumbIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedbackRepository.findAll().size();
        // set the field null
        feedback.setThumb(null);

        // Create the Feedback, which fails.
        FeedbackDTO feedbackDTO = feedbackMapper.toDto(feedback);

        restFeedbackMockMvc
            .perform(post("/api/feedbacks").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feedbackDTO)))
            .andExpect(status().isBadRequest());

        List<Feedback> feedbackList = feedbackRepository.findAll();
        assertThat(feedbackList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSolvedIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedbackRepository.findAll().size();
        // set the field null
        feedback.setSolved(null);

        // Create the Feedback, which fails.
        FeedbackDTO feedbackDTO = feedbackMapper.toDto(feedback);

        restFeedbackMockMvc
            .perform(post("/api/feedbacks").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feedbackDTO)))
            .andExpect(status().isBadRequest());

        List<Feedback> feedbackList = feedbackRepository.findAll();
        assertThat(feedbackList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAnonymIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedbackRepository.findAll().size();
        // set the field null
        feedback.setAnonym(null);

        // Create the Feedback, which fails.
        FeedbackDTO feedbackDTO = feedbackMapper.toDto(feedback);

        restFeedbackMockMvc
            .perform(post("/api/feedbacks").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feedbackDTO)))
            .andExpect(status().isBadRequest());

        List<Feedback> feedbackList = feedbackRepository.findAll();
        assertThat(feedbackList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFeedbacks() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbackList
        restFeedbackMockMvc
            .perform(get("/api/feedbacks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(feedback.getId().intValue())))
            .andExpect(jsonPath("$.[*].entityName").value(hasItem(DEFAULT_ENTITY_NAME.toString())))
            .andExpect(jsonPath("$.[*].entityId").value(hasItem(DEFAULT_ENTITY_ID.intValue())))
            .andExpect(jsonPath("$.[*].thumb").value(hasItem(DEFAULT_THUMB.booleanValue())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON)))
            .andExpect(jsonPath("$.[*].solved").value(hasItem(DEFAULT_SOLVED.booleanValue())))
            .andExpect(jsonPath("$.[*].anonym").value(hasItem(DEFAULT_ANONYM.booleanValue())));
    }

    @Test
    @Transactional
    void getFeedback() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get the feedback
        restFeedbackMockMvc
            .perform(get("/api/feedbacks/{id}", feedback.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(feedback.getId().intValue()))
            .andExpect(jsonPath("$.entityName").value(DEFAULT_ENTITY_NAME.toString()))
            .andExpect(jsonPath("$.entityId").value(DEFAULT_ENTITY_ID.intValue()))
            .andExpect(jsonPath("$.thumb").value(DEFAULT_THUMB.booleanValue()))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON))
            .andExpect(jsonPath("$.solved").value(DEFAULT_SOLVED.booleanValue()))
            .andExpect(jsonPath("$.anonym").value(DEFAULT_ANONYM.booleanValue()));
    }

    @Test
    @Transactional
    void getFeedbacksByIdFiltering() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        Long id = feedback.getId();

        defaultFeedbackShouldBeFound("id.equals=" + id);
        defaultFeedbackShouldNotBeFound("id.notEquals=" + id);

        defaultFeedbackShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFeedbackShouldNotBeFound("id.greaterThan=" + id);

        defaultFeedbackShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFeedbackShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFeedbacksByEntityNameIsEqualToSomething() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbackList where entityName equals to DEFAULT_ENTITY_NAME
        defaultFeedbackShouldBeFound("entityName.equals=" + DEFAULT_ENTITY_NAME);

        // Get all the feedbackList where entityName equals to UPDATED_ENTITY_NAME
        defaultFeedbackShouldNotBeFound("entityName.equals=" + UPDATED_ENTITY_NAME);
    }

    @Test
    @Transactional
    void getAllFeedbacksByEntityNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbackList where entityName not equals to DEFAULT_ENTITY_NAME
        defaultFeedbackShouldNotBeFound("entityName.notEquals=" + DEFAULT_ENTITY_NAME);

        // Get all the feedbackList where entityName not equals to UPDATED_ENTITY_NAME
        defaultFeedbackShouldBeFound("entityName.notEquals=" + UPDATED_ENTITY_NAME);
    }

    @Test
    @Transactional
    void getAllFeedbacksByEntityNameIsInShouldWork() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbackList where entityName in DEFAULT_ENTITY_NAME or UPDATED_ENTITY_NAME
        defaultFeedbackShouldBeFound("entityName.in=" + DEFAULT_ENTITY_NAME + "," + UPDATED_ENTITY_NAME);

        // Get all the feedbackList where entityName equals to UPDATED_ENTITY_NAME
        defaultFeedbackShouldNotBeFound("entityName.in=" + UPDATED_ENTITY_NAME);
    }

    @Test
    @Transactional
    void getAllFeedbacksByEntityNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbackList where entityName is not null
        defaultFeedbackShouldBeFound("entityName.specified=true");

        // Get all the feedbackList where entityName is null
        defaultFeedbackShouldNotBeFound("entityName.specified=false");
    }

    @Test
    @Transactional
    void getAllFeedbacksByEntityIdIsEqualToSomething() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbackList where entityId equals to DEFAULT_ENTITY_ID
        defaultFeedbackShouldBeFound("entityId.equals=" + DEFAULT_ENTITY_ID);

        // Get all the feedbackList where entityId equals to UPDATED_ENTITY_ID
        defaultFeedbackShouldNotBeFound("entityId.equals=" + UPDATED_ENTITY_ID);
    }

    @Test
    @Transactional
    void getAllFeedbacksByEntityIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbackList where entityId not equals to DEFAULT_ENTITY_ID
        defaultFeedbackShouldNotBeFound("entityId.notEquals=" + DEFAULT_ENTITY_ID);

        // Get all the feedbackList where entityId not equals to UPDATED_ENTITY_ID
        defaultFeedbackShouldBeFound("entityId.notEquals=" + UPDATED_ENTITY_ID);
    }

    @Test
    @Transactional
    void getAllFeedbacksByEntityIdIsInShouldWork() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbackList where entityId in DEFAULT_ENTITY_ID or UPDATED_ENTITY_ID
        defaultFeedbackShouldBeFound("entityId.in=" + DEFAULT_ENTITY_ID + "," + UPDATED_ENTITY_ID);

        // Get all the feedbackList where entityId equals to UPDATED_ENTITY_ID
        defaultFeedbackShouldNotBeFound("entityId.in=" + UPDATED_ENTITY_ID);
    }

    @Test
    @Transactional
    void getAllFeedbacksByEntityIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbackList where entityId is not null
        defaultFeedbackShouldBeFound("entityId.specified=true");

        // Get all the feedbackList where entityId is null
        defaultFeedbackShouldNotBeFound("entityId.specified=false");
    }

    @Test
    @Transactional
    void getAllFeedbacksByEntityIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbackList where entityId is greater than or equal to DEFAULT_ENTITY_ID
        defaultFeedbackShouldBeFound("entityId.greaterThanOrEqual=" + DEFAULT_ENTITY_ID);

        // Get all the feedbackList where entityId is greater than or equal to UPDATED_ENTITY_ID
        defaultFeedbackShouldNotBeFound("entityId.greaterThanOrEqual=" + UPDATED_ENTITY_ID);
    }

    @Test
    @Transactional
    void getAllFeedbacksByEntityIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbackList where entityId is less than or equal to DEFAULT_ENTITY_ID
        defaultFeedbackShouldBeFound("entityId.lessThanOrEqual=" + DEFAULT_ENTITY_ID);

        // Get all the feedbackList where entityId is less than or equal to SMALLER_ENTITY_ID
        defaultFeedbackShouldNotBeFound("entityId.lessThanOrEqual=" + SMALLER_ENTITY_ID);
    }

    @Test
    @Transactional
    void getAllFeedbacksByEntityIdIsLessThanSomething() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbackList where entityId is less than DEFAULT_ENTITY_ID
        defaultFeedbackShouldNotBeFound("entityId.lessThan=" + DEFAULT_ENTITY_ID);

        // Get all the feedbackList where entityId is less than UPDATED_ENTITY_ID
        defaultFeedbackShouldBeFound("entityId.lessThan=" + UPDATED_ENTITY_ID);
    }

    @Test
    @Transactional
    void getAllFeedbacksByEntityIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbackList where entityId is greater than DEFAULT_ENTITY_ID
        defaultFeedbackShouldNotBeFound("entityId.greaterThan=" + DEFAULT_ENTITY_ID);

        // Get all the feedbackList where entityId is greater than SMALLER_ENTITY_ID
        defaultFeedbackShouldBeFound("entityId.greaterThan=" + SMALLER_ENTITY_ID);
    }

    @Test
    @Transactional
    void getAllFeedbacksByThumbIsEqualToSomething() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbackList where thumb equals to DEFAULT_THUMB
        defaultFeedbackShouldBeFound("thumb.equals=" + DEFAULT_THUMB);

        // Get all the feedbackList where thumb equals to UPDATED_THUMB
        defaultFeedbackShouldNotBeFound("thumb.equals=" + UPDATED_THUMB);
    }

    @Test
    @Transactional
    void getAllFeedbacksByThumbIsNotEqualToSomething() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbackList where thumb not equals to DEFAULT_THUMB
        defaultFeedbackShouldNotBeFound("thumb.notEquals=" + DEFAULT_THUMB);

        // Get all the feedbackList where thumb not equals to UPDATED_THUMB
        defaultFeedbackShouldBeFound("thumb.notEquals=" + UPDATED_THUMB);
    }

    @Test
    @Transactional
    void getAllFeedbacksByThumbIsInShouldWork() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbackList where thumb in DEFAULT_THUMB or UPDATED_THUMB
        defaultFeedbackShouldBeFound("thumb.in=" + DEFAULT_THUMB + "," + UPDATED_THUMB);

        // Get all the feedbackList where thumb equals to UPDATED_THUMB
        defaultFeedbackShouldNotBeFound("thumb.in=" + UPDATED_THUMB);
    }

    @Test
    @Transactional
    void getAllFeedbacksByThumbIsNullOrNotNull() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbackList where thumb is not null
        defaultFeedbackShouldBeFound("thumb.specified=true");

        // Get all the feedbackList where thumb is null
        defaultFeedbackShouldNotBeFound("thumb.specified=false");
    }

    @Test
    @Transactional
    void getAllFeedbacksByReasonIsEqualToSomething() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbackList where reason equals to DEFAULT_REASON
        defaultFeedbackShouldBeFound("reason.equals=" + DEFAULT_REASON);

        // Get all the feedbackList where reason equals to UPDATED_REASON
        defaultFeedbackShouldNotBeFound("reason.equals=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    void getAllFeedbacksByReasonIsNotEqualToSomething() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbackList where reason not equals to DEFAULT_REASON
        defaultFeedbackShouldNotBeFound("reason.notEquals=" + DEFAULT_REASON);

        // Get all the feedbackList where reason not equals to UPDATED_REASON
        defaultFeedbackShouldBeFound("reason.notEquals=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    void getAllFeedbacksByReasonIsInShouldWork() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbackList where reason in DEFAULT_REASON or UPDATED_REASON
        defaultFeedbackShouldBeFound("reason.in=" + DEFAULT_REASON + "," + UPDATED_REASON);

        // Get all the feedbackList where reason equals to UPDATED_REASON
        defaultFeedbackShouldNotBeFound("reason.in=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    void getAllFeedbacksByReasonIsNullOrNotNull() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbackList where reason is not null
        defaultFeedbackShouldBeFound("reason.specified=true");

        // Get all the feedbackList where reason is null
        defaultFeedbackShouldNotBeFound("reason.specified=false");
    }

    @Test
    @Transactional
    void getAllFeedbacksByReasonContainsSomething() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbackList where reason contains DEFAULT_REASON
        defaultFeedbackShouldBeFound("reason.contains=" + DEFAULT_REASON);

        // Get all the feedbackList where reason contains UPDATED_REASON
        defaultFeedbackShouldNotBeFound("reason.contains=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    void getAllFeedbacksByReasonNotContainsSomething() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbackList where reason does not contain DEFAULT_REASON
        defaultFeedbackShouldNotBeFound("reason.doesNotContain=" + DEFAULT_REASON);

        // Get all the feedbackList where reason does not contain UPDATED_REASON
        defaultFeedbackShouldBeFound("reason.doesNotContain=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    void getAllFeedbacksBySolvedIsEqualToSomething() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbackList where solved equals to DEFAULT_SOLVED
        defaultFeedbackShouldBeFound("solved.equals=" + DEFAULT_SOLVED);

        // Get all the feedbackList where solved equals to UPDATED_SOLVED
        defaultFeedbackShouldNotBeFound("solved.equals=" + UPDATED_SOLVED);
    }

    @Test
    @Transactional
    void getAllFeedbacksBySolvedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbackList where solved not equals to DEFAULT_SOLVED
        defaultFeedbackShouldNotBeFound("solved.notEquals=" + DEFAULT_SOLVED);

        // Get all the feedbackList where solved not equals to UPDATED_SOLVED
        defaultFeedbackShouldBeFound("solved.notEquals=" + UPDATED_SOLVED);
    }

    @Test
    @Transactional
    void getAllFeedbacksBySolvedIsInShouldWork() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbackList where solved in DEFAULT_SOLVED or UPDATED_SOLVED
        defaultFeedbackShouldBeFound("solved.in=" + DEFAULT_SOLVED + "," + UPDATED_SOLVED);

        // Get all the feedbackList where solved equals to UPDATED_SOLVED
        defaultFeedbackShouldNotBeFound("solved.in=" + UPDATED_SOLVED);
    }

    @Test
    @Transactional
    void getAllFeedbacksBySolvedIsNullOrNotNull() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbackList where solved is not null
        defaultFeedbackShouldBeFound("solved.specified=true");

        // Get all the feedbackList where solved is null
        defaultFeedbackShouldNotBeFound("solved.specified=false");
    }

    @Test
    @Transactional
    void getAllFeedbacksByAnonymIsEqualToSomething() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbackList where anonym equals to DEFAULT_ANONYM
        defaultFeedbackShouldBeFound("anonym.equals=" + DEFAULT_ANONYM);

        // Get all the feedbackList where anonym equals to UPDATED_ANONYM
        defaultFeedbackShouldNotBeFound("anonym.equals=" + UPDATED_ANONYM);
    }

    @Test
    @Transactional
    void getAllFeedbacksByAnonymIsNotEqualToSomething() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbackList where anonym not equals to DEFAULT_ANONYM
        defaultFeedbackShouldNotBeFound("anonym.notEquals=" + DEFAULT_ANONYM);

        // Get all the feedbackList where anonym not equals to UPDATED_ANONYM
        defaultFeedbackShouldBeFound("anonym.notEquals=" + UPDATED_ANONYM);
    }

    @Test
    @Transactional
    void getAllFeedbacksByAnonymIsInShouldWork() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbackList where anonym in DEFAULT_ANONYM or UPDATED_ANONYM
        defaultFeedbackShouldBeFound("anonym.in=" + DEFAULT_ANONYM + "," + UPDATED_ANONYM);

        // Get all the feedbackList where anonym equals to UPDATED_ANONYM
        defaultFeedbackShouldNotBeFound("anonym.in=" + UPDATED_ANONYM);
    }

    @Test
    @Transactional
    void getAllFeedbacksByAnonymIsNullOrNotNull() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbackList where anonym is not null
        defaultFeedbackShouldBeFound("anonym.specified=true");

        // Get all the feedbackList where anonym is null
        defaultFeedbackShouldNotBeFound("anonym.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFeedbackShouldBeFound(String filter) throws Exception {
        restFeedbackMockMvc
            .perform(get("/api/feedbacks?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(feedback.getId().intValue())))
            .andExpect(jsonPath("$.[*].entityName").value(hasItem(DEFAULT_ENTITY_NAME.toString())))
            .andExpect(jsonPath("$.[*].entityId").value(hasItem(DEFAULT_ENTITY_ID.intValue())))
            .andExpect(jsonPath("$.[*].thumb").value(hasItem(DEFAULT_THUMB.booleanValue())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON)))
            .andExpect(jsonPath("$.[*].solved").value(hasItem(DEFAULT_SOLVED.booleanValue())))
            .andExpect(jsonPath("$.[*].anonym").value(hasItem(DEFAULT_ANONYM.booleanValue())));

        // Check, that the count call also returns 1
        restFeedbackMockMvc
            .perform(get("/api/feedbacks/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFeedbackShouldNotBeFound(String filter) throws Exception {
        restFeedbackMockMvc
            .perform(get("/api/feedbacks?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFeedbackMockMvc
            .perform(get("/api/feedbacks/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFeedback() throws Exception {
        // Get the feedback
        restFeedbackMockMvc.perform(get("/api/feedbacks/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void updateFeedback() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        int databaseSizeBeforeUpdate = feedbackRepository.findAll().size();

        // Update the feedback
        Feedback updatedFeedback = feedbackRepository.findById(feedback.getId()).get();
        // Disconnect from session so that the updates on updatedFeedback are not directly saved in db
        em.detach(updatedFeedback);
        updatedFeedback
            .entityName(UPDATED_ENTITY_NAME)
            .entityId(UPDATED_ENTITY_ID)
            .thumb(UPDATED_THUMB)
            .reason(UPDATED_REASON)
            .solved(UPDATED_SOLVED)
            .anonym(UPDATED_ANONYM);
        FeedbackDTO feedbackDTO = feedbackMapper.toDto(updatedFeedback);

        restFeedbackMockMvc
            .perform(put("/api/feedbacks").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feedbackDTO)))
            .andExpect(status().isOk());

        // Validate the Feedback in the database
        List<Feedback> feedbackList = feedbackRepository.findAll();
        assertThat(feedbackList).hasSize(databaseSizeBeforeUpdate);
        Feedback testFeedback = feedbackList.get(feedbackList.size() - 1);
        assertThat(testFeedback.getEntityName()).isEqualTo(UPDATED_ENTITY_NAME);
        assertThat(testFeedback.getEntityId()).isEqualTo(UPDATED_ENTITY_ID);
        assertThat(testFeedback.getThumb()).isEqualTo(UPDATED_THUMB);
        assertThat(testFeedback.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testFeedback.getSolved()).isEqualTo(UPDATED_SOLVED);
        assertThat(testFeedback.getAnonym()).isEqualTo(UPDATED_ANONYM);
    }

    @Test
    @Transactional
    void updateNonExistingFeedback() throws Exception {
        int databaseSizeBeforeUpdate = feedbackRepository.findAll().size();

        // Create the Feedback
        FeedbackDTO feedbackDTO = feedbackMapper.toDto(feedback);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFeedbackMockMvc
            .perform(put("/api/feedbacks").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feedbackDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Feedback in the database
        List<Feedback> feedbackList = feedbackRepository.findAll();
        assertThat(feedbackList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFeedbackWithPatch() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        int databaseSizeBeforeUpdate = feedbackRepository.findAll().size();

        // Update the feedback using partial update
        Feedback partialUpdatedFeedback = new Feedback();
        partialUpdatedFeedback.setId(feedback.getId());

        partialUpdatedFeedback
            .entityName(UPDATED_ENTITY_NAME)
            .entityId(UPDATED_ENTITY_ID)
            .thumb(UPDATED_THUMB)
            .solved(UPDATED_SOLVED)
            .anonym(UPDATED_ANONYM);

        restFeedbackMockMvc
            .perform(
                patch("/api/feedbacks")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFeedback))
            )
            .andExpect(status().isOk());

        // Validate the Feedback in the database
        List<Feedback> feedbackList = feedbackRepository.findAll();
        assertThat(feedbackList).hasSize(databaseSizeBeforeUpdate);
        Feedback testFeedback = feedbackList.get(feedbackList.size() - 1);
        assertThat(testFeedback.getEntityName()).isEqualTo(UPDATED_ENTITY_NAME);
        assertThat(testFeedback.getEntityId()).isEqualTo(UPDATED_ENTITY_ID);
        assertThat(testFeedback.getThumb()).isEqualTo(UPDATED_THUMB);
        assertThat(testFeedback.getReason()).isEqualTo(DEFAULT_REASON);
        assertThat(testFeedback.getSolved()).isEqualTo(UPDATED_SOLVED);
        assertThat(testFeedback.getAnonym()).isEqualTo(UPDATED_ANONYM);
    }

    @Test
    @Transactional
    void fullUpdateFeedbackWithPatch() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        int databaseSizeBeforeUpdate = feedbackRepository.findAll().size();

        // Update the feedback using partial update
        Feedback partialUpdatedFeedback = new Feedback();
        partialUpdatedFeedback.setId(feedback.getId());

        partialUpdatedFeedback
            .entityName(UPDATED_ENTITY_NAME)
            .entityId(UPDATED_ENTITY_ID)
            .thumb(UPDATED_THUMB)
            .reason(UPDATED_REASON)
            .solved(UPDATED_SOLVED)
            .anonym(UPDATED_ANONYM);

        restFeedbackMockMvc
            .perform(
                patch("/api/feedbacks")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFeedback))
            )
            .andExpect(status().isOk());

        // Validate the Feedback in the database
        List<Feedback> feedbackList = feedbackRepository.findAll();
        assertThat(feedbackList).hasSize(databaseSizeBeforeUpdate);
        Feedback testFeedback = feedbackList.get(feedbackList.size() - 1);
        assertThat(testFeedback.getEntityName()).isEqualTo(UPDATED_ENTITY_NAME);
        assertThat(testFeedback.getEntityId()).isEqualTo(UPDATED_ENTITY_ID);
        assertThat(testFeedback.getThumb()).isEqualTo(UPDATED_THUMB);
        assertThat(testFeedback.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testFeedback.getSolved()).isEqualTo(UPDATED_SOLVED);
        assertThat(testFeedback.getAnonym()).isEqualTo(UPDATED_ANONYM);
    }

    @Test
    @Transactional
    void partialUpdateFeedbackShouldThrown() throws Exception {
        // Update the feedback without id should throw
        Feedback partialUpdatedFeedback = new Feedback();

        restFeedbackMockMvc
            .perform(
                patch("/api/feedbacks")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFeedback))
            )
            .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void deleteFeedback() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        int databaseSizeBeforeDelete = feedbackRepository.findAll().size();

        // Delete the feedback
        restFeedbackMockMvc
            .perform(delete("/api/feedbacks/{id}", feedback.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Feedback> feedbackList = feedbackRepository.findAll();
        assertThat(feedbackList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    void manageFeedback_create() throws Exception {
        int databaseSizeBeforeCreate = feedbackRepository.findAll().size();
        // Create the Feedback
        FeedbackDTO feedbackDTO = feedbackMapper.toDto(feedback);
        restFeedbackMockMvc
            .perform(
                post("/api/feedbacks/{entityName}/{entityId}", feedback.getEntityName().getValue(), feedback.getEntityId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(feedbackDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Feedback in the database
        List<Feedback> feedbackList = feedbackRepository.findAll();
        assertThat(feedbackList).hasSize(databaseSizeBeforeCreate + 1);
        Feedback testFeedback = feedbackList.get(feedbackList.size() - 1);
        assertThat(testFeedback.getEntityName()).isEqualTo(DEFAULT_ENTITY_NAME);
        assertThat(testFeedback.getEntityId()).isEqualTo(DEFAULT_ENTITY_ID);
        assertThat(testFeedback.getThumb()).isEqualTo(DEFAULT_THUMB);
        assertThat(testFeedback.getReason()).isEqualTo(DEFAULT_REASON);
        assertThat(testFeedback.getSolved()).isEqualTo(DEFAULT_SOLVED);
        assertThat(testFeedback.getAnonym()).isEqualTo(DEFAULT_ANONYM);
    }

    @Test
    @Transactional
    @WithMockUser(username = Constants.SYSTEM)
    void manageFeedback_update() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        int databaseSizeBeforeUpdate = feedbackRepository.findAll().size();

        // Update the feedback
        Feedback updatedFeedback = feedbackRepository.findById(feedback.getId()).get();
        // Disconnect from session so that the updates on updatedFeedback are not directly saved in db
        em.detach(updatedFeedback);
        updatedFeedback.thumb(UPDATED_THUMB).reason(UPDATED_REASON).solved(UPDATED_SOLVED).anonym(UPDATED_ANONYM);
        FeedbackDTO feedbackDTO = feedbackMapper.toDto(updatedFeedback);

        restFeedbackMockMvc
            .perform(
                post("/api/feedbacks/{entityName}/{entityId}", feedback.getEntityName().getValue(), feedback.getEntityId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(feedbackDTO))
            )
            .andExpect(status().isOk());

        // Validate the Feedback in the database
        List<Feedback> feedbackList = feedbackRepository.findAll();
        assertThat(feedbackList).hasSize(databaseSizeBeforeUpdate);
        Feedback testFeedback = feedbackList.get(feedbackList.size() - 1);
        assertThat(testFeedback.getEntityName()).isEqualTo(DEFAULT_ENTITY_NAME);
        assertThat(testFeedback.getEntityId()).isEqualTo(DEFAULT_ENTITY_ID);
        assertThat(testFeedback.getThumb()).isEqualTo(UPDATED_THUMB);
        assertThat(testFeedback.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testFeedback.getSolved()).isEqualTo(UPDATED_SOLVED);
        assertThat(testFeedback.getAnonym()).isEqualTo(UPDATED_ANONYM);
    }

    @Test
    @Transactional
    @WithMockUser(username = Constants.SYSTEM)
    void manageFeedback_delete() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        int databaseSizeBeforeDelete = feedbackRepository.findAll().size();

        // Update the feedback
        Feedback updatedFeedback = feedbackRepository.findById(feedback.getId()).get();
        // Disconnect from session so that the updates on updatedFeedback are not directly saved in db
        em.detach(updatedFeedback);
        updatedFeedback.thumb(null);
        FeedbackDTO feedbackDTO = feedbackMapper.toDto(updatedFeedback);

        // Delete the feedback
        restFeedbackMockMvc
            .perform(
                post("/api/feedbacks/{entityName}/{entityId}", feedback.getEntityName().getValue(), feedback.getEntityId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(feedbackDTO))
            )
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Feedback> feedbackList = feedbackRepository.findAll();
        assertThat(feedbackList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
