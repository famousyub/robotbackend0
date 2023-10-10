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
public class Planifications implements Serializable {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDateTime date;

    @Column(nullable = false)
    private OffsetDateTime dateCreated;

    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

    @Column
    private String status;

    public Planifications(Long id, LocalDateTime date, OffsetDateTime dateCreated, OffsetDateTime lastUpdated, String status, Set<Robots> planificationRobotses) {
        this.id = id;
        this.date = date;
        this.dateCreated = dateCreated;
        this.lastUpdated = lastUpdated;
        this.status = status;
        this.planificationRobotses = planificationRobotses;
    }

    public Planifications() {
    }

    @OneToMany(mappedBy = "planification")
    private Set<Robots> planificationRobotses;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<Robots> getPlanificationRobotses() {
        return planificationRobotses;
    }

    public void setPlanificationRobotses(Set<Robots> planificationRobotses) {
        this.planificationRobotses = planificationRobotses;
    }
}