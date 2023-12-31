package com.example.polls.repository;

import com.example.polls.controller.RobotDetails;
import com.example.polls.model.Excution;
import com.example.polls.model.Planifications;
import com.example.polls.model.Robots;
import com.example.polls.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RobotRepository  extends JpaRepository<Robots,Long> {


    Robots findFirstByUser(User user);

    Robots findFirstByPlanification(Planifications planifications);

    Robots findFirstByExcution(Excution excution);

    boolean existsByPlanificationId(Long id);

    boolean existsByExcutionId(Long id);
}
