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

@SpringJUnitConfig(TestPersistenceConfig.class)
@TestConstructor(autowireMode = AutowireMode.ALL)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

@Transactional
@lombok.extern.slf4j.Slf4j
public class SessionApiEntityLifeCycleUsingGetCurrentSessionTest {

    private SessionFactory sessionFactory;

    private SessionApiEntityLifeCycleUsingGetCurrentSessionTest(SessionFactory sessionFactory) {
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
    void test_contains() {
        var department = new Department("Java");
        this.getCurrentSession().persist(department);

            assertTrue(this.getCurrentSession().contains(department));
    }

    @Test
    void test_evict() {
        var department = new Department("Java");
        this.getCurrentSession().persist(department);

            assertTrue(this.getCurrentSession().contains(department));

        this.getCurrentSession().evict(department);

            assertFalse(this.getCurrentSession().contains(department));
    }

    @Test
    void test_detach() {
        var department = new Department("Java");
        this.getCurrentSession().persist(department);

            assertTrue(this.getCurrentSession().contains(department));

        this.getCurrentSession().detach(department);

            assertFalse(this.getCurrentSession().contains(department));
    }

    @Test
    void test_update_modifedDetachedInstance() {

        var department = new Department("Java");
        this.getCurrentSession().persist(department);

            assertTrue(this.getCurrentSession().contains(department));

        this.getCurrentSession().detach(department);

            assertFalse(this.getCurrentSession().contains(department));

        department.setName("Kotlin");

        this.getCurrentSession().update(department);

            assertTrue(this.getCurrentSession().contains(department));
    }

    @Test
    void test_attach_employee_unmmdifedDetachedInstance() {

        var employee = new Employee("Java");
        this.getCurrentSession().persist(employee);

            assertTrue(this.getCurrentSession().contains(employee));

        this.getCurrentSession().detach(employee);

            assertFalse(this.getCurrentSession().contains(employee));

        //employee.setName("Kotlin");

        // attach()
        this.getCurrentSession().lock(employee, LockMode.NONE);
        //this.getCurrentSession().buildLockRequest(LockOptions.NONE).lock(employee);

            assertTrue(this.getCurrentSession().contains(employee));
    }

    // org.hibernate.HibernateException: reassociated object has dirty collection reference

    @Test
    void test_attach_unmmdifedDetachedInstance() {

        var department = new Department("Java");
        this.getCurrentSession().persist(department);

            assertTrue(this.getCurrentSession().contains(department));

        this.getCurrentSession().detach(department);

            assertFalse(this.getCurrentSession().contains(department));

        //department.setName("Kotlin");

        // attach()
        assertThrows(HibernateException.class, () -> {
            this.getCurrentSession().lock(department, LockMode.NONE);
            //this.getCurrentSession().buildLockRequest(LockOptions.NONE).lock(department);
        });

            assertTrue(this.getCurrentSession().contains(department));

    }

    @Test
    void test_merge_modifedDetachedInstance() {

        var department = new Department("Java");
        this.getCurrentSession().persist(department);

            assertTrue(this.getCurrentSession().contains(department));

        this.getCurrentSession().detach(department);

            assertFalse(this.getCurrentSession().contains(department));

        department.setName("Kotlin");

        var newdept = this.getCurrentSession().merge(department);

            assertTrue(this.getCurrentSession().contains(newdept));
    }


    @Test
    void test_merge_unmodifedDetachedInstance() {

        var department = new Department("Java");
        this.getCurrentSession().persist(department);

            assertTrue(this.getCurrentSession().contains(department));

        this.getCurrentSession().detach(department);

            assertFalse(this.getCurrentSession().contains(department));

        //department.setName("Kotlin");

        var newdept = this.getCurrentSession().merge(department);

            assertTrue(this.getCurrentSession().contains(newdept));
    }

    @Test
    void test_refresh_modifedDetachedInstance() {

        var department = this.getCurrentSession().get(Department.class, 1L);
        log.info("{}", department);
            assertThat(department.getName()).isEqualTo("Development");

        department.setName("Kotlin");
            assertThat(department.getName()).isEqualTo("Kotlin");

        this.getCurrentSession().refresh(department);
            assertThat(department.getName()).isEqualTo("Development");
    }

    @Test
    void test_persist_only() {
        var department = new Department("Java");
        this.getCurrentSession().persist(department);
    }

    @Test
    void test_persist_and_flush() {

        var department = new Department("Java");
        this.getCurrentSession().persist(department);

        this.getCurrentSession().flush();

        department.setName("Kotlin");
    }

}
