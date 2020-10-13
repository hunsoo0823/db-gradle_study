package com.oreilly.hh.data;

import java.io.Serializable;

import javax.persistence.*;

/**
 *       Represents an album in the music database, an organized list of tracks.
 *       @author Jim Elliott (with help from Hibernate)
 *
 */
@Entity
@Table(name = "ALBUM_TRACKS")
public class AlbumTrack implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ALBUM_ID")
    private Album album;

    @ManyToOne
    @JoinColumn(name = "TRACK_ID")
    private Track track;

    private Integer disc;

    private Integer positionOnDisc;

    public AlbumTrack() {
    }

    public AlbumTrack(Album album, Track track, Integer disc, Integer positionOnDisc) {
        this.album = album;
        this.track = track;
        this.disc = disc;
        this.positionOnDisc = positionOnDisc;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Track getTrack() {
        return this.track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    public Integer getDisc() {
        return this.disc;
    }

    public void setDisc(Integer disc) {
        this.disc = disc;
    }

    public Integer getPositionOnDisc() {
        return this.positionOnDisc;
    }

    public void setPositionOnDisc(Integer positionOnDisc) {
        this.positionOnDisc = positionOnDisc;
    }

    /**
     * toString
     * @return String
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        //buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
        buffer.append("AlbumTrack(");
        buffer.append("track").append("='").append(getTrack()).append("' ");
        buffer.append(")");

        return buffer.toString();
    }

}

