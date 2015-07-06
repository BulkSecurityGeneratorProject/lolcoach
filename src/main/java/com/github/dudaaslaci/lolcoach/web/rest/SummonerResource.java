package com.github.dudaaslaci.lolcoach.web.rest;

import java.util.Optional;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.github.dudaaslaci.lolcoach.domain.Summoner;
import com.github.dudaaslaci.lolcoach.service.SummonerService;

/**
 * REST controller for managing Summoner.
 */
@RestController
@RequestMapping("/api")
public class SummonerResource {

    private final Logger log = LoggerFactory.getLogger(SummonerResource.class);

    @Inject
    private SummonerService summonerService;

    /**
     * GET /summoners/:id -> get the "id" summoner.
     */
    @RequestMapping(value = "/summoners/{region}/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Summoner> get(@PathVariable String region, @PathVariable Long id) {
        log.debug("REST request to get Summoner : {}", id);
        return Optional.ofNullable(summonerService.findById(region, id))
                .map(summoner -> new ResponseEntity<>(summoner, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET /summoners/:region/:name -> get the "name" summoner from "region" region.
     */
    @RequestMapping(value = "/summoners/by-name/{region}/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Summoner> getByName(@PathVariable String region, @PathVariable String name) {
        log.debug("REST request to get Summoner : {}/{}", region, name);
        return Optional.ofNullable(summonerService.findByRegionAndName(region, name))
                .map(summoner -> new ResponseEntity<>(summoner, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
