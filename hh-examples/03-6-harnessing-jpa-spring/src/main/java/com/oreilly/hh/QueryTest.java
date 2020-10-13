package com.oreilly.hh;

import java.time.LocalTime;
import java.util.*;

import javax.persistence.*;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.oreilly.hh.data.*;

public class QueryTest {

    public static List<Track> tracksNoLongerThan(LocalTime length, EntityManager em) {
        TypedQuery<Track> query =
            em.createNamedQuery("com.oreilly.hh.tracksNoLongerThan", Track.class);
        query.setParameter("length", length);
        return query.getResultList();
    }

    public static void main(String args[]) throws Exception {

        var context = new ClassPathXmlApplicationContext("applicationContext.xml");

        var emf = context.getBean("entityManagerFactory", EntityManagerFactory.class);
        var em  = emf.createEntityManager();

        var tx  = em.getTransaction();

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

        context.close();
    }
}
