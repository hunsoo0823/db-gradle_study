package com.oreilly.hh;

import javax.persistence.*;

import com.oreilly.hh.data.*;

import java.time.LocalTime;
import java.util.*;

/**
 * Create sample data, letting Hibernate persist it for us.
 */
public class CreateTest {

    /**
     * Look up an artist record given a name.
     * @param name the name of the artist desired.
     * @param create controls whether a new record should be created if
     *        the specified artist is not yet in the database.
     * @param em the JPA EntityManager that can retrieve data
     * @return the artist with the specified name, or <code>null</code> if no
     *         such artist exists and <code>create</code> is <code>false</code>.
     */
    public static Artist getArtist(String name, boolean create, EntityManager em) {
        TypedQuery<Artist> query = em.createNamedQuery("com.oreilly.hh.artistByName", Artist.class);
        query.setParameter("name", name);

        //Artist found = query.getSingleResult(); --> throws RuntimeException if not exists
        //Artist found = query.setMaxResults(1).getResultList().stream().findFirst().orElse(null);
        // from JPA 2.2
        Artist found = query.setMaxResults(1).getResultStream().findFirst().orElse(null);
        if (found == null && create) {
            found = new Artist(name, new HashSet<Track>());
            em.persist(found);
        }
        return found;
    }

    /**
     * Utility method to associate an artist with a track
     */
    private static void addTrackArtist(Track track, Artist artist) {
        track.getArtists().add(artist);
    }

    public static void main(String args[]) throws Exception {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager        em  = emf.createEntityManager();
        EntityTransaction    tx  = em.getTransaction();

        try {
            // Create some data and persist it
            tx.begin();

            Track track = new Track("Russian Trance",
                                    "vol2/album610/track02.mp3",
                                    LocalTime.of(00,03,30), new HashSet<Artist>(),
                                    (short)0,
                                    new HashSet<String>());
            addTrackArtist(track, getArtist("PPK", true, em));
            em.persist(track);

            track = new Track("Video Killed the Radio Star",
                              "vol2/album611/track12.mp3",
                              LocalTime.of(00,03,49), new HashSet<Artist>(),
                              (short)0,
                              new HashSet<String>());
            addTrackArtist(track, getArtist("The Buggles", true, em));
            em.persist(track);

            track = new Track("Gravity's Angel",
                              "vol2/album175/track03.mp3",
                              LocalTime.of(00,06,06), new HashSet<Artist>(),
                              (short)0,
                              new HashSet<String>());
            addTrackArtist(track, getArtist("Laurie Anderson", true, em));
            em.persist(track);

            track = new Track("Adagio for Strings (Ferry Corsten Remix)",
                              "vol2/album972/track01.mp3",
                              LocalTime.of(00,06,35), new HashSet<Artist>(),
                              (short)0,
                              new HashSet<String>());
            addTrackArtist(track, getArtist("William Orbit", true, em));
            addTrackArtist(track, getArtist("Ferry Corsten", true, em));
            addTrackArtist(track, getArtist("Samuel Barber", true, em));
            em.persist(track);

            track = new Track("Adagio for Strings (ATB Remix)",
                              "vol2/album972/track02.mp3",
                              LocalTime.of(00,07,39), new HashSet<Artist>(),
                              (short)0,
                              new HashSet<String>());
            addTrackArtist(track, getArtist("William Orbit", true, em));
            addTrackArtist(track, getArtist("ATB", true, em));
            addTrackArtist(track, getArtist("Samuel Barber", true, em));
            em.persist(track);

            track = new Track("The World '99",
                              "vol2/singles/pvw99.mp3",
                              LocalTime.of(00,07,05), new HashSet<Artist>(),
                              (short)0,
                              new HashSet<String>());
            addTrackArtist(track, getArtist("Pulp Victim", true, em));
            addTrackArtist(track, getArtist("Ferry Corsten", true, em));
            em.persist(track);

            track = new Track("Test Tone 1",
                              "vol2/singles/test01.mp3",
                              LocalTime.of(00,00,10), new HashSet<Artist>(),
                              (short)0,
                              new HashSet<String>());
            track.getComments().add("Pink noise to test equalization");
            em.persist(track);

            // We're done; make our changes permanent
            tx.commit();

        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        } finally {
            em.close();
        }

        emf.close();
    }
}
