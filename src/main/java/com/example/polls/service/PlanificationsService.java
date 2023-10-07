package com.example.polls.service;;


import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.example.polls.dto.PlanificationsDTO;
import com.example.polls.model.Planifications;
import com.example.polls.model.Robots;
import com.example.polls.repository.PlanificationRepository;
import com.example.polls.repository.RobotRepository;
import com.example.polls.util.NotFoundException;
import com.example.polls.util.WebUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class PlanificationsService {

    private final PlanificationRepository planificationsRepository;
    private final RobotRepository robotsRepository;

    public PlanificationsService(final PlanificationRepository planificationsRepository,
            final RobotRepository robotsRepository) {
        this.planificationsRepository = planificationsRepository;
        this.robotsRepository = robotsRepository;
    }

    public List<PlanificationsDTO> findAll() {
        final List<Planifications> planificationses = planificationsRepository.findAll(Sort.by("id"));
        return planificationses.stream()
                .map(planifications -> mapToDTO(planifications, new PlanificationsDTO()))
                .collect(Collectors.toList());
    }

    public PlanificationsDTO get(final Long id) {
        return planificationsRepository.findById(id)
                .map(planifications -> mapToDTO(planifications, new PlanificationsDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final PlanificationsDTO planificationsDTO) {
        final Planifications planifications = new Planifications();


        planifications.setDateCreated(OffsetDateTime.now());
        planifications.setLastUpdated(OffsetDateTime.now());
        mapToEntity(planificationsDTO, planifications);
        return planificationsRepository.save(planifications).getId();
    }

    public void update(final Long id, final PlanificationsDTO planificationsDTO) {
        final Planifications planifications = planificationsRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(planificationsDTO, planifications);
        planificationsRepository.save(planifications);
    }

    public void delete(final Long id) {
        planificationsRepository.deleteById(id);
    }

    private PlanificationsDTO mapToDTO(final Planifications planifications,
            final PlanificationsDTO planificationsDTO) {
        planificationsDTO.setId(planifications.getId());
        planificationsDTO.setDate(planifications.getDate());
        planificationsDTO.setStatus(planifications.getStatus());
        return planificationsDTO;
    }

    private Planifications mapToEntity(final PlanificationsDTO planificationsDTO,
            final Planifications planifications) {
        planifications.setDate(planificationsDTO.getDate());
        planifications.setStatus(planificationsDTO.getStatus());
        return planifications;
    }

    public String getReferencedWarning(final Long id) {
        final Planifications planifications = planificationsRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Robots planificationRobots = robotsRepository.findFirstByPlanification(planifications);
        if (planificationRobots != null) {
            return WebUtils.getMessage("planifications.robots.planification.referenced", planificationRobots.getId());
        }
        return null;
    }

}
