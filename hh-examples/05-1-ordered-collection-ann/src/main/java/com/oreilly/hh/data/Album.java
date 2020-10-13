package com.oreilly.hh.data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

/**
 *       Represents an album in the music database, an organized list of tracks.
 *       @author Jim Elliott (with help from Hibernate)
 *
 */
@Entity
@Table(name = "ALBUM")
public class Album implements java.io.Serializable {

    @Id
    @Column(name = "ALBUM_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String title;

    private Integer numDiscs;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "ALBUM_TRACKS",
               joinColumns = @JoinColumn(name = "ALBUM_ID"))
    @OrderColumn(name = "LIST_POS")
    private List<Track> tracks = new ArrayList<Track>(0);

    /**
     *         When the album was created
     *
     */
    private LocalDate added = LocalDate.now();

    public Album() {
    }

    public Album(String title) {
        this.title = title;
    }

    public Album(String title, Integer numDiscs, List<Track> tracks) {
        this.title = title;
        this.numDiscs = numDiscs;
        this.tracks = tracks;
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
    public LocalDate getAdded() {
        return this.added;
    }

    public void setAdded(LocalDate added) {
        this.added = added;
    }

    /**
     * toString
     * @return String
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        //buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
        buffer.append("Album(");
        buffer.append("title").append("='").append(getTitle()).append("' ");
        buffer.append("tracks").append("='").append(getTracks()).append("' ");
        buffer.append(")");

        return buffer.toString();
    }

}

