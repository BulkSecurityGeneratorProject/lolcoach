package com.github.dudaaslaci.lolcoach.web.rest;

import com.github.dudaaslaci.lolcoach.Application;
import com.github.dudaaslaci.lolcoach.domain.MatchSummary;
import com.github.dudaaslaci.lolcoach.repository.MatchSummaryRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MatchSummaryResource REST controller.
 *
 * @see MatchSummaryResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class MatchSummaryResourceTest {


    private static final Long DEFAULT_MATCH_CREATION = 0L;
    private static final Long UPDATED_MATCH_CREATION = 1L;

    private static final Integer DEFAULT_MAP_ID = 0;
    private static final Integer UPDATED_MAP_ID = 1;

    private static final Long DEFAULT_MATCH_DURATION = 0L;
    private static final Long UPDATED_MATCH_DURATION = 1L;

    private static final Long DEFAULT_MATCH_ID = 0L;
    private static final Long UPDATED_MATCH_ID = 1L;
    private static final String DEFAULT_MATCH_MODE = "CLASSIC";
    private static final String UPDATED_MATCH_MODE = "ARAM";
    private static final String DEFAULT_MATCH_TYPE = "MATCHED_GAME";
    private static final String UPDATED_MATCH_TYPE = "CUSTOM_GAME";
    private static final String DEFAULT_MATCH_VERSION = "SAMPLE_TEXT";
    private static final String UPDATED_MATCH_VERSION = "UPDATED_TEXT";
    private static final String DEFAULT_PLATFORM_ID = "SAMPLE_TEXT";
    private static final String UPDATED_PLATFORM_ID = "UPDATED_TEXT";
    private static final String DEFAULT_REGION = "SAMPLE_TEXT";
    private static final String UPDATED_REGION = "UPDATED_TEXT";
    private static final String DEFAULT_SEASON = "SAMPLE_TEXT";
    private static final String UPDATED_SEASON = "UPDATED_TEXT";

    @Inject
    private MatchSummaryRepository matchSummaryRepository;

    private MockMvc restMatchSummaryMockMvc;

    private MatchSummary matchSummary;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MatchSummaryResource matchSummaryResource = new MatchSummaryResource();
        ReflectionTestUtils.setField(matchSummaryResource, "matchSummaryRepository", matchSummaryRepository);
        this.restMatchSummaryMockMvc = MockMvcBuilders.standaloneSetup(matchSummaryResource).build();
    }

    @Before
    public void initTest() {
        matchSummary = new MatchSummary();
        matchSummary.setMatchCreation(DEFAULT_MATCH_CREATION);
        matchSummary.setMapId(DEFAULT_MAP_ID);
        matchSummary.setMatchDuration(DEFAULT_MATCH_DURATION);
        matchSummary.setMatchId(DEFAULT_MATCH_ID);
        matchSummary.setMatchMode(DEFAULT_MATCH_MODE);
        matchSummary.setMatchType(DEFAULT_MATCH_TYPE);
        matchSummary.setMatchVersion(DEFAULT_MATCH_VERSION);
        matchSummary.setPlatformId(DEFAULT_PLATFORM_ID);
        matchSummary.setRegion(DEFAULT_REGION);
        matchSummary.setSeason(DEFAULT_SEASON);
    }

    @Test
    @Transactional
    public void createMatchSummary() throws Exception {
        int databaseSizeBeforeCreate = matchSummaryRepository.findAll().size();

        // Create the MatchSummary
        restMatchSummaryMockMvc.perform(post("/api/matchSummarys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(matchSummary)))
                .andExpect(status().isCreated());

        // Validate the MatchSummary in the database
        List<MatchSummary> matchSummarys = matchSummaryRepository.findAll();
        assertThat(matchSummarys).hasSize(databaseSizeBeforeCreate + 1);
        MatchSummary testMatchSummary = matchSummarys.get(matchSummarys.size() - 1);
        assertThat(testMatchSummary.getMatchCreation()).isEqualTo(DEFAULT_MATCH_CREATION);
        assertThat(testMatchSummary.getMapId()).isEqualTo(DEFAULT_MAP_ID);
        assertThat(testMatchSummary.getMatchDuration()).isEqualTo(DEFAULT_MATCH_DURATION);
        assertThat(testMatchSummary.getMatchId()).isEqualTo(DEFAULT_MATCH_ID);
        assertThat(testMatchSummary.getMatchMode()).isEqualTo(DEFAULT_MATCH_MODE);
        assertThat(testMatchSummary.getMatchType()).isEqualTo(DEFAULT_MATCH_TYPE);
        assertThat(testMatchSummary.getMatchVersion()).isEqualTo(DEFAULT_MATCH_VERSION);
        assertThat(testMatchSummary.getPlatformId()).isEqualTo(DEFAULT_PLATFORM_ID);
        assertThat(testMatchSummary.getRegion()).isEqualTo(DEFAULT_REGION);
        assertThat(testMatchSummary.getSeason()).isEqualTo(DEFAULT_SEASON);
    }

    @Test
    @Transactional
    public void checkMatchCreationIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(matchSummaryRepository.findAll()).hasSize(0);
        // set the field null
        matchSummary.setMatchCreation(null);

        // Create the MatchSummary, which fails.
        restMatchSummaryMockMvc.perform(post("/api/matchSummarys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(matchSummary)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<MatchSummary> matchSummarys = matchSummaryRepository.findAll();
        assertThat(matchSummarys).hasSize(0);
    }

    @Test
    @Transactional
    public void checkMapIdIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(matchSummaryRepository.findAll()).hasSize(0);
        // set the field null
        matchSummary.setMapId(null);

        // Create the MatchSummary, which fails.
        restMatchSummaryMockMvc.perform(post("/api/matchSummarys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(matchSummary)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<MatchSummary> matchSummarys = matchSummaryRepository.findAll();
        assertThat(matchSummarys).hasSize(0);
    }

    @Test
    @Transactional
    public void checkMatchDurationIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(matchSummaryRepository.findAll()).hasSize(0);
        // set the field null
        matchSummary.setMatchDuration(null);

        // Create the MatchSummary, which fails.
        restMatchSummaryMockMvc.perform(post("/api/matchSummarys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(matchSummary)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<MatchSummary> matchSummarys = matchSummaryRepository.findAll();
        assertThat(matchSummarys).hasSize(0);
    }

    @Test
    @Transactional
    public void checkMatchIdIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(matchSummaryRepository.findAll()).hasSize(0);
        // set the field null
        matchSummary.setMatchId(null);

        // Create the MatchSummary, which fails.
        restMatchSummaryMockMvc.perform(post("/api/matchSummarys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(matchSummary)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<MatchSummary> matchSummarys = matchSummaryRepository.findAll();
        assertThat(matchSummarys).hasSize(0);
    }

    @Test
    @Transactional
    public void checkMatchModeIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(matchSummaryRepository.findAll()).hasSize(0);
        // set the field null
        matchSummary.setMatchMode(null);

        // Create the MatchSummary, which fails.
        restMatchSummaryMockMvc.perform(post("/api/matchSummarys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(matchSummary)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<MatchSummary> matchSummarys = matchSummaryRepository.findAll();
        assertThat(matchSummarys).hasSize(0);
    }

    @Test
    @Transactional
    public void checkMatchTypeIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(matchSummaryRepository.findAll()).hasSize(0);
        // set the field null
        matchSummary.setMatchType(null);

        // Create the MatchSummary, which fails.
        restMatchSummaryMockMvc.perform(post("/api/matchSummarys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(matchSummary)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<MatchSummary> matchSummarys = matchSummaryRepository.findAll();
        assertThat(matchSummarys).hasSize(0);
    }

    @Test
    @Transactional
    public void checkMatchVersionIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(matchSummaryRepository.findAll()).hasSize(0);
        // set the field null
        matchSummary.setMatchVersion(null);

        // Create the MatchSummary, which fails.
        restMatchSummaryMockMvc.perform(post("/api/matchSummarys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(matchSummary)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<MatchSummary> matchSummarys = matchSummaryRepository.findAll();
        assertThat(matchSummarys).hasSize(0);
    }

    @Test
    @Transactional
    public void checkPlatformIdIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(matchSummaryRepository.findAll()).hasSize(0);
        // set the field null
        matchSummary.setPlatformId(null);

        // Create the MatchSummary, which fails.
        restMatchSummaryMockMvc.perform(post("/api/matchSummarys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(matchSummary)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<MatchSummary> matchSummarys = matchSummaryRepository.findAll();
        assertThat(matchSummarys).hasSize(0);
    }

    @Test
    @Transactional
    public void checkRegionIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(matchSummaryRepository.findAll()).hasSize(0);
        // set the field null
        matchSummary.setRegion(null);

        // Create the MatchSummary, which fails.
        restMatchSummaryMockMvc.perform(post("/api/matchSummarys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(matchSummary)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<MatchSummary> matchSummarys = matchSummaryRepository.findAll();
        assertThat(matchSummarys).hasSize(0);
    }

    @Test
    @Transactional
    public void checkSeasonIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(matchSummaryRepository.findAll()).hasSize(0);
        // set the field null
        matchSummary.setSeason(null);

        // Create the MatchSummary, which fails.
        restMatchSummaryMockMvc.perform(post("/api/matchSummarys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(matchSummary)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<MatchSummary> matchSummarys = matchSummaryRepository.findAll();
        assertThat(matchSummarys).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllMatchSummarys() throws Exception {
        // Initialize the database
        matchSummaryRepository.saveAndFlush(matchSummary);

        // Get all the matchSummarys
        restMatchSummaryMockMvc.perform(get("/api/matchSummarys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(matchSummary.getId().intValue())))
                .andExpect(jsonPath("$.[*].matchCreation").value(hasItem(DEFAULT_MATCH_CREATION.intValue())))
                .andExpect(jsonPath("$.[*].mapId").value(hasItem(DEFAULT_MAP_ID)))
                .andExpect(jsonPath("$.[*].matchDuration").value(hasItem(DEFAULT_MATCH_DURATION.intValue())))
                .andExpect(jsonPath("$.[*].matchId").value(hasItem(DEFAULT_MATCH_ID.intValue())))
                .andExpect(jsonPath("$.[*].matchMode").value(hasItem(DEFAULT_MATCH_MODE.toString())))
                .andExpect(jsonPath("$.[*].matchType").value(hasItem(DEFAULT_MATCH_TYPE.toString())))
                .andExpect(jsonPath("$.[*].matchVersion").value(hasItem(DEFAULT_MATCH_VERSION.toString())))
                .andExpect(jsonPath("$.[*].platformId").value(hasItem(DEFAULT_PLATFORM_ID.toString())))
                .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION.toString())))
                .andExpect(jsonPath("$.[*].season").value(hasItem(DEFAULT_SEASON.toString())));
    }

    @Test
    @Transactional
    public void getMatchSummary() throws Exception {
        // Initialize the database
        matchSummaryRepository.saveAndFlush(matchSummary);

        // Get the matchSummary
        restMatchSummaryMockMvc.perform(get("/api/matchSummarys/{id}", matchSummary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(matchSummary.getId().intValue()))
            .andExpect(jsonPath("$.matchCreation").value(DEFAULT_MATCH_CREATION.intValue()))
            .andExpect(jsonPath("$.mapId").value(DEFAULT_MAP_ID))
            .andExpect(jsonPath("$.matchDuration").value(DEFAULT_MATCH_DURATION.intValue()))
            .andExpect(jsonPath("$.matchId").value(DEFAULT_MATCH_ID.intValue()))
            .andExpect(jsonPath("$.matchMode").value(DEFAULT_MATCH_MODE.toString()))
            .andExpect(jsonPath("$.matchType").value(DEFAULT_MATCH_TYPE.toString()))
            .andExpect(jsonPath("$.matchVersion").value(DEFAULT_MATCH_VERSION.toString()))
            .andExpect(jsonPath("$.platformId").value(DEFAULT_PLATFORM_ID.toString()))
            .andExpect(jsonPath("$.region").value(DEFAULT_REGION.toString()))
            .andExpect(jsonPath("$.season").value(DEFAULT_SEASON.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMatchSummary() throws Exception {
        // Get the matchSummary
        restMatchSummaryMockMvc.perform(get("/api/matchSummarys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMatchSummary() throws Exception {
        // Initialize the database
        matchSummaryRepository.saveAndFlush(matchSummary);

		int databaseSizeBeforeUpdate = matchSummaryRepository.findAll().size();

        // Update the matchSummary
        matchSummary.setMatchCreation(UPDATED_MATCH_CREATION);
        matchSummary.setMapId(UPDATED_MAP_ID);
        matchSummary.setMatchDuration(UPDATED_MATCH_DURATION);
        matchSummary.setMatchId(UPDATED_MATCH_ID);
        matchSummary.setMatchMode(UPDATED_MATCH_MODE);
        matchSummary.setMatchType(UPDATED_MATCH_TYPE);
        matchSummary.setMatchVersion(UPDATED_MATCH_VERSION);
        matchSummary.setPlatformId(UPDATED_PLATFORM_ID);
        matchSummary.setRegion(UPDATED_REGION);
        matchSummary.setSeason(UPDATED_SEASON);
        restMatchSummaryMockMvc.perform(put("/api/matchSummarys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(matchSummary)))
                .andExpect(status().isOk());

        // Validate the MatchSummary in the database
        List<MatchSummary> matchSummarys = matchSummaryRepository.findAll();
        assertThat(matchSummarys).hasSize(databaseSizeBeforeUpdate);
        MatchSummary testMatchSummary = matchSummarys.get(matchSummarys.size() - 1);
        assertThat(testMatchSummary.getMatchCreation()).isEqualTo(UPDATED_MATCH_CREATION);
        assertThat(testMatchSummary.getMapId()).isEqualTo(UPDATED_MAP_ID);
        assertThat(testMatchSummary.getMatchDuration()).isEqualTo(UPDATED_MATCH_DURATION);
        assertThat(testMatchSummary.getMatchId()).isEqualTo(UPDATED_MATCH_ID);
        assertThat(testMatchSummary.getMatchMode()).isEqualTo(UPDATED_MATCH_MODE);
        assertThat(testMatchSummary.getMatchType()).isEqualTo(UPDATED_MATCH_TYPE);
        assertThat(testMatchSummary.getMatchVersion()).isEqualTo(UPDATED_MATCH_VERSION);
        assertThat(testMatchSummary.getPlatformId()).isEqualTo(UPDATED_PLATFORM_ID);
        assertThat(testMatchSummary.getRegion()).isEqualTo(UPDATED_REGION);
        assertThat(testMatchSummary.getSeason()).isEqualTo(UPDATED_SEASON);
    }

    @Test
    @Transactional
    public void deleteMatchSummary() throws Exception {
        // Initialize the database
        matchSummaryRepository.saveAndFlush(matchSummary);

		int databaseSizeBeforeDelete = matchSummaryRepository.findAll().size();

        // Get the matchSummary
        restMatchSummaryMockMvc.perform(delete("/api/matchSummarys/{id}", matchSummary.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<MatchSummary> matchSummarys = matchSummaryRepository.findAll();
        assertThat(matchSummarys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
