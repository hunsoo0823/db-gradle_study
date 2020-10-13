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
    public static List<Object[]> tracksNoLongerThan(LocalTime length, Session session) {
        Query<Object[]> query = session.createNamedQuery("com.oreilly.hh.tracksNoLongerThan", Object[].class);
        query.setParameter("length", length);
        return query.list();
    }

    /**
     * Build a parenthetical, comma-separated list of artist names.
     *
     * @param artists the artists whose names are to be displayed.
     * @return the formatted list, or an empty string if the set was empty.
     */
    public static String listArtistNames(Set<Artist> artists) {
        StringBuilder result = new StringBuilder();
        for (Artist artist : artists) {
            result.append((result.length() == 0) ? "(" : ", ");
            result.append(artist.getName());
        }
        if (result.length() > 0) {
            result.append(") ");
        }
        return result.toString();
    }

    /**
     * Print summary information about tracks associated with an artist.
     *
     * @param artist the artist in whose tracks we're interested.
     * @param session the Hibernate session that can retrieve data.
     **/
    public static void printTrackSummary(Artist artist, Session session) {
        //Query query = session.getNamedQuery("com.oreilly.hh.trackSummary");
        Query<Object[]> query = session.createNamedQuery("com.oreilly.hh.trackSummary", Object[].class);
        query.setParameter("artist", artist);
        Object[] results = (Object[])query.uniqueResult();
        System.out.println("Summary of tracks by " + artist.getName() + ':');
        System.out.println("       Total tracks: " + results[0]);
        System.out.println("     Shortest track: " + results[1]);
        System.out.println("      Longest track: " + results[2]);
    }

    /**
     * Print tracks that end some number of seconds into their final minute.
     *
     * @param seconds, the number of seconds at which we want tracks to end.
     * @param session the Hibernate session that can retrieve data.
     **/
    public static void printTracksEndingAt(int seconds, Session session) {
        Query query = session.getNamedQuery("com.oreilly.hh.tracksEndingAt");
        query.setParameter("seconds", seconds);
        List results = query.list();
        for (ListIterator iter = results.listIterator(); iter.hasNext();) {
            Track track = (Track)iter.next();
            System.out.println("Track: " + track.getTitle() +
                               ", length=" + track.getPlayTime());
        }
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

            // Print IDs and titles of tracks that will fit in seven minutes
            List<Object[]> titles = tracksNoLongerThan(LocalTime.of(00,07,00), session);

            for (Object[] row : titles) {
                Integer id = (Integer)row[0];
                String title = (String)row[1];
                System.out.println("Track: " + title + " [ID=" + id + ']');
            }
            printTrackSummary(CreateTest.getArtist("Samuel Barber",
                                                   false, session), session);
            System.out.println("Tracks ending halfway through final minute:");
            printTracksEndingAt(30, session);

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
