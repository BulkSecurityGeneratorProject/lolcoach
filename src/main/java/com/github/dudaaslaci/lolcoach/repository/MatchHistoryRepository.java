package com.github.dudaaslaci.lolcoach.repository;

import com.github.dudaaslaci.lolcoach.domain.MatchHistory;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the MatchHistory entity.
 */
public interface MatchHistoryRepository extends JpaRepository<MatchHistory,Long> {

}
