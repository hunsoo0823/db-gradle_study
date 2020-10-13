package com.oreilly.hh.dao.hibernate;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.oreilly.hh.dao.TrackDAO;
import com.oreilly.hh.data.Track;

@Repository
@Transactional
public class TrackHibernateDAO implements TrackDAO {

    @PersistenceContext
    private EntityManager em;

    public Track persist(Track track) {
        em.persist(track);
        return track;
    }

    public void delete (Track track) {
        em.remove(track);
    }

    public Track findById(int id) {
        return em.find(Track.class, id);
    }

    public List<Track> findAll() {
        var hql = "select t from Track t";
        return em.createQuery(hql, Track.class)
                 .getResultList();
    }

    public List<Track> findByNameLike(final String namePattern) {
        var hql = "select t from Track t where name LIKE :name";
        return em.createQuery(hql, Track.class)
                 .setParameter("name", namePattern)
                 .getResultList();
    }

    public List<Track> tracksNoLongerThan(LocalTime length) {
        TypedQuery<Track> query =
            em.createNamedQuery("com.oreilly.hh.tracksNoLongerThan", Track.class);
        query.setParameter("length", length);
        return query.getResultList();
    }

}

