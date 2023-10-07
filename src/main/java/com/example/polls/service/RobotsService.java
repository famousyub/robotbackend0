package com.example.polls.service;;


import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.example.polls.dto.RobotsDTO;
import com.example.polls.model.Excution;
import com.example.polls.model.Planifications;
import com.example.polls.model.Robots;
import com.example.polls.model.User;
import com.example.polls.repository.ExcutionRepository;
import com.example.polls.repository.PlanificationRepository;
import com.example.polls.repository.RobotRepository;
import com.example.polls.repository.UserRepository;
import com.example.polls.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class RobotsService {

    private final RobotRepository robotsRepository;
    private final UserRepository userRepository;
    private final PlanificationRepository planificationsRepository;
    private final ExcutionRepository excutionRepository;

    public RobotsService(final RobotRepository robotsRepository,
            final UserRepository userRepository,
            final PlanificationRepository planificationsRepository,
            final ExcutionRepository excutionRepository) {
        this.robotsRepository = robotsRepository;
        this.userRepository = userRepository;
        this.planificationsRepository = planificationsRepository;
        this.excutionRepository = excutionRepository;
    }

    public List<RobotsDTO> findAll() {
        final List<Robots> robotses = robotsRepository.findAll(Sort.by("id"));
        return robotses.stream()
                .map(robots -> mapToDTO(robots, new RobotsDTO()))
                .collect(Collectors.toList());
    }

    public RobotsDTO get(final Long id) {
        return robotsRepository.findById(id)
                .map(robots -> mapToDTO(robots, new RobotsDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final RobotsDTO robotsDTO, final User user) {
        final Robots robots = new Robots();
        robots.setCreatedAt(Instant.now());
        robots.setDateCreated(OffsetDateTime.now());
        robots.setLastUpdated(OffsetDateTime.now());
        robots.setCreatedBy(user.getId());
        robots.setUser(user);
        mapToEntity(robotsDTO, robots);
        return robotsRepository.save(robots).getId();
    }

    public void update(final Long id, final RobotsDTO robotsDTO) {
        final Robots robots = robotsRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(robotsDTO, robots);
        robotsRepository.save(robots);
    }

    public void delete(final Long id) {
        robotsRepository.deleteById(id);
    }

    private RobotsDTO mapToDTO(final Robots robots, final RobotsDTO robotsDTO) {
        robotsDTO.setId(robots.getId());
        robotsDTO.setNpm(robots.getNpm());
        robotsDTO.setDescription(robots.getDescription());
        robotsDTO.setEmplacement(robots.getEmplacement());
        robotsDTO.setUser(robots.getUser() == null ? null : robots.getUser().getId());
        robotsDTO.setPlanification(robots.getPlanification() == null ? null : robots.getPlanification().getId());
        robotsDTO.setExcution(robots.getExcution() == null ? null : robots.getExcution().getId());
        return robotsDTO;
    }

    private Robots mapToEntity(final RobotsDTO robotsDTO, final Robots robots) {
        robots.setNpm(robotsDTO.getNpm());
        robots.setDescription(robotsDTO.getDescription());
        robots.setEmplacement(robotsDTO.getEmplacement());
        final User user = robotsDTO.getUser() == null ? null : userRepository.findById(robotsDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        robots.setUser(user);
        final Planifications planification = robotsDTO.getPlanification() == null ? null : planificationsRepository.findById(robotsDTO.getPlanification())
                .orElseThrow(() -> new NotFoundException("planification not found"));
        robots.setPlanification(planification);
        final Excution excution = robotsDTO.getExcution() == null ? null : excutionRepository.findById(robotsDTO.getExcution())
                .orElseThrow(() -> new NotFoundException("excution not found"));
        robots.setExcution(excution);
        return robots;
    }

    public boolean planificationExists(final Long id) {
        return robotsRepository.existsByPlanificationId(id);
    }

    public boolean excutionExists(final Long id) {
        return robotsRepository.existsByExcutionId(id);
    }

}
