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

import com.oreilly.hh.data.Employee;
import com.oreilly.hh.data.Company;

public class CompanyEmployeeTest {

    private static SessionFactory sessionFactory = null;

    private static List<String> empNames =
        Arrays.asList("Lim Si Wan", "Lee Sung Min");

    private static int empCount = empNames.size();

    @BeforeAll
    public static void setUp() throws Exception {
        sessionFactory = HibernateUtil5.getSessionFactory();

        // Initialize the database for test
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        Company company = new Company("One International");
        session.save(company);

        for (String ename : empNames) {
            session.save(new Employee(ename, company));
        }

        tx.commit();
        session.close();
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
    public void testCount() {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        Query<Employee> q = session.createQuery("from Employee", Employee.class);

        List<Employee> resultList = q.list();
        int count = resultList.size();

        tx.commit();
        session.close();

        assertThat(count).isEqualTo(empCount);
    }

    @Test
    public void testCollection() {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        Query<Employee> q = session.createQuery("from Employee", Employee.class);

        List<Employee> resultList = q.list();
        int count = resultList.size();

        List<String> employees = new ArrayList<String>();

        System.out.println("num of employees: " + count);
        for (Employee next : resultList) {
            employees.add(next.getName());
        }

        tx.commit();
        session.close();

        assertThat(employees).contains("Lim Si Wan", "Lee Sung Min");

    }

    @Test
    public void testAssociation() {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        Query<Employee> q = session.createQuery("from Employee", Employee.class);

        List<Employee> resultList = q.list();

        for (Employee next : resultList) {
            assertThat(next.getCompany().getName()).isEqualTo("One International");
        }

        tx.commit();
        session.close();

    }
}
