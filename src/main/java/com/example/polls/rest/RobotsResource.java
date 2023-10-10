package com.example.polls.rest;


import com.example.polls.dto.RobotsDTO;
import com.example.polls.model.Excution;
import com.example.polls.model.Planifications;
import com.example.polls.model.Robots;
import com.example.polls.model.User;
import com.example.polls.repository.ExcutionRepository;
import com.example.polls.repository.PlanificationRepository;
import com.example.polls.repository.RobotRepository;
import com.example.polls.repository.UserRepository;
import com.example.polls.security.CurrentUser;
import com.example.polls.security.UserPrincipal;
import com.example.polls.service.RobotsService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping(value = "/api/robotss", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin("*")
public class RobotsResource {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RobotRepository robotRepository;

    @Autowired
    private PlanificationRepository planificationRepository;

    @Autowired
    private ExcutionRepository excutionRepository;

    private final RobotsService robotsService;

    public RobotsResource(final RobotsService robotsService) {
        this.robotsService = robotsService;
    }

    @GetMapping
    public ResponseEntity<List<RobotsDTO>> getAllRobotss() {
        return ResponseEntity.ok(robotsService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RobotsDTO> getRobots(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(robotsService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createRobots(@RequestBody @Valid final RobotsDTO robotsDTO,

                                             @CurrentUser UserPrincipal currentUser) {

        User me= userRepository.findById(currentUser.getId()).get();
        robotsDTO.setUser(me.getId());
        final Long createdId = robotsService.create(robotsDTO,me);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{robId}/{excutId}/{planId}")
    @ApiResponse(responseCode = "200")
    public ResponseEntity<Long> affecteRobots(@PathVariable("robId") Long robotId,
                                              @PathVariable("excutId") Long excid,
                                              @PathVariable("planId") Long palnId,
                                             @CurrentUser UserPrincipal currentUser) {

        User me= userRepository.findById(currentUser.getId()).get();
        Robots r = robotRepository.findById(robotId).get();
        Excution excut = excutionRepository.findById(excid).get();
        Planifications plan = planificationRepository.findById(palnId).get();
        r.setPlanification(plan);
        r.setExcution(excut);
        r.setId(robotId);
        robotRepository.save(r);
        return new ResponseEntity<>(robotId, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Long> updateRobots(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final RobotsDTO robotsDTO) {
        robotsDTO.setId(robotsDTO.getId());

        robotsService.update(id, robotsDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteRobots(@PathVariable(name = "id") final Long id) {
        robotsService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
