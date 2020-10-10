package uc.dei.mse.supportcare.web.rest;

import uc.dei.mse.supportcare.SupportcareApp;
import uc.dei.mse.supportcare.domain.Protocol;
import uc.dei.mse.supportcare.domain.TherapeuticRegime;
import uc.dei.mse.supportcare.domain.Outcome;
import uc.dei.mse.supportcare.domain.Guide;
import uc.dei.mse.supportcare.repository.ProtocolRepository;
import uc.dei.mse.supportcare.service.ProtocolService;
import uc.dei.mse.supportcare.service.dto.ProtocolDTO;
import uc.dei.mse.supportcare.service.mapper.ProtocolMapper;
import uc.dei.mse.supportcare.service.dto.ProtocolCriteria;
import uc.dei.mse.supportcare.service.ProtocolQueryService;

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
 * Integration tests for the {@link ProtocolResource} REST controller.
 */
@SpringBootTest(classes = SupportcareApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProtocolResourceIT {

    private static final String DEFAULT_TOXICITY_DIAGNOSIS = "AAAAAAAAAA";
    private static final String UPDATED_TOXICITY_DIAGNOSIS = "BBBBBBBBBB";

    private static final String DEFAULT_CREATE_USER = "AAAAAAAAAA";
    private static final String UPDATED_CREATE_USER = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATE_USER = "AAAAAAAAAA";
    private static final String UPDATED_UPDATE_USER = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ProtocolRepository protocolRepository;

    @Autowired
    private ProtocolMapper protocolMapper;

    @Autowired
    private ProtocolService protocolService;

    @Autowired
    private ProtocolQueryService protocolQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProtocolMockMvc;

    private Protocol protocol;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Protocol createEntity(EntityManager em) {
        Protocol protocol = new Protocol()
            .toxicityDiagnosis(DEFAULT_TOXICITY_DIAGNOSIS)
            .createUser(DEFAULT_CREATE_USER)
            .createDate(DEFAULT_CREATE_DATE)
            .updateUser(DEFAULT_UPDATE_USER)
            .updateDate(DEFAULT_UPDATE_DATE);
        return protocol;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Protocol createUpdatedEntity(EntityManager em) {
        Protocol protocol = new Protocol()
            .toxicityDiagnosis(UPDATED_TOXICITY_DIAGNOSIS)
            .createUser(UPDATED_CREATE_USER)
            .createDate(UPDATED_CREATE_DATE)
            .updateUser(UPDATED_UPDATE_USER)
            .updateDate(UPDATED_UPDATE_DATE);
        return protocol;
    }

    @BeforeEach
    public void initTest() {
        protocol = createEntity(em);
    }

    @Test
    @Transactional
    public void createProtocol() throws Exception {
        int databaseSizeBeforeCreate = protocolRepository.findAll().size();
        // Create the Protocol
        ProtocolDTO protocolDTO = protocolMapper.toDto(protocol);
        restProtocolMockMvc.perform(post("/api/protocols")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(protocolDTO)))
            .andExpect(status().isCreated());

        // Validate the Protocol in the database
        List<Protocol> protocolList = protocolRepository.findAll();
        assertThat(protocolList).hasSize(databaseSizeBeforeCreate + 1);
        Protocol testProtocol = protocolList.get(protocolList.size() - 1);
        assertThat(testProtocol.getToxicityDiagnosis()).isEqualTo(DEFAULT_TOXICITY_DIAGNOSIS);
        assertThat(testProtocol.getCreateUser()).isEqualTo(DEFAULT_CREATE_USER);
        assertThat(testProtocol.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testProtocol.getUpdateUser()).isEqualTo(DEFAULT_UPDATE_USER);
        assertThat(testProtocol.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void createProtocolWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = protocolRepository.findAll().size();

        // Create the Protocol with an existing ID
        protocol.setId(1L);
        ProtocolDTO protocolDTO = protocolMapper.toDto(protocol);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProtocolMockMvc.perform(post("/api/protocols")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(protocolDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Protocol in the database
        List<Protocol> protocolList = protocolRepository.findAll();
        assertThat(protocolList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkToxicityDiagnosisIsRequired() throws Exception {
        int databaseSizeBeforeTest = protocolRepository.findAll().size();
        // set the field null
        protocol.setToxicityDiagnosis(null);

        // Create the Protocol, which fails.
        ProtocolDTO protocolDTO = protocolMapper.toDto(protocol);


        restProtocolMockMvc.perform(post("/api/protocols")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(protocolDTO)))
            .andExpect(status().isBadRequest());

        List<Protocol> protocolList = protocolRepository.findAll();
        assertThat(protocolList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateUserIsRequired() throws Exception {
        int databaseSizeBeforeTest = protocolRepository.findAll().size();
        // set the field null
        protocol.setCreateUser(null);

        // Create the Protocol, which fails.
        ProtocolDTO protocolDTO = protocolMapper.toDto(protocol);


        restProtocolMockMvc.perform(post("/api/protocols")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(protocolDTO)))
            .andExpect(status().isBadRequest());

        List<Protocol> protocolList = protocolRepository.findAll();
        assertThat(protocolList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = protocolRepository.findAll().size();
        // set the field null
        protocol.setCreateDate(null);

        // Create the Protocol, which fails.
        ProtocolDTO protocolDTO = protocolMapper.toDto(protocol);


        restProtocolMockMvc.perform(post("/api/protocols")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(protocolDTO)))
            .andExpect(status().isBadRequest());

        List<Protocol> protocolList = protocolRepository.findAll();
        assertThat(protocolList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateUserIsRequired() throws Exception {
        int databaseSizeBeforeTest = protocolRepository.findAll().size();
        // set the field null
        protocol.setUpdateUser(null);

        // Create the Protocol, which fails.
        ProtocolDTO protocolDTO = protocolMapper.toDto(protocol);


        restProtocolMockMvc.perform(post("/api/protocols")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(protocolDTO)))
            .andExpect(status().isBadRequest());

        List<Protocol> protocolList = protocolRepository.findAll();
        assertThat(protocolList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = protocolRepository.findAll().size();
        // set the field null
        protocol.setUpdateDate(null);

        // Create the Protocol, which fails.
        ProtocolDTO protocolDTO = protocolMapper.toDto(protocol);


        restProtocolMockMvc.perform(post("/api/protocols")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(protocolDTO)))
            .andExpect(status().isBadRequest());

        List<Protocol> protocolList = protocolRepository.findAll();
        assertThat(protocolList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProtocols() throws Exception {
        // Initialize the database
        protocolRepository.saveAndFlush(protocol);

        // Get all the protocolList
        restProtocolMockMvc.perform(get("/api/protocols?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(protocol.getId().intValue())))
            .andExpect(jsonPath("$.[*].toxicityDiagnosis").value(hasItem(DEFAULT_TOXICITY_DIAGNOSIS)))
            .andExpect(jsonPath("$.[*].createUser").value(hasItem(DEFAULT_CREATE_USER)))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].updateUser").value(hasItem(DEFAULT_UPDATE_USER)))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getProtocol() throws Exception {
        // Initialize the database
        protocolRepository.saveAndFlush(protocol);

        // Get the protocol
        restProtocolMockMvc.perform(get("/api/protocols/{id}", protocol.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(protocol.getId().intValue()))
            .andExpect(jsonPath("$.toxicityDiagnosis").value(DEFAULT_TOXICITY_DIAGNOSIS))
            .andExpect(jsonPath("$.createUser").value(DEFAULT_CREATE_USER))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.updateUser").value(DEFAULT_UPDATE_USER))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()));
    }


    @Test
    @Transactional
    public void getProtocolsByIdFiltering() throws Exception {
        // Initialize the database
        protocolRepository.saveAndFlush(protocol);

        Long id = protocol.getId();

        defaultProtocolShouldBeFound("id.equals=" + id);
        defaultProtocolShouldNotBeFound("id.notEquals=" + id);

        defaultProtocolShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProtocolShouldNotBeFound("id.greaterThan=" + id);

        defaultProtocolShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProtocolShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllProtocolsByToxicityDiagnosisIsEqualToSomething() throws Exception {
        // Initialize the database
        protocolRepository.saveAndFlush(protocol);

        // Get all the protocolList where toxicityDiagnosis equals to DEFAULT_TOXICITY_DIAGNOSIS
        defaultProtocolShouldBeFound("toxicityDiagnosis.equals=" + DEFAULT_TOXICITY_DIAGNOSIS);

        // Get all the protocolList where toxicityDiagnosis equals to UPDATED_TOXICITY_DIAGNOSIS
        defaultProtocolShouldNotBeFound("toxicityDiagnosis.equals=" + UPDATED_TOXICITY_DIAGNOSIS);
    }

    @Test
    @Transactional
    public void getAllProtocolsByToxicityDiagnosisIsNotEqualToSomething() throws Exception {
        // Initialize the database
        protocolRepository.saveAndFlush(protocol);

        // Get all the protocolList where toxicityDiagnosis not equals to DEFAULT_TOXICITY_DIAGNOSIS
        defaultProtocolShouldNotBeFound("toxicityDiagnosis.notEquals=" + DEFAULT_TOXICITY_DIAGNOSIS);

        // Get all the protocolList where toxicityDiagnosis not equals to UPDATED_TOXICITY_DIAGNOSIS
        defaultProtocolShouldBeFound("toxicityDiagnosis.notEquals=" + UPDATED_TOXICITY_DIAGNOSIS);
    }

    @Test
    @Transactional
    public void getAllProtocolsByToxicityDiagnosisIsInShouldWork() throws Exception {
        // Initialize the database
        protocolRepository.saveAndFlush(protocol);

        // Get all the protocolList where toxicityDiagnosis in DEFAULT_TOXICITY_DIAGNOSIS or UPDATED_TOXICITY_DIAGNOSIS
        defaultProtocolShouldBeFound("toxicityDiagnosis.in=" + DEFAULT_TOXICITY_DIAGNOSIS + "," + UPDATED_TOXICITY_DIAGNOSIS);

        // Get all the protocolList where toxicityDiagnosis equals to UPDATED_TOXICITY_DIAGNOSIS
        defaultProtocolShouldNotBeFound("toxicityDiagnosis.in=" + UPDATED_TOXICITY_DIAGNOSIS);
    }

    @Test
    @Transactional
    public void getAllProtocolsByToxicityDiagnosisIsNullOrNotNull() throws Exception {
        // Initialize the database
        protocolRepository.saveAndFlush(protocol);

        // Get all the protocolList where toxicityDiagnosis is not null
        defaultProtocolShouldBeFound("toxicityDiagnosis.specified=true");

        // Get all the protocolList where toxicityDiagnosis is null
        defaultProtocolShouldNotBeFound("toxicityDiagnosis.specified=false");
    }
                @Test
    @Transactional
    public void getAllProtocolsByToxicityDiagnosisContainsSomething() throws Exception {
        // Initialize the database
        protocolRepository.saveAndFlush(protocol);

        // Get all the protocolList where toxicityDiagnosis contains DEFAULT_TOXICITY_DIAGNOSIS
        defaultProtocolShouldBeFound("toxicityDiagnosis.contains=" + DEFAULT_TOXICITY_DIAGNOSIS);

        // Get all the protocolList where toxicityDiagnosis contains UPDATED_TOXICITY_DIAGNOSIS
        defaultProtocolShouldNotBeFound("toxicityDiagnosis.contains=" + UPDATED_TOXICITY_DIAGNOSIS);
    }

    @Test
    @Transactional
    public void getAllProtocolsByToxicityDiagnosisNotContainsSomething() throws Exception {
        // Initialize the database
        protocolRepository.saveAndFlush(protocol);

        // Get all the protocolList where toxicityDiagnosis does not contain DEFAULT_TOXICITY_DIAGNOSIS
        defaultProtocolShouldNotBeFound("toxicityDiagnosis.doesNotContain=" + DEFAULT_TOXICITY_DIAGNOSIS);

        // Get all the protocolList where toxicityDiagnosis does not contain UPDATED_TOXICITY_DIAGNOSIS
        defaultProtocolShouldBeFound("toxicityDiagnosis.doesNotContain=" + UPDATED_TOXICITY_DIAGNOSIS);
    }


    @Test
    @Transactional
    public void getAllProtocolsByCreateUserIsEqualToSomething() throws Exception {
        // Initialize the database
        protocolRepository.saveAndFlush(protocol);

        // Get all the protocolList where createUser equals to DEFAULT_CREATE_USER
        defaultProtocolShouldBeFound("createUser.equals=" + DEFAULT_CREATE_USER);

        // Get all the protocolList where createUser equals to UPDATED_CREATE_USER
        defaultProtocolShouldNotBeFound("createUser.equals=" + UPDATED_CREATE_USER);
    }

    @Test
    @Transactional
    public void getAllProtocolsByCreateUserIsNotEqualToSomething() throws Exception {
        // Initialize the database
        protocolRepository.saveAndFlush(protocol);

        // Get all the protocolList where createUser not equals to DEFAULT_CREATE_USER
        defaultProtocolShouldNotBeFound("createUser.notEquals=" + DEFAULT_CREATE_USER);

        // Get all the protocolList where createUser not equals to UPDATED_CREATE_USER
        defaultProtocolShouldBeFound("createUser.notEquals=" + UPDATED_CREATE_USER);
    }

    @Test
    @Transactional
    public void getAllProtocolsByCreateUserIsInShouldWork() throws Exception {
        // Initialize the database
        protocolRepository.saveAndFlush(protocol);

        // Get all the protocolList where createUser in DEFAULT_CREATE_USER or UPDATED_CREATE_USER
        defaultProtocolShouldBeFound("createUser.in=" + DEFAULT_CREATE_USER + "," + UPDATED_CREATE_USER);

        // Get all the protocolList where createUser equals to UPDATED_CREATE_USER
        defaultProtocolShouldNotBeFound("createUser.in=" + UPDATED_CREATE_USER);
    }

    @Test
    @Transactional
    public void getAllProtocolsByCreateUserIsNullOrNotNull() throws Exception {
        // Initialize the database
        protocolRepository.saveAndFlush(protocol);

        // Get all the protocolList where createUser is not null
        defaultProtocolShouldBeFound("createUser.specified=true");

        // Get all the protocolList where createUser is null
        defaultProtocolShouldNotBeFound("createUser.specified=false");
    }
                @Test
    @Transactional
    public void getAllProtocolsByCreateUserContainsSomething() throws Exception {
        // Initialize the database
        protocolRepository.saveAndFlush(protocol);

        // Get all the protocolList where createUser contains DEFAULT_CREATE_USER
        defaultProtocolShouldBeFound("createUser.contains=" + DEFAULT_CREATE_USER);

        // Get all the protocolList where createUser contains UPDATED_CREATE_USER
        defaultProtocolShouldNotBeFound("createUser.contains=" + UPDATED_CREATE_USER);
    }

    @Test
    @Transactional
    public void getAllProtocolsByCreateUserNotContainsSomething() throws Exception {
        // Initialize the database
        protocolRepository.saveAndFlush(protocol);

        // Get all the protocolList where createUser does not contain DEFAULT_CREATE_USER
        defaultProtocolShouldNotBeFound("createUser.doesNotContain=" + DEFAULT_CREATE_USER);

        // Get all the protocolList where createUser does not contain UPDATED_CREATE_USER
        defaultProtocolShouldBeFound("createUser.doesNotContain=" + UPDATED_CREATE_USER);
    }


    @Test
    @Transactional
    public void getAllProtocolsByCreateDateIsEqualToSomething() throws Exception {
        // Initialize the database
        protocolRepository.saveAndFlush(protocol);

        // Get all the protocolList where createDate equals to DEFAULT_CREATE_DATE
        defaultProtocolShouldBeFound("createDate.equals=" + DEFAULT_CREATE_DATE);

        // Get all the protocolList where createDate equals to UPDATED_CREATE_DATE
        defaultProtocolShouldNotBeFound("createDate.equals=" + UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void getAllProtocolsByCreateDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        protocolRepository.saveAndFlush(protocol);

        // Get all the protocolList where createDate not equals to DEFAULT_CREATE_DATE
        defaultProtocolShouldNotBeFound("createDate.notEquals=" + DEFAULT_CREATE_DATE);

        // Get all the protocolList where createDate not equals to UPDATED_CREATE_DATE
        defaultProtocolShouldBeFound("createDate.notEquals=" + UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void getAllProtocolsByCreateDateIsInShouldWork() throws Exception {
        // Initialize the database
        protocolRepository.saveAndFlush(protocol);

        // Get all the protocolList where createDate in DEFAULT_CREATE_DATE or UPDATED_CREATE_DATE
        defaultProtocolShouldBeFound("createDate.in=" + DEFAULT_CREATE_DATE + "," + UPDATED_CREATE_DATE);

        // Get all the protocolList where createDate equals to UPDATED_CREATE_DATE
        defaultProtocolShouldNotBeFound("createDate.in=" + UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void getAllProtocolsByCreateDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        protocolRepository.saveAndFlush(protocol);

        // Get all the protocolList where createDate is not null
        defaultProtocolShouldBeFound("createDate.specified=true");

        // Get all the protocolList where createDate is null
        defaultProtocolShouldNotBeFound("createDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllProtocolsByUpdateUserIsEqualToSomething() throws Exception {
        // Initialize the database
        protocolRepository.saveAndFlush(protocol);

        // Get all the protocolList where updateUser equals to DEFAULT_UPDATE_USER
        defaultProtocolShouldBeFound("updateUser.equals=" + DEFAULT_UPDATE_USER);

        // Get all the protocolList where updateUser equals to UPDATED_UPDATE_USER
        defaultProtocolShouldNotBeFound("updateUser.equals=" + UPDATED_UPDATE_USER);
    }

    @Test
    @Transactional
    public void getAllProtocolsByUpdateUserIsNotEqualToSomething() throws Exception {
        // Initialize the database
        protocolRepository.saveAndFlush(protocol);

        // Get all the protocolList where updateUser not equals to DEFAULT_UPDATE_USER
        defaultProtocolShouldNotBeFound("updateUser.notEquals=" + DEFAULT_UPDATE_USER);

        // Get all the protocolList where updateUser not equals to UPDATED_UPDATE_USER
        defaultProtocolShouldBeFound("updateUser.notEquals=" + UPDATED_UPDATE_USER);
    }

    @Test
    @Transactional
    public void getAllProtocolsByUpdateUserIsInShouldWork() throws Exception {
        // Initialize the database
        protocolRepository.saveAndFlush(protocol);

        // Get all the protocolList where updateUser in DEFAULT_UPDATE_USER or UPDATED_UPDATE_USER
        defaultProtocolShouldBeFound("updateUser.in=" + DEFAULT_UPDATE_USER + "," + UPDATED_UPDATE_USER);

        // Get all the protocolList where updateUser equals to UPDATED_UPDATE_USER
        defaultProtocolShouldNotBeFound("updateUser.in=" + UPDATED_UPDATE_USER);
    }

    @Test
    @Transactional
    public void getAllProtocolsByUpdateUserIsNullOrNotNull() throws Exception {
        // Initialize the database
        protocolRepository.saveAndFlush(protocol);

        // Get all the protocolList where updateUser is not null
        defaultProtocolShouldBeFound("updateUser.specified=true");

        // Get all the protocolList where updateUser is null
        defaultProtocolShouldNotBeFound("updateUser.specified=false");
    }
                @Test
    @Transactional
    public void getAllProtocolsByUpdateUserContainsSomething() throws Exception {
        // Initialize the database
        protocolRepository.saveAndFlush(protocol);

        // Get all the protocolList where updateUser contains DEFAULT_UPDATE_USER
        defaultProtocolShouldBeFound("updateUser.contains=" + DEFAULT_UPDATE_USER);

        // Get all the protocolList where updateUser contains UPDATED_UPDATE_USER
        defaultProtocolShouldNotBeFound("updateUser.contains=" + UPDATED_UPDATE_USER);
    }

    @Test
    @Transactional
    public void getAllProtocolsByUpdateUserNotContainsSomething() throws Exception {
        // Initialize the database
        protocolRepository.saveAndFlush(protocol);

        // Get all the protocolList where updateUser does not contain DEFAULT_UPDATE_USER
        defaultProtocolShouldNotBeFound("updateUser.doesNotContain=" + DEFAULT_UPDATE_USER);

        // Get all the protocolList where updateUser does not contain UPDATED_UPDATE_USER
        defaultProtocolShouldBeFound("updateUser.doesNotContain=" + UPDATED_UPDATE_USER);
    }


    @Test
    @Transactional
    public void getAllProtocolsByUpdateDateIsEqualToSomething() throws Exception {
        // Initialize the database
        protocolRepository.saveAndFlush(protocol);

        // Get all the protocolList where updateDate equals to DEFAULT_UPDATE_DATE
        defaultProtocolShouldBeFound("updateDate.equals=" + DEFAULT_UPDATE_DATE);

        // Get all the protocolList where updateDate equals to UPDATED_UPDATE_DATE
        defaultProtocolShouldNotBeFound("updateDate.equals=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void getAllProtocolsByUpdateDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        protocolRepository.saveAndFlush(protocol);

        // Get all the protocolList where updateDate not equals to DEFAULT_UPDATE_DATE
        defaultProtocolShouldNotBeFound("updateDate.notEquals=" + DEFAULT_UPDATE_DATE);

        // Get all the protocolList where updateDate not equals to UPDATED_UPDATE_DATE
        defaultProtocolShouldBeFound("updateDate.notEquals=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void getAllProtocolsByUpdateDateIsInShouldWork() throws Exception {
        // Initialize the database
        protocolRepository.saveAndFlush(protocol);

        // Get all the protocolList where updateDate in DEFAULT_UPDATE_DATE or UPDATED_UPDATE_DATE
        defaultProtocolShouldBeFound("updateDate.in=" + DEFAULT_UPDATE_DATE + "," + UPDATED_UPDATE_DATE);

        // Get all the protocolList where updateDate equals to UPDATED_UPDATE_DATE
        defaultProtocolShouldNotBeFound("updateDate.in=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void getAllProtocolsByUpdateDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        protocolRepository.saveAndFlush(protocol);

        // Get all the protocolList where updateDate is not null
        defaultProtocolShouldBeFound("updateDate.specified=true");

        // Get all the protocolList where updateDate is null
        defaultProtocolShouldNotBeFound("updateDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllProtocolsByTherapeuticRegimeIsEqualToSomething() throws Exception {
        // Initialize the database
        protocolRepository.saveAndFlush(protocol);
        TherapeuticRegime therapeuticRegime = TherapeuticRegimeResourceIT.createEntity(em);
        em.persist(therapeuticRegime);
        em.flush();
        protocol.setTherapeuticRegime(therapeuticRegime);
        protocolRepository.saveAndFlush(protocol);
        Long therapeuticRegimeId = therapeuticRegime.getId();

        // Get all the protocolList where therapeuticRegime equals to therapeuticRegimeId
        defaultProtocolShouldBeFound("therapeuticRegimeId.equals=" + therapeuticRegimeId);

        // Get all the protocolList where therapeuticRegime equals to therapeuticRegimeId + 1
        defaultProtocolShouldNotBeFound("therapeuticRegimeId.equals=" + (therapeuticRegimeId + 1));
    }


    @Test
    @Transactional
    public void getAllProtocolsByOutcomeIsEqualToSomething() throws Exception {
        // Initialize the database
        protocolRepository.saveAndFlush(protocol);
        Outcome outcome = OutcomeResourceIT.createEntity(em);
        em.persist(outcome);
        em.flush();
        protocol.setOutcome(outcome);
        protocolRepository.saveAndFlush(protocol);
        Long outcomeId = outcome.getId();

        // Get all the protocolList where outcome equals to outcomeId
        defaultProtocolShouldBeFound("outcomeId.equals=" + outcomeId);

        // Get all the protocolList where outcome equals to outcomeId + 1
        defaultProtocolShouldNotBeFound("outcomeId.equals=" + (outcomeId + 1));
    }


    @Test
    @Transactional
    public void getAllProtocolsByGuideIsEqualToSomething() throws Exception {
        // Initialize the database
        protocolRepository.saveAndFlush(protocol);
        Guide guide = GuideResourceIT.createEntity(em);
        em.persist(guide);
        em.flush();
        protocol.setGuide(guide);
        protocolRepository.saveAndFlush(protocol);
        Long guideId = guide.getId();

        // Get all the protocolList where guide equals to guideId
        defaultProtocolShouldBeFound("guideId.equals=" + guideId);

        // Get all the protocolList where guide equals to guideId + 1
        defaultProtocolShouldNotBeFound("guideId.equals=" + (guideId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProtocolShouldBeFound(String filter) throws Exception {
        restProtocolMockMvc.perform(get("/api/protocols?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(protocol.getId().intValue())))
            .andExpect(jsonPath("$.[*].toxicityDiagnosis").value(hasItem(DEFAULT_TOXICITY_DIAGNOSIS)))
            .andExpect(jsonPath("$.[*].createUser").value(hasItem(DEFAULT_CREATE_USER)))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].updateUser").value(hasItem(DEFAULT_UPDATE_USER)))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())));

        // Check, that the count call also returns 1
        restProtocolMockMvc.perform(get("/api/protocols/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProtocolShouldNotBeFound(String filter) throws Exception {
        restProtocolMockMvc.perform(get("/api/protocols?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProtocolMockMvc.perform(get("/api/protocols/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingProtocol() throws Exception {
        // Get the protocol
        restProtocolMockMvc.perform(get("/api/protocols/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProtocol() throws Exception {
        // Initialize the database
        protocolRepository.saveAndFlush(protocol);

        int databaseSizeBeforeUpdate = protocolRepository.findAll().size();

        // Update the protocol
        Protocol updatedProtocol = protocolRepository.findById(protocol.getId()).get();
        // Disconnect from session so that the updates on updatedProtocol are not directly saved in db
        em.detach(updatedProtocol);
        updatedProtocol
            .toxicityDiagnosis(UPDATED_TOXICITY_DIAGNOSIS)
            .createUser(UPDATED_CREATE_USER)
            .createDate(UPDATED_CREATE_DATE)
            .updateUser(UPDATED_UPDATE_USER)
            .updateDate(UPDATED_UPDATE_DATE);
        ProtocolDTO protocolDTO = protocolMapper.toDto(updatedProtocol);

        restProtocolMockMvc.perform(put("/api/protocols")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(protocolDTO)))
            .andExpect(status().isOk());

        // Validate the Protocol in the database
        List<Protocol> protocolList = protocolRepository.findAll();
        assertThat(protocolList).hasSize(databaseSizeBeforeUpdate);
        Protocol testProtocol = protocolList.get(protocolList.size() - 1);
        assertThat(testProtocol.getToxicityDiagnosis()).isEqualTo(UPDATED_TOXICITY_DIAGNOSIS);
        assertThat(testProtocol.getCreateUser()).isEqualTo(UPDATED_CREATE_USER);
        assertThat(testProtocol.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testProtocol.getUpdateUser()).isEqualTo(UPDATED_UPDATE_USER);
        assertThat(testProtocol.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingProtocol() throws Exception {
        int databaseSizeBeforeUpdate = protocolRepository.findAll().size();

        // Create the Protocol
        ProtocolDTO protocolDTO = protocolMapper.toDto(protocol);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProtocolMockMvc.perform(put("/api/protocols")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(protocolDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Protocol in the database
        List<Protocol> protocolList = protocolRepository.findAll();
        assertThat(protocolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProtocol() throws Exception {
        // Initialize the database
        protocolRepository.saveAndFlush(protocol);

        int databaseSizeBeforeDelete = protocolRepository.findAll().size();

        // Delete the protocol
        restProtocolMockMvc.perform(delete("/api/protocols/{id}", protocol.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Protocol> protocolList = protocolRepository.findAll();
        assertThat(protocolList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
