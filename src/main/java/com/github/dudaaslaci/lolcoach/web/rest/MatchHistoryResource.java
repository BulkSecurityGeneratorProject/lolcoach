package com.github.dudaaslaci.lolcoach.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.github.dudaaslaci.lolcoach.domain.MatchHistory;
import com.github.dudaaslaci.lolcoach.repository.MatchHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing MatchHistory.
 */
@RestController
@RequestMapping("/api")
public class MatchHistoryResource {

    private final Logger log = LoggerFactory.getLogger(MatchHistoryResource.class);

    @Inject
    private MatchHistoryRepository matchHistoryRepository;

    /**
     * POST  /matchHistorys -> Create a new matchHistory.
     */
    @RequestMapping(value = "/matchHistorys",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody MatchHistory matchHistory) throws URISyntaxException {
        log.debug("REST request to save MatchHistory : {}", matchHistory);
        if (matchHistory.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new matchHistory cannot already have an ID").build();
        }
        matchHistoryRepository.save(matchHistory);
        return ResponseEntity.created(new URI("/api/matchHistorys/" + matchHistory.getId())).build();
    }

    /**
     * PUT  /matchHistorys -> Updates an existing matchHistory.
     */
    @RequestMapping(value = "/matchHistorys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody MatchHistory matchHistory) throws URISyntaxException {
        log.debug("REST request to update MatchHistory : {}", matchHistory);
        if (matchHistory.getId() == null) {
            return create(matchHistory);
        }
        matchHistoryRepository.save(matchHistory);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /matchHistorys -> get all the matchHistorys.
     */
    @RequestMapping(value = "/matchHistorys",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<MatchHistory> getAll() {
        log.debug("REST request to get all MatchHistorys");
        return matchHistoryRepository.findAll();
    }

    /**
     * GET  /matchHistorys/:id -> get the "id" matchHistory.
     */
    @RequestMapping(value = "/matchHistorys/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MatchHistory> get(@PathVariable Long id) {
        log.debug("REST request to get MatchHistory : {}", id);
        return Optional.ofNullable(matchHistoryRepository.findOne(id))
            .map(matchHistory -> new ResponseEntity<>(
                matchHistory,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /matchHistorys/:id -> delete the "id" matchHistory.
     */
    @RequestMapping(value = "/matchHistorys/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete MatchHistory : {}", id);
        matchHistoryRepository.delete(id);
    }
}
