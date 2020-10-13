package com.oreilly.hh.data;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.*;

/**
 *       Represents a single playable track in the music database.
 *       @author Jim Elliott (with help from Hibernate)
 *
 */
@Entity
@Table(name = "TRACK")
public class Track  implements java.io.Serializable {

    @Id
    @Column(name = "TRACK_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String title;

    private String filePath;

    /**
     * Playing time
     */
    private LocalTime playTime;

    /**
     * When the track was created
     */
    private LocalDate added = LocalDate.now();

    /**
     * How loud to play the track
     */
    private short volume;

    public Track() {
    }

    public Track(String title, String filePath, short volume) {
        this.title = title;
        this.filePath = filePath;
        this.volume = volume;
    }

    public Track(String title, String filePath, LocalTime playTime, short volume) {
        this.title = title;
        this.filePath = filePath;
        this.playTime = playTime;
        this.volume = volume;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     *
     * Playing time
     */
    public LocalTime getPlayTime() {
        return this.playTime;
    }

    public void setPlayTime(LocalTime playTime) {
        this.playTime = playTime;
    }

    /**
     *
     * When the track was created
     */
    public LocalDate getAdded() {
        return this.added;
    }

    public void setAdded(LocalDate added) {
        this.added = added;
    }

    /**
     *
     * How loud to play the track
     */
    public short getVolume() {
        return this.volume;
    }

    public void setVolume(short volume) {
        this.volume = volume;
    }
}

