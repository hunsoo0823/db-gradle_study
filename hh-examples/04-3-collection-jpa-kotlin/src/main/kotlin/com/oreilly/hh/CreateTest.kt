package com.oreilly.hh

import java.time.LocalTime

import javax.persistence.EntityManager
import javax.persistence.Persistence

import com.oreilly.hh.data.*

fun getArtist(name: String, create: Boolean, em: EntityManager): Artist? {
    val query = em.createNamedQuery("com.oreilly.hh.artistByName", Artist::class.java)
    query.setParameter("name", name)

    //Artist found = query.getSingleResult(); --> throws RuntimeException if not exists
    //val results = query.resultList
    //var found: Artist? = if (results.isEmpty()) null else results[0]
    var found = query.setMaxResults(1).getResultStream().findFirst().orElse(null)

    if (found == null && create) {
        found = Artist(name, HashSet())
        em.persist(found)
    }
    return found
}

private fun addTrackArtist(track: Track, artist: Artist?) {
    if (artist != null)
        track.artists.add(artist)
}

fun main() {
    Persistence.createEntityManagerFactory("default").use { emf ->

        emf.createEntityManager().use { em ->
            em.transaction.use {
                var track = Track("Russian Trance", "vol2/album610/track02.mp3",
                        LocalTime.of(0, 3, 30))
                addTrackArtist(track, getArtist("PPK", true, em))
                em.persist(track)

                track = Track("Video Killed the Radio Star", "vol2/album611/track12.mp3",
                        LocalTime.of(0, 3, 49))
                addTrackArtist(track, getArtist("The Buggles", true, em))
                em.persist(track)

                track = Track("Gravity's Angel", "vol2/album175/track03.mp3",
                        LocalTime.of(0, 6, 6))
                addTrackArtist(track, getArtist("Laurie Anderson", true, em))
                em.persist(track)

                track = Track("Adagio for Strings (Ferry Corsten Remix)", "vol2/album972/track01.mp3",
                        LocalTime.of(0, 6, 35))
                addTrackArtist(track, getArtist("William Orbit", true, em))
                addTrackArtist(track, getArtist("Ferry Corsten", true, em))
                addTrackArtist(track, getArtist("Samuel Barber", true, em))
                em.persist(track)

                track = Track("Adagio for Strings (ATB Remix)", "vol2/album972/track02.mp3",
                        LocalTime.of(0, 7, 39))
                addTrackArtist(track, getArtist("William Orbit", true, em))
                addTrackArtist(track, getArtist("ATB", true, em))
                addTrackArtist(track, getArtist("Samuel Barber", true, em))
                em.persist(track)

                track = Track("The World '99", "vol2/singles/pvw99.mp3",
                        LocalTime.of(0, 7, 5))
                addTrackArtist(track, getArtist("Pulp Victim", true, em))
                addTrackArtist(track, getArtist("Ferry Corsten", true, em))
                em.persist(track)

                track = Track("Test Tone 1", "vol2/singles/test01.mp3",
                        LocalTime.of(0, 0, 10))
                track.comments.add("Pink noise to test equalization")
                em.persist(track)
            }
        }
    }
}
