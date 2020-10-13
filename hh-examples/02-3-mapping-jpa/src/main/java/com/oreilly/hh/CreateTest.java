package com.oreilly.hh;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Create sample data, letting Hibernate persist it for us.
 */
public class CreateTest {

    public static void main(String args[]) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        emf.close();
    }

}
