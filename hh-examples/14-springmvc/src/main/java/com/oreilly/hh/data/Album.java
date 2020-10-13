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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "ALBUM_ARTISTS",
               joinColumns = @JoinColumn(name = "ALBUM_ID"),
               inverseJoinColumns = @JoinColumn(name = "ARTIST_ID"))
    private Set<Artist> artists = new HashSet<Artist>(0);

    @ElementCollection
    @JoinTable(name = "ALBUM_COMMENTS",
               joinColumns = @JoinColumn(name = "ALBUM_ID"))
    @Column(name = "COMMENT")
    private Set<String> comments = new HashSet<String>(0);

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "album")
    @OrderColumn(name = "LIST_POS")
    private List<AlbumTrack> tracks = new ArrayList<AlbumTrack>(0);

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

    public Album(String title, Integer numDiscs, Set<Artist> artists, Set<String> comments, List<AlbumTrack> tracks) {
        this.title = title;
        this.numDiscs = numDiscs;
        this.artists = artists;
        this.comments = comments;
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

    public Set<Artist> getArtists() {
        return this.artists;
    }

    public void setArtists(Set<Artist> artists) {
        this.artists = artists;
    }

    public Set<String> getComments() {
        return this.comments;
    }

    public void setComments(Set<String> comments) {
        this.comments = comments;
    }

    public List<AlbumTrack> getTracks() {
        return this.tracks;
    }

    public void setTracks(List<AlbumTrack> tracks) {
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

