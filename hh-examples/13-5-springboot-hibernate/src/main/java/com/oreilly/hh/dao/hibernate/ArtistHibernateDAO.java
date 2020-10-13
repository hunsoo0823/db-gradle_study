package com.oreilly.hh.dao.hibernate;

import java.util.HashSet;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.oreilly.hh.dao.ArtistDAO;
import com.oreilly.hh.data.Artist;
import com.oreilly.hh.data.Track;

@Repository
public class ArtistHibernateDAO implements ArtistDAO {

    // @Autowired
    // private SessionFactory sessionFactory;
    //
    // public void setSessionFactory(SessionFactory sessionFactory) {
    //     this.sessionFactory = sessionFactory;
    // }

    @PersistenceContext
    private EntityManager entityManager;

    public Artist persist(Artist artist) {
        //sessionFactory.getCurrentSession().save(artist);
        Session session = null;
        if (entityManager == null || (session = entityManager.unwrap(Session.class)) == null) {
            throw new NullPointerException();
        }
        session.save(artist);
        return artist;
    }

    public void delete (Artist artist) {
        //sessionFactory.getCurrentSession().delete(artist);
        Session session = null;
        if (entityManager == null || (session = entityManager.unwrap(Session.class)) == null) {
            throw new NullPointerException();
        }
        session.delete(artist);
    }

    public Artist uniqueByName(final String name) {
        //Query<Artist> query = sessionFactory.getCurrentSession().createNamedQuery("com.oreilly.hh.artistByName", Artist.class);

        Session session = null;
        if (entityManager == null || (session = entityManager.unwrap(Session.class)) == null) {
            throw new NullPointerException();
        }
        Query<Artist> query = session.createNamedQuery("com.oreilly.hh.artistByName", Artist.class);
        query.setParameter("name", name);
        return query.uniqueResult();
    }

    /**
     * Look up an artist record given a name.
     *
     * @param name
     *            the name of the artist desired.
     * @param create
     *            controls whether a new record should be created if the
     *            specified artist is not yet in the database.
     * @param session
     *            the Hibernate session that can retrieve data
     * @return the artist with the specified name, or <code>null</code> if
     *         no such artist exists and <code>create</code> is
     *         <code>false</code>.
     */
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
