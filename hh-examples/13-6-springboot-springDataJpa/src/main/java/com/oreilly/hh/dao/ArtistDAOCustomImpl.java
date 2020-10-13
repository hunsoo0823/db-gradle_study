package com.oreilly.hh.dao;

import java.util.HashSet;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.oreilly.hh.data.Artist;
import com.oreilly.hh.data.Track;

public class ArtistDAOCustomImpl implements ArtistDAOCustom {

    @PersistenceContext
    EntityManager entityManager;

    /**
     * Returns the matching Artist object.   If the
     * create parameter is true, this method will
     * insert a new Artist and return the newly created
     * Artist object.
     */
    public Artist getArtist(String name, boolean create) {
        TypedQuery<Artist> query =
            entityManager.createQuery("SELECT a FROM Artist a WHERE a.name = :name", Artist.class);
        query.setParameter("name", name);

        /* Bad
        Optional<Artist> opt = Optional.ofNullable(query.getSingleResult());
        Artist found = opt.isPresent() ? opt.get() : null;
        if (found == null && create) {
            found = new Artist(name, new HashSet<Track>(), null);
            entityManager.persist(found);
        }
        */

        /* The exceptions are slow and synchronized, avoid if you can
        Artist found = null;
        try {
            found = query.getSingleResult();
        }
        catch(NoResultException e) {
            if (create) {
                found = new Artist(name, new HashSet<Track>(), null);
                entityManager.persist(found);
            }
        }
        */

        /*
        List<Artist> results = query.getResultList();

        Artist found = null;
        if (!results.isEmpty()) {
            // ignores multiple results
            found = results.get(0);
        }
        */

        // https://stackoverflow.com/questions/2002993/jpa-getsingleresult-or-null
        // from JPA 2.2
        Artist found = query.setMaxResults(1).getResultStream().findFirst().orElse(null);

        if (found == null && create) {
            found = new Artist(name, new HashSet<Track>(), null);
            entityManager.persist(found);
        }

        if (found != null && found.getActualArtist() != null) {
            return found.getActualArtist();
        }
        return found;
    }

}

