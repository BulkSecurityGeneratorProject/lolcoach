package com.github.dudaaslaci.lolcoach.service;

import java.util.Optional;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.dudaaslaci.lolcoach.domain.Summoner;
import com.github.dudaaslaci.lolcoach.repository.SummonerRepository;

@Service
public class SummonerService {


    
    @Inject
    private SummonerRepository summonerRepository;
    
    @Inject
    private RiotService riotService;

    public Summoner findByRegionAndName(String region, String name) {
        Summoner summoner = summonerRepository.findOneByRegionAndName(region, name);
        if (null != summoner) 
            return summoner;
        summoner = riotService.getSummonerByName(region, name);
        summonerRepository.saveAndFlush(summoner);
        
        return summoner;
    }

}
