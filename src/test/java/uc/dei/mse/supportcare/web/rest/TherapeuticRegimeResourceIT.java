package uc.dei.mse.supportcare.web.rest;

import uc.dei.mse.supportcare.SupportcareApp;
import uc.dei.mse.supportcare.domain.TherapeuticRegime;
import uc.dei.mse.supportcare.domain.Drug;
import uc.dei.mse.supportcare.repository.TherapeuticRegimeRepository;
import uc.dei.mse.supportcare.service.TherapeuticRegimeService;
import uc.dei.mse.supportcare.service.dto.TherapeuticRegimeDTO;
import uc.dei.mse.supportcare.service.mapper.TherapeuticRegimeMapper;
import uc.dei.mse.supportcare.service.dto.TherapeuticRegimeCriteria;
import uc.dei.mse.supportcare.service.TherapeuticRegimeQueryService;

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
 * Integration tests for the {@link TherapeuticRegimeResource} REST controller.
 */
@SpringBootTest(classes = SupportcareApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TherapeuticRegimeResourceIT {

    private static final String DEFAULT_TIMING = "AAAAAAAAAA";
    private static final String UPDATED_TIMING = "BBBBBBBBBB";

    private static final String DEFAULT_DIETARY = "AAAAAAAAAA";
    private static final String UPDATED_DIETARY = "BBBBBBBBBB";

    private static final String DEFAULT_SIDE_EFFECTS = "AAAAAAAAAA";
    private static final String UPDATED_SIDE_EFFECTS = "BBBBBBBBBB";

    private static final String DEFAULT_CREATE_USER = "AAAAAAAAAA";
    private static final String UPDATED_CREATE_USER = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATE_USER = "AAAAAAAAAA";
    private static final String UPDATED_UPDATE_USER = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

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
            .timing(DEFAULT_TIMING)
            .dietary(DEFAULT_DIETARY)
            .sideEffects(DEFAULT_SIDE_EFFECTS)
            .createUser(DEFAULT_CREATE_USER)
            .createDate(DEFAULT_CREATE_DATE)
            .updateUser(DEFAULT_UPDATE_USER)
            .updateDate(DEFAULT_UPDATE_DATE);
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
            .timing(UPDATED_TIMING)
            .dietary(UPDATED_DIETARY)
            .sideEffects(UPDATED_SIDE_EFFECTS)
            .createUser(UPDATED_CREATE_USER)
            .createDate(UPDATED_CREATE_DATE)
            .updateUser(UPDATED_UPDATE_USER)
            .updateDate(UPDATED_UPDATE_DATE);
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
        assertThat(testTherapeuticRegime.getTiming()).isEqualTo(DEFAULT_TIMING);
        assertThat(testTherapeuticRegime.getDietary()).isEqualTo(DEFAULT_DIETARY);
        assertThat(testTherapeuticRegime.getSideEffects()).isEqualTo(DEFAULT_SIDE_EFFECTS);
        assertThat(testTherapeuticRegime.getCreateUser()).isEqualTo(DEFAULT_CREATE_USER);
        assertThat(testTherapeuticRegime.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testTherapeuticRegime.getUpdateUser()).isEqualTo(DEFAULT_UPDATE_USER);
        assertThat(testTherapeuticRegime.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
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
    public void checkTimingIsRequired() throws Exception {
        int databaseSizeBeforeTest = therapeuticRegimeRepository.findAll().size();
        // set the field null
        therapeuticRegime.setTiming(null);

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
    public void checkDietaryIsRequired() throws Exception {
        int databaseSizeBeforeTest = therapeuticRegimeRepository.findAll().size();
        // set the field null
        therapeuticRegime.setDietary(null);

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
    public void checkSideEffectsIsRequired() throws Exception {
        int databaseSizeBeforeTest = therapeuticRegimeRepository.findAll().size();
        // set the field null
        therapeuticRegime.setSideEffects(null);

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
    public void checkCreateUserIsRequired() throws Exception {
        int databaseSizeBeforeTest = therapeuticRegimeRepository.findAll().size();
        // set the field null
        therapeuticRegime.setCreateUser(null);

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
    public void checkCreateDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = therapeuticRegimeRepository.findAll().size();
        // set the field null
        therapeuticRegime.setCreateDate(null);

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
    public void checkUpdateUserIsRequired() throws Exception {
        int databaseSizeBeforeTest = therapeuticRegimeRepository.findAll().size();
        // set the field null
        therapeuticRegime.setUpdateUser(null);

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
    public void checkUpdateDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = therapeuticRegimeRepository.findAll().size();
        // set the field null
        therapeuticRegime.setUpdateDate(null);

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
            .andExpect(jsonPath("$.[*].timing").value(hasItem(DEFAULT_TIMING)))
            .andExpect(jsonPath("$.[*].dietary").value(hasItem(DEFAULT_DIETARY)))
            .andExpect(jsonPath("$.[*].sideEffects").value(hasItem(DEFAULT_SIDE_EFFECTS)))
            .andExpect(jsonPath("$.[*].createUser").value(hasItem(DEFAULT_CREATE_USER)))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].updateUser").value(hasItem(DEFAULT_UPDATE_USER)))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())));
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
            .andExpect(jsonPath("$.timing").value(DEFAULT_TIMING))
            .andExpect(jsonPath("$.dietary").value(DEFAULT_DIETARY))
            .andExpect(jsonPath("$.sideEffects").value(DEFAULT_SIDE_EFFECTS))
            .andExpect(jsonPath("$.createUser").value(DEFAULT_CREATE_USER))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.updateUser").value(DEFAULT_UPDATE_USER))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()));
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
    public void getAllTherapeuticRegimesByDietaryIsEqualToSomething() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where dietary equals to DEFAULT_DIETARY
        defaultTherapeuticRegimeShouldBeFound("dietary.equals=" + DEFAULT_DIETARY);

        // Get all the therapeuticRegimeList where dietary equals to UPDATED_DIETARY
        defaultTherapeuticRegimeShouldNotBeFound("dietary.equals=" + UPDATED_DIETARY);
    }

    @Test
    @Transactional
    public void getAllTherapeuticRegimesByDietaryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where dietary not equals to DEFAULT_DIETARY
        defaultTherapeuticRegimeShouldNotBeFound("dietary.notEquals=" + DEFAULT_DIETARY);

        // Get all the therapeuticRegimeList where dietary not equals to UPDATED_DIETARY
        defaultTherapeuticRegimeShouldBeFound("dietary.notEquals=" + UPDATED_DIETARY);
    }

    @Test
    @Transactional
    public void getAllTherapeuticRegimesByDietaryIsInShouldWork() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where dietary in DEFAULT_DIETARY or UPDATED_DIETARY
        defaultTherapeuticRegimeShouldBeFound("dietary.in=" + DEFAULT_DIETARY + "," + UPDATED_DIETARY);

        // Get all the therapeuticRegimeList where dietary equals to UPDATED_DIETARY
        defaultTherapeuticRegimeShouldNotBeFound("dietary.in=" + UPDATED_DIETARY);
    }

    @Test
    @Transactional
    public void getAllTherapeuticRegimesByDietaryIsNullOrNotNull() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where dietary is not null
        defaultTherapeuticRegimeShouldBeFound("dietary.specified=true");

        // Get all the therapeuticRegimeList where dietary is null
        defaultTherapeuticRegimeShouldNotBeFound("dietary.specified=false");
    }
                @Test
    @Transactional
    public void getAllTherapeuticRegimesByDietaryContainsSomething() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where dietary contains DEFAULT_DIETARY
        defaultTherapeuticRegimeShouldBeFound("dietary.contains=" + DEFAULT_DIETARY);

        // Get all the therapeuticRegimeList where dietary contains UPDATED_DIETARY
        defaultTherapeuticRegimeShouldNotBeFound("dietary.contains=" + UPDATED_DIETARY);
    }

    @Test
    @Transactional
    public void getAllTherapeuticRegimesByDietaryNotContainsSomething() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where dietary does not contain DEFAULT_DIETARY
        defaultTherapeuticRegimeShouldNotBeFound("dietary.doesNotContain=" + DEFAULT_DIETARY);

        // Get all the therapeuticRegimeList where dietary does not contain UPDATED_DIETARY
        defaultTherapeuticRegimeShouldBeFound("dietary.doesNotContain=" + UPDATED_DIETARY);
    }


    @Test
    @Transactional
    public void getAllTherapeuticRegimesBySideEffectsIsEqualToSomething() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where sideEffects equals to DEFAULT_SIDE_EFFECTS
        defaultTherapeuticRegimeShouldBeFound("sideEffects.equals=" + DEFAULT_SIDE_EFFECTS);

        // Get all the therapeuticRegimeList where sideEffects equals to UPDATED_SIDE_EFFECTS
        defaultTherapeuticRegimeShouldNotBeFound("sideEffects.equals=" + UPDATED_SIDE_EFFECTS);
    }

    @Test
    @Transactional
    public void getAllTherapeuticRegimesBySideEffectsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where sideEffects not equals to DEFAULT_SIDE_EFFECTS
        defaultTherapeuticRegimeShouldNotBeFound("sideEffects.notEquals=" + DEFAULT_SIDE_EFFECTS);

        // Get all the therapeuticRegimeList where sideEffects not equals to UPDATED_SIDE_EFFECTS
        defaultTherapeuticRegimeShouldBeFound("sideEffects.notEquals=" + UPDATED_SIDE_EFFECTS);
    }

    @Test
    @Transactional
    public void getAllTherapeuticRegimesBySideEffectsIsInShouldWork() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where sideEffects in DEFAULT_SIDE_EFFECTS or UPDATED_SIDE_EFFECTS
        defaultTherapeuticRegimeShouldBeFound("sideEffects.in=" + DEFAULT_SIDE_EFFECTS + "," + UPDATED_SIDE_EFFECTS);

        // Get all the therapeuticRegimeList where sideEffects equals to UPDATED_SIDE_EFFECTS
        defaultTherapeuticRegimeShouldNotBeFound("sideEffects.in=" + UPDATED_SIDE_EFFECTS);
    }

    @Test
    @Transactional
    public void getAllTherapeuticRegimesBySideEffectsIsNullOrNotNull() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where sideEffects is not null
        defaultTherapeuticRegimeShouldBeFound("sideEffects.specified=true");

        // Get all the therapeuticRegimeList where sideEffects is null
        defaultTherapeuticRegimeShouldNotBeFound("sideEffects.specified=false");
    }
                @Test
    @Transactional
    public void getAllTherapeuticRegimesBySideEffectsContainsSomething() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where sideEffects contains DEFAULT_SIDE_EFFECTS
        defaultTherapeuticRegimeShouldBeFound("sideEffects.contains=" + DEFAULT_SIDE_EFFECTS);

        // Get all the therapeuticRegimeList where sideEffects contains UPDATED_SIDE_EFFECTS
        defaultTherapeuticRegimeShouldNotBeFound("sideEffects.contains=" + UPDATED_SIDE_EFFECTS);
    }

    @Test
    @Transactional
    public void getAllTherapeuticRegimesBySideEffectsNotContainsSomething() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where sideEffects does not contain DEFAULT_SIDE_EFFECTS
        defaultTherapeuticRegimeShouldNotBeFound("sideEffects.doesNotContain=" + DEFAULT_SIDE_EFFECTS);

        // Get all the therapeuticRegimeList where sideEffects does not contain UPDATED_SIDE_EFFECTS
        defaultTherapeuticRegimeShouldBeFound("sideEffects.doesNotContain=" + UPDATED_SIDE_EFFECTS);
    }


    @Test
    @Transactional
    public void getAllTherapeuticRegimesByCreateUserIsEqualToSomething() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where createUser equals to DEFAULT_CREATE_USER
        defaultTherapeuticRegimeShouldBeFound("createUser.equals=" + DEFAULT_CREATE_USER);

        // Get all the therapeuticRegimeList where createUser equals to UPDATED_CREATE_USER
        defaultTherapeuticRegimeShouldNotBeFound("createUser.equals=" + UPDATED_CREATE_USER);
    }

    @Test
    @Transactional
    public void getAllTherapeuticRegimesByCreateUserIsNotEqualToSomething() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where createUser not equals to DEFAULT_CREATE_USER
        defaultTherapeuticRegimeShouldNotBeFound("createUser.notEquals=" + DEFAULT_CREATE_USER);

        // Get all the therapeuticRegimeList where createUser not equals to UPDATED_CREATE_USER
        defaultTherapeuticRegimeShouldBeFound("createUser.notEquals=" + UPDATED_CREATE_USER);
    }

    @Test
    @Transactional
    public void getAllTherapeuticRegimesByCreateUserIsInShouldWork() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where createUser in DEFAULT_CREATE_USER or UPDATED_CREATE_USER
        defaultTherapeuticRegimeShouldBeFound("createUser.in=" + DEFAULT_CREATE_USER + "," + UPDATED_CREATE_USER);

        // Get all the therapeuticRegimeList where createUser equals to UPDATED_CREATE_USER
        defaultTherapeuticRegimeShouldNotBeFound("createUser.in=" + UPDATED_CREATE_USER);
    }

    @Test
    @Transactional
    public void getAllTherapeuticRegimesByCreateUserIsNullOrNotNull() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where createUser is not null
        defaultTherapeuticRegimeShouldBeFound("createUser.specified=true");

        // Get all the therapeuticRegimeList where createUser is null
        defaultTherapeuticRegimeShouldNotBeFound("createUser.specified=false");
    }
                @Test
    @Transactional
    public void getAllTherapeuticRegimesByCreateUserContainsSomething() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where createUser contains DEFAULT_CREATE_USER
        defaultTherapeuticRegimeShouldBeFound("createUser.contains=" + DEFAULT_CREATE_USER);

        // Get all the therapeuticRegimeList where createUser contains UPDATED_CREATE_USER
        defaultTherapeuticRegimeShouldNotBeFound("createUser.contains=" + UPDATED_CREATE_USER);
    }

    @Test
    @Transactional
    public void getAllTherapeuticRegimesByCreateUserNotContainsSomething() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where createUser does not contain DEFAULT_CREATE_USER
        defaultTherapeuticRegimeShouldNotBeFound("createUser.doesNotContain=" + DEFAULT_CREATE_USER);

        // Get all the therapeuticRegimeList where createUser does not contain UPDATED_CREATE_USER
        defaultTherapeuticRegimeShouldBeFound("createUser.doesNotContain=" + UPDATED_CREATE_USER);
    }


    @Test
    @Transactional
    public void getAllTherapeuticRegimesByCreateDateIsEqualToSomething() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where createDate equals to DEFAULT_CREATE_DATE
        defaultTherapeuticRegimeShouldBeFound("createDate.equals=" + DEFAULT_CREATE_DATE);

        // Get all the therapeuticRegimeList where createDate equals to UPDATED_CREATE_DATE
        defaultTherapeuticRegimeShouldNotBeFound("createDate.equals=" + UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void getAllTherapeuticRegimesByCreateDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where createDate not equals to DEFAULT_CREATE_DATE
        defaultTherapeuticRegimeShouldNotBeFound("createDate.notEquals=" + DEFAULT_CREATE_DATE);

        // Get all the therapeuticRegimeList where createDate not equals to UPDATED_CREATE_DATE
        defaultTherapeuticRegimeShouldBeFound("createDate.notEquals=" + UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void getAllTherapeuticRegimesByCreateDateIsInShouldWork() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where createDate in DEFAULT_CREATE_DATE or UPDATED_CREATE_DATE
        defaultTherapeuticRegimeShouldBeFound("createDate.in=" + DEFAULT_CREATE_DATE + "," + UPDATED_CREATE_DATE);

        // Get all the therapeuticRegimeList where createDate equals to UPDATED_CREATE_DATE
        defaultTherapeuticRegimeShouldNotBeFound("createDate.in=" + UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void getAllTherapeuticRegimesByCreateDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where createDate is not null
        defaultTherapeuticRegimeShouldBeFound("createDate.specified=true");

        // Get all the therapeuticRegimeList where createDate is null
        defaultTherapeuticRegimeShouldNotBeFound("createDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllTherapeuticRegimesByUpdateUserIsEqualToSomething() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where updateUser equals to DEFAULT_UPDATE_USER
        defaultTherapeuticRegimeShouldBeFound("updateUser.equals=" + DEFAULT_UPDATE_USER);

        // Get all the therapeuticRegimeList where updateUser equals to UPDATED_UPDATE_USER
        defaultTherapeuticRegimeShouldNotBeFound("updateUser.equals=" + UPDATED_UPDATE_USER);
    }

    @Test
    @Transactional
    public void getAllTherapeuticRegimesByUpdateUserIsNotEqualToSomething() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where updateUser not equals to DEFAULT_UPDATE_USER
        defaultTherapeuticRegimeShouldNotBeFound("updateUser.notEquals=" + DEFAULT_UPDATE_USER);

        // Get all the therapeuticRegimeList where updateUser not equals to UPDATED_UPDATE_USER
        defaultTherapeuticRegimeShouldBeFound("updateUser.notEquals=" + UPDATED_UPDATE_USER);
    }

    @Test
    @Transactional
    public void getAllTherapeuticRegimesByUpdateUserIsInShouldWork() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where updateUser in DEFAULT_UPDATE_USER or UPDATED_UPDATE_USER
        defaultTherapeuticRegimeShouldBeFound("updateUser.in=" + DEFAULT_UPDATE_USER + "," + UPDATED_UPDATE_USER);

        // Get all the therapeuticRegimeList where updateUser equals to UPDATED_UPDATE_USER
        defaultTherapeuticRegimeShouldNotBeFound("updateUser.in=" + UPDATED_UPDATE_USER);
    }

    @Test
    @Transactional
    public void getAllTherapeuticRegimesByUpdateUserIsNullOrNotNull() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where updateUser is not null
        defaultTherapeuticRegimeShouldBeFound("updateUser.specified=true");

        // Get all the therapeuticRegimeList where updateUser is null
        defaultTherapeuticRegimeShouldNotBeFound("updateUser.specified=false");
    }
                @Test
    @Transactional
    public void getAllTherapeuticRegimesByUpdateUserContainsSomething() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where updateUser contains DEFAULT_UPDATE_USER
        defaultTherapeuticRegimeShouldBeFound("updateUser.contains=" + DEFAULT_UPDATE_USER);

        // Get all the therapeuticRegimeList where updateUser contains UPDATED_UPDATE_USER
        defaultTherapeuticRegimeShouldNotBeFound("updateUser.contains=" + UPDATED_UPDATE_USER);
    }

    @Test
    @Transactional
    public void getAllTherapeuticRegimesByUpdateUserNotContainsSomething() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where updateUser does not contain DEFAULT_UPDATE_USER
        defaultTherapeuticRegimeShouldNotBeFound("updateUser.doesNotContain=" + DEFAULT_UPDATE_USER);

        // Get all the therapeuticRegimeList where updateUser does not contain UPDATED_UPDATE_USER
        defaultTherapeuticRegimeShouldBeFound("updateUser.doesNotContain=" + UPDATED_UPDATE_USER);
    }


    @Test
    @Transactional
    public void getAllTherapeuticRegimesByUpdateDateIsEqualToSomething() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where updateDate equals to DEFAULT_UPDATE_DATE
        defaultTherapeuticRegimeShouldBeFound("updateDate.equals=" + DEFAULT_UPDATE_DATE);

        // Get all the therapeuticRegimeList where updateDate equals to UPDATED_UPDATE_DATE
        defaultTherapeuticRegimeShouldNotBeFound("updateDate.equals=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void getAllTherapeuticRegimesByUpdateDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where updateDate not equals to DEFAULT_UPDATE_DATE
        defaultTherapeuticRegimeShouldNotBeFound("updateDate.notEquals=" + DEFAULT_UPDATE_DATE);

        // Get all the therapeuticRegimeList where updateDate not equals to UPDATED_UPDATE_DATE
        defaultTherapeuticRegimeShouldBeFound("updateDate.notEquals=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void getAllTherapeuticRegimesByUpdateDateIsInShouldWork() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where updateDate in DEFAULT_UPDATE_DATE or UPDATED_UPDATE_DATE
        defaultTherapeuticRegimeShouldBeFound("updateDate.in=" + DEFAULT_UPDATE_DATE + "," + UPDATED_UPDATE_DATE);

        // Get all the therapeuticRegimeList where updateDate equals to UPDATED_UPDATE_DATE
        defaultTherapeuticRegimeShouldNotBeFound("updateDate.in=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void getAllTherapeuticRegimesByUpdateDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);

        // Get all the therapeuticRegimeList where updateDate is not null
        defaultTherapeuticRegimeShouldBeFound("updateDate.specified=true");

        // Get all the therapeuticRegimeList where updateDate is null
        defaultTherapeuticRegimeShouldNotBeFound("updateDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllTherapeuticRegimesByDrugIsEqualToSomething() throws Exception {
        // Initialize the database
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);
        Drug drug = DrugResourceIT.createEntity(em);
        em.persist(drug);
        em.flush();
        therapeuticRegime.setDrug(drug);
        therapeuticRegimeRepository.saveAndFlush(therapeuticRegime);
        Long drugId = drug.getId();

        // Get all the therapeuticRegimeList where drug equals to drugId
        defaultTherapeuticRegimeShouldBeFound("drugId.equals=" + drugId);

        // Get all the therapeuticRegimeList where drug equals to drugId + 1
        defaultTherapeuticRegimeShouldNotBeFound("drugId.equals=" + (drugId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTherapeuticRegimeShouldBeFound(String filter) throws Exception {
        restTherapeuticRegimeMockMvc.perform(get("/api/therapeutic-regimes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(therapeuticRegime.getId().intValue())))
            .andExpect(jsonPath("$.[*].timing").value(hasItem(DEFAULT_TIMING)))
            .andExpect(jsonPath("$.[*].dietary").value(hasItem(DEFAULT_DIETARY)))
            .andExpect(jsonPath("$.[*].sideEffects").value(hasItem(DEFAULT_SIDE_EFFECTS)))
            .andExpect(jsonPath("$.[*].createUser").value(hasItem(DEFAULT_CREATE_USER)))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].updateUser").value(hasItem(DEFAULT_UPDATE_USER)))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())));

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
            .timing(UPDATED_TIMING)
            .dietary(UPDATED_DIETARY)
            .sideEffects(UPDATED_SIDE_EFFECTS)
            .createUser(UPDATED_CREATE_USER)
            .createDate(UPDATED_CREATE_DATE)
            .updateUser(UPDATED_UPDATE_USER)
            .updateDate(UPDATED_UPDATE_DATE);
        TherapeuticRegimeDTO therapeuticRegimeDTO = therapeuticRegimeMapper.toDto(updatedTherapeuticRegime);

        restTherapeuticRegimeMockMvc.perform(put("/api/therapeutic-regimes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(therapeuticRegimeDTO)))
            .andExpect(status().isOk());

        // Validate the TherapeuticRegime in the database
        List<TherapeuticRegime> therapeuticRegimeList = therapeuticRegimeRepository.findAll();
        assertThat(therapeuticRegimeList).hasSize(databaseSizeBeforeUpdate);
        TherapeuticRegime testTherapeuticRegime = therapeuticRegimeList.get(therapeuticRegimeList.size() - 1);
        assertThat(testTherapeuticRegime.getTiming()).isEqualTo(UPDATED_TIMING);
        assertThat(testTherapeuticRegime.getDietary()).isEqualTo(UPDATED_DIETARY);
        assertThat(testTherapeuticRegime.getSideEffects()).isEqualTo(UPDATED_SIDE_EFFECTS);
        assertThat(testTherapeuticRegime.getCreateUser()).isEqualTo(UPDATED_CREATE_USER);
        assertThat(testTherapeuticRegime.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testTherapeuticRegime.getUpdateUser()).isEqualTo(UPDATED_UPDATE_USER);
        assertThat(testTherapeuticRegime.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
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
