package com.oreilly.hh;

import java.time.LocalTime;
import java.util.*;

import org.hibernate.query.Query;
import org.hibernate.*;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.oreilly.hh.data.*;

public class CreateTest {

    public static void main(String args[]) throws Exception {

        var context = new ClassPathXmlApplicationContext("applicationContext.xml");

        var sessionFactory = context.getBean("sessionFactory", SessionFactory.class);
        var session = sessionFactory.openSession();

        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            Track track = new Track("Russian Trance",
                                    "vol2/album610/track02.mp3",
                                    LocalTime.of(00,03,30),
                                    (short)0);
            session.save(track);

            track = new Track("Video Killed the Radio Star",
                              "vol2/album611/track12.mp3",
                              LocalTime.of(00,03,49),
                              (short)0);
            session.save(track);

            track = new Track("Gravity's Angel",
                              "vol2/album175/track03.mp3",
                              LocalTime.of(00,06,06),
                              (short)0);
            session.save(track);

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
