package com.github.dudaaslaci.lolcoach.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.github.dudaaslaci.lolcoach.domain.MatchSummary;
import com.github.dudaaslaci.lolcoach.repository.MatchSummaryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing MatchSummary.
 */
@RestController
@RequestMapping("/api")
public class MatchSummaryResource {

    private final Logger log = LoggerFactory.getLogger(MatchSummaryResource.class);

    @Inject
    private MatchSummaryRepository matchSummaryRepository;

    /**
     * POST  /matchSummarys -> Create a new matchSummary.
     */
    @RequestMapping(value = "/matchSummarys",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody MatchSummary matchSummary) throws URISyntaxException {
        log.debug("REST request to save MatchSummary : {}", matchSummary);
        if (matchSummary.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new matchSummary cannot already have an ID").build();
        }
        matchSummaryRepository.save(matchSummary);
        return ResponseEntity.created(new URI("/api/matchSummarys/" + matchSummary.getId())).build();
    }

    /**
     * PUT  /matchSummarys -> Updates an existing matchSummary.
     */
    @RequestMapping(value = "/matchSummarys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody MatchSummary matchSummary) throws URISyntaxException {
        log.debug("REST request to update MatchSummary : {}", matchSummary);
        if (matchSummary.getId() == null) {
            return create(matchSummary);
        }
        matchSummaryRepository.save(matchSummary);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /matchSummarys -> get all the matchSummarys.
     */
    @RequestMapping(value = "/matchSummarys",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<MatchSummary> getAll() {
        log.debug("REST request to get all MatchSummarys");
        return matchSummaryRepository.findAll();
    }

    /**
     * GET  /matchSummarys/:id -> get the "id" matchSummary.
     */
    @RequestMapping(value = "/matchSummarys/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MatchSummary> get(@PathVariable Long id) {
        log.debug("REST request to get MatchSummary : {}", id);
        return Optional.ofNullable(matchSummaryRepository.findOne(id))
            .map(matchSummary -> new ResponseEntity<>(
                matchSummary,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /matchSummarys/:id -> delete the "id" matchSummary.
     */
    @RequestMapping(value = "/matchSummarys/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete MatchSummary : {}", id);
        matchSummaryRepository.delete(id);
    }
}
