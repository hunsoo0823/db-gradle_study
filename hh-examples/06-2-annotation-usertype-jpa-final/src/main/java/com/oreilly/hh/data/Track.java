package com.oreilly.hh.data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

/**
 *       Represents a single playable track in the music database.
 *       @author Jim Elliott (with help from Hibernate)
 *
 */
@Entity
@Table(name = "TRACK")
@NamedQuery(name = "com.oreilly.hh.tracksNoLongerThan",
            query = "from Track as track where track.playTime <= :length")
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

    @ManyToMany
    @JoinTable(name = "TRACK_ARTISTS",
               joinColumns = {@JoinColumn(name = "TRACK_ID")},
               inverseJoinColumns = {@JoinColumn(name = "ARTIST_ID")})
    private Set<Artist> artists = new HashSet<Artist>(0);

    /**
     * When the track was created
     */
    private LocalDate added = LocalDate.now();

    /**
     * How loud to play the track
     */
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "left", column = @Column(name = "VOL_LEFT")),
        @AttributeOverride(name = "right", column = @Column(name = "VOL_RIGHT"))
    })
    private StereoVolume volume;

    /**
     * Media on which track was obtained
     */
    @Enumerated(EnumType.STRING)
    private SourceMedia sourceMedia;

    /**
     * Any notes the user has made about the track.
     */
    @ElementCollection
    @JoinTable(name = "TRACK_COMMENTS",
               joinColumns = @JoinColumn(name = "TRACK_ID"))
    @Column(name = "COMMENT")
    private Set<String> comments = new HashSet<String>(0);

    public Track() {
    }

    public Track(String title, String filePath) {
        this.title = title;
        this.filePath = filePath;
    }

    public Track(String title, String filePath, LocalTime playTime, Set<Artist> artists, StereoVolume volume, SourceMedia sourceMedia, Set<String> comments) {
        this.title = title;
        this.filePath = filePath;
        this.playTime = playTime;
        this.artists = artists;
        this.volume = volume;
        this.sourceMedia = sourceMedia;
        this.comments = comments;
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

    public Set<Artist> getArtists() {
        return this.artists;
    }

    public void setArtists(Set<Artist> artists) {
        this.artists = artists;
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
    public StereoVolume getVolume() {
        return this.volume;
    }

    public void setVolume(StereoVolume volume) {
        this.volume = volume;
    }

    /**
     *
     * Media on which track was obtained
     */
    public SourceMedia getSourceMedia() {
        return this.sourceMedia;
    }

    public void setSourceMedia(SourceMedia sourceMedia) {
        this.sourceMedia = sourceMedia;
    }

    public Set<String> getComments() {
        return this.comments;
    }

    public void setComments(Set<String> comments) {
        this.comments = comments;
    }

    /**
     * toString
     * @return String
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        //buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
        buffer.append("Track(");
        buffer.append("title").append("='").append(getTitle()).append("' ");
        buffer.append("volume").append("='").append(getVolume()).append("' ");
        buffer.append("sourceMedia").append("='").append(getSourceMedia()).append("' ");
        buffer.append(")");

        return buffer.toString();
    }

}

