package com.oreilly.hh.dao.hibernate;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.transaction.annotation.Transactional;

import com.oreilly.hh.dao.TrackDAO;
import com.oreilly.hh.data.Track;

@Transactional
public class TrackHibernateDAO implements TrackDAO {

    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Track persist(Track track) {
        entityManager.persist(track);
        return track;
    }

    public void delete (Track track) {
        entityManager.remove(track);
    }

    public List<Track> tracksNoLongerThan(LocalTime length) {
        TypedQuery<Track> query =
            entityManager.createNamedQuery("com.oreilly.hh.tracksNoLongerThan", Track.class);
        query.setParameter("length", length);
        return query.getResultList();
    }

}

