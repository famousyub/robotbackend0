package com.example.polls.dto;


import javax.validation.constraints.Size;

public class RolesDTO {

    private Long id;

    @Size(max = 255)
    private String nom;

    private Long user;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(final String nom) {
        this.nom = nom;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(final Long user) {
        this.user = user;
    }

}
