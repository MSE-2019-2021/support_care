package uc.dei.mse.supportivecare.web.rest;

import uc.dei.mse.supportivecare.SupportivecareApp;
import uc.dei.mse.supportivecare.domain.Diagnostic;
import uc.dei.mse.supportivecare.domain.TherapeuticRegime;
import uc.dei.mse.supportivecare.repository.DiagnosticRepository;
import uc.dei.mse.supportivecare.service.DiagnosticService;
import uc.dei.mse.supportivecare.service.dto.DiagnosticDTO;
import uc.dei.mse.supportivecare.service.mapper.DiagnosticMapper;
import uc.dei.mse.supportivecare.service.dto.DiagnosticCriteria;
import uc.dei.mse.supportivecare.service.DiagnosticQueryService;

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
 * Integration tests for the {@link DiagnosticResource} REST controller.
 */
@SpringBootTest(classes = SupportivecareApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DiagnosticResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_NOTICE = "AAAAAAAAAA";
    private static final String UPDATED_NOTICE = "BBBBBBBBBB";

    @Autowired
    private DiagnosticRepository diagnosticRepository;

    @Autowired
    private DiagnosticMapper diagnosticMapper;

    @Autowired
    private DiagnosticService diagnosticService;

    @Autowired
    private DiagnosticQueryService diagnosticQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDiagnosticMockMvc;

    private Diagnostic diagnostic;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Diagnostic createEntity(EntityManager em) {
        Diagnostic diagnostic = new Diagnostic()
            .name(DEFAULT_NAME)
            .notice(DEFAULT_NOTICE);
        return diagnostic;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Diagnostic createUpdatedEntity(EntityManager em) {
        Diagnostic diagnostic = new Diagnostic()
            .name(UPDATED_NAME)
            .notice(UPDATED_NOTICE);
        return diagnostic;
    }

    @BeforeEach
    public void initTest() {
        diagnostic = createEntity(em);
    }

    @Test
    @Transactional
    public void createDiagnostic() throws Exception {
        int databaseSizeBeforeCreate = diagnosticRepository.findAll().size();
        // Create the Diagnostic
        DiagnosticDTO diagnosticDTO = diagnosticMapper.toDto(diagnostic);
        restDiagnosticMockMvc.perform(post("/api/diagnostics")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(diagnosticDTO)))
            .andExpect(status().isCreated());

        // Validate the Diagnostic in the database
        List<Diagnostic> diagnosticList = diagnosticRepository.findAll();
        assertThat(diagnosticList).hasSize(databaseSizeBeforeCreate + 1);
        Diagnostic testDiagnostic = diagnosticList.get(diagnosticList.size() - 1);
        assertThat(testDiagnostic.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDiagnostic.getNotice()).isEqualTo(DEFAULT_NOTICE);
    }

    @Test
    @Transactional
    public void createDiagnosticWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = diagnosticRepository.findAll().size();

        // Create the Diagnostic with an existing ID
        diagnostic.setId(1L);
        DiagnosticDTO diagnosticDTO = diagnosticMapper.toDto(diagnostic);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDiagnosticMockMvc.perform(post("/api/diagnostics")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(diagnosticDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Diagnostic in the database
        List<Diagnostic> diagnosticList = diagnosticRepository.findAll();
        assertThat(diagnosticList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = diagnosticRepository.findAll().size();
        // set the field null
        diagnostic.setName(null);

        // Create the Diagnostic, which fails.
        DiagnosticDTO diagnosticDTO = diagnosticMapper.toDto(diagnostic);


        restDiagnosticMockMvc.perform(post("/api/diagnostics")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(diagnosticDTO)))
            .andExpect(status().isBadRequest());

        List<Diagnostic> diagnosticList = diagnosticRepository.findAll();
        assertThat(diagnosticList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDiagnostics() throws Exception {
        // Initialize the database
        diagnosticRepository.saveAndFlush(diagnostic);

        // Get all the diagnosticList
        restDiagnosticMockMvc.perform(get("/api/diagnostics?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(diagnostic.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].notice").value(hasItem(DEFAULT_NOTICE)));
    }
    
    @Test
    @Transactional
    public void getDiagnostic() throws Exception {
        // Initialize the database
        diagnosticRepository.saveAndFlush(diagnostic);

        // Get the diagnostic
        restDiagnosticMockMvc.perform(get("/api/diagnostics/{id}", diagnostic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(diagnostic.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.notice").value(DEFAULT_NOTICE));
    }


    @Test
    @Transactional
    public void getDiagnosticsByIdFiltering() throws Exception {
        // Initialize the database
        diagnosticRepository.saveAndFlush(diagnostic);

        Long id = diagnostic.getId();

        defaultDiagnosticShouldBeFound("id.equals=" + id);
        defaultDiagnosticShouldNotBeFound("id.notEquals=" + id);

        defaultDiagnosticShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDiagnosticShouldNotBeFound("id.greaterThan=" + id);

        defaultDiagnosticShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDiagnosticShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDiagnosticsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        diagnosticRepository.saveAndFlush(diagnostic);

        // Get all the diagnosticList where name equals to DEFAULT_NAME
        defaultDiagnosticShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the diagnosticList where name equals to UPDATED_NAME
        defaultDiagnosticShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDiagnosticsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        diagnosticRepository.saveAndFlush(diagnostic);

        // Get all the diagnosticList where name not equals to DEFAULT_NAME
        defaultDiagnosticShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the diagnosticList where name not equals to UPDATED_NAME
        defaultDiagnosticShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDiagnosticsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        diagnosticRepository.saveAndFlush(diagnostic);

        // Get all the diagnosticList where name in DEFAULT_NAME or UPDATED_NAME
        defaultDiagnosticShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the diagnosticList where name equals to UPDATED_NAME
        defaultDiagnosticShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDiagnosticsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        diagnosticRepository.saveAndFlush(diagnostic);

        // Get all the diagnosticList where name is not null
        defaultDiagnosticShouldBeFound("name.specified=true");

        // Get all the diagnosticList where name is null
        defaultDiagnosticShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllDiagnosticsByNameContainsSomething() throws Exception {
        // Initialize the database
        diagnosticRepository.saveAndFlush(diagnostic);

        // Get all the diagnosticList where name contains DEFAULT_NAME
        defaultDiagnosticShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the diagnosticList where name contains UPDATED_NAME
        defaultDiagnosticShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDiagnosticsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        diagnosticRepository.saveAndFlush(diagnostic);

        // Get all the diagnosticList where name does not contain DEFAULT_NAME
        defaultDiagnosticShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the diagnosticList where name does not contain UPDATED_NAME
        defaultDiagnosticShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllDiagnosticsByNoticeIsEqualToSomething() throws Exception {
        // Initialize the database
        diagnosticRepository.saveAndFlush(diagnostic);

        // Get all the diagnosticList where notice equals to DEFAULT_NOTICE
        defaultDiagnosticShouldBeFound("notice.equals=" + DEFAULT_NOTICE);

        // Get all the diagnosticList where notice equals to UPDATED_NOTICE
        defaultDiagnosticShouldNotBeFound("notice.equals=" + UPDATED_NOTICE);
    }

    @Test
    @Transactional
    public void getAllDiagnosticsByNoticeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        diagnosticRepository.saveAndFlush(diagnostic);

        // Get all the diagnosticList where notice not equals to DEFAULT_NOTICE
        defaultDiagnosticShouldNotBeFound("notice.notEquals=" + DEFAULT_NOTICE);

        // Get all the diagnosticList where notice not equals to UPDATED_NOTICE
        defaultDiagnosticShouldBeFound("notice.notEquals=" + UPDATED_NOTICE);
    }

    @Test
    @Transactional
    public void getAllDiagnosticsByNoticeIsInShouldWork() throws Exception {
        // Initialize the database
        diagnosticRepository.saveAndFlush(diagnostic);

        // Get all the diagnosticList where notice in DEFAULT_NOTICE or UPDATED_NOTICE
        defaultDiagnosticShouldBeFound("notice.in=" + DEFAULT_NOTICE + "," + UPDATED_NOTICE);

        // Get all the diagnosticList where notice equals to UPDATED_NOTICE
        defaultDiagnosticShouldNotBeFound("notice.in=" + UPDATED_NOTICE);
    }

    @Test
    @Transactional
    public void getAllDiagnosticsByNoticeIsNullOrNotNull() throws Exception {
        // Initialize the database
        diagnosticRepository.saveAndFlush(diagnostic);

        // Get all the diagnosticList where notice is not null
        defaultDiagnosticShouldBeFound("notice.specified=true");

        // Get all the diagnosticList where notice is null
        defaultDiagnosticShouldNotBeFound("notice.specified=false");
    }
                @Test
    @Transactional
    public void getAllDiagnosticsByNoticeContainsSomething() throws Exception {
        // Initialize the database
        diagnosticRepository.saveAndFlush(diagnostic);

        // Get all the diagnosticList where notice contains DEFAULT_NOTICE
        defaultDiagnosticShouldBeFound("notice.contains=" + DEFAULT_NOTICE);

        // Get all the diagnosticList where notice contains UPDATED_NOTICE
        defaultDiagnosticShouldNotBeFound("notice.contains=" + UPDATED_NOTICE);
    }

    @Test
    @Transactional
    public void getAllDiagnosticsByNoticeNotContainsSomething() throws Exception {
        // Initialize the database
        diagnosticRepository.saveAndFlush(diagnostic);

        // Get all the diagnosticList where notice does not contain DEFAULT_NOTICE
        defaultDiagnosticShouldNotBeFound("notice.doesNotContain=" + DEFAULT_NOTICE);

        // Get all the diagnosticList where notice does not contain UPDATED_NOTICE
        defaultDiagnosticShouldBeFound("notice.doesNotContain=" + UPDATED_NOTICE);
    }


    @Test
    @Transactional
    public void getAllDiagnosticsByTherapeuticRegimeIsEqualToSomething() throws Exception {
        // Initialize the database
        diagnosticRepository.saveAndFlush(diagnostic);
        TherapeuticRegime therapeuticRegime = TherapeuticRegimeResourceIT.createEntity(em);
        em.persist(therapeuticRegime);
        em.flush();
        diagnostic.addTherapeuticRegime(therapeuticRegime);
        diagnosticRepository.saveAndFlush(diagnostic);
        Long therapeuticRegimeId = therapeuticRegime.getId();

        // Get all the diagnosticList where therapeuticRegime equals to therapeuticRegimeId
        defaultDiagnosticShouldBeFound("therapeuticRegimeId.equals=" + therapeuticRegimeId);

        // Get all the diagnosticList where therapeuticRegime equals to therapeuticRegimeId + 1
        defaultDiagnosticShouldNotBeFound("therapeuticRegimeId.equals=" + (therapeuticRegimeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDiagnosticShouldBeFound(String filter) throws Exception {
        restDiagnosticMockMvc.perform(get("/api/diagnostics?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(diagnostic.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].notice").value(hasItem(DEFAULT_NOTICE)));

        // Check, that the count call also returns 1
        restDiagnosticMockMvc.perform(get("/api/diagnostics/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDiagnosticShouldNotBeFound(String filter) throws Exception {
        restDiagnosticMockMvc.perform(get("/api/diagnostics?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDiagnosticMockMvc.perform(get("/api/diagnostics/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingDiagnostic() throws Exception {
        // Get the diagnostic
        restDiagnosticMockMvc.perform(get("/api/diagnostics/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDiagnostic() throws Exception {
        // Initialize the database
        diagnosticRepository.saveAndFlush(diagnostic);

        int databaseSizeBeforeUpdate = diagnosticRepository.findAll().size();

        // Update the diagnostic
        Diagnostic updatedDiagnostic = diagnosticRepository.findById(diagnostic.getId()).get();
        // Disconnect from session so that the updates on updatedDiagnostic are not directly saved in db
        em.detach(updatedDiagnostic);
        updatedDiagnostic
            .name(UPDATED_NAME)
            .notice(UPDATED_NOTICE);
        DiagnosticDTO diagnosticDTO = diagnosticMapper.toDto(updatedDiagnostic);

        restDiagnosticMockMvc.perform(put("/api/diagnostics")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(diagnosticDTO)))
            .andExpect(status().isOk());

        // Validate the Diagnostic in the database
        List<Diagnostic> diagnosticList = diagnosticRepository.findAll();
        assertThat(diagnosticList).hasSize(databaseSizeBeforeUpdate);
        Diagnostic testDiagnostic = diagnosticList.get(diagnosticList.size() - 1);
        assertThat(testDiagnostic.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDiagnostic.getNotice()).isEqualTo(UPDATED_NOTICE);
    }

    @Test
    @Transactional
    public void updateNonExistingDiagnostic() throws Exception {
        int databaseSizeBeforeUpdate = diagnosticRepository.findAll().size();

        // Create the Diagnostic
        DiagnosticDTO diagnosticDTO = diagnosticMapper.toDto(diagnostic);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDiagnosticMockMvc.perform(put("/api/diagnostics")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(diagnosticDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Diagnostic in the database
        List<Diagnostic> diagnosticList = diagnosticRepository.findAll();
        assertThat(diagnosticList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDiagnostic() throws Exception {
        // Initialize the database
        diagnosticRepository.saveAndFlush(diagnostic);

        int databaseSizeBeforeDelete = diagnosticRepository.findAll().size();

        // Delete the diagnostic
        restDiagnosticMockMvc.perform(delete("/api/diagnostics/{id}", diagnostic.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Diagnostic> diagnosticList = diagnosticRepository.findAll();
        assertThat(diagnosticList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
