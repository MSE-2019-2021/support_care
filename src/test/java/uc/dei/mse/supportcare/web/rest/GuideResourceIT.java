package uc.dei.mse.supportcare.web.rest;

import uc.dei.mse.supportcare.SupportcareApp;
import uc.dei.mse.supportcare.domain.Guide;
import uc.dei.mse.supportcare.repository.GuideRepository;
import uc.dei.mse.supportcare.service.GuideService;
import uc.dei.mse.supportcare.service.dto.GuideDTO;
import uc.dei.mse.supportcare.service.mapper.GuideMapper;
import uc.dei.mse.supportcare.service.dto.GuideCriteria;
import uc.dei.mse.supportcare.service.GuideQueryService;

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
 * Integration tests for the {@link GuideResource} REST controller.
 */
@SpringBootTest(classes = SupportcareApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class GuideResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CREATE_USER = "AAAAAAAAAA";
    private static final String UPDATED_CREATE_USER = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATE_USER = "AAAAAAAAAA";
    private static final String UPDATED_UPDATE_USER = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private GuideRepository guideRepository;

    @Autowired
    private GuideMapper guideMapper;

    @Autowired
    private GuideService guideService;

    @Autowired
    private GuideQueryService guideQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGuideMockMvc;

    private Guide guide;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Guide createEntity(EntityManager em) {
        Guide guide = new Guide()
            .description(DEFAULT_DESCRIPTION)
            .createUser(DEFAULT_CREATE_USER)
            .createDate(DEFAULT_CREATE_DATE)
            .updateUser(DEFAULT_UPDATE_USER)
            .updateDate(DEFAULT_UPDATE_DATE);
        return guide;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Guide createUpdatedEntity(EntityManager em) {
        Guide guide = new Guide()
            .description(UPDATED_DESCRIPTION)
            .createUser(UPDATED_CREATE_USER)
            .createDate(UPDATED_CREATE_DATE)
            .updateUser(UPDATED_UPDATE_USER)
            .updateDate(UPDATED_UPDATE_DATE);
        return guide;
    }

    @BeforeEach
    public void initTest() {
        guide = createEntity(em);
    }

    @Test
    @Transactional
    public void createGuide() throws Exception {
        int databaseSizeBeforeCreate = guideRepository.findAll().size();
        // Create the Guide
        GuideDTO guideDTO = guideMapper.toDto(guide);
        restGuideMockMvc.perform(post("/api/guides")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(guideDTO)))
            .andExpect(status().isCreated());

        // Validate the Guide in the database
        List<Guide> guideList = guideRepository.findAll();
        assertThat(guideList).hasSize(databaseSizeBeforeCreate + 1);
        Guide testGuide = guideList.get(guideList.size() - 1);
        assertThat(testGuide.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testGuide.getCreateUser()).isEqualTo(DEFAULT_CREATE_USER);
        assertThat(testGuide.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testGuide.getUpdateUser()).isEqualTo(DEFAULT_UPDATE_USER);
        assertThat(testGuide.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void createGuideWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = guideRepository.findAll().size();

        // Create the Guide with an existing ID
        guide.setId(1L);
        GuideDTO guideDTO = guideMapper.toDto(guide);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGuideMockMvc.perform(post("/api/guides")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(guideDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Guide in the database
        List<Guide> guideList = guideRepository.findAll();
        assertThat(guideList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = guideRepository.findAll().size();
        // set the field null
        guide.setDescription(null);

        // Create the Guide, which fails.
        GuideDTO guideDTO = guideMapper.toDto(guide);


        restGuideMockMvc.perform(post("/api/guides")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(guideDTO)))
            .andExpect(status().isBadRequest());

        List<Guide> guideList = guideRepository.findAll();
        assertThat(guideList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateUserIsRequired() throws Exception {
        int databaseSizeBeforeTest = guideRepository.findAll().size();
        // set the field null
        guide.setCreateUser(null);

        // Create the Guide, which fails.
        GuideDTO guideDTO = guideMapper.toDto(guide);


        restGuideMockMvc.perform(post("/api/guides")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(guideDTO)))
            .andExpect(status().isBadRequest());

        List<Guide> guideList = guideRepository.findAll();
        assertThat(guideList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = guideRepository.findAll().size();
        // set the field null
        guide.setCreateDate(null);

        // Create the Guide, which fails.
        GuideDTO guideDTO = guideMapper.toDto(guide);


        restGuideMockMvc.perform(post("/api/guides")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(guideDTO)))
            .andExpect(status().isBadRequest());

        List<Guide> guideList = guideRepository.findAll();
        assertThat(guideList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateUserIsRequired() throws Exception {
        int databaseSizeBeforeTest = guideRepository.findAll().size();
        // set the field null
        guide.setUpdateUser(null);

        // Create the Guide, which fails.
        GuideDTO guideDTO = guideMapper.toDto(guide);


        restGuideMockMvc.perform(post("/api/guides")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(guideDTO)))
            .andExpect(status().isBadRequest());

        List<Guide> guideList = guideRepository.findAll();
        assertThat(guideList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = guideRepository.findAll().size();
        // set the field null
        guide.setUpdateDate(null);

        // Create the Guide, which fails.
        GuideDTO guideDTO = guideMapper.toDto(guide);


        restGuideMockMvc.perform(post("/api/guides")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(guideDTO)))
            .andExpect(status().isBadRequest());

        List<Guide> guideList = guideRepository.findAll();
        assertThat(guideList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGuides() throws Exception {
        // Initialize the database
        guideRepository.saveAndFlush(guide);

        // Get all the guideList
        restGuideMockMvc.perform(get("/api/guides?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(guide.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].createUser").value(hasItem(DEFAULT_CREATE_USER)))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].updateUser").value(hasItem(DEFAULT_UPDATE_USER)))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getGuide() throws Exception {
        // Initialize the database
        guideRepository.saveAndFlush(guide);

        // Get the guide
        restGuideMockMvc.perform(get("/api/guides/{id}", guide.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(guide.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.createUser").value(DEFAULT_CREATE_USER))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.updateUser").value(DEFAULT_UPDATE_USER))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()));
    }


    @Test
    @Transactional
    public void getGuidesByIdFiltering() throws Exception {
        // Initialize the database
        guideRepository.saveAndFlush(guide);

        Long id = guide.getId();

        defaultGuideShouldBeFound("id.equals=" + id);
        defaultGuideShouldNotBeFound("id.notEquals=" + id);

        defaultGuideShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultGuideShouldNotBeFound("id.greaterThan=" + id);

        defaultGuideShouldBeFound("id.lessThanOrEqual=" + id);
        defaultGuideShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllGuidesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        guideRepository.saveAndFlush(guide);

        // Get all the guideList where description equals to DEFAULT_DESCRIPTION
        defaultGuideShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the guideList where description equals to UPDATED_DESCRIPTION
        defaultGuideShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllGuidesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        guideRepository.saveAndFlush(guide);

        // Get all the guideList where description not equals to DEFAULT_DESCRIPTION
        defaultGuideShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the guideList where description not equals to UPDATED_DESCRIPTION
        defaultGuideShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllGuidesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        guideRepository.saveAndFlush(guide);

        // Get all the guideList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultGuideShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the guideList where description equals to UPDATED_DESCRIPTION
        defaultGuideShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllGuidesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        guideRepository.saveAndFlush(guide);

        // Get all the guideList where description is not null
        defaultGuideShouldBeFound("description.specified=true");

        // Get all the guideList where description is null
        defaultGuideShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllGuidesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        guideRepository.saveAndFlush(guide);

        // Get all the guideList where description contains DEFAULT_DESCRIPTION
        defaultGuideShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the guideList where description contains UPDATED_DESCRIPTION
        defaultGuideShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllGuidesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        guideRepository.saveAndFlush(guide);

        // Get all the guideList where description does not contain DEFAULT_DESCRIPTION
        defaultGuideShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the guideList where description does not contain UPDATED_DESCRIPTION
        defaultGuideShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllGuidesByCreateUserIsEqualToSomething() throws Exception {
        // Initialize the database
        guideRepository.saveAndFlush(guide);

        // Get all the guideList where createUser equals to DEFAULT_CREATE_USER
        defaultGuideShouldBeFound("createUser.equals=" + DEFAULT_CREATE_USER);

        // Get all the guideList where createUser equals to UPDATED_CREATE_USER
        defaultGuideShouldNotBeFound("createUser.equals=" + UPDATED_CREATE_USER);
    }

    @Test
    @Transactional
    public void getAllGuidesByCreateUserIsNotEqualToSomething() throws Exception {
        // Initialize the database
        guideRepository.saveAndFlush(guide);

        // Get all the guideList where createUser not equals to DEFAULT_CREATE_USER
        defaultGuideShouldNotBeFound("createUser.notEquals=" + DEFAULT_CREATE_USER);

        // Get all the guideList where createUser not equals to UPDATED_CREATE_USER
        defaultGuideShouldBeFound("createUser.notEquals=" + UPDATED_CREATE_USER);
    }

    @Test
    @Transactional
    public void getAllGuidesByCreateUserIsInShouldWork() throws Exception {
        // Initialize the database
        guideRepository.saveAndFlush(guide);

        // Get all the guideList where createUser in DEFAULT_CREATE_USER or UPDATED_CREATE_USER
        defaultGuideShouldBeFound("createUser.in=" + DEFAULT_CREATE_USER + "," + UPDATED_CREATE_USER);

        // Get all the guideList where createUser equals to UPDATED_CREATE_USER
        defaultGuideShouldNotBeFound("createUser.in=" + UPDATED_CREATE_USER);
    }

    @Test
    @Transactional
    public void getAllGuidesByCreateUserIsNullOrNotNull() throws Exception {
        // Initialize the database
        guideRepository.saveAndFlush(guide);

        // Get all the guideList where createUser is not null
        defaultGuideShouldBeFound("createUser.specified=true");

        // Get all the guideList where createUser is null
        defaultGuideShouldNotBeFound("createUser.specified=false");
    }
                @Test
    @Transactional
    public void getAllGuidesByCreateUserContainsSomething() throws Exception {
        // Initialize the database
        guideRepository.saveAndFlush(guide);

        // Get all the guideList where createUser contains DEFAULT_CREATE_USER
        defaultGuideShouldBeFound("createUser.contains=" + DEFAULT_CREATE_USER);

        // Get all the guideList where createUser contains UPDATED_CREATE_USER
        defaultGuideShouldNotBeFound("createUser.contains=" + UPDATED_CREATE_USER);
    }

    @Test
    @Transactional
    public void getAllGuidesByCreateUserNotContainsSomething() throws Exception {
        // Initialize the database
        guideRepository.saveAndFlush(guide);

        // Get all the guideList where createUser does not contain DEFAULT_CREATE_USER
        defaultGuideShouldNotBeFound("createUser.doesNotContain=" + DEFAULT_CREATE_USER);

        // Get all the guideList where createUser does not contain UPDATED_CREATE_USER
        defaultGuideShouldBeFound("createUser.doesNotContain=" + UPDATED_CREATE_USER);
    }


    @Test
    @Transactional
    public void getAllGuidesByCreateDateIsEqualToSomething() throws Exception {
        // Initialize the database
        guideRepository.saveAndFlush(guide);

        // Get all the guideList where createDate equals to DEFAULT_CREATE_DATE
        defaultGuideShouldBeFound("createDate.equals=" + DEFAULT_CREATE_DATE);

        // Get all the guideList where createDate equals to UPDATED_CREATE_DATE
        defaultGuideShouldNotBeFound("createDate.equals=" + UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void getAllGuidesByCreateDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        guideRepository.saveAndFlush(guide);

        // Get all the guideList where createDate not equals to DEFAULT_CREATE_DATE
        defaultGuideShouldNotBeFound("createDate.notEquals=" + DEFAULT_CREATE_DATE);

        // Get all the guideList where createDate not equals to UPDATED_CREATE_DATE
        defaultGuideShouldBeFound("createDate.notEquals=" + UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void getAllGuidesByCreateDateIsInShouldWork() throws Exception {
        // Initialize the database
        guideRepository.saveAndFlush(guide);

        // Get all the guideList where createDate in DEFAULT_CREATE_DATE or UPDATED_CREATE_DATE
        defaultGuideShouldBeFound("createDate.in=" + DEFAULT_CREATE_DATE + "," + UPDATED_CREATE_DATE);

        // Get all the guideList where createDate equals to UPDATED_CREATE_DATE
        defaultGuideShouldNotBeFound("createDate.in=" + UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void getAllGuidesByCreateDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        guideRepository.saveAndFlush(guide);

        // Get all the guideList where createDate is not null
        defaultGuideShouldBeFound("createDate.specified=true");

        // Get all the guideList where createDate is null
        defaultGuideShouldNotBeFound("createDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllGuidesByUpdateUserIsEqualToSomething() throws Exception {
        // Initialize the database
        guideRepository.saveAndFlush(guide);

        // Get all the guideList where updateUser equals to DEFAULT_UPDATE_USER
        defaultGuideShouldBeFound("updateUser.equals=" + DEFAULT_UPDATE_USER);

        // Get all the guideList where updateUser equals to UPDATED_UPDATE_USER
        defaultGuideShouldNotBeFound("updateUser.equals=" + UPDATED_UPDATE_USER);
    }

    @Test
    @Transactional
    public void getAllGuidesByUpdateUserIsNotEqualToSomething() throws Exception {
        // Initialize the database
        guideRepository.saveAndFlush(guide);

        // Get all the guideList where updateUser not equals to DEFAULT_UPDATE_USER
        defaultGuideShouldNotBeFound("updateUser.notEquals=" + DEFAULT_UPDATE_USER);

        // Get all the guideList where updateUser not equals to UPDATED_UPDATE_USER
        defaultGuideShouldBeFound("updateUser.notEquals=" + UPDATED_UPDATE_USER);
    }

    @Test
    @Transactional
    public void getAllGuidesByUpdateUserIsInShouldWork() throws Exception {
        // Initialize the database
        guideRepository.saveAndFlush(guide);

        // Get all the guideList where updateUser in DEFAULT_UPDATE_USER or UPDATED_UPDATE_USER
        defaultGuideShouldBeFound("updateUser.in=" + DEFAULT_UPDATE_USER + "," + UPDATED_UPDATE_USER);

        // Get all the guideList where updateUser equals to UPDATED_UPDATE_USER
        defaultGuideShouldNotBeFound("updateUser.in=" + UPDATED_UPDATE_USER);
    }

    @Test
    @Transactional
    public void getAllGuidesByUpdateUserIsNullOrNotNull() throws Exception {
        // Initialize the database
        guideRepository.saveAndFlush(guide);

        // Get all the guideList where updateUser is not null
        defaultGuideShouldBeFound("updateUser.specified=true");

        // Get all the guideList where updateUser is null
        defaultGuideShouldNotBeFound("updateUser.specified=false");
    }
                @Test
    @Transactional
    public void getAllGuidesByUpdateUserContainsSomething() throws Exception {
        // Initialize the database
        guideRepository.saveAndFlush(guide);

        // Get all the guideList where updateUser contains DEFAULT_UPDATE_USER
        defaultGuideShouldBeFound("updateUser.contains=" + DEFAULT_UPDATE_USER);

        // Get all the guideList where updateUser contains UPDATED_UPDATE_USER
        defaultGuideShouldNotBeFound("updateUser.contains=" + UPDATED_UPDATE_USER);
    }

    @Test
    @Transactional
    public void getAllGuidesByUpdateUserNotContainsSomething() throws Exception {
        // Initialize the database
        guideRepository.saveAndFlush(guide);

        // Get all the guideList where updateUser does not contain DEFAULT_UPDATE_USER
        defaultGuideShouldNotBeFound("updateUser.doesNotContain=" + DEFAULT_UPDATE_USER);

        // Get all the guideList where updateUser does not contain UPDATED_UPDATE_USER
        defaultGuideShouldBeFound("updateUser.doesNotContain=" + UPDATED_UPDATE_USER);
    }


    @Test
    @Transactional
    public void getAllGuidesByUpdateDateIsEqualToSomething() throws Exception {
        // Initialize the database
        guideRepository.saveAndFlush(guide);

        // Get all the guideList where updateDate equals to DEFAULT_UPDATE_DATE
        defaultGuideShouldBeFound("updateDate.equals=" + DEFAULT_UPDATE_DATE);

        // Get all the guideList where updateDate equals to UPDATED_UPDATE_DATE
        defaultGuideShouldNotBeFound("updateDate.equals=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void getAllGuidesByUpdateDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        guideRepository.saveAndFlush(guide);

        // Get all the guideList where updateDate not equals to DEFAULT_UPDATE_DATE
        defaultGuideShouldNotBeFound("updateDate.notEquals=" + DEFAULT_UPDATE_DATE);

        // Get all the guideList where updateDate not equals to UPDATED_UPDATE_DATE
        defaultGuideShouldBeFound("updateDate.notEquals=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void getAllGuidesByUpdateDateIsInShouldWork() throws Exception {
        // Initialize the database
        guideRepository.saveAndFlush(guide);

        // Get all the guideList where updateDate in DEFAULT_UPDATE_DATE or UPDATED_UPDATE_DATE
        defaultGuideShouldBeFound("updateDate.in=" + DEFAULT_UPDATE_DATE + "," + UPDATED_UPDATE_DATE);

        // Get all the guideList where updateDate equals to UPDATED_UPDATE_DATE
        defaultGuideShouldNotBeFound("updateDate.in=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void getAllGuidesByUpdateDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        guideRepository.saveAndFlush(guide);

        // Get all the guideList where updateDate is not null
        defaultGuideShouldBeFound("updateDate.specified=true");

        // Get all the guideList where updateDate is null
        defaultGuideShouldNotBeFound("updateDate.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultGuideShouldBeFound(String filter) throws Exception {
        restGuideMockMvc.perform(get("/api/guides?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(guide.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].createUser").value(hasItem(DEFAULT_CREATE_USER)))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].updateUser").value(hasItem(DEFAULT_UPDATE_USER)))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())));

        // Check, that the count call also returns 1
        restGuideMockMvc.perform(get("/api/guides/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultGuideShouldNotBeFound(String filter) throws Exception {
        restGuideMockMvc.perform(get("/api/guides?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restGuideMockMvc.perform(get("/api/guides/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingGuide() throws Exception {
        // Get the guide
        restGuideMockMvc.perform(get("/api/guides/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGuide() throws Exception {
        // Initialize the database
        guideRepository.saveAndFlush(guide);

        int databaseSizeBeforeUpdate = guideRepository.findAll().size();

        // Update the guide
        Guide updatedGuide = guideRepository.findById(guide.getId()).get();
        // Disconnect from session so that the updates on updatedGuide are not directly saved in db
        em.detach(updatedGuide);
        updatedGuide
            .description(UPDATED_DESCRIPTION)
            .createUser(UPDATED_CREATE_USER)
            .createDate(UPDATED_CREATE_DATE)
            .updateUser(UPDATED_UPDATE_USER)
            .updateDate(UPDATED_UPDATE_DATE);
        GuideDTO guideDTO = guideMapper.toDto(updatedGuide);

        restGuideMockMvc.perform(put("/api/guides")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(guideDTO)))
            .andExpect(status().isOk());

        // Validate the Guide in the database
        List<Guide> guideList = guideRepository.findAll();
        assertThat(guideList).hasSize(databaseSizeBeforeUpdate);
        Guide testGuide = guideList.get(guideList.size() - 1);
        assertThat(testGuide.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testGuide.getCreateUser()).isEqualTo(UPDATED_CREATE_USER);
        assertThat(testGuide.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testGuide.getUpdateUser()).isEqualTo(UPDATED_UPDATE_USER);
        assertThat(testGuide.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingGuide() throws Exception {
        int databaseSizeBeforeUpdate = guideRepository.findAll().size();

        // Create the Guide
        GuideDTO guideDTO = guideMapper.toDto(guide);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGuideMockMvc.perform(put("/api/guides")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(guideDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Guide in the database
        List<Guide> guideList = guideRepository.findAll();
        assertThat(guideList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGuide() throws Exception {
        // Initialize the database
        guideRepository.saveAndFlush(guide);

        int databaseSizeBeforeDelete = guideRepository.findAll().size();

        // Delete the guide
        restGuideMockMvc.perform(delete("/api/guides/{id}", guide.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Guide> guideList = guideRepository.findAll();
        assertThat(guideList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
