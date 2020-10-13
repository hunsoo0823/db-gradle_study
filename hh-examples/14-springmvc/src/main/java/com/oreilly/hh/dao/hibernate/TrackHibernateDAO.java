package com.oreilly.hh.dao.hibernate;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.query.Query;
import org.hibernate.SessionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.oreilly.hh.dao.TrackDAO;
import com.oreilly.hh.data.Track;

@Repository
@Transactional
public class TrackHibernateDAO implements TrackDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Track persist(Track track) {
        //sessionFactory.getCurrentSession().save(track);
        sessionFactory.getCurrentSession().saveOrUpdate(track);
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

