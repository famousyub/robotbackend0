package com.example.polls.repository;

import com.example.polls.model.ExcutionT;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ExcutionTRepository  extends JpaRepository<ExcutionT,Long> {
    List<ExcutionT> findByPublished(boolean published);

    List<ExcutionT> findByTitleContaining(String title);

}
