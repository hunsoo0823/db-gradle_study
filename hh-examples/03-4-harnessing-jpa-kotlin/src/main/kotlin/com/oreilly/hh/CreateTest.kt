package com.oreilly.hh

import java.time.LocalTime

import javax.persistence.Persistence

import com.oreilly.hh.data.*

fun main() {
    Persistence.createEntityManagerFactory("default").use { emf ->

        emf.createEntityManager().use { em ->
            em.transaction.use {
                var track = Track("Russian Trance", "vol2/album610/track02.mp3",
                        LocalTime.of(0, 3, 30))
                em.persist(track)

                track = Track("Video Killed the Radio Star", "vol2/album611/track12.mp3",
                        LocalTime.of(0, 3, 49))
                em.persist(track)

                track = Track("Gravity's Angel", "vol2/album175/track03.mp3",
                        LocalTime.of(0, 6, 6))
                em.persist(track)
            }
        }
    }
}
