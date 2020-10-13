package com.oreilly.hh.data;

import java.time.LocalDate;
import java.util.*;

import javax.persistence.*;

@Entity
@Table(name = "ALBUM")
public class Album implements java.io.Serializable {

    @Id
    @Column(name = "ALBUM_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String title;

    private Integer numDiscs;

    public Album() {
    }

    public Album(String title) {
        this.title = title;
    }

    public Album(String title, Integer numDiscs) {
        this.title = title;
        this.numDiscs = numDiscs;
    }

    public Integer getId() {
        return this.id;
    }

    // protected is not working !!!
    // SpringMVC: updating insert new tuple!!!
    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getNumDiscs() {
        return this.numDiscs;
    }

    public void setNumDiscs(Integer numDiscs) {
        this.numDiscs = numDiscs;
    }

}

