package com.oreilly.hh;

import java.time.LocalTime;
import java.util.*;

import org.hibernate.query.Query;
import org.hibernate.*;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.oreilly.hh.data.*;

public class QueryTest {

    public static List<Track> tracksNoLongerThan(LocalTime length, Session session) {
        Query<Track> query =
            session.createNamedQuery("com.oreilly.hh.tracksNoLongerThan", Track.class);
        query.setParameter("length", length);
        return query.list();
    }

    public static void main(String args[]) throws Exception {

        var context = new ClassPathXmlApplicationContext("applicationContext.xml");

        var sessionFactory = context.getBean("sessionFactory", SessionFactory.class);
        var session = sessionFactory.openSession();

        Transaction tx = null;

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

        context.close();
    }
}
