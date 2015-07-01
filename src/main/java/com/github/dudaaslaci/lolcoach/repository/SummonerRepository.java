package com.github.dudaaslaci.lolcoach.repository;

import com.github.dudaaslaci.lolcoach.domain.Summoner;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Summoner entity.
 */
public interface SummonerRepository extends JpaRepository<Summoner,Long> {

}
