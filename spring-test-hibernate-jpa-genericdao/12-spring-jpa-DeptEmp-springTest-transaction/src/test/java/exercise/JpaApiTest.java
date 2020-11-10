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
public class JpaApiTest {

    @PersistenceContext
    EntityManager entityManager;

    @Test
    void test_find() {
        var department = entityManager.find(Department.class, 1L);

        assertThat(department.getName()).isEqualTo("Development");
    }

    @Test
    void test_getReference() {
        var department = entityManager.getReference(Department.class, 1L);

        assertThat(department.getName()).isEqualTo("Development");
    }

    @Test
    void test_createQuery_count() {
        var qry = "SELECT count(d) FROM Department d";
        var cnt = entityManager.createQuery(qry, Long.class).getSingleResult();
        log.debug("cnt = {}", cnt);
    }

    @Test
    void test_createQuery_count_detail() {
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
    void test_createQuery_findAll() {
        var cntQry = "SELECT count(d) FROM Department d";
        var cnt = entityManager.createQuery(cntQry, Long.class).getSingleResult();
        log.debug("cnt = {}", cnt);

        var deptQry = "SELECT d FROM Department d";
        var departments = entityManager.createQuery(deptQry, Department.class).getResultList();

        assertThat(Long.valueOf(departments.size())).isEqualTo(cnt);
        assertThat(departments).extracting("id").contains(1L, 2L, 3L, 4L);
        assertThat(departments).extracting("name").contains("Development", "Management", "Marketing", "Personnel");
    }

    @Test
    void test_contains() {
        var department = new Department("Java");
        entityManager.persist(department);

        assertTrue(entityManager.contains(department));
    }

}
