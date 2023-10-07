package com.example.polls.rest;


import com.example.polls.dto.PlanificationsDTO;
import com.example.polls.service.PlanificationsService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping(value = "/api/planificationss", produces = MediaType.APPLICATION_JSON_VALUE)
public class PlanificationsResource {

    private final PlanificationsService planificationsService;

    public PlanificationsResource(final PlanificationsService planificationsService) {
        this.planificationsService = planificationsService;
    }

    @GetMapping
    public ResponseEntity<List<PlanificationsDTO>> getAllPlanificationss() {
        return ResponseEntity.ok(planificationsService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanificationsDTO> getPlanifications(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(planificationsService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createPlanifications(
            @RequestBody @Valid final PlanificationsDTO planificationsDTO) {
        planificationsDTO.setDate(LocalDateTime.now());

        final Long createdId = planificationsService.create(planificationsDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updatePlanifications(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final PlanificationsDTO planificationsDTO) {
        planificationsService.update(id, planificationsDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePlanifications(@PathVariable(name = "id") final Long id) {
        planificationsService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
