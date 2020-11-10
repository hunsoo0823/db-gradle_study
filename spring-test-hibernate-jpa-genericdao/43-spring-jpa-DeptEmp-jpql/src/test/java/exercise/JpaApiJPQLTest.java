package exercise;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import javax.persistence.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.test.context.TestConstructor.AutowireMode;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import exercise.config.TestPersistenceConfig;
import exercise.model.Department;
import exercise.model.Employee;
import exercise.model.EmployeeValue;

// -------------------------------------------------
// JPQL – How to Define Queries in JPA and Hibernate
// By Thorben Janssen
// https://thorben-janssen.com/jpql/
// -------------------------------------------------

@SpringJUnitConfig(TestPersistenceConfig.class)
@TestConstructor(autowireMode = AutowireMode.ALL)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

@Transactional
@lombok.extern.slf4j.Slf4j
public class JpaApiJPQLTest {

    @PersistenceContext
    EntityManager entityManager;

    @Test
    void test_select_entity_from() {

        var qry = "SELECT e FROM Employee e";
        var employees = entityManager.createQuery(qry, Employee.class).getResultList();

        assertThat(employees).extracting("id").contains(5L,6L,7L,8L,9L,10L,11L,12L,13L,14L,15L);
    }

    @Test
    void test_select_entity_from_where() {

        var qry = "SELECT e FROM Employee e WHERE e.name LIKE :pattern";
        var employees = entityManager.createQuery(qry, Employee.class)
            .setParameter("pattern", "R%")
            .getResultList();

        assertThat(employees).extracting("name").contains("Ramon", "Rosemary");
    }

    @Test
    void test_select_entity_from_where_IS_NULL() {

        var qry = "SELECT e FROM Employee e WHERE e.department IS NULL";
        var employees = entityManager.createQuery(qry, Employee.class).getResultList();

        assertThat(employees).extracting("name").contains("Bonnie");
    }

    @Test
    void test_select_entity_from_where_IS_NOT_EMPTY() {

        var qry = "SELECT d FROM Department d WHERE d.employees IS NOT EMPTY";
        var departments = entityManager.createQuery(qry, Department.class).getResultList();

        assertThat(departments).extracting("name").contains("Development", "Management", "Marketing", "Personnel");
    }

    @Test
    void test_select_attributes_from() {

        var qry = "SELECT e.id, e.name FROM Employee e";
        List<Object[]> employees = entityManager.createQuery(qry, Object[].class).getResultList();

        assertThat(employees.size()).isEqualTo(11);

        if (false) {
            for (Object[] emp: employees) {
                Long id = (Long) emp[0];
                String name = (String) emp[1];

                log.info("Employee(id: {}, name: {})", id, name);
            }
        }
    }

    @Test
    void test_select_tuple_from() {

        var qry = "SELECT e.id, e.name FROM Employee e";
        List<Tuple> employees = entityManager.createQuery(qry, Tuple.class).getResultList();

        assertThat(employees.size()).isEqualTo(11);

        if (false) {
            for (Tuple emp: employees) {
                Long id = emp.get(0, Long.class);
                String name = emp.get(1, String.class);

                log.info("Employee(id: {}, name: {})", id, name);
            }
        }
    }

    @Test
    void test_select_constructor_from() {

        var qry = "SELECT new exercise.model.EmployeeValue(e.id, e.name) FROM Employee e";
        List<EmployeeValue> employees = entityManager.createQuery(qry, EmployeeValue.class).getResultList();

        assertThat(employees.size()).isEqualTo(11);
    }

    @Test
    void test_polymorphism() {

        var qry = "SELECT o FROM java.lang.Object o";
        List<Object> objects = entityManager.createQuery(qry, Object.class).getResultList();

        assertThat(objects.size()).isEqualTo(4 + 11);
    }

    @Test
    void test_inner_join_implicit() {

        var qry = "SELECT e FROM Employee e JOIN e.department d";
        var employees = entityManager.createQuery(qry, Employee.class).getResultList();

        // Notice : 15L
        assertThat(employees).extracting("id").contains(5L,6L,7L,8L,9L,10L,11L,12L,13L,14L);
    }

    @Test
    void test_inner_join_explicit() { // from Hibernate 5.1 and JPA 2.2

        var qry = "SELECT e FROM Employee e JOIN Department d ON e.name = d.name";
        var employees = entityManager.createQuery(qry, Employee.class).getResultList();

        assertThat(employees).isEmpty();
    }

    @Test
    void test_left_outer_join() {

        var qry = "SELECT e FROM Employee e LEFT JOIN e.department d";
        var employees = entityManager.createQuery(qry, Employee.class).getResultList();

        // Notice : 15L
        assertThat(employees).extracting("id").contains(5L,6L,7L,8L,9L,10L,11L,12L,13L,14L,15L);
    }

    @Test
    void test_join_fetch() {

        var qry = "SELECT e FROM Employee e JOIN FETCH e.department d";
        var employees = entityManager.createQuery(qry, Employee.class).getResultList();

        assertThat(employees).extracting("id").contains(5L,6L,7L,8L,9L,10L,11L,12L,13L,14L);
    }

    @Test
    void test_left_join_fetch() {

        var qry = "SELECT e FROM Employee e LEFT JOIN FETCH e.department d";
        var employees = entityManager.createQuery(qry, Employee.class).getResultList();

        assertThat(employees).extracting("id").contains(5L,6L,7L,8L,9L,10L,11L,12L,13L,14L,15L);
    }

    @Test
    void test_inner_join_returning_multiple_entities() {

        var qry = "SELECT d, e FROM Department d JOIN d.employees e";
        List<Object[]> dept_emps = entityManager.createQuery(qry, Object[].class).getResultList();

        assertThat(dept_emps.size()).isEqualTo(10);

        if (false) {
            for (Object[] dept_emp: dept_emps) {
                Department dept = (Department) dept_emp[0];
                Employee emp = (Employee) dept_emp[1];

                log.trace("{} <--- {} ::: {}", dept, emp, dept.getEmployees());
            }
        }
    }

}

