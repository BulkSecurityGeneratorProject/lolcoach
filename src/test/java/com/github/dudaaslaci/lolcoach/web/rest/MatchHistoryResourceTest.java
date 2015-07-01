package com.github.dudaaslaci.lolcoach.web.rest;

import com.github.dudaaslaci.lolcoach.Application;
import com.github.dudaaslaci.lolcoach.domain.MatchHistory;
import com.github.dudaaslaci.lolcoach.repository.MatchHistoryRepository;

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
 * Test class for the MatchHistoryResource REST controller.
 *
 * @see MatchHistoryResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class MatchHistoryResourceTest {


    @Inject
    private MatchHistoryRepository matchHistoryRepository;

    private MockMvc restMatchHistoryMockMvc;

    private MatchHistory matchHistory;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MatchHistoryResource matchHistoryResource = new MatchHistoryResource();
        ReflectionTestUtils.setField(matchHistoryResource, "matchHistoryRepository", matchHistoryRepository);
        this.restMatchHistoryMockMvc = MockMvcBuilders.standaloneSetup(matchHistoryResource).build();
    }

    @Before
    public void initTest() {
        matchHistory = new MatchHistory();
    }

    @Test
    @Transactional
    public void createMatchHistory() throws Exception {
        int databaseSizeBeforeCreate = matchHistoryRepository.findAll().size();

        // Create the MatchHistory
        restMatchHistoryMockMvc.perform(post("/api/matchHistorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(matchHistory)))
                .andExpect(status().isCreated());

        // Validate the MatchHistory in the database
        List<MatchHistory> matchHistorys = matchHistoryRepository.findAll();
        assertThat(matchHistorys).hasSize(databaseSizeBeforeCreate + 1);
        MatchHistory testMatchHistory = matchHistorys.get(matchHistorys.size() - 1);
    }

    @Test
    @Transactional
    public void getAllMatchHistorys() throws Exception {
        // Initialize the database
        matchHistoryRepository.saveAndFlush(matchHistory);

        // Get all the matchHistorys
        restMatchHistoryMockMvc.perform(get("/api/matchHistorys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(matchHistory.getId().intValue())));
    }

    @Test
    @Transactional
    public void getMatchHistory() throws Exception {
        // Initialize the database
        matchHistoryRepository.saveAndFlush(matchHistory);

        // Get the matchHistory
        restMatchHistoryMockMvc.perform(get("/api/matchHistorys/{id}", matchHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(matchHistory.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMatchHistory() throws Exception {
        // Get the matchHistory
        restMatchHistoryMockMvc.perform(get("/api/matchHistorys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMatchHistory() throws Exception {
        // Initialize the database
        matchHistoryRepository.saveAndFlush(matchHistory);

		int databaseSizeBeforeUpdate = matchHistoryRepository.findAll().size();

        // Update the matchHistory
        restMatchHistoryMockMvc.perform(put("/api/matchHistorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(matchHistory)))
                .andExpect(status().isOk());

        // Validate the MatchHistory in the database
        List<MatchHistory> matchHistorys = matchHistoryRepository.findAll();
        assertThat(matchHistorys).hasSize(databaseSizeBeforeUpdate);
        MatchHistory testMatchHistory = matchHistorys.get(matchHistorys.size() - 1);
    }

    @Test
    @Transactional
    public void deleteMatchHistory() throws Exception {
        // Initialize the database
        matchHistoryRepository.saveAndFlush(matchHistory);

		int databaseSizeBeforeDelete = matchHistoryRepository.findAll().size();

        // Get the matchHistory
        restMatchHistoryMockMvc.perform(delete("/api/matchHistorys/{id}", matchHistory.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<MatchHistory> matchHistorys = matchHistoryRepository.findAll();
        assertThat(matchHistorys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
