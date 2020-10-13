package com.oreilly.hh.dao.hibernate;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.query.Query;
import org.hibernate.SessionFactory;

import org.springframework.transaction.annotation.Transactional;

import com.oreilly.hh.dao.TrackDAO;
import com.oreilly.hh.data.Track;

@Transactional
public class TrackHibernateDAO implements TrackDAO {

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Track persist(Track track) {
        sessionFactory.getCurrentSession().save(track);
        return track;
    }

    public void delete (Track track) {
        sessionFactory.getCurrentSession().delete(track);
    }

    public List<Track> tracksNoLongerThan(LocalTime length) {
        Query<Track> query =
            sessionFactory.getCurrentSession().createNamedQuery("com.oreilly.hh.tracksNoLongerThan", Track.class);
        query.setParameter("length", length);
        return query.list();
    }

}

