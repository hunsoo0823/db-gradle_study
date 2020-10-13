package com.oreilly.hh.data

import javax.persistence.*

@Entity
@NamedQuery(
        name = "com.oreilly.hh.artistByName",
        query = "from Artist as artist where upper(artist.name) = upper(:name)")
class Artist(
        @Column(unique = true)
        var name: String = "",

        @ManyToMany(mappedBy = "artists")
        var tracks: MutableSet<Track> = mutableSetOf(),

        @Id
        @Column(name = "ARTIST_ID")
        @GeneratedValue
        var id: Int = 0
) {
    // {aTrack.artists.map { "'${it.name}'" }}
    override fun toString(): String = "Artist(id=$id, name='$name', tracks = ${tracks.map { "'${it.title}'" }})"
    override fun equals(other: Any?): Boolean = other is Artist && other.id == this.id
    override fun hashCode(): Int = id
}
