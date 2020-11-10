package exercise;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.hibernate.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.test.context.TestConstructor.AutowireMode;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import exercise.config.TestPersistenceConfig;
import exercise.model.Department;
import exercise.model.Employee;

// -------------------------------------------------------------------------------
// What is the difference between JOIN and JOIN FETCH when using JPA and Hibernate
// https://stackoverflow.com/questions/17431312/what-is-the-difference-between-join-and-join-fetch-when-using-jpa-and-hibernate
// -------------------------------------------------------------------------------

@SpringJUnitConfig(TestPersistenceConfig.class)
@TestConstructor(autowireMode = AutowireMode.ALL)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

@Transactional
@lombok.extern.slf4j.Slf4j
public class SessionApiJoinFetchTest {

    private SessionFactory sessionFactory;

    private SessionApiJoinFetchTest(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    protected Session getCurrentSession() {
        return this.sessionFactory.getCurrentSession();
    }

    @Test
    void test_count() {
        var qry = "SELECT count(d) FROM Department d";
        var cnt = this.getCurrentSession().createQuery(qry, Long.class).getSingleResult();
        log.debug("cnt = {}", cnt);
    }

    @Test
    void test_count_detail() {
        var qry = "SELECT count(d) FROM Department d";
        var cnt1 = this.getCurrentSession().createQuery(qry, Long.class).getSingleResult();
        log.debug("cnt1 = {}", cnt1);

        var department = new Department("Java");
            assertNull(department.getId());

        this.getCurrentSession().persist(department);
            assertNotNull(department.getId());

            assertTrue(this.getCurrentSession().contains(department));

        var cnt2 = this.getCurrentSession().createQuery(qry, Long.class).getSingleResult();
        log.debug("cnt2 = {}", cnt2);

            assertThat(cnt2).isEqualTo(cnt1 + 1);
    }

    @Test
    void test_findAll() {
        var cntQry = "SELECT count(d) FROM Department d";
        var cnt = this.getCurrentSession().createQuery(cntQry, Long.class).getSingleResult();
        log.debug("cnt = {}", cnt);

        var deptQry = "SELECT d FROM Department d";
        var departments = this.getCurrentSession().createQuery(deptQry, Department.class).getResultList();

            assertThat(Long.valueOf(departments.size())).isEqualTo(cnt);
    }

    @Test
    void test_no_join() {
        var cntQry = "SELECT count(e) FROM Employee e";
        var cnt = this.getCurrentSession().createQuery(cntQry, Long.class).getSingleResult();

        var empQry = "SELECT e FROM Employee e";
        var employees = this.getCurrentSession().createQuery(empQry, Employee.class).getResultList();

        assertThat(Long.valueOf(employees.size())).isEqualTo(cnt);

    }

    /*
    JPQL:
        FROM Employee emp
        JOIN emp.department dep

    Generated SQL:
        SELECT emp.*
        FROM employee emp
        JOIN department dep ON emp.department_id = dep.id
    */

    @Test
    void test_inner_join() {
        var cntQry = "SELECT count(e) FROM Employee e";
        var cnt = this.getCurrentSession().createQuery(cntQry, Long.class).getSingleResult();

        var empQry = "SELECT e FROM Employee e JOIN e.department d";
        var employees = this.getCurrentSession().createQuery(empQry, Employee.class).getResultList();

        assertThat(Long.valueOf(employees.size())).isNotEqualTo(cnt);

    }

    /*
    JPQL:
        FROM Employee emp
        JOIN FETCH emp.department dep

    Generated SQL:
        SELECT emp.*, dept.*
        FROM employee emp
        JOIN department dep ON emp.department_id = dep.id
    */

    @Test
    void test_join_fetch() {
        var cntQry = "SELECT count(e) FROM Employee e";
        var cnt = this.getCurrentSession().createQuery(cntQry, Long.class).getSingleResult();

        var empQry = "SELECT e FROM Employee e JOIN FETCH e.department d";
        var employees = this.getCurrentSession().createQuery(empQry, Employee.class).getResultList();

        assertThat(Long.valueOf(employees.size())).isNotEqualTo(cnt);
    }

    @Test
    void test_left_join() {
        var cntQry = "SELECT count(e) FROM Employee e";
        var cnt = this.getCurrentSession().createQuery(cntQry, Long.class).getSingleResult();

        var empQry = "SELECT e FROM Employee e LEFT JOIN e.department d";
        var employees = this.getCurrentSession().createQuery(empQry, Employee.class).getResultList();

        assertThat(Long.valueOf(employees.size())).isEqualTo(cnt);

    }

    @Test
    void test_left_join_fetch() {
        var cntQry = "SELECT count(e) FROM Employee e";
        var cnt = this.getCurrentSession().createQuery(cntQry, Long.class).getSingleResult();

        var empQry = "SELECT e FROM Employee e LEFT JOIN FETCH e.department d";
        var employees = this.getCurrentSession().createQuery(empQry, Employee.class).getResultList();

        assertThat(Long.valueOf(employees.size())).isEqualTo(cnt);

    }

    /*
    @Test
    void test_inner_join_project_two_object() {
        var cntQry = "SELECT count(e) FROM Employee e";
        var cnt = this.getCurrentSession().createQuery(cntQry, Long.class).getSingleResult();

        var empQry = "SELECT e, d FROM Employee e JOIN e.department d";
        var employees = this.getCurrentSession().createQuery(empQry, Employee.class).getResultList();

        assertThat(Long.valueOf(employees.size())).isEqualTo(cnt);

    }
    */

}

