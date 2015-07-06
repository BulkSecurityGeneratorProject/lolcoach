package com.github.dudaaslaci.lolcoach.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.github.dudaaslaci.lolcoach.domain.Summoner;

@Service
public class RiotService {
    private static final String SUMMONER_PATH = "/summoner/";
    private static final String SUMMONER_BY_NAME_PATH = "/summoner/by-name/";
    private static final String RIOT_EUNE_API_URL = "https://eune.api.pvp.net/api/lol/";
    private static final String VERSION_PATH = "/v";
    private static final String RIOT_SUMMONER_API_VERSION = "1.4";
    private static final String API_KEY_PATH = "?api_key=";

    @Value("${riot-api.key}") private String RIOT_API_KEY;

    public Summoner getSummonerByName(String region, String name) {
        RestTemplate restTemplate = new RestTemplate();
        ParameterizedTypeReference<Map<String, Summoner>> responseType = new ParameterizedTypeReference<Map<String, Summoner>>() {};
        Summoner summoner = null;
        try {
        summoner = restTemplate.exchange(
                RIOT_EUNE_API_URL
                + region
                + VERSION_PATH
                + RIOT_SUMMONER_API_VERSION
                + SUMMONER_BY_NAME_PATH
                + name
                + API_KEY_PATH
                + RIOT_API_KEY,
                HttpMethod.GET, null, responseType).getBody().get(name.toLowerCase());
        } catch (HttpClientErrorException e) {
            return null;
        }
        summoner.setRegion(region);
        return summoner;
    }

    public Summoner getSummonerById(String region, Long id) {
        RestTemplate restTemplate = new RestTemplate();
        ParameterizedTypeReference<Map<Long, Summoner>> responseType = new ParameterizedTypeReference<Map<Long, Summoner>>() {};
        Summoner summoner = null;
        try {
        summoner = restTemplate.exchange(
                RIOT_EUNE_API_URL
                + region
                + VERSION_PATH
                + RIOT_SUMMONER_API_VERSION
                + SUMMONER_PATH
                + id
                + API_KEY_PATH
                + RIOT_API_KEY,
                HttpMethod.GET, null, responseType).getBody().get(id);
        } catch (HttpClientErrorException e) {
            return null;
        }

        return summoner;
    }

}
