package com.github.dudaaslaci.lolcoach.web.rest;

import com.github.dudaaslaci.lolcoach.Application;
import com.github.dudaaslaci.lolcoach.domain.Summoner;
import com.github.dudaaslaci.lolcoach.repository.SummonerRepository;

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
 * Test class for the SummonerResource REST controller.
 *
 * @see SummonerResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SummonerResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_REGION = "SAMPLE_TEXT";
    private static final String UPDATED_REGION = "UPDATED_TEXT";

    @Inject
    private SummonerRepository summonerRepository;

    private MockMvc restSummonerMockMvc;

    private Summoner summoner;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SummonerResource summonerResource = new SummonerResource();
        ReflectionTestUtils.setField(summonerResource, "summonerRepository", summonerRepository);
        this.restSummonerMockMvc = MockMvcBuilders.standaloneSetup(summonerResource).build();
    }

    @Before
    public void initTest() {
        summoner = new Summoner();
        summoner.setName(DEFAULT_NAME);
        summoner.setRegion(DEFAULT_REGION);
    }

    @Test
    @Transactional
    public void createSummoner() throws Exception {
        int databaseSizeBeforeCreate = summonerRepository.findAll().size();

        // Create the Summoner
        restSummonerMockMvc.perform(post("/api/summoners")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(summoner)))
                .andExpect(status().isCreated());

        // Validate the Summoner in the database
        List<Summoner> summoners = summonerRepository.findAll();
        assertThat(summoners).hasSize(databaseSizeBeforeCreate + 1);
        Summoner testSummoner = summoners.get(summoners.size() - 1);
        assertThat(testSummoner.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSummoner.getRegion()).isEqualTo(DEFAULT_REGION);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(summonerRepository.findAll()).hasSize(0);
        // set the field null
        summoner.setName(null);

        // Create the Summoner, which fails.
        restSummonerMockMvc.perform(post("/api/summoners")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(summoner)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Summoner> summoners = summonerRepository.findAll();
        assertThat(summoners).hasSize(0);
    }

    @Test
    @Transactional
    public void checkRegionIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(summonerRepository.findAll()).hasSize(0);
        // set the field null
        summoner.setRegion(null);

        // Create the Summoner, which fails.
        restSummonerMockMvc.perform(post("/api/summoners")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(summoner)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Summoner> summoners = summonerRepository.findAll();
        assertThat(summoners).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllSummoners() throws Exception {
        // Initialize the database
        summonerRepository.saveAndFlush(summoner);

        // Get all the summoners
        restSummonerMockMvc.perform(get("/api/summoners"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(summoner.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION.toString())));
    }

    @Test
    @Transactional
    public void getSummoner() throws Exception {
        // Initialize the database
        summonerRepository.saveAndFlush(summoner);

        // Get the summoner
        restSummonerMockMvc.perform(get("/api/summoners/{id}", summoner.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(summoner.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.region").value(DEFAULT_REGION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSummoner() throws Exception {
        // Get the summoner
        restSummonerMockMvc.perform(get("/api/summoners/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSummoner() throws Exception {
        // Initialize the database
        summonerRepository.saveAndFlush(summoner);

		int databaseSizeBeforeUpdate = summonerRepository.findAll().size();

        // Update the summoner
        summoner.setName(UPDATED_NAME);
        summoner.setRegion(UPDATED_REGION);
        restSummonerMockMvc.perform(put("/api/summoners")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(summoner)))
                .andExpect(status().isOk());

        // Validate the Summoner in the database
        List<Summoner> summoners = summonerRepository.findAll();
        assertThat(summoners).hasSize(databaseSizeBeforeUpdate);
        Summoner testSummoner = summoners.get(summoners.size() - 1);
        assertThat(testSummoner.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSummoner.getRegion()).isEqualTo(UPDATED_REGION);
    }

    @Test
    @Transactional
    public void deleteSummoner() throws Exception {
        // Initialize the database
        summonerRepository.saveAndFlush(summoner);

		int databaseSizeBeforeDelete = summonerRepository.findAll().size();

        // Get the summoner
        restSummonerMockMvc.perform(delete("/api/summoners/{id}", summoner.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Summoner> summoners = summonerRepository.findAll();
        assertThat(summoners).hasSize(databaseSizeBeforeDelete - 1);
    }
}
