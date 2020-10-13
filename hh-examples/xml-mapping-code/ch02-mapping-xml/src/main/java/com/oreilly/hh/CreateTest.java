package com.oreilly.hh;

import org.hibernate.query.Query;
import org.hibernate.cfg.Configuration;
import org.hibernate.*;

import com.oreilly.hh.data.*;

import java.sql.Time;
import java.util.Date;

/**
 * Create sample data, letting Hibernate persist it for us.
 */
public class CreateTest {

    public static void main(String args[]) throws Exception {
        //Configuration config = new Configuration();
        //config.configure();

        // Get the session factory we can use for persistence
        //SessionFactory sessionFactory = config.buildSessionFactory();
        SessionFactory sessionFactory = HibernateUtil5.getSessionFactory();

        // Clean up after ourselves
        sessionFactory.close();
    }
}
