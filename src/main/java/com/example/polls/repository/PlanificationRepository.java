package com.example.polls.repository;

import com.example.polls.model.Planifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PlanificationRepository extends JpaRepository<Planifications,Long> {
}
