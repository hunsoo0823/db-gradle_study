package com.oreilly.hh.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

//import org.hibernate.annotations.Index;
import javax.persistence.Index;
//import org.hibernate.annotations.IndexColumn;
import javax.persistence.OrderColumn;
//import org.hibernate.annotations.ElementCollection;
import javax.persistence.ElementCollection;

/**
 *       Represents an album in the music database, an organized list of tracks.
 *       @author Jim Elliott (with help from Hibernate)
 *
 */
public class Album implements java.io.Serializable {

    private Integer id;

    private String title;

    private Integer numDiscs;

    private List<Track> tracks = new ArrayList<Track>(0);

    /**
     *         When the album was created
     *
     */
    private Date added;

    public Album() {
    }

    public Album(String title) {
        this.title = title;
    }

    public Album(String title, Integer numDiscs, List<Track> tracks, Date added) {
        this.title = title;
        this.numDiscs = numDiscs;
        this.tracks = tracks;
        this.added = added;
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

    public List<Track> getTracks() {
        return this.tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    /**
     *
     * When the album was created
     *
     */
    public Date getAdded() {
        return this.added;
    }

    public void setAdded(Date added) {
        this.added = added;
    }

    /**
     * toString
     * @return String
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
        buffer.append("title").append("='").append(getTitle()).append("' ");
        buffer.append("tracks").append("='").append(getTracks()).append("' ");
        buffer.append("]");

        return buffer.toString();
    }

}

