package com.oreilly.hh;

import javax.persistence.*;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.oreilly.hh.dao.TrackDAO;
import com.oreilly.hh.dao.hibernate.TrackHibernateDAO;

import com.oreilly.hh.data.*;

import java.time.LocalTime;
import java.util.*;

public class QueryTest {

    public static void main(String args[]) throws Exception {

        var context = new ClassPathXmlApplicationContext("applicationContext.xml");

        var trackDAO = context.getBean(TrackDAO.class);

        // Print the tracks that will fit in seven minutes
        List<Track> tracks = trackDAO.tracksNoLongerThan(LocalTime.of(00,07,00));
        for (Track track : tracks) {
            System.out.println("Track: \"" + track.getTitle() + "\", " + track.getPlayTime());
        }

        context.close();
    }
}
