package com.vencislav.carserviceapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.vencislav.carserviceapp.domain.Visit;

import com.vencislav.carserviceapp.repository.VisitRepository;
import com.vencislav.carserviceapp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Visit.
 */
@RestController
@RequestMapping("/api")
public class VisitResource {

    private final Logger log = LoggerFactory.getLogger(VisitResource.class);

    private static final String ENTITY_NAME = "visit";
        
    private final VisitRepository visitRepository;

    public VisitResource(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    /**
     * POST  /visits : Create a new visit.
     *
     * @param visit the visit to create
     * @return the ResponseEntity with status 201 (Created) and with body the new visit, or with status 400 (Bad Request) if the visit has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/visits")
    @Timed
    public ResponseEntity<Visit> createVisit(@Valid @RequestBody Visit visit) throws URISyntaxException {
        log.debug("REST request to save Visit : {}", visit);
        if (visit.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new visit cannot already have an ID")).body(null);
        }
        Visit result = visitRepository.save(visit);
        return ResponseEntity.created(new URI("/api/visits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /visits : Updates an existing visit.
     *
     * @param visit the visit to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated visit,
     * or with status 400 (Bad Request) if the visit is not valid,
     * or with status 500 (Internal Server Error) if the visit couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/visits")
    @Timed
    public ResponseEntity<Visit> updateVisit(@Valid @RequestBody Visit visit) throws URISyntaxException {
        log.debug("REST request to update Visit : {}", visit);
        if (visit.getId() == null) {
            return createVisit(visit);
        }
        Visit result = visitRepository.save(visit);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, visit.getId().toString()))
            .body(result);
    }

    /**
     * GET  /visits : get all the visits.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of visits in body
     */
    @GetMapping("/visits")
    @Timed
    public List<Visit> getAllVisits() {
        log.debug("REST request to get all Visits");
        List<Visit> visits = visitRepository.findAll();
        return visits;
    }

    /**
     * GET  /visits/:id : get the "id" visit.
     *
     * @param id the id of the visit to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the visit, or with status 404 (Not Found)
     */
    @GetMapping("/visits/{id}")
    @Timed
    public ResponseEntity<Visit> getVisit(@PathVariable Long id) {
        log.debug("REST request to get Visit : {}", id);
        Visit visit = visitRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(visit));
    }

    /**
     * DELETE  /visits/:id : delete the "id" visit.
     *
     * @param id the id of the visit to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/visits/{id}")
    @Timed
    public ResponseEntity<Void> deleteVisit(@PathVariable Long id) {
        log.debug("REST request to delete Visit : {}", id);
        visitRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}