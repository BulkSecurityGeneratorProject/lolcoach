package com.github.dudaaslaci.lolcoach.web.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

import com.github.dudaaslaci.lolcoach.Application;
import com.github.dudaaslaci.lolcoach.domain.Summoner;
import com.github.dudaaslaci.lolcoach.repository.SummonerRepository;

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

    private static final String DEFAULT_NAME = "printy";
    private static final String DEFAULT_REGION = "eune";

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
    public void getSummoner() throws Exception {
        // Initialize the database
        summonerRepository.saveAndFlush(summoner);

        // Get the summoner
        restSummonerMockMvc.perform(get("/api/summoners/{id}", summoner.getId())).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(summoner.getId().intValue()))
                .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
                .andExpect(jsonPath("$.region").value(DEFAULT_REGION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSummoner() throws Exception {
        // Get the summoner
        restSummonerMockMvc.perform(get("/api/summoners/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }
    
    @Test
    @Transactional
    public void getSummonerByName() throws Exception {
        restSummonerMockMvc.perform(get("/api/summoners/{region}/{name}", summoner.getRegion(), summoner.getName()))
                        .andExpect(status().isOk())
                        //.andExpect(jsonPath("$.id").value(summoner.getId().intValue()))
                        .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
                        .andExpect(jsonPath("$.region").value(DEFAULT_REGION.toString()));
    }
}
