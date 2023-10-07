package com.example.polls.service;


import java.util.List;
import java.util.stream.Collectors;

import com.example.polls.dto.UserDTO;
import com.example.polls.model.Robots;
import com.example.polls.model.Role;
import com.example.polls.model.RoleName;
import com.example.polls.model.User;
import com.example.polls.payload.UserSummary;
import com.example.polls.repository.RobotRepository;
import com.example.polls.repository.RoleRepository;
import com.example.polls.repository.UserRepository;
import com.example.polls.util.NotFoundException;
import com.example.polls.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    //@Autowired
    //PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    private final RoleRepository rolesRepository;
    private final RobotRepository robotsRepository;

    public UserService(final UserRepository userRepository, final RoleRepository rolesRepository,
                       final RobotRepository robotsRepository) {
        this.userRepository = userRepository;
        this.rolesRepository = rolesRepository;
        this.robotsRepository = robotsRepository;
    }

    public List<UserDTO> findAll() {
        final List<User> users = userRepository.findAll(Sort.by("id"));
        return users.stream()
                .map(user -> mapToDTO(user, new UserDTO()))
                .collect(Collectors.toList());
    }

    public UserDTO get(final Long id) {
        return userRepository.findById(id)
                .map(user -> mapToDTO(user, new UserDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final UserDTO userDTO) {
        final User user = new User();

        mapToEntity(userDTO, user);
        return userRepository.save(user).getId();
    }

    public void update(final Long id, final UserDTO userDTO) {
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);

        mapToEntity(userDTO, user);
        userRepository.save(user);
    }

    public void delete(final Long id) {
        userRepository.deleteById(id);
    }

    private UserDTO mapToDTO(final User user, final UserDTO userDTO) {
        userDTO.setId(user.getId());
        userDTO.setFullname(user.getName());
        userDTO.setEmail(user.getEmail());
        //userDTO.setPassword(passwordEncoder.encode( user.getPassword()));
        userDTO.setPassword(user.getPassword());
        userDTO.setTelephone(user.getUsername());
        return userDTO;
    }

    private User mapToEntity(final UserDTO userDTO, final User user) {
        user.setName(userDTO.getFullname());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setName(userDTO.getTelephone());
        return user;
    }

    public boolean fullnameExists(final String fullname) {
        return userRepository.existsByUsername(fullname);
    }

    public boolean emailExists(final String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean telephoneExists(final String telephone) {
        return userRepository.existsByUsername(telephone);
    }

    public String getReferencedWarning(final Long id) {
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Role userRoles = rolesRepository.findByName(RoleName.ROLE_USER).get();
        if (userRoles != null) {
            return WebUtils.getMessage("user.roles.user.referenced", userRoles.getId());
        }
        final Robots userRobots = robotsRepository.findFirstByUser(user);
        if (userRobots != null) {
            return WebUtils.getMessage("user.robots.user.referenced", userRobots.getId());
        }
        return null;
    }

}