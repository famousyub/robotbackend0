package com.example.polls.controller;


import com.example.polls.model.User;
import com.example.polls.repository.UserRepository;
import com.example.polls.security.CurrentUser;
import com.example.polls.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/check")
@CrossOrigin("*")
public class CheckAdminController {

    @Autowired
    private UserRepository userRepository ;




    @GetMapping("/me")

    public ResponseEntity<?> curetnUserApp(@CurrentUser UserPrincipal currentUser)
    {

        User ui =  userRepository.findById(currentUser.getId()).get();

        UserOrAdmin _userorAdmin  =   new UserOrAdmin();

        _userorAdmin.setAdmin(ui.getIadmin());
        _userorAdmin.setEmail(ui.getEmail());
        _userorAdmin.setUsername(ui.getUsername());
        _userorAdmin.setId(ui.getId());

        return  ResponseEntity.ok().body(ui);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> chakeAdmin(@PathVariable("id") Long id)
    {

         User ui =  userRepository.findById(id).get();

         UserOrAdmin _userorAdmin  =   new UserOrAdmin();

         _userorAdmin.setAdmin(ui.getIadmin());
         _userorAdmin.setEmail(ui.getEmail());
         _userorAdmin.setUsername(ui.getUsername());
         _userorAdmin.setId(ui.getId());

         return  ResponseEntity.ok().body(ui);



    }
 }
