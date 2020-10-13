package com.oreilly.hh.dao.hibernate;

import java.util.HashSet;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.transaction.annotation.Transactional;

import com.oreilly.hh.dao.ArtistDAO;
import com.oreilly.hh.data.Artist;
import com.oreilly.hh.data.Track;

@Transactional
public class ArtistHibernateDAO implements ArtistDAO {

    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Artist persist(Artist artist) {
        entityManager.persist(artist);
        return artist;
    }

    public void delete (Artist artist) {
        entityManager.remove(artist);
    }

    // EntityManager.getSingleResult()
    //      NonUniqueResultException if multple result
    //      NoResultException        if not exist
    public Artist uniqueByName(final String name) {
        TypedQuery<Artist> query =
            entityManager.createNamedQuery("com.oreilly.hh.artistByName", Artist.class);
        query.setParameter("name", name);
        // return query.getSingleResult();      // yck
        return query.setMaxResults(1).getResultStream().findFirst().orElse(null);
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
