package com.github.dudaaslaci.lolcoach.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.dudaaslaci.lolcoach.domain.Summoner;

/**
 * Spring Data JPA repository for the Summoner entity.
 */
public interface SummonerRepository extends JpaRepository<Summoner,Long> {

    Summoner findOneByRegionAndName(String region, String name);

}
