package com.oreilly.hh.data;

import java.util.HashSet;
import java.util.Set;

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

    private Artist actualArtist;

    public Artist() {
    }

    public Artist(String name) {
        this.name = name;
    }

    public Artist(String name, Set<Track> tracks, Artist actualArtist) {
        this.name = name;
        this.tracks = tracks;
        this.actualArtist = actualArtist;
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

    public Artist getActualArtist() {
        return this.actualArtist;
    }

    public void setActualArtist(Artist actualArtist) {
        this.actualArtist = actualArtist;
    }

    /**
     * toString
     * @return String
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
        buffer.append("name").append("='").append(getName()).append("' ");
        buffer.append("actualArtist").append("='").append(getActualArtist()).append("' ");
        buffer.append("]");

        return buffer.toString();
    }

}

