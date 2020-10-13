package com.oreilly.hh.dao.hibernate;

import java.util.HashSet;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.springframework.transaction.annotation.Transactional;

import com.oreilly.hh.dao.ArtistDAO;
import com.oreilly.hh.data.Artist;
import com.oreilly.hh.data.Track;

@Transactional
public class ArtistHibernateDAO implements ArtistDAO {

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Artist persist(Artist artist) {
        sessionFactory.getCurrentSession().save(artist);
        return artist;
    }

    public void delete (Artist artist) {
        sessionFactory.getCurrentSession().delete(artist);
    }

    // Session.uniqueResult()
    //      NonUniqueResultException if multple result
    //      null                     if not exist
    public Artist uniqueByName(final String name) {
        Query<Artist> query =
            sessionFactory.getCurrentSession().createNamedQuery("com.oreilly.hh.artistByName", Artist.class);
        query.setParameter("name", name);
        return query.uniqueResult();
    }

    public Artist getArtist(String name, boolean create) {
        Artist found = uniqueByName(name);
        if (found == null && create) {
            found = new Artist(name, new HashSet<Track>(), null);
            found = persist(found);
        }
        if (found != null && found.getActualArtist() != null) {
            return found.getActualArtist();
        }
        return found;
    }

}
