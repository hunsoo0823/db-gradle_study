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

fun main() {
    Persistence.createEntityManagerFactory("default").use { emf ->

        emf.createEntityManager().use { em ->
            em.transaction.use {

                // Print the tracks that will fit in seven minutes
                val tracks = tracksNoLongerThan(LocalTime.of(0, 7, 0), em)

                for (aTrack in tracks) {
                    println("Track: '${aTrack.title}' ${aTrack.playTime}")
                }
            }
        }
    }
}
