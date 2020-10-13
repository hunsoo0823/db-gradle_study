package com.oreilly.hh.data

import java.time.LocalDate
import java.time.LocalTime

import javax.persistence.*

@Entity
@NamedQuery(
        name = "com.oreilly.hh.tracksNoLongerThan",
        query = "from Track as track where track.playTime <= :length")
class Track(
        var title: String = "",

        var filePath: String = "",

        var playTime: LocalTime = LocalTime.now(),

        var added: LocalDate = LocalDate.now(),

        var volume: Short = 0,

        @ManyToMany
        @JoinTable(
                name = "TRACK_ARTISTS",
                joinColumns = [JoinColumn(name = "TRACK_ID")],
                inverseJoinColumns = [JoinColumn(name = "ARTIST_ID")])
        var artists: MutableSet<Artist> = mutableSetOf(),

        @ElementCollection
        @JoinTable(name = "TRACK_COMMENTS", joinColumns = [JoinColumn(name = "TRACK_ID")])
        @Column(name = "COMMENT")
        var comments: MutableSet<String> = mutableSetOf(),

        @Id
        @Column(name = "TRACK_ID")
        @GeneratedValue
        var id: Int = 0
) {
    override fun toString(): String = "Track(id=$id, title='$title', artists = ${artists.map { "'${it.name}'" }})"
    override fun equals(other: Any?): Boolean = other is Track && other.id == this.id
    override fun hashCode(): Int = id
}
