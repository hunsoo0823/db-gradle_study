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
                String mediaInfo = "";
                if (aTrack.getSourceMedia() != null) {
                    mediaInfo = "from " + aTrack.getSourceMedia().getDescription();
                }
                System.out.printf("Track: \"%s\" %s %s, %s\n",
                                  aTrack.getTitle(),
                                  listArtistNames(aTrack.getArtists()),
                                  aTrack.getPlayTime(),
                                  mediaInfo);
                for (String comment : aTrack.getComments()) {
                    System.out.println("  Comment: " + comment);
                }
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
