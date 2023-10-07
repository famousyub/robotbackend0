package com.example.polls.dto;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


public class ExcutionDTO {

    private Long id;

    @NotNull
    private  String log ;

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    private LocalDateTime date;

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

}
