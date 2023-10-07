package com.example.polls.dto;


import javax.validation.constraints.Size;
import java.time.LocalDateTime;


public class PlanificationsDTO {

    private Long id;

    private LocalDateTime date;

    @Size(max = 255)
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(final LocalDateTime date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

}
