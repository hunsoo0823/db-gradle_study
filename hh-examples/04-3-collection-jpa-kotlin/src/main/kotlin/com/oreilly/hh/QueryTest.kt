package com.oreilly.hh

import java.time.LocalTime

import javax.persistence.EntityManager
import javax.persistence.Persistence

import com.oreilly.hh.data.*


fun tracksNoLongerThan(length: LocalTime, em: EntityManager): List<Track> {
    val query = em.createNamedQuery("com.oreilly.hh.tracksNoLongerThan", Track::class.java)
    query.setParameter("length", length)
    return query.resultList
}

/*
fun listArtistNames(artists: Set<Artist>): String {
    // val result = StringBuilder()
    // for (artist in artists) {
    //     result.append(if (result.isEmpty()) "(" else ", ")
    //     result.append(artist.name)
    // }
    // if (result.isNotEmpty()) {
    //     result.append(") ")
    // }
    // return result.toString()

    return "${artists.map { "'${it.name}'" }}"
}
*/

fun main() {
    Persistence.createEntityManagerFactory("default").use { emf ->

        emf.createEntityManager().use { em ->
            em.transaction.use {

                // Print the tracks that will fit in seven minutes
                val tracks = tracksNoLongerThan(LocalTime.of(0, 7, 0), em)

                for (aTrack in tracks) {
                    println(aTrack)
                    for (comment in aTrack.comments) {
                        println("  Comment: $comment")
                    }
                }
            }
        }
    }
}
