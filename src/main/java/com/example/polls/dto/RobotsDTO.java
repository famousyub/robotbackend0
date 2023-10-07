package com.example.polls.dto;


import javax.validation.constraints.Size;

public class RobotsDTO {

    private Long id;

    @Size(max = 255)
    private String npm;

    @Size(max = 255)
    private String description;

    @Size(max = 255)
    private String emplacement;

    private Long user;

    private Long planification;

    private Long excution;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getNpm() {
        return npm;
    }

    public void setNpm(final String npm) {
        this.npm = npm;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getEmplacement() {
        return emplacement;
    }

    public void setEmplacement(final String emplacement) {
        this.emplacement = emplacement;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(final Long user) {
        this.user = user;
    }

    public Long getPlanification() {
        return planification;
    }

    public void setPlanification(final Long planification) {
        this.planification = planification;
    }

    public Long getExcution() {
        return excution;
    }

    public void setExcution(final Long excution) {
        this.excution = excution;
    }

}
