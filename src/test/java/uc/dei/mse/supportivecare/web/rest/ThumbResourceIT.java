package uc.dei.mse.supportivecare.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
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
import uc.dei.mse.supportivecare.domain.Thumb;
import uc.dei.mse.supportivecare.domain.enumeration.EntityFeedback;
import uc.dei.mse.supportivecare.repository.ThumbRepository;
import uc.dei.mse.supportivecare.service.ThumbQueryService;
import uc.dei.mse.supportivecare.service.dto.ThumbDTO;
import uc.dei.mse.supportivecare.service.mapper.ThumbMapper;

/**
 * Integration tests for the {@link ThumbResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ThumbResourceIT {

    private static final EntityFeedback DEFAULT_ENTITY_TYPE = EntityFeedback.ACTIVE_SUBSTANCE;
    private static final EntityFeedback UPDATED_ENTITY_TYPE = EntityFeedback.THERAPEUTIC_REGIME;

    private static final Long DEFAULT_ENTITY_ID = 1L;
    private static final Long UPDATED_ENTITY_ID = 2L;
    private static final Long SMALLER_ENTITY_ID = 1L - 1L;

    private static final Boolean DEFAULT_THUMB = false;
    private static final Boolean UPDATED_THUMB = true;

    @Autowired
    private ThumbRepository thumbRepository;

    @Autowired
    private ThumbMapper thumbMapper;

    @Autowired
    private ThumbQueryService thumbQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restThumbMockMvc;

    private Thumb thumb;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Thumb createEntity(EntityManager em) {
        Thumb thumb = new Thumb().entityType(DEFAULT_ENTITY_TYPE).entityId(DEFAULT_ENTITY_ID).thumb(DEFAULT_THUMB);
        return thumb;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Thumb createUpdatedEntity(EntityManager em) {
        Thumb thumb = new Thumb().entityType(UPDATED_ENTITY_TYPE).entityId(UPDATED_ENTITY_ID).thumb(UPDATED_THUMB);
        return thumb;
    }

    @BeforeEach
    public void initTest() {
        thumb = createEntity(em);
    }

    @Test
    @Transactional
    void createThumb() throws Exception {
        int databaseSizeBeforeCreate = thumbRepository.findAll().size();
        // Create the Thumb
        ThumbDTO thumbDTO = thumbMapper.toDto(thumb);
        restThumbMockMvc
            .perform(post("/api/thumbs").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(thumbDTO)))
            .andExpect(status().isCreated());

        // Validate the Thumb in the database
        List<Thumb> thumbList = thumbRepository.findAll();
        assertThat(thumbList).hasSize(databaseSizeBeforeCreate + 1);
        Thumb testThumb = thumbList.get(thumbList.size() - 1);
        assertThat(testThumb.getEntityType()).isEqualTo(DEFAULT_ENTITY_TYPE);
        assertThat(testThumb.getEntityId()).isEqualTo(DEFAULT_ENTITY_ID);
        assertThat(testThumb.getThumb()).isEqualTo(DEFAULT_THUMB);
    }

    @Test
    @Transactional
    void createThumbWithExistingId() throws Exception {
        // Create the Thumb with an existing ID
        thumb.setId(1L);
        ThumbDTO thumbDTO = thumbMapper.toDto(thumb);

        int databaseSizeBeforeCreate = thumbRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restThumbMockMvc
            .perform(post("/api/thumbs").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(thumbDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Thumb in the database
        List<Thumb> thumbList = thumbRepository.findAll();
        assertThat(thumbList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEntityTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = thumbRepository.findAll().size();
        // set the field null
        thumb.setEntityType(null);

        // Create the Thumb, which fails.
        ThumbDTO thumbDTO = thumbMapper.toDto(thumb);

        restThumbMockMvc
            .perform(post("/api/thumbs").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(thumbDTO)))
            .andExpect(status().isBadRequest());

        List<Thumb> thumbList = thumbRepository.findAll();
        assertThat(thumbList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEntityIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = thumbRepository.findAll().size();
        // set the field null
        thumb.setEntityId(null);

        // Create the Thumb, which fails.
        ThumbDTO thumbDTO = thumbMapper.toDto(thumb);

        restThumbMockMvc
            .perform(post("/api/thumbs").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(thumbDTO)))
            .andExpect(status().isBadRequest());

        List<Thumb> thumbList = thumbRepository.findAll();
        assertThat(thumbList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllThumbs() throws Exception {
        // Initialize the database
        thumbRepository.saveAndFlush(thumb);

        // Get all the thumbList
        restThumbMockMvc
            .perform(get("/api/thumbs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(thumb.getId().intValue())))
            .andExpect(jsonPath("$.[*].entityType").value(hasItem(DEFAULT_ENTITY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].entityId").value(hasItem(DEFAULT_ENTITY_ID.intValue())))
            .andExpect(jsonPath("$.[*].thumb").value(hasItem(DEFAULT_THUMB.booleanValue())));
    }

    @Test
    @Transactional
    void getThumb() throws Exception {
        // Initialize the database
        thumbRepository.saveAndFlush(thumb);

        // Get the thumb
        restThumbMockMvc
            .perform(get("/api/thumbs/{id}", thumb.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(thumb.getId().intValue()))
            .andExpect(jsonPath("$.entityType").value(DEFAULT_ENTITY_TYPE.toString()))
            .andExpect(jsonPath("$.entityId").value(DEFAULT_ENTITY_ID.intValue()))
            .andExpect(jsonPath("$.thumb").value(DEFAULT_THUMB.booleanValue()));
    }

    @Test
    @Transactional
    void getThumbsByIdFiltering() throws Exception {
        // Initialize the database
        thumbRepository.saveAndFlush(thumb);

        Long id = thumb.getId();

        defaultThumbShouldBeFound("id.equals=" + id);
        defaultThumbShouldNotBeFound("id.notEquals=" + id);

        defaultThumbShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultThumbShouldNotBeFound("id.greaterThan=" + id);

        defaultThumbShouldBeFound("id.lessThanOrEqual=" + id);
        defaultThumbShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllThumbsByEntityTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        thumbRepository.saveAndFlush(thumb);

        // Get all the thumbList where entityType equals to DEFAULT_ENTITY_TYPE
        defaultThumbShouldBeFound("entityType.equals=" + DEFAULT_ENTITY_TYPE);

        // Get all the thumbList where entityType equals to UPDATED_ENTITY_TYPE
        defaultThumbShouldNotBeFound("entityType.equals=" + UPDATED_ENTITY_TYPE);
    }

    @Test
    @Transactional
    void getAllThumbsByEntityTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        thumbRepository.saveAndFlush(thumb);

        // Get all the thumbList where entityType not equals to DEFAULT_ENTITY_TYPE
        defaultThumbShouldNotBeFound("entityType.notEquals=" + DEFAULT_ENTITY_TYPE);

        // Get all the thumbList where entityType not equals to UPDATED_ENTITY_TYPE
        defaultThumbShouldBeFound("entityType.notEquals=" + UPDATED_ENTITY_TYPE);
    }

    @Test
    @Transactional
    void getAllThumbsByEntityTypeIsInShouldWork() throws Exception {
        // Initialize the database
        thumbRepository.saveAndFlush(thumb);

        // Get all the thumbList where entityType in DEFAULT_ENTITY_TYPE or UPDATED_ENTITY_TYPE
        defaultThumbShouldBeFound("entityType.in=" + DEFAULT_ENTITY_TYPE + "," + UPDATED_ENTITY_TYPE);

        // Get all the thumbList where entityType equals to UPDATED_ENTITY_TYPE
        defaultThumbShouldNotBeFound("entityType.in=" + UPDATED_ENTITY_TYPE);
    }

    @Test
    @Transactional
    void getAllThumbsByEntityTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        thumbRepository.saveAndFlush(thumb);

        // Get all the thumbList where entityType is not null
        defaultThumbShouldBeFound("entityType.specified=true");

        // Get all the thumbList where entityType is null
        defaultThumbShouldNotBeFound("entityType.specified=false");
    }

    @Test
    @Transactional
    void getAllThumbsByEntityIdIsEqualToSomething() throws Exception {
        // Initialize the database
        thumbRepository.saveAndFlush(thumb);

        // Get all the thumbList where entityId equals to DEFAULT_ENTITY_ID
        defaultThumbShouldBeFound("entityId.equals=" + DEFAULT_ENTITY_ID);

        // Get all the thumbList where entityId equals to UPDATED_ENTITY_ID
        defaultThumbShouldNotBeFound("entityId.equals=" + UPDATED_ENTITY_ID);
    }

    @Test
    @Transactional
    void getAllThumbsByEntityIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        thumbRepository.saveAndFlush(thumb);

        // Get all the thumbList where entityId not equals to DEFAULT_ENTITY_ID
        defaultThumbShouldNotBeFound("entityId.notEquals=" + DEFAULT_ENTITY_ID);

        // Get all the thumbList where entityId not equals to UPDATED_ENTITY_ID
        defaultThumbShouldBeFound("entityId.notEquals=" + UPDATED_ENTITY_ID);
    }

    @Test
    @Transactional
    void getAllThumbsByEntityIdIsInShouldWork() throws Exception {
        // Initialize the database
        thumbRepository.saveAndFlush(thumb);

        // Get all the thumbList where entityId in DEFAULT_ENTITY_ID or UPDATED_ENTITY_ID
        defaultThumbShouldBeFound("entityId.in=" + DEFAULT_ENTITY_ID + "," + UPDATED_ENTITY_ID);

        // Get all the thumbList where entityId equals to UPDATED_ENTITY_ID
        defaultThumbShouldNotBeFound("entityId.in=" + UPDATED_ENTITY_ID);
    }

    @Test
    @Transactional
    void getAllThumbsByEntityIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        thumbRepository.saveAndFlush(thumb);

        // Get all the thumbList where entityId is not null
        defaultThumbShouldBeFound("entityId.specified=true");

        // Get all the thumbList where entityId is null
        defaultThumbShouldNotBeFound("entityId.specified=false");
    }

    @Test
    @Transactional
    void getAllThumbsByEntityIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        thumbRepository.saveAndFlush(thumb);

        // Get all the thumbList where entityId is greater than or equal to DEFAULT_ENTITY_ID
        defaultThumbShouldBeFound("entityId.greaterThanOrEqual=" + DEFAULT_ENTITY_ID);

        // Get all the thumbList where entityId is greater than or equal to UPDATED_ENTITY_ID
        defaultThumbShouldNotBeFound("entityId.greaterThanOrEqual=" + UPDATED_ENTITY_ID);
    }

    @Test
    @Transactional
    void getAllThumbsByEntityIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        thumbRepository.saveAndFlush(thumb);

        // Get all the thumbList where entityId is less than or equal to DEFAULT_ENTITY_ID
        defaultThumbShouldBeFound("entityId.lessThanOrEqual=" + DEFAULT_ENTITY_ID);

        // Get all the thumbList where entityId is less than or equal to SMALLER_ENTITY_ID
        defaultThumbShouldNotBeFound("entityId.lessThanOrEqual=" + SMALLER_ENTITY_ID);
    }

    @Test
    @Transactional
    void getAllThumbsByEntityIdIsLessThanSomething() throws Exception {
        // Initialize the database
        thumbRepository.saveAndFlush(thumb);

        // Get all the thumbList where entityId is less than DEFAULT_ENTITY_ID
        defaultThumbShouldNotBeFound("entityId.lessThan=" + DEFAULT_ENTITY_ID);

        // Get all the thumbList where entityId is less than UPDATED_ENTITY_ID
        defaultThumbShouldBeFound("entityId.lessThan=" + UPDATED_ENTITY_ID);
    }

    @Test
    @Transactional
    void getAllThumbsByEntityIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        thumbRepository.saveAndFlush(thumb);

        // Get all the thumbList where entityId is greater than DEFAULT_ENTITY_ID
        defaultThumbShouldNotBeFound("entityId.greaterThan=" + DEFAULT_ENTITY_ID);

        // Get all the thumbList where entityId is greater than SMALLER_ENTITY_ID
        defaultThumbShouldBeFound("entityId.greaterThan=" + SMALLER_ENTITY_ID);
    }

    @Test
    @Transactional
    void getAllThumbsByThumbIsEqualToSomething() throws Exception {
        // Initialize the database
        thumbRepository.saveAndFlush(thumb);

        // Get all the thumbList where thumb equals to DEFAULT_THUMB
        defaultThumbShouldBeFound("thumb.equals=" + DEFAULT_THUMB);

        // Get all the thumbList where thumb equals to UPDATED_THUMB
        defaultThumbShouldNotBeFound("thumb.equals=" + UPDATED_THUMB);
    }

    @Test
    @Transactional
    void getAllThumbsByThumbIsNotEqualToSomething() throws Exception {
        // Initialize the database
        thumbRepository.saveAndFlush(thumb);

        // Get all the thumbList where thumb not equals to DEFAULT_THUMB
        defaultThumbShouldNotBeFound("thumb.notEquals=" + DEFAULT_THUMB);

        // Get all the thumbList where thumb not equals to UPDATED_THUMB
        defaultThumbShouldBeFound("thumb.notEquals=" + UPDATED_THUMB);
    }

    @Test
    @Transactional
    void getAllThumbsByThumbIsInShouldWork() throws Exception {
        // Initialize the database
        thumbRepository.saveAndFlush(thumb);

        // Get all the thumbList where thumb in DEFAULT_THUMB or UPDATED_THUMB
        defaultThumbShouldBeFound("thumb.in=" + DEFAULT_THUMB + "," + UPDATED_THUMB);

        // Get all the thumbList where thumb equals to UPDATED_THUMB
        defaultThumbShouldNotBeFound("thumb.in=" + UPDATED_THUMB);
    }

    @Test
    @Transactional
    void getAllThumbsByThumbIsNullOrNotNull() throws Exception {
        // Initialize the database
        thumbRepository.saveAndFlush(thumb);

        // Get all the thumbList where thumb is not null
        defaultThumbShouldBeFound("thumb.specified=true");

        // Get all the thumbList where thumb is null
        defaultThumbShouldNotBeFound("thumb.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultThumbShouldBeFound(String filter) throws Exception {
        restThumbMockMvc
            .perform(get("/api/thumbs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(thumb.getId().intValue())))
            .andExpect(jsonPath("$.[*].entityType").value(hasItem(DEFAULT_ENTITY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].entityId").value(hasItem(DEFAULT_ENTITY_ID.intValue())))
            .andExpect(jsonPath("$.[*].thumb").value(hasItem(DEFAULT_THUMB.booleanValue())));

        // Check, that the count call also returns 1
        restThumbMockMvc
            .perform(get("/api/thumbs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultThumbShouldNotBeFound(String filter) throws Exception {
        restThumbMockMvc
            .perform(get("/api/thumbs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restThumbMockMvc
            .perform(get("/api/thumbs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingThumb() throws Exception {
        // Get the thumb
        restThumbMockMvc.perform(get("/api/thumbs/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void updateThumb() throws Exception {
        // Initialize the database
        thumbRepository.saveAndFlush(thumb);

        int databaseSizeBeforeUpdate = thumbRepository.findAll().size();

        // Update the thumb
        Thumb updatedThumb = thumbRepository.findById(thumb.getId()).get();
        // Disconnect from session so that the updates on updatedThumb are not directly saved in db
        em.detach(updatedThumb);
        updatedThumb.entityType(UPDATED_ENTITY_TYPE).entityId(UPDATED_ENTITY_ID).thumb(UPDATED_THUMB);
        ThumbDTO thumbDTO = thumbMapper.toDto(updatedThumb);

        restThumbMockMvc
            .perform(put("/api/thumbs").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(thumbDTO)))
            .andExpect(status().isOk());

        // Validate the Thumb in the database
        List<Thumb> thumbList = thumbRepository.findAll();
        assertThat(thumbList).hasSize(databaseSizeBeforeUpdate);
        Thumb testThumb = thumbList.get(thumbList.size() - 1);
        assertThat(testThumb.getEntityType()).isEqualTo(UPDATED_ENTITY_TYPE);
        assertThat(testThumb.getEntityId()).isEqualTo(UPDATED_ENTITY_ID);
        assertThat(testThumb.getThumb()).isEqualTo(UPDATED_THUMB);
    }

    @Test
    @Transactional
    void updateNonExistingThumb() throws Exception {
        int databaseSizeBeforeUpdate = thumbRepository.findAll().size();

        // Create the Thumb
        ThumbDTO thumbDTO = thumbMapper.toDto(thumb);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restThumbMockMvc
            .perform(put("/api/thumbs").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(thumbDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Thumb in the database
        List<Thumb> thumbList = thumbRepository.findAll();
        assertThat(thumbList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateThumbWithPatch() throws Exception {
        // Initialize the database
        thumbRepository.saveAndFlush(thumb);

        int databaseSizeBeforeUpdate = thumbRepository.findAll().size();

        // Update the thumb using partial update
        Thumb partialUpdatedThumb = new Thumb();
        partialUpdatedThumb.setId(thumb.getId());

        restThumbMockMvc
            .perform(
                patch("/api/thumbs")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedThumb))
            )
            .andExpect(status().isOk());

        // Validate the Thumb in the database
        List<Thumb> thumbList = thumbRepository.findAll();
        assertThat(thumbList).hasSize(databaseSizeBeforeUpdate);
        Thumb testThumb = thumbList.get(thumbList.size() - 1);
        assertThat(testThumb.getEntityType()).isEqualTo(DEFAULT_ENTITY_TYPE);
        assertThat(testThumb.getEntityId()).isEqualTo(DEFAULT_ENTITY_ID);
        assertThat(testThumb.getThumb()).isEqualTo(DEFAULT_THUMB);
    }

    @Test
    @Transactional
    void fullUpdateThumbWithPatch() throws Exception {
        // Initialize the database
        thumbRepository.saveAndFlush(thumb);

        int databaseSizeBeforeUpdate = thumbRepository.findAll().size();

        // Update the thumb using partial update
        Thumb partialUpdatedThumb = new Thumb();
        partialUpdatedThumb.setId(thumb.getId());

        partialUpdatedThumb.entityType(UPDATED_ENTITY_TYPE).entityId(UPDATED_ENTITY_ID).thumb(UPDATED_THUMB);

        restThumbMockMvc
            .perform(
                patch("/api/thumbs")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedThumb))
            )
            .andExpect(status().isOk());

        // Validate the Thumb in the database
        List<Thumb> thumbList = thumbRepository.findAll();
        assertThat(thumbList).hasSize(databaseSizeBeforeUpdate);
        Thumb testThumb = thumbList.get(thumbList.size() - 1);
        assertThat(testThumb.getEntityType()).isEqualTo(UPDATED_ENTITY_TYPE);
        assertThat(testThumb.getEntityId()).isEqualTo(UPDATED_ENTITY_ID);
        assertThat(testThumb.getThumb()).isEqualTo(UPDATED_THUMB);
    }

    @Test
    @Transactional
    void partialUpdateThumbShouldThrown() throws Exception {
        // Update the thumb without id should throw
        Thumb partialUpdatedThumb = new Thumb();

        restThumbMockMvc
            .perform(
                patch("/api/thumbs")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedThumb))
            )
            .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void deleteThumb() throws Exception {
        // Initialize the database
        thumbRepository.saveAndFlush(thumb);

        int databaseSizeBeforeDelete = thumbRepository.findAll().size();

        // Delete the thumb
        restThumbMockMvc
            .perform(delete("/api/thumbs/{id}", thumb.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Thumb> thumbList = thumbRepository.findAll();
        assertThat(thumbList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    @WithMockUser(username = Constants.SYSTEM)
    void countThumbsFromEntity() throws Exception {
        // Initialize the database
        thumbRepository.saveAndFlush(thumb);

        restThumbMockMvc
            .perform(
                get("/api/thumbs/{entityType}/{entityId}/count", thumb.getEntityType().getValue(), thumb.getEntityId())
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.countThumbUp", is(0)))
            .andExpect(jsonPath("$.countThumbDown", is(1)))
            .andExpect(jsonPath("$.thumb", is(DEFAULT_THUMB.booleanValue())));
    }

    @Test
    @Transactional
    void manageThumbFromEntity_create() throws Exception {
        int databaseSizeBeforeCreate = thumbRepository.findAll().size();
        // Create the Thumb
        ThumbDTO thumbDTO = thumbMapper.toDto(thumb);
        restThumbMockMvc
            .perform(
                post("/api/thumbs/{entityType}/{entityId}", thumb.getEntityType().getValue(), thumb.getEntityId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(thumbDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Thumb in the database
        List<Thumb> thumbList = thumbRepository.findAll();
        assertThat(thumbList).hasSize(databaseSizeBeforeCreate + 1);
        Thumb testThumb = thumbList.get(thumbList.size() - 1);
        assertThat(testThumb.getEntityType()).isEqualTo(DEFAULT_ENTITY_TYPE);
        assertThat(testThumb.getEntityId()).isEqualTo(DEFAULT_ENTITY_ID);
        assertThat(testThumb.getThumb()).isEqualTo(DEFAULT_THUMB);
    }

    @Test
    @Transactional
    @WithMockUser(username = Constants.SYSTEM)
    void manageThumbFromEntity_update() throws Exception {
        // Initialize the database
        thumbRepository.saveAndFlush(thumb);

        int databaseSizeBeforeUpdate = thumbRepository.findAll().size();

        // Update the thumb
        Thumb updatedThumb = thumbRepository.findById(thumb.getId()).get();
        // Disconnect from session so that the updates on updatedThumb are not directly saved in db
        em.detach(updatedThumb);
        updatedThumb.thumb(UPDATED_THUMB);
        ThumbDTO thumbDTO = thumbMapper.toDto(updatedThumb);

        restThumbMockMvc
            .perform(
                post("/api/thumbs/{entityType}/{entityId}", thumb.getEntityType().getValue(), thumb.getEntityId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(thumbDTO))
            )
            .andExpect(status().isOk());

        // Validate the Thumb in the database
        List<Thumb> thumbList = thumbRepository.findAll();
        assertThat(thumbList).hasSize(databaseSizeBeforeUpdate);
        Thumb testThumb = thumbList.get(thumbList.size() - 1);
        assertThat(testThumb.getEntityType()).isEqualTo(DEFAULT_ENTITY_TYPE);
        assertThat(testThumb.getEntityId()).isEqualTo(DEFAULT_ENTITY_ID);
        assertThat(testThumb.getThumb()).isEqualTo(UPDATED_THUMB);
    }

    @Test
    @Transactional
    @WithMockUser(username = Constants.SYSTEM)
    void manageThumbFromEntity_delete() throws Exception {
        // Initialize the database
        thumbRepository.saveAndFlush(thumb);

        int databaseSizeBeforeDelete = thumbRepository.findAll().size();

        // Update the thumb
        Thumb updatedThumb = thumbRepository.findById(thumb.getId()).get();
        // Disconnect from session so that the updates on updatedThumb are not directly saved in db
        em.detach(updatedThumb);
        updatedThumb.thumb(null);
        ThumbDTO thumbDTO = thumbMapper.toDto(updatedThumb);

        restThumbMockMvc
            .perform(
                post("/api/thumbs/{entityType}/{entityId}", thumb.getEntityType().getValue(), thumb.getEntityId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(thumbDTO))
            )
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Thumb> thumbList = thumbRepository.findAll();
        assertThat(thumbList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    void manageThumbFromEntity_wrongEntityName() throws Exception {
        int databaseSizeBeforeCreate = thumbRepository.findAll().size();
        // Create the Thumb
        ThumbDTO thumbDTO = thumbMapper.toDto(thumb);
        restThumbMockMvc
            .perform(
                post("/api/thumbs/{entityType}/{entityId}", "test", thumb.getEntityId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(thumbDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Feedback in the database
        List<Thumb> thumbList = thumbRepository.findAll();
        assertThat(thumbList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void manageThumbFromEntity_wrongEntityId() throws Exception {
        int databaseSizeBeforeCreate = thumbRepository.findAll().size();
        // Create the Thumb
        ThumbDTO thumbDTO = thumbMapper.toDto(thumb);
        restThumbMockMvc
            .perform(
                post("/api/thumbs/{entityType}/{entityId}", thumb.getEntityType().getValue(), 456)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(thumbDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Feedback in the database
        List<Thumb> thumbList = thumbRepository.findAll();
        assertThat(thumbList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    @WithMockUser(username = Constants.SYSTEM)
    void manageThumbFromEntity_wrongUser() throws Exception {
        // Initialize the database
        thumbRepository.saveAndFlush(thumb);

        int databaseSizeBeforeUpdate = thumbRepository.findAll().size();

        // Update the thumb
        Thumb updatedThumb = thumbRepository.findById(thumb.getId()).get();
        // Disconnect from session so that the updates on updatedThumb are not directly saved in db
        em.detach(updatedThumb);
        updatedThumb.thumb(UPDATED_THUMB).setCreatedBy("user");
        ThumbDTO thumbDTO = thumbMapper.toDto(updatedThumb);

        restThumbMockMvc
            .perform(
                post("/api/thumbs/{entityType}/{entityId}", thumb.getEntityType().getValue(), thumb.getEntityId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(thumbDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Thumb in the database
        List<Thumb> thumbList = thumbRepository.findAll();
        assertThat(thumbList).hasSize(databaseSizeBeforeUpdate);
    }
}
