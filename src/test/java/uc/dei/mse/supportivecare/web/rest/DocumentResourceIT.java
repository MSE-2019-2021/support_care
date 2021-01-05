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
import uc.dei.mse.supportivecare.domain.Content;
import uc.dei.mse.supportivecare.domain.Document;
import uc.dei.mse.supportivecare.domain.Outcome;
import uc.dei.mse.supportivecare.repository.DocumentRepository;
import uc.dei.mse.supportivecare.service.DocumentQueryService;
import uc.dei.mse.supportivecare.service.dto.DocumentCriteria;
import uc.dei.mse.supportivecare.service.dto.DocumentDTO;
import uc.dei.mse.supportivecare.service.mapper.DocumentMapper;

/**
 * Integration tests for the {@link DocumentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DocumentResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final Long DEFAULT_SIZE = 1L;
    private static final Long UPDATED_SIZE = 2L;
    private static final Long SMALLER_SIZE = 1L - 1L;

    private static final String DEFAULT_MIME_TYPE = "text/plain";
    private static final String UPDATED_MIME_TYPE = "text/plain";

    private static final byte[] DEFAULT_DATA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DATA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DATA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DATA_CONTENT_TYPE = "image/png";

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private DocumentMapper documentMapper;

    @Autowired
    private DocumentQueryService documentQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDocumentMockMvc;

    private Document document;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Document createEntity(EntityManager em) {
        Content content = new Content().data(DEFAULT_DATA).dataContentType(DEFAULT_DATA_CONTENT_TYPE);
        Document document = new Document().title(DEFAULT_TITLE).size(DEFAULT_SIZE).mimeType(DEFAULT_MIME_TYPE).content(content);
        // Add required entity
        Outcome outcome;
        if (TestUtil.findAll(em, Outcome.class).isEmpty()) {
            outcome = OutcomeResourceIT.createEntity(em);
            em.persist(outcome);
            em.flush();
        } else {
            outcome = TestUtil.findAll(em, Outcome.class).get(0);
        }
        document.setOutcome(outcome);
        return document;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Document createUpdatedEntity(EntityManager em) {
        Content content = new Content().data(UPDATED_DATA).dataContentType(UPDATED_DATA_CONTENT_TYPE);
        Document document = new Document().title(UPDATED_TITLE).size(UPDATED_SIZE).mimeType(UPDATED_MIME_TYPE).content(content);
        // Add required entity
        Outcome outcome;
        if (TestUtil.findAll(em, Outcome.class).isEmpty()) {
            outcome = OutcomeResourceIT.createUpdatedEntity(em);
            em.persist(outcome);
            em.flush();
        } else {
            outcome = TestUtil.findAll(em, Outcome.class).get(0);
        }
        document.setOutcome(outcome);
        return document;
    }

    @BeforeEach
    public void initTest() {
        document = createEntity(em);
    }

    @Test
    @Transactional
    void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentRepository.findAll().size();
        // set the field null
        document.setTitle(null);

        // Create the Document, which fails.
        DocumentDTO documentDTO = documentMapper.toDto(document);

        restDocumentMockMvc
            .perform(post("/api/documents").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(documentDTO)))
            .andExpect(status().isBadRequest());

        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSizeIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentRepository.findAll().size();
        // set the field null
        document.setSize(null);

        // Create the Document, which fails.
        DocumentDTO documentDTO = documentMapper.toDto(document);

        restDocumentMockMvc
            .perform(post("/api/documents").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(documentDTO)))
            .andExpect(status().isBadRequest());

        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDocuments() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList
        restDocumentMockMvc
            .perform(get("/api/documents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(document.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].size").value(hasItem(DEFAULT_SIZE.intValue())))
            .andExpect(jsonPath("$.[*].mimeType").value(hasItem(DEFAULT_MIME_TYPE)));
    }

    @Test
    @Transactional
    void getDocument() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get the document
        restDocumentMockMvc
            .perform(get("/api/documents/{id}", document.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(document.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.size").value(DEFAULT_SIZE.intValue()))
            .andExpect(jsonPath("$.mimeType").value(DEFAULT_MIME_TYPE));
    }

    @Test
    @Transactional
    void getDownloadDocument() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get the document
        restDocumentMockMvc
            .perform(get("/api/documents/{id}/$content", document.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.parseMediaType(document.getMimeType())));
    }

    @Test
    @Transactional
    void getDocumentsByIdFiltering() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        Long id = document.getId();

        defaultDocumentShouldBeFound("id.equals=" + id);
        defaultDocumentShouldNotBeFound("id.notEquals=" + id);

        defaultDocumentShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDocumentShouldNotBeFound("id.greaterThan=" + id);

        defaultDocumentShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDocumentShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDocumentsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where title equals to DEFAULT_TITLE
        defaultDocumentShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the documentList where title equals to UPDATED_TITLE
        defaultDocumentShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllDocumentsByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where title not equals to DEFAULT_TITLE
        defaultDocumentShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the documentList where title not equals to UPDATED_TITLE
        defaultDocumentShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllDocumentsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultDocumentShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the documentList where title equals to UPDATED_TITLE
        defaultDocumentShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllDocumentsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where title is not null
        defaultDocumentShouldBeFound("title.specified=true");

        // Get all the documentList where title is null
        defaultDocumentShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllDocumentsByTitleContainsSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where title contains DEFAULT_TITLE
        defaultDocumentShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the documentList where title contains UPDATED_TITLE
        defaultDocumentShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllDocumentsByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where title does not contain DEFAULT_TITLE
        defaultDocumentShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the documentList where title does not contain UPDATED_TITLE
        defaultDocumentShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllDocumentsBySizeIsEqualToSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where size equals to DEFAULT_SIZE
        defaultDocumentShouldBeFound("size.equals=" + DEFAULT_SIZE);

        // Get all the documentList where size equals to UPDATED_SIZE
        defaultDocumentShouldNotBeFound("size.equals=" + UPDATED_SIZE);
    }

    @Test
    @Transactional
    void getAllDocumentsBySizeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where size not equals to DEFAULT_SIZE
        defaultDocumentShouldNotBeFound("size.notEquals=" + DEFAULT_SIZE);

        // Get all the documentList where size not equals to UPDATED_SIZE
        defaultDocumentShouldBeFound("size.notEquals=" + UPDATED_SIZE);
    }

    @Test
    @Transactional
    void getAllDocumentsBySizeIsInShouldWork() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where size in DEFAULT_SIZE or UPDATED_SIZE
        defaultDocumentShouldBeFound("size.in=" + DEFAULT_SIZE + "," + UPDATED_SIZE);

        // Get all the documentList where size equals to UPDATED_SIZE
        defaultDocumentShouldNotBeFound("size.in=" + UPDATED_SIZE);
    }

    @Test
    @Transactional
    void getAllDocumentsBySizeIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where size is not null
        defaultDocumentShouldBeFound("size.specified=true");

        // Get all the documentList where size is null
        defaultDocumentShouldNotBeFound("size.specified=false");
    }

    @Test
    @Transactional
    void getAllDocumentsBySizeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where size is greater than or equal to DEFAULT_SIZE
        defaultDocumentShouldBeFound("size.greaterThanOrEqual=" + DEFAULT_SIZE);

        // Get all the documentList where size is greater than or equal to UPDATED_SIZE
        defaultDocumentShouldNotBeFound("size.greaterThanOrEqual=" + UPDATED_SIZE);
    }

    @Test
    @Transactional
    void getAllDocumentsBySizeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where size is less than or equal to DEFAULT_SIZE
        defaultDocumentShouldBeFound("size.lessThanOrEqual=" + DEFAULT_SIZE);

        // Get all the documentList where size is less than or equal to SMALLER_SIZE
        defaultDocumentShouldNotBeFound("size.lessThanOrEqual=" + SMALLER_SIZE);
    }

    @Test
    @Transactional
    void getAllDocumentsBySizeIsLessThanSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where size is less than DEFAULT_SIZE
        defaultDocumentShouldNotBeFound("size.lessThan=" + DEFAULT_SIZE);

        // Get all the documentList where size is less than UPDATED_SIZE
        defaultDocumentShouldBeFound("size.lessThan=" + UPDATED_SIZE);
    }

    @Test
    @Transactional
    void getAllDocumentsBySizeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where size is greater than DEFAULT_SIZE
        defaultDocumentShouldNotBeFound("size.greaterThan=" + DEFAULT_SIZE);

        // Get all the documentList where size is greater than SMALLER_SIZE
        defaultDocumentShouldBeFound("size.greaterThan=" + SMALLER_SIZE);
    }

    @Test
    @Transactional
    void getAllDocumentsByContentIsEqualToSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);
        Content content = ContentResourceIT.createEntity(em);
        em.persist(content);
        em.flush();
        document.setContent(content);
        documentRepository.saveAndFlush(document);
        Long contentId = content.getId();

        // Get all the documentList where content equals to contentId
        defaultDocumentShouldBeFound("contentId.equals=" + contentId);

        // Get all the documentList where content equals to contentId + 1
        defaultDocumentShouldNotBeFound("contentId.equals=" + (contentId + 1));
    }

    @Test
    @Transactional
    void getAllDocumentsByOutcomeIsEqualToSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);
        Outcome outcome = OutcomeResourceIT.createEntity(em);
        em.persist(outcome);
        em.flush();
        document.setOutcome(outcome);
        documentRepository.saveAndFlush(document);
        Long outcomeId = outcome.getId();

        // Get all the documentList where outcome equals to outcomeId
        defaultDocumentShouldBeFound("outcomeId.equals=" + outcomeId);

        // Get all the documentList where outcome equals to outcomeId + 1
        defaultDocumentShouldNotBeFound("outcomeId.equals=" + (outcomeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDocumentShouldBeFound(String filter) throws Exception {
        restDocumentMockMvc
            .perform(get("/api/documents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(document.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].size").value(hasItem(DEFAULT_SIZE.intValue())))
            .andExpect(jsonPath("$.[*].mimeType").value(hasItem(DEFAULT_MIME_TYPE)));

        // Check, that the count call also returns 1
        restDocumentMockMvc
            .perform(get("/api/documents/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDocumentShouldNotBeFound(String filter) throws Exception {
        restDocumentMockMvc
            .perform(get("/api/documents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDocumentMockMvc
            .perform(get("/api/documents/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDocument() throws Exception {
        // Get the document
        restDocumentMockMvc.perform(get("/api/documents/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void updateNonExistingDocument() throws Exception {
        int databaseSizeBeforeUpdate = documentRepository.findAll().size();

        // Create the Document
        DocumentDTO documentDTO = documentMapper.toDto(document);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentMockMvc
            .perform(put("/api/documents").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(documentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDocumentWithPatch() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        int databaseSizeBeforeUpdate = documentRepository.findAll().size();

        // Update the document using partial update
        Document partialUpdatedDocument = new Document();
        partialUpdatedDocument.setId(document.getId());

        partialUpdatedDocument.size(UPDATED_SIZE).mimeType(UPDATED_MIME_TYPE);

        restDocumentMockMvc
            .perform(
                patch("/api/documents")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDocument))
            )
            .andExpect(status().isOk());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeUpdate);
        Document testDocument = documentList.get(documentList.size() - 1);
        assertThat(testDocument.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testDocument.getSize()).isEqualTo(UPDATED_SIZE);
        assertThat(testDocument.getMimeType()).isEqualTo(UPDATED_MIME_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateDocumentWithPatch() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        int databaseSizeBeforeUpdate = documentRepository.findAll().size();

        // Update the document using partial update
        Document partialUpdatedDocument = new Document();
        partialUpdatedDocument.setId(document.getId());

        partialUpdatedDocument.title(UPDATED_TITLE).size(UPDATED_SIZE).mimeType(UPDATED_MIME_TYPE);

        restDocumentMockMvc
            .perform(
                patch("/api/documents")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDocument))
            )
            .andExpect(status().isOk());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeUpdate);
        Document testDocument = documentList.get(documentList.size() - 1);
        assertThat(testDocument.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testDocument.getSize()).isEqualTo(UPDATED_SIZE);
        assertThat(testDocument.getMimeType()).isEqualTo(UPDATED_MIME_TYPE);
    }

    @Test
    @Transactional
    void partialUpdateDocumentShouldThrown() throws Exception {
        // Update the document without id should throw
        Document partialUpdatedDocument = new Document();

        restDocumentMockMvc
            .perform(
                patch("/api/documents")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDocument))
            )
            .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void deleteDocument() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        int databaseSizeBeforeDelete = documentRepository.findAll().size();

        // Delete the document
        restDocumentMockMvc
            .perform(delete("/api/documents/{id}", document.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
