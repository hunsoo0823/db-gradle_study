package com.oreilly.hh.dao.hibernate;

import javax.persistence.EntityManager;

import org.springframework.transaction.annotation.Transactional;

import com.oreilly.hh.dao.AlbumDAO;
import com.oreilly.hh.data.Album;

@Transactional
public class AlbumHibernateDAO implements AlbumDAO {

    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Album persist(Album album) {
        entityManager.persist(album);
        return album;
    }

    public void delete (Album album) {
        entityManager.remove(album);
    }

}
