package exercise;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.test.context.TestConstructor.AutowireMode;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import exercise.config.TestPersistenceConfig;
import exercise.model.Department;

@SpringJUnitConfig(TestPersistenceConfig.class)
@TestConstructor(autowireMode = AutowireMode.ALL)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

@Transactional
@lombok.extern.slf4j.Slf4j
public class JpaApiEntityLifeCycleTest {

    @PersistenceContext
    EntityManager entityManager;

    @Test
    void test_count() {
        var qry = "SELECT count(d) FROM Department d";
        var cnt = entityManager.createQuery(qry, Long.class).getSingleResult();
        log.debug("cnt = {}", cnt);
    }

    @Test
    void test_count_detail() {
        var qry = "SELECT count(d) FROM Department d";
        var cnt1 = entityManager.createQuery(qry, Long.class).getSingleResult();
        log.debug("cnt1 = {}", cnt1);

        var department = new Department("Java");
            assertNull(department.getId());

        entityManager.persist(department);
            assertNotNull(department.getId());

            assertTrue(entityManager.contains(department));

        var cnt2 = entityManager.createQuery(qry, Long.class).getSingleResult();
        log.debug("cnt2 = {}", cnt2);

            assertThat(cnt2).isEqualTo(cnt1 + 1);
    }

    @Test
    void test_find() {
        // Returns:
        //     the found entity instance or null if the entity does not exist

        var found = entityManager.find(Department.class, 1L);
        // select * from Department d where d.id = 1
        log.debug("called find(1L)");

        assertThat(found.getName()).isEqualTo("Development");

        var not_found = entityManager.find(Department.class, 10003L);
        // select * from Department d where d.id = 10003
        log.debug("called find(10003L)");

        assertNull(not_found);
    }

    @Test
    void test_getReference() {

        // Get an instance, whose state may be lazily fetched.

        var found = entityManager.getReference(Department.class, 1L);
        assertThat(found.getId()).isEqualTo(1L);

        log.debug("call getReference()");
        var not_found = entityManager.getReference(Department.class, 10003L);
        log.debug("done getReference()");

        // The EntityNotFoundException is thrown when the instance state is first accessed,
        //  if the requested instance does not exist in the database.

        System.out.println("Name of found = " + found.getName());
        // select * from Department d where d.id = 1

        assertThrows(EntityNotFoundException.class, () -> {
            System.out.println("Name of not_found = " + not_found.getName());
            // select * from Department d where d.id = 10003
        });
    }


    @Test
    void test_findAll() {
        var cntQry = "SELECT count(d) FROM Department d";
        var cnt = entityManager.createQuery(cntQry, Long.class).getSingleResult();
        log.debug("cnt = {}", cnt);

        var deptQry = "SELECT d FROM Department d";
        var departments = entityManager.createQuery(deptQry, Department.class).getResultList();

            assertThat(Long.valueOf(departments.size())).isEqualTo(cnt);
    }

    @Test
    void test_contains() {
        var department = new Department("Java");
        entityManager.persist(department);

            assertTrue(entityManager.contains(department));
    }

    /*
    @Test
    void test_evict() {
        var department = new Department("Java");
        entityManager.persist(department);

            assertTrue(entityManager.contains(department));

        entityManager.evict(department);

            assertFalse(entityManager.contains(department));
    }
    */

    @Test
    void test_detach() {
        var department = new Department("Java");
        entityManager.persist(department);

            assertTrue(entityManager.contains(department));

        entityManager.detach(department);

            assertFalse(entityManager.contains(department));
    }

    /*
    @Test
    void test_update_modifedDetachedInstance() {

        var department = new Department("Java");
        entityManager.persist(department);

            assertTrue(entityManager.contains(department));

        entityManager.detach(department);

            assertFalse(entityManager.contains(department));

        department.setName("Kotlin");

        entityManager.update(department);

            assertTrue(entityManager.contains(department));
    }

    @Test
    void test_attach_employee_unmmdifedDetachedInstance() {

        var employee = new Employee("Java");
        entityManager.persist(employee);

            assertTrue(entityManager.contains(employee));

        entityManager.detach(employee);

            assertFalse(entityManager.contains(employee));

        //employee.setName("Kotlin");

        // attach()
        entityManager.lock(employee, LockMode.NONE);
        //entityManager.buildLockRequest(LockOptions.NONE).lock(employee);

            assertTrue(entityManager.contains(employee));
    }

    // org.hibernate.HibernateException: reassociated object has dirty collection reference

    @Test
    void test_attach_unmmdifedDetachedInstance() {

        var department = new Department("Java");
        entityManager.persist(department);

            assertTrue(entityManager.contains(department));

        entityManager.detach(department);

            assertFalse(entityManager.contains(department));

        //department.setName("Kotlin");

        // attach()
        assertThrows(HibernateException.class, () -> {
            entityManager.lock(department, LockMode.NONE);
            //entityManager.buildLockRequest(LockOptions.NONE).lock(department);
        });

            assertTrue(entityManager.contains(department));

    }
    */

    @Test
    void test_merge_modifedDetachedInstance() {

        var department = new Department("Java");
        entityManager.persist(department);

            assertTrue(entityManager.contains(department));

        entityManager.detach(department);

            assertFalse(entityManager.contains(department));

        department.setName("Kotlin");

        var newdept = entityManager.merge(department);

            assertTrue(entityManager.contains(newdept));
    }


    @Test
    void test_merge_unmodifedDetachedInstance() {

        var department = new Department("Java");
        entityManager.persist(department);

            assertTrue(entityManager.contains(department));

        entityManager.detach(department);

            assertFalse(entityManager.contains(department));

        //department.setName("Kotlin");

        var newdept = entityManager.merge(department);

            assertTrue(entityManager.contains(newdept));
    }

    @Test
    void test_refresh_modifedDetachedInstance() {

        var department = entityManager.find(Department.class, 1L);
        log.info("{}", department);
            assertThat(department.getName()).isEqualTo("Development");

        department.setName("Kotlin");
            assertThat(department.getName()).isEqualTo("Kotlin");

        entityManager.refresh(department);
            assertThat(department.getName()).isEqualTo("Development");
    }

    @Test
    void test_persist_only() {
        var department = new Department("Java");
        entityManager.persist(department);
    }

    @Test
    void test_persist_and_flush() {

        var department = new Department("Java");
        entityManager.persist(department);

        entityManager.flush();

        department.setName("Kotlin");
    }

}
