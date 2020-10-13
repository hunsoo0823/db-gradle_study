package com.oreilly.hh.dao.hibernate;

import java.util.List;
import java.util.ArrayList;

import org.hibernate.query.Query;
import org.hibernate.SessionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.oreilly.hh.dao.AlbumDAO;
import com.oreilly.hh.data.Album;

@Repository
@Transactional
public class AlbumHibernateDAO implements AlbumDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Album persist(Album album) {
        //sessionFactory.getCurrentSession().save(album);
        sessionFactory.getCurrentSession().saveOrUpdate(album);
        return album;
    }

    public void delete (Album album) {
        sessionFactory.getCurrentSession().delete(album);
    }

    public List<Album> list() {
        Query<Album> query = sessionFactory.getCurrentSession()
                             .createQuery("select album from Album album", Album.class);
        return query.list();
    }

    public Album get(Integer id) {
        return sessionFactory.getCurrentSession()
               .get(Album.class, id);
    }
}
