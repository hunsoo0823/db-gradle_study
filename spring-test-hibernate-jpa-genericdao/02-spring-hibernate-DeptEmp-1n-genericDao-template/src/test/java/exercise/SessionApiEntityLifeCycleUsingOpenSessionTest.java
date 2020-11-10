package exercise;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.hibernate.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.test.context.TestConstructor.AutowireMode;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import exercise.config.TestPersistenceConfig;
import exercise.model.Department;
import exercise.model.Employee;

@SpringJUnitConfig(TestPersistenceConfig.class)
@TestConstructor(autowireMode = AutowireMode.ALL)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

//Do not use @Transactional
@lombok.extern.slf4j.Slf4j
public class SessionApiEntityLifeCycleUsingOpenSessionTest {

    private SessionFactory sessionFactory;

    private Session session;
    private Transaction tx;

    private SessionApiEntityLifeCycleUsingOpenSessionTest(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @BeforeEach
    public void openSession() {
        session = sessionFactory.openSession();
        tx = session.beginTransaction();
    }

    @AfterEach
    public void closeSession() {
        tx.rollback();
        session.close();
    }

    @Test
    void test_count() {
        var qry = "SELECT count(d) FROM Department d";
        var cnt = session.createQuery(qry, Long.class).getSingleResult();
        log.debug("cnt = {}", cnt);
    }

    @Test
    void test_count_detail() {
        var qry = "SELECT count(d) FROM Department d";
        var cnt1 = session.createQuery(qry, Long.class).getSingleResult();
        log.debug("cnt1 = {}", cnt1);

        var department = new Department("Java");
            assertNull(department.getId());

        session.persist(department);
            assertNotNull(department.getId());

            assertTrue(session.contains(department));

        var cnt2 = session.createQuery(qry, Long.class).getSingleResult();
        log.debug("cnt2 = {}", cnt2);

            assertThat(cnt2).isEqualTo(cnt1 + 1);
    }

    @Test
    void test_findAll() {
        var cntQry = "SELECT count(d) FROM Department d";
        var cnt = session.createQuery(cntQry, Long.class).getSingleResult();
        log.debug("cnt = {}", cnt);

        var deptQry = "SELECT d FROM Department d";
        var departments = session.createQuery(deptQry, Department.class).getResultList();

            assertThat(Long.valueOf(departments.size())).isEqualTo(cnt);
    }

    @Test
    void test_contains() {
        var department = new Department("Java");
        session.persist(department);

            assertTrue(session.contains(department));
    }

    @Test
    void test_evict() {
        var department = new Department("Java");
        session.persist(department);

            assertTrue(session.contains(department));

        session.evict(department);

            assertFalse(session.contains(department));
    }

    @Test
    void test_detach() {
        var department = new Department("Java");
        session.persist(department);

            assertTrue(session.contains(department));

        session.detach(department);

            assertFalse(session.contains(department));
    }

    @Test
    void test_update_modifedDetachedInstance() {

        var department = new Department("Java");
        session.persist(department);

            assertTrue(session.contains(department));

        session.detach(department);

            assertFalse(session.contains(department));

        department.setName("Kotlin");

        session.update(department);

            assertTrue(session.contains(department));
    }

    @Test
    void test_attach_employee_unmmdifedDetachedInstance() {

        var employee = new Employee("Java");
        session.persist(employee);

            assertTrue(session.contains(employee));

        session.detach(employee);

            assertFalse(session.contains(employee));

        //employee.setName("Kotlin");

        // attach()
        session.lock(employee, LockMode.NONE);
        //session.buildLockRequest(LockOptions.NONE).lock(employee);

            assertTrue(session.contains(employee));
    }

    // org.hibernate.HibernateException: reassociated object has dirty collection reference

    @Test
    void test_attach_unmmdifedDetachedInstance() {

        var department = new Department("Java");
        session.persist(department);

            assertTrue(session.contains(department));

        session.detach(department);

            assertFalse(session.contains(department));

        //department.setName("Kotlin");

        // attach()
        assertThrows(HibernateException.class, () -> {
            session.lock(department, LockMode.NONE);
            //session.buildLockRequest(LockOptions.NONE).lock(department);
        });

            assertTrue(session.contains(department));

    }

    @Test
    void test_merge_modifedDetachedInstance() {

        var department = new Department("Java");
        session.persist(department);

            assertTrue(session.contains(department));

        session.detach(department);

            assertFalse(session.contains(department));

        department.setName("Kotlin");

        var newdept = session.merge(department);

            assertTrue(session.contains(newdept));
    }


    @Test
    void test_merge_unmodifedDetachedInstance() {

        var department = new Department("Java");
        session.persist(department);

            assertTrue(session.contains(department));

        session.detach(department);

            assertFalse(session.contains(department));

        //department.setName("Kotlin");

        var newdept = session.merge(department);

            assertTrue(session.contains(newdept));
    }

    @Test
    void test_refresh_modifedDetachedInstance() {

        var department = session.get(Department.class, 1L);
        log.info("{}", department);
            assertThat(department.getName()).isEqualTo("Development");

        department.setName("Kotlin");
            assertThat(department.getName()).isEqualTo("Kotlin");

        session.refresh(department);
            assertThat(department.getName()).isEqualTo("Development");
    }

    @Test
    void test_persist_only() {
        var department = new Department("Java");
        session.persist(department);
    }

    @Test
    void test_persist_and_flush() {

        var department = new Department("Java");
        session.persist(department);

        session.flush();

        department.setName("Kotlin");
    }

}
