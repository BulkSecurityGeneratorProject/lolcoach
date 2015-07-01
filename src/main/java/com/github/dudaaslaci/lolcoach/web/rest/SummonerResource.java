package com.github.dudaaslaci.lolcoach.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.github.dudaaslaci.lolcoach.domain.Summoner;
import com.github.dudaaslaci.lolcoach.repository.SummonerRepository;
import com.github.dudaaslaci.lolcoach.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
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
 * REST controller for managing Summoner.
 */
@RestController
@RequestMapping("/api")
public class SummonerResource {

    private final Logger log = LoggerFactory.getLogger(SummonerResource.class);

    @Inject
    private SummonerRepository summonerRepository;

    /**
     * POST  /summoners -> Create a new summoner.
     */
    @RequestMapping(value = "/summoners",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Summoner summoner) throws URISyntaxException {
        log.debug("REST request to save Summoner : {}", summoner);
        if (summoner.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new summoner cannot already have an ID").build();
        }
        summonerRepository.save(summoner);
        return ResponseEntity.created(new URI("/api/summoners/" + summoner.getId())).build();
    }

    /**
     * PUT  /summoners -> Updates an existing summoner.
     */
    @RequestMapping(value = "/summoners",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Summoner summoner) throws URISyntaxException {
        log.debug("REST request to update Summoner : {}", summoner);
        if (summoner.getId() == null) {
            return create(summoner);
        }
        summonerRepository.save(summoner);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /summoners -> get all the summoners.
     */
    @RequestMapping(value = "/summoners",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Summoner>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Summoner> page = summonerRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/summoners", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /summoners/:id -> get the "id" summoner.
     */
    @RequestMapping(value = "/summoners/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Summoner> get(@PathVariable Long id) {
        log.debug("REST request to get Summoner : {}", id);
        return Optional.ofNullable(summonerRepository.findOne(id))
            .map(summoner -> new ResponseEntity<>(
                summoner,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /summoners/:id -> delete the "id" summoner.
     */
    @RequestMapping(value = "/summoners/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Summoner : {}", id);
        summonerRepository.delete(id);
    }
}
