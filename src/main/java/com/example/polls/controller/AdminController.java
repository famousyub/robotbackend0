package com.example.polls.controller;


import com.example.polls.exception.AppException;
import com.example.polls.model.*;
import com.example.polls.payload.ApiResponse;
import com.example.polls.payload.SignUpRequest;
import com.example.polls.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequestMapping("/api")
@RestController
@CrossOrigin("*")
public class AdminController {

    @Autowired
    private UserRepository  userRepository ;

    @Autowired
    private RobotRepository robotRepository ;


    @Autowired
    private PlanificationRepository planificationRepository ;

    @Autowired
    private ExcutionRepository excutionRepository ;

    @Autowired
    private RoleRepository roleRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;





    @PostMapping("/updateuser/{id}")
    public  ResponseEntity<?> updateUSser(@PathVariable("id") Long id,@Valid  @RequestBody SignUpRequest signUpRequest){


        User _updateuser = userRepository.getById(id);


        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account

        _updateuser.setId(id);

        _updateuser.setPassword(passwordEncoder.encode(_updateuser.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));

        _updateuser.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(_updateuser);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User updated  successfully"));


    }


    @PostMapping("/addUser")
    public  ResponseEntity<?> ajouteUser( @Valid  @RequestBody SignUpRequest signUpRequest)
    {


        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }


    @GetMapping("/mrobots/{id}")

    public   ResponseEntity<?> MAllrobots(@PathVariable("id") Long id)
    {

           User ui = userRepository.findById(id).get();



             List<Robots> r =  ui.getUserRobotses() .stream().collect(Collectors.toList());

            UserOrAdmin _userorAdmin  =   new UserOrAdmin();

            _userorAdmin.setAdmin(ui.getIadmin());
            _userorAdmin.setEmail(ui.getEmail());
            _userorAdmin.setUsername(ui.getUsername());
            _userorAdmin.setId(ui.getId());
            _userorAdmin.setRobots(r);







        return      ResponseEntity.ok().body(_userorAdmin);


    }
    @GetMapping("/allusers")
    public ResponseEntity<?> getallusers()
    {


        List<User> allusers = userRepository.findAll();

        return  ResponseEntity.ok().body(allusers);

    }


    @GetMapping("/allrebots")
    public ResponseEntity<?> getallrebots()
    {


        List<Robots> allusers = robotRepository.findAll();

        return  ResponseEntity.ok().body(allusers);

    }

    @GetMapping("/allplanifications")
    public ResponseEntity<?> getallplanifcation()
    {


        List<Planifications> allusers = planificationRepository.findAll();

        return  ResponseEntity.ok().body(allusers);

    }
    @GetMapping("/allexcutions")
    public ResponseEntity<?> getallexutions()
    {


        List<Excution> allusers = excutionRepository.findAll();

        return  ResponseEntity.ok().body(allusers);

    }
}
