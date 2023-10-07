package com.example.polls.repository;

import com.example.polls.model.Excution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ExcutionRepository extends JpaRepository<Excution,Long> {
}
