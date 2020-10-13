package com.oreilly.hh.dao.hibernate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.oreilly.hh.dao.AlbumDAO;
import com.oreilly.hh.data.Album;

@Repository
public class AlbumHibernateDAO implements AlbumDAO {

    // @Autowired
    // private SessionFactory sessionFactory;
    //
    // public void setSessionFactory(SessionFactory sessionFactory) {
    //     this.sessionFactory = sessionFactory;
    // }

    @PersistenceContext
    private EntityManager entityManager;

    public Album persist(Album album) {
        //sessionFactory.getCurrentSession().save(album);
        Session session = null;
        if (entityManager == null || (session = entityManager.unwrap(Session.class)) == null) {
            throw new NullPointerException();
        }
        session.save(album);
        return album;
    }

    public void delete (Album album) {
        //sessionFactory.getCurrentSession().delete(album);
        Session session = null;
        if (entityManager == null || (session = entityManager.unwrap(Session.class)) == null) {
            throw new NullPointerException();
        }
        session.delete(album);
    }

}
