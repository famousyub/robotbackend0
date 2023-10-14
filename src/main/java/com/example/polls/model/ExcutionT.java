package com.example.polls.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "excutiont")
public class ExcutionT  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "log")
    private String log;

    private Long robotsId ;

    public Long getRobotsId() {
        return robotsId;
    }

    public void setRobotsId(Long robotsId) {
        this.robotsId = robotsId;
    }

    @Column(name = "published")
    private boolean published;

    public ExcutionT() {

    }

    public ExcutionT(String title, String log, boolean published) {
        this.title = title;
        this.log = log;
        this.published = published;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean isPublished) {
        this.published = isPublished;
    }

    @Override
    public String toString() {
        return "Tutorial [id=" + id + ", title=" + title + ", desc=" + log + ", published=" + published + "]";
    }

}