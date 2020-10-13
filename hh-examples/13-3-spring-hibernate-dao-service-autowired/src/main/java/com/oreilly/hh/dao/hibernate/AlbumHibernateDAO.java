package com.oreilly.hh.dao.hibernate;

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

    public Album persist(Album album) {
        sessionFactory.getCurrentSession().save(album);
        return album;
    }

    public void delete (Album album) {
        sessionFactory.getCurrentSession().delete(album);
    }

}
