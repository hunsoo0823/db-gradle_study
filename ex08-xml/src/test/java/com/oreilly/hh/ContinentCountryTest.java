package com.oreilly.hh;

import java.util.*;

import org.junit.jupiter.api.*;                             // Test, BeforeAll, BeforeEach, AfterEach
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;           // assertEquals, assertTrue
import static org.assertj.core.api.Assertions.assertThat;   // assertThat
import static org.assertj.core.api.Assertions.withPrecision;

import org.hibernate.query.Query;
import org.hibernate.cfg.Configuration;
import org.hibernate.*;

import com.oreilly.hh.data.Continent;
import com.oreilly.hh.data.Country;

public class ContinentCountryTest {

    private static SessionFactory sessionFactory = null;

    private static List<String> empNames =
        Arrays.asList("Lim Si Wan", "Lee Sung Min");

    private static int empCount = empNames.size();

    @BeforeAll
    public static void setUp() throws Exception {
        sessionFactory = HibernateUtil5.getSessionFactory();
    }

    @AfterAll
    public static void tearDown() throws Exception {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    @BeforeEach
    public void runBeforeEveryTest() {
    }

    @AfterEach
    public void runAfterEveryTest() {
    }

    @Test
    public void testContinentCount() {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        Query<Continent> q = session.createQuery("from Continent", Continent.class);

        List<Continent> resultList = q.list();
        int count = resultList.size();

        tx.commit();
        session.close();

        assertThat(3).isEqualTo(count);
    }

    @Test
    public void testCountryCount() {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        Query<Country> q = session.createQuery("from Country", Country.class);

        List<Country> resultList = q.list();
        int count = resultList.size();

        tx.commit();
        session.close();

        assertThat(8).isEqualTo(count);
    }

    @Test
    public void testContinentCollection() {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        Query<Continent> q = session.createQuery("from Continent", Continent.class);

        List<Continent> resultList = q.list();
        int count = resultList.size();

        List<String> list = new ArrayList<String>();

        System.out.println("num of continents: " + count);
        for (Continent next : resultList) {
            list.add(next.getName());
        }

        tx.commit();
        session.close();

        assertThat(list).contains("Africa", "Europe", "Oceania");
    }

    @Test
    public void testCountryCollection() {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        Query<Country> q = session.createQuery("from Country", Country.class);

        List<Country> resultList = q.list();
        int count = resultList.size();

        List<String> list = new ArrayList<String>();

        System.out.println("num of countries: " + count);
        for (Country next : resultList) {
            list.add(next.getName());
        }

        tx.commit();
        session.close();

        assertThat(list).contains("Germany", "Ghana", "Australia", "Greece", "Georgia", "New Zealand", "Gambia", "Gabon");
    }

    @Test
    public void testAssociation() {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        Query<Country> q = session.createQuery("from Country", Country.class);

        List<Country> resultList = q.list();

        Set<String> continents = new HashSet<String>();

        for (Country next : resultList) {
            continents.add(next.getContinent().getName());
        }

        assertThat(continents).contains("Africa", "Europe", "Oceania");

        tx.commit();
        session.close();

    }
}
