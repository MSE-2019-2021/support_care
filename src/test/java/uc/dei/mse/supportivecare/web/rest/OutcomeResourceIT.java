package uc.dei.mse.supportivecare.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import uc.dei.mse.supportivecare.IntegrationTest;
import uc.dei.mse.supportivecare.domain.Document;
import uc.dei.mse.supportivecare.domain.Outcome;
import uc.dei.mse.supportivecare.domain.Symptom;
import uc.dei.mse.supportivecare.repository.OutcomeRepository;
import uc.dei.mse.supportivecare.service.DocumentService;
import uc.dei.mse.supportivecare.service.OutcomeQueryService;
import uc.dei.mse.supportivecare.service.OutcomeService;
import uc.dei.mse.supportivecare.service.dto.OutcomeCriteria;
import uc.dei.mse.supportivecare.service.dto.OutcomeDTO;
import uc.dei.mse.supportivecare.service.mapper.DocumentContentMapper;
import uc.dei.mse.supportivecare.service.mapper.OutcomeMapper;

/**
 * Integration tests for the {@link OutcomeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OutcomeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private OutcomeRepository outcomeRepository;

    @Autowired
    private OutcomeMapper outcomeMapper;

    @Autowired
    private OutcomeQueryService outcomeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOutcomeMockMvc;

    @Autowired
    private OutcomeService outcomeService;

    @Autowired
    private DocumentContentMapper documentContentMapper;

    @Autowired
    private DocumentService documentService;

    private Outcome outcome;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Outcome createEntity(EntityManager em) {
        Outcome outcome = new Outcome().name(DEFAULT_NAME).description(DEFAULT_DESCRIPTION);
        return outcome;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Outcome createUpdatedEntity(EntityManager em) {
        Outcome outcome = new Outcome().name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
        return outcome;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OutcomeResource outcomeResource = new OutcomeResource(
            outcomeService,
            outcomeQueryService,
            documentContentMapper,
            documentService
        );
    }

    @BeforeEach
    public void initTest() {
        outcome = createEntity(em);
    }

    @Test
    @Transactional
    void createOutcome() throws Exception {
        int databaseSizeBeforeCreate = outcomeRepository.findAll().size();
        // Create the Outcome
        OutcomeDTO outcomeDTO = outcomeMapper.toDto(outcome);
        MockMultipartFile file = new MockMultipartFile("files", "hello.txt", MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());

        MockMultipartFile outcome = new MockMultipartFile(
            "outcomeDTO",
            "outcomeDTO",
            MediaType.APPLICATION_JSON_VALUE,
            TestUtil.convertObjectToJsonBytes(outcomeDTO)
        );

        restOutcomeMockMvc.perform(multipart("/api/outcomes").file(outcome).file(file)).andExpect(status().isCreated());

        // Validate the Outcome in the database
        List<Outcome> outcomeList = outcomeRepository.findAll();
        assertThat(outcomeList).hasSize(databaseSizeBeforeCreate + 1);
        Outcome testOutcome = outcomeList.get(outcomeList.size() - 1);
        assertThat(testOutcome.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOutcome.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createOutcomeWithExistingId() throws Exception {
        // Create the Outcome with an existing ID
        outcome.setId(1L);
        OutcomeDTO outcomeDTO = outcomeMapper.toDto(outcome);
        MockMultipartFile file = new MockMultipartFile("files", "hello.txt", MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());

        MockMultipartFile outcome = new MockMultipartFile(
            "outcomeDTO",
            "outcomeDTO",
            MediaType.APPLICATION_JSON_VALUE,
            TestUtil.convertObjectToJsonBytes(outcomeDTO)
        );

        int databaseSizeBeforeCreate = outcomeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOutcomeMockMvc.perform(multipart("/api/outcomes").file(outcome).file(file)).andExpect(status().isBadRequest());

        // Validate the Outcome in the database
        List<Outcome> outcomeList = outcomeRepository.findAll();
        assertThat(outcomeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = outcomeRepository.findAll().size();
        // set the field null
        outcome.setName(null);

        // Create the Outcome, which fails.
        OutcomeDTO outcomeDTO = outcomeMapper.toDto(outcome);
        MockMultipartFile file = new MockMultipartFile("files", "hello.txt", MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());

        MockMultipartFile outcome = new MockMultipartFile(
            "outcomeDTO",
            "outcomeDTO",
            MediaType.APPLICATION_JSON_VALUE,
            TestUtil.convertObjectToJsonBytes(outcomeDTO)
        );

        restOutcomeMockMvc.perform(multipart("/api/outcomes").file(outcome).file(file)).andExpect(status().isBadRequest());

        List<Outcome> outcomeList = outcomeRepository.findAll();
        assertThat(outcomeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOutcomes() throws Exception {
        // Initialize the database
        outcomeRepository.saveAndFlush(outcome);

        // Get all the outcomeList
        restOutcomeMockMvc
            .perform(get("/api/outcomes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(outcome.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getOutcome() throws Exception {
        // Initialize the database
        outcomeRepository.saveAndFlush(outcome);

        // Get the outcome
        restOutcomeMockMvc
            .perform(get("/api/outcomes/{id}", outcome.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(outcome.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getOutcomesByIdFiltering() throws Exception {
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
    void getAllOutcomesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        outcomeRepository.saveAndFlush(outcome);

        // Get all the outcomeList where name equals to DEFAULT_NAME
        defaultOutcomeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the outcomeList where name equals to UPDATED_NAME
        defaultOutcomeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOutcomesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        outcomeRepository.saveAndFlush(outcome);

        // Get all the outcomeList where name not equals to DEFAULT_NAME
        defaultOutcomeShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the outcomeList where name not equals to UPDATED_NAME
        defaultOutcomeShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOutcomesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        outcomeRepository.saveAndFlush(outcome);

        // Get all the outcomeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultOutcomeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the outcomeList where name equals to UPDATED_NAME
        defaultOutcomeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOutcomesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        outcomeRepository.saveAndFlush(outcome);

        // Get all the outcomeList where name is not null
        defaultOutcomeShouldBeFound("name.specified=true");

        // Get all the outcomeList where name is null
        defaultOutcomeShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllOutcomesByNameContainsSomething() throws Exception {
        // Initialize the database
        outcomeRepository.saveAndFlush(outcome);

        // Get all the outcomeList where name contains DEFAULT_NAME
        defaultOutcomeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the outcomeList where name contains UPDATED_NAME
        defaultOutcomeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOutcomesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        outcomeRepository.saveAndFlush(outcome);

        // Get all the outcomeList where name does not contain DEFAULT_NAME
        defaultOutcomeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the outcomeList where name does not contain UPDATED_NAME
        defaultOutcomeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOutcomesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        outcomeRepository.saveAndFlush(outcome);

        // Get all the outcomeList where description equals to DEFAULT_DESCRIPTION
        defaultOutcomeShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the outcomeList where description equals to UPDATED_DESCRIPTION
        defaultOutcomeShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOutcomesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        outcomeRepository.saveAndFlush(outcome);

        // Get all the outcomeList where description not equals to DEFAULT_DESCRIPTION
        defaultOutcomeShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the outcomeList where description not equals to UPDATED_DESCRIPTION
        defaultOutcomeShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOutcomesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        outcomeRepository.saveAndFlush(outcome);

        // Get all the outcomeList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultOutcomeShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the outcomeList where description equals to UPDATED_DESCRIPTION
        defaultOutcomeShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOutcomesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        outcomeRepository.saveAndFlush(outcome);

        // Get all the outcomeList where description is not null
        defaultOutcomeShouldBeFound("description.specified=true");

        // Get all the outcomeList where description is null
        defaultOutcomeShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllOutcomesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        outcomeRepository.saveAndFlush(outcome);

        // Get all the outcomeList where description contains DEFAULT_DESCRIPTION
        defaultOutcomeShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the outcomeList where description contains UPDATED_DESCRIPTION
        defaultOutcomeShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOutcomesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        outcomeRepository.saveAndFlush(outcome);

        // Get all the outcomeList where description does not contain DEFAULT_DESCRIPTION
        defaultOutcomeShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the outcomeList where description does not contain UPDATED_DESCRIPTION
        defaultOutcomeShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOutcomesByDocumentIsEqualToSomething() throws Exception {
        // Initialize the database
        outcomeRepository.saveAndFlush(outcome);
        Document document = DocumentResourceIT.createEntity(em);
        em.persist(document);
        em.flush();
        outcome.addDocument(document);
        outcomeRepository.saveAndFlush(outcome);
        Long documentId = document.getId();

        // Get all the outcomeList where document equals to documentId
        defaultOutcomeShouldBeFound("documentId.equals=" + documentId);

        // Get all the outcomeList where document equals to documentId + 1
        defaultOutcomeShouldNotBeFound("documentId.equals=" + (documentId + 1));
    }

    @Test
    @Transactional
    void getAllOutcomesBySymptomIsEqualToSomething() throws Exception {
        // Initialize the database
        outcomeRepository.saveAndFlush(outcome);
        Symptom symptom = SymptomResourceIT.createEntity(em);
        em.persist(symptom);
        em.flush();
        outcome.addSymptom(symptom);
        outcomeRepository.saveAndFlush(outcome);
        Long symptomId = symptom.getId();

        // Get all the outcomeList where symptom equals to symptomId
        defaultOutcomeShouldBeFound("symptomId.equals=" + symptomId);

        // Get all the outcomeList where symptom equals to symptomId + 1
        defaultOutcomeShouldNotBeFound("symptomId.equals=" + (symptomId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOutcomeShouldBeFound(String filter) throws Exception {
        restOutcomeMockMvc
            .perform(get("/api/outcomes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(outcome.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restOutcomeMockMvc
            .perform(get("/api/outcomes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOutcomeShouldNotBeFound(String filter) throws Exception {
        restOutcomeMockMvc
            .perform(get("/api/outcomes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOutcomeMockMvc
            .perform(get("/api/outcomes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingOutcome() throws Exception {
        // Get the outcome
        restOutcomeMockMvc.perform(get("/api/outcomes/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void updateOutcome() throws Exception {
        // Initialize the database
        outcomeRepository.saveAndFlush(outcome);

        int databaseSizeBeforeUpdate = outcomeRepository.findAll().size();

        // Update the outcome
        Outcome updatedOutcome = outcomeRepository.findById(outcome.getId()).get();
        // Disconnect from session so that the updates on updatedOutcome are not directly saved in db
        em.detach(updatedOutcome);
        updatedOutcome.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
        OutcomeDTO outcomeDTO = outcomeMapper.toDto(updatedOutcome);
        MockMultipartFile file = new MockMultipartFile("files", "hello.txt", MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());

        MockMultipartFile outcome = new MockMultipartFile(
            "outcomeDTO",
            "outcomeDTO",
            MediaType.APPLICATION_JSON_VALUE,
            TestUtil.convertObjectToJsonBytes(outcomeDTO)
        );

        MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.fileUpload("/api/outcomes");

        builder.with(
            new RequestPostProcessor() {
                @Override
                public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                    request.setMethod("PUT");
                    return request;
                }
            }
        );

        restOutcomeMockMvc.perform(builder.file(outcome).file(file)).andExpect(status().isOk());

        // Validate the Outcome in the database
        List<Outcome> outcomeList = outcomeRepository.findAll();
        assertThat(outcomeList).hasSize(databaseSizeBeforeUpdate);
        Outcome testOutcome = outcomeList.get(outcomeList.size() - 1);
        assertThat(testOutcome.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOutcome.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void updateNonExistingOutcome() throws Exception {
        int databaseSizeBeforeUpdate = outcomeRepository.findAll().size();

        // Create the Outcome
        OutcomeDTO outcomeDTO = outcomeMapper.toDto(outcome);
        MockMultipartFile file = new MockMultipartFile("files", "hello.txt", MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());

        MockMultipartFile outcome = new MockMultipartFile(
            "outcomeDTO",
            "outcomeDTO",
            MediaType.APPLICATION_JSON_VALUE,
            TestUtil.convertObjectToJsonBytes(outcomeDTO)
        );

        MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.fileUpload("/api/outcomes");

        builder.with(
            new RequestPostProcessor() {
                @Override
                public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                    request.setMethod("PUT");
                    return request;
                }
            }
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOutcomeMockMvc.perform(builder.file(outcome).file(file)).andExpect(status().isBadRequest());

        // Validate the Outcome in the database
        List<Outcome> outcomeList = outcomeRepository.findAll();
        assertThat(outcomeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOutcomeWithPatch() throws Exception {
        // Initialize the database
        outcomeRepository.saveAndFlush(outcome);

        int databaseSizeBeforeUpdate = outcomeRepository.findAll().size();

        // Update the outcome using partial update
        Outcome partialUpdatedOutcome = new Outcome();
        partialUpdatedOutcome.setId(outcome.getId());

        restOutcomeMockMvc
            .perform(
                patch("/api/outcomes")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOutcome))
            )
            .andExpect(status().isOk());

        // Validate the Outcome in the database
        List<Outcome> outcomeList = outcomeRepository.findAll();
        assertThat(outcomeList).hasSize(databaseSizeBeforeUpdate);
        Outcome testOutcome = outcomeList.get(outcomeList.size() - 1);
        assertThat(testOutcome.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOutcome.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateOutcomeWithPatch() throws Exception {
        // Initialize the database
        outcomeRepository.saveAndFlush(outcome);

        int databaseSizeBeforeUpdate = outcomeRepository.findAll().size();

        // Update the outcome using partial update
        Outcome partialUpdatedOutcome = new Outcome();
        partialUpdatedOutcome.setId(outcome.getId());

        partialUpdatedOutcome.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restOutcomeMockMvc
            .perform(
                patch("/api/outcomes")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOutcome))
            )
            .andExpect(status().isOk());

        // Validate the Outcome in the database
        List<Outcome> outcomeList = outcomeRepository.findAll();
        assertThat(outcomeList).hasSize(databaseSizeBeforeUpdate);
        Outcome testOutcome = outcomeList.get(outcomeList.size() - 1);
        assertThat(testOutcome.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOutcome.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void partialUpdateOutcomeShouldThrown() throws Exception {
        // Update the outcome without id should throw
        Outcome partialUpdatedOutcome = new Outcome();

        restOutcomeMockMvc
            .perform(
                patch("/api/outcomes")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOutcome))
            )
            .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void deleteOutcome() throws Exception {
        // Initialize the database
        outcomeRepository.saveAndFlush(outcome);

        int databaseSizeBeforeDelete = outcomeRepository.findAll().size();

        // Delete the outcome
        restOutcomeMockMvc
            .perform(delete("/api/outcomes/{id}", outcome.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Outcome> outcomeList = outcomeRepository.findAll();
        assertThat(outcomeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
