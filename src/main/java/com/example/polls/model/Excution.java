package com.example.polls.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Set;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;


@Entity
@EntityListeners(AuditingEntityListener.class)
public class Excution implements Serializable {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime date = LocalDateTime.now();

    @Column(nullable = false)
    private OffsetDateTime dateCreated;

    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

    public Excution() {
    }

    @OneToMany(mappedBy = "excution")
    private Set<Robots> excutionRobotses;


    private  String log ;

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public Excution(Long id, LocalDateTime date, OffsetDateTime dateCreated, OffsetDateTime lastUpdated, Set<Robots> excutionRobotses) {
        this.id = id;
        this.date = date;
        this.dateCreated = dateCreated;
        this.lastUpdated = lastUpdated;
        this.excutionRobotses = excutionRobotses;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public OffsetDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(OffsetDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public OffsetDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(OffsetDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Set<Robots> getExcutionRobotses() {
        return excutionRobotses;
    }

    public void setExcutionRobotses(Set<Robots> excutionRobotses) {
        this.excutionRobotses = excutionRobotses;
    }
}