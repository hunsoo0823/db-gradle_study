package com.oreilly.hh.dao.hibernate;

import org.hibernate.SessionFactory;

import org.springframework.transaction.annotation.Transactional;

import com.oreilly.hh.dao.AlbumDAO;
import com.oreilly.hh.data.Album;

@Transactional
public class AlbumHibernateDAO implements AlbumDAO {

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Album persist(Album album) {
        sessionFactory.getCurrentSession().save(album);
        return album;
    }

    public void delete (Album album) {
        sessionFactory.getCurrentSession().delete(album);
    }

}
