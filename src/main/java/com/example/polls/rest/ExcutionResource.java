package com.example.polls.rest;


import com.example.polls.dto.ExcutionDTO;
import com.example.polls.service.ExcutionService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping(value = "/api/excutions", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin("*")
public class ExcutionResource {

    private final ExcutionService excutionService;

    public ExcutionResource(final ExcutionService excutionService) {
        this.excutionService = excutionService;
    }

    @GetMapping
    public ResponseEntity<List<ExcutionDTO>> getAllExcutions() {
        return ResponseEntity.ok(excutionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExcutionDTO> getExcution(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(excutionService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createExcution(@RequestBody @Valid final ExcutionDTO excutionDTO) {
        excutionDTO.setDate(LocalDateTime.now());
        final Long createdId = excutionService.create(excutionDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateExcution(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final ExcutionDTO excutionDTO) {
        excutionService.update(id, excutionDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteExcution(@PathVariable(name = "id") final Long id) {
        excutionService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
