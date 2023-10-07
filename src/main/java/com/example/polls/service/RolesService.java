package com.example.polls.service;


import java.util.List;
import java.util.stream.Collectors;

import com.example.polls.dto.RolesDTO;
import com.example.polls.model.Role;
import com.example.polls.model.RoleName;
import com.example.polls.model.User;
import com.example.polls.repository.RoleRepository;
import com.example.polls.repository.UserRepository;
import com.example.polls.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class RolesService {

    private final RoleRepository rolesRepository;
    private final UserRepository userRepository;

    public RolesService(final RoleRepository rolesRepository,
            final UserRepository userRepository) {
        this.rolesRepository = rolesRepository;
        this.userRepository = userRepository;
    }

    public List<RolesDTO> findAll() {
        final List<Role> roles = rolesRepository.findAll(Sort.by("id"));
        return roles.stream()
                .map(role-> mapToDTO(role, new RolesDTO()))
                .collect(Collectors.toList());
    }

    public RolesDTO get(final Long id) {
        return rolesRepository.findById(id)
                .map(roles -> mapToDTO(roles, new RolesDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final RolesDTO rolesDTO) {
        final Role roles = new Role();
        mapToEntity(rolesDTO, roles);
        return rolesRepository.save(roles).getId();
    }

    public void update(final Long id, final RolesDTO rolesDTO) {
        final Role roles = rolesRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(rolesDTO, roles);
        rolesRepository.save(roles);
    }

    public void delete(final Long id) {
        rolesRepository.deleteById(id);
    }

    private RolesDTO mapToDTO(final Role roles, final RolesDTO rolesDTO) {
        rolesDTO.setId(roles.getId());
        rolesDTO.setNom(roles.getName().toString());

        return rolesDTO;
    }

    private Role mapToEntity(final RolesDTO rolesDTO, final Role roles) {
        roles.setName(RoleName.ROLE_USER);
        final User user = rolesDTO.getUser() == null ? null : userRepository.findById(rolesDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));

        return roles;
    }

}
