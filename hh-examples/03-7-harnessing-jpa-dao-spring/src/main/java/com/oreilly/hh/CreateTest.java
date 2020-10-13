package com.oreilly.hh;

import java.time.LocalTime;
import java.util.*;

import javax.persistence.*;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.oreilly.hh.dao.TrackDAO;
import com.oreilly.hh.dao.hibernate.TrackHibernateDAO;
import com.oreilly.hh.data.*;

public class CreateTest {

    public static void main(String args[]) throws Exception {

        var context = new ClassPathXmlApplicationContext("applicationContext.xml");

        var trackDAO = context.getBean(TrackDAO.class);

        Track track = new Track("Russian Trance",
                                "vol2/album610/track02.mp3",
                                LocalTime.of(00,03,30),
                                (short)0);
        trackDAO.persist(track);

        track = new Track("Video Killed the Radio Star",
                          "vol2/album611/track12.mp3",
                          LocalTime.of(00,03,49),
                          (short)0);
        trackDAO.persist(track);

        track = new Track("Gravity's Angel",
                          "vol2/album175/track03.mp3",
                          LocalTime.of(00,06,06),
                          (short)0);
        trackDAO.persist(track);

        context.close();
    }
}
