package com.oreilly.hh;

import org.hibernate.query.Query;
import org.hibernate.*;

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
     * @param session the Hibernate session that can retrieve data.
     * @return a list of {@link Track}s meeting the length restriction.
     */
    public static List<Track> tracksNoLongerThan(LocalTime length, Session session) {
        Query<Track> query = session.createNamedQuery("com.oreilly.hh.tracksNoLongerThan", Track.class);
        query.setParameter("length", length);
        return query.list();
    }

    /**
     * Look up and print some tracks when invoked from the command line.
     */
    public static void main(String args[]) throws Exception {

        SessionFactory sessionFactory = HibernateUtil5.getSessionFactory();
        Session        session = sessionFactory.openSession();
        Transaction    tx = null;

        try {
            tx = session.beginTransaction();

            // Print the tracks that will fit in seven minutes
            List<Track> tracks = tracksNoLongerThan(LocalTime.of(00,07,00), session);

            for (Track aTrack : tracks) {
                System.out.printf("Track: \"%s\" %s\n", aTrack.getTitle(), aTrack.getPlayTime());
            }

            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }

        sessionFactory.close();
    }
}
