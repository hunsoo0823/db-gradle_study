package com.oreilly.hh;

import org.hibernate.SessionFactory;

/**
 * Create sample data, letting Hibernate persist it for us.
 */
public class CreateTest {

    public static void main(String args[]) throws Exception {
        SessionFactory sessionFactory = HibernateUtil5.getSessionFactory();
        sessionFactory.close();
    }

}
