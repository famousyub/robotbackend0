package com.example.polls.service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.example.polls.dto.ExcutionDTO;
import com.example.polls.model.Excution;
import com.example.polls.model.Robots;
import com.example.polls.repository.ExcutionRepository;
import com.example.polls.repository.RobotRepository;
import com.example.polls.util.NotFoundException;
import com.example.polls.util.WebUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ExcutionService {

    private final ExcutionRepository excutionRepository;
    private final RobotRepository robotsRepository;

    public ExcutionService(final ExcutionRepository excutionRepository,
            final RobotRepository robotsRepository) {
        this.excutionRepository = excutionRepository;
        this.robotsRepository = robotsRepository;
    }

    public List<ExcutionDTO> findAll() {
        final List<Excution> excutions = excutionRepository.findAll(Sort.by("id"));
        return excutions.stream()
                .map(excution -> mapToDTO(excution, new ExcutionDTO()))
                .collect(Collectors.toList());
    }

    public ExcutionDTO get(final Long id) {
        return excutionRepository.findById(id)
                .map(excution -> mapToDTO(excution, new ExcutionDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ExcutionDTO excutionDTO) {
        final Excution excution = new Excution();

        excution.setDateCreated(OffsetDateTime.now());
        excution.setDate(LocalDateTime.now());
        excution.setLastUpdated(OffsetDateTime.now());
        mapToEntity(excutionDTO, excution);
        return excutionRepository.save(excution).getId();
    }

    public void update(final Long id, final ExcutionDTO excutionDTO) {
        final Excution excution = excutionRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(excutionDTO, excution);
        excutionRepository.save(excution);
    }

    public void delete(final Long id) {
        excutionRepository.deleteById(id);
    }

    private ExcutionDTO mapToDTO(final Excution excution, final ExcutionDTO excutionDTO) {
        excutionDTO.setId(excution.getId());
        excutionDTO.setDate(excution.getDate());
        return excutionDTO;
    }

    private Excution mapToEntity(final ExcutionDTO excutionDTO, final Excution excution) {
        excution.setDate(excutionDTO.getDate());
        return excution;
    }

    public String getReferencedWarning(final Long id) {
        final Excution excution = excutionRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Robots excutionRobots = robotsRepository.findFirstByExcution(excution);
        if (excutionRobots != null) {
            return WebUtils.getMessage("excution.robots.excution.referenced", excutionRobots.getId());
        }
        return null;
    }

}
