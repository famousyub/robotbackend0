package com.example.polls.rest;


import com.example.polls.dto.RolesDTO;
import com.example.polls.service.RolesService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping(value = "/api/roless", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin("*")
public class RolesResource {

    private final RolesService rolesService;

    public RolesResource(final RolesService rolesService) {
        this.rolesService = rolesService;
    }

    @GetMapping
    public ResponseEntity<List<RolesDTO>> getAllRoless() {
        return ResponseEntity.ok(rolesService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RolesDTO> getRoles(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(rolesService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createRoles(@RequestBody @Valid final RolesDTO rolesDTO) {

        final Long createdId = rolesService.create(rolesDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateRoles(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final RolesDTO rolesDTO) {
        rolesService.update(id, rolesDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteRoles(@PathVariable(name = "id") final Long id) {
        rolesService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
