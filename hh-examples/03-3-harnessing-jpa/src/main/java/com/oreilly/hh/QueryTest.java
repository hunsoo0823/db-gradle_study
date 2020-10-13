package com.oreilly.hh;

import javax.persistence.*;

import com.oreilly.hh.data.*;

import java.time.LocalTime;
import java.util.*;

/**
 * Retrieve data as objects
 */
public class QueryTest {

    /**
     * Retrieve any tracks that fit in the specified amount of time.
     *
     * @param length the maximum playing time for tracks to be returned.
     * @param em the Jpa EntityManager that can retrieve data.
     * @return a list of {@link Track}s meeting the length restriction.
     */
    public static List<Track> tracksNoLongerThan(LocalTime length, EntityManager em) {
        TypedQuery<Track> query = em.createNamedQuery("com.oreilly.hh.tracksNoLongerThan", Track.class);
        query.setParameter("length", length);
        return query.getResultList();
    }

    /**
     * Look up and print some tracks when invoked from the command line.
     */
    public static void main(String args[]) throws Exception {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager        em  = emf.createEntityManager();
        EntityTransaction    tx  = em.getTransaction();

        try {
            tx.begin();

            // Print the tracks that will fit in seven minutes
            List<Track> tracks = tracksNoLongerThan(LocalTime.of(00,07,00), em);

            for (Track aTrack : tracks) {
                System.out.printf("Track: \"%s\" %s\n", aTrack.getTitle(), aTrack.getPlayTime());
            }

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
