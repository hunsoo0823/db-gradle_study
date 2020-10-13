package com.oreilly.hh;


public class CreateTest {

    public static void main(String[] args) {
        Session session;
        Transaction tx;

        session = HibernateUtil5.getSessionFactory().getCurrentSession();
        tx = session.beginTransaction();

        // Continent

        Continent africa = new Continent();
        africa.setName("Africa");


    }

}

