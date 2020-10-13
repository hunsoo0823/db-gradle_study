package com.oreilly.hh.dao.hibernate;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.oreilly.hh.dao.TrackDAO;
import com.oreilly.hh.data.Track;

@Repository
public class TrackHibernateDAO implements TrackDAO {

    // @Autowired
    // private SessionFactory sessionFactory;
    //
    // public void setSessionFactory(SessionFactory sessionFactory) {
    //     this.sessionFactory = sessionFactory;
    // }

    @PersistenceContext
    private EntityManager entityManager;

    public Track persist(Track track) {
        //sessionFactory.getCurrentSession().save(track);
        Session session = null;
        if (entityManager == null || (session = entityManager.unwrap(Session.class)) == null) {
            throw new NullPointerException();
        }
        session.save(track);
        return track;
    }

    public void delete (Track track) {
        //sessionFactory.getCurrentSession().delete(track);
        Session session = null;
        if (entityManager == null || (session = entityManager.unwrap(Session.class)) == null) {
            throw new NullPointerException();
        }
        session.delete(track);
    }

    public List<Track> tracksNoLongerThan(LocalTime length) {
        //Query<Track> query = sessionFactory.getCurrentSession().createNamedQuery("com.oreilly.hh.tracksNoLongerThan", Track.class);
        Session session = null;
        if (entityManager == null || (session = entityManager.unwrap(Session.class)) == null) {
            throw new NullPointerException();
        }
        Query<Track> query = session.createNamedQuery("com.oreilly.hh.tracksNoLongerThan", Track.class);
        query.setParameter("length", length);
        return query.list();
    }

}

