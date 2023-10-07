package com.example.polls;

import java.util.HashSet;
import java.util.Set;

import com.example.polls.model.Role;
import com.example.polls.model.RoleName;
import com.example.polls.model.User;
import com.example.polls.repository.RoleRepository;
import com.example.polls.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;




//@Component
public class MyRunnable  implements CommandLineRunner {


    @Autowired
    UserRepository loginRepo;

    @Autowired
    private RoleRepository rolesRepository;



    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public void run(String... args) throws Exception {
        // TODO Auto-generated method stub

/*        Role rol2 = new Role();
        rol1.setName(RoleName.ROLE_USER);
        rol2.setName(RoleName.ROLE_ADMIN);

        rolesRepository.save(rol1);
        rolesRepository.save(rol2);
        Set<Role> roles = new HashSet<>();

 */
        Role rol2 = rolesRepository.findByName(RoleName.ROLE_USER).get();
        Role role1 = rolesRepository.findByName(RoleName.ROLE_ADMIN).get();

        Set<Role> roles = new HashSet<>();
roles.add(role1);
roles.add(rol2);
        User u = new User();

        u.setEmail("ayoub@gmail.com");


        u.setUsername("ayoubsmayen");


        u.setName("ayoub");
        u.setPassword(passwordEncoder.encode("1234"));

        u.setRoles(roles);






        loginRepo.save(u);


    }

}