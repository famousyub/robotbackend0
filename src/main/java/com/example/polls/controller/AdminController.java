package com.example.polls.controller;


import com.example.polls.model.Excution;
import com.example.polls.model.Planifications;
import com.example.polls.model.Robots;
import com.example.polls.model.User;
import com.example.polls.repository.ExcutionRepository;
import com.example.polls.repository.PlanificationRepository;
import com.example.polls.repository.RobotRepository;
import com.example.polls.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
