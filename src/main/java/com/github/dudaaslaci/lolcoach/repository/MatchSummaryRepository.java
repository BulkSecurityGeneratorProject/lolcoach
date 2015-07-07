package com.github.dudaaslaci.lolcoach.repository;

import com.github.dudaaslaci.lolcoach.domain.MatchSummary;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the MatchSummary entity.
 */
public interface MatchSummaryRepository extends JpaRepository<MatchSummary,Long> {

}
