package com.oreilly.hh.data;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

//import org.hibernate.annotations.Index;
import javax.persistence.Index;
//import org.hibernate.annotations.IndexColumn;
import javax.persistence.OrderColumn;
//import org.hibernate.annotations.ElementCollection;
import javax.persistence.ElementCollection;

/**
 *       Represents an artist who is associated with a track or album.
 *       @author Jim Elliott (with help from Hibernate)
 *
 */
public class Artist  implements java.io.Serializable {

    private Integer id;

    private String name;

    /**
     * Tracks by this artist
     */
    private Set<Track> tracks = new HashSet<Track>(0);

    public Artist() {
    }

    public Artist(String name) {
        this.name = name;
    }

    public Artist(String name, Set<Track> tracks) {
        this.name = name;
        this.tracks = tracks;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * Tracks by this artist
     */
    public Set<Track> getTracks() {
        return this.tracks;
    }

    public void setTracks(Set<Track> tracks) {
        this.tracks = tracks;
    }

    /**
     * toString
     * @return String
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
        buffer.append("name").append("='").append(getName()).append("' ");
        buffer.append("]");

        return buffer.toString();
    }

}

