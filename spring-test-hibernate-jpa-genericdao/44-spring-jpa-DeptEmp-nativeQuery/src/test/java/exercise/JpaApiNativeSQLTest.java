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
import exercise.model.DepartmentValue;
import exercise.model.Employee;
import exercise.model.EmployeeValue;

// -------------------------------------------------
// Native Queries – How to call native SQL queries with JPA & Hibernate
// By Thorben Janssen
// https://thorben-janssen.com/jpa-native-queries/
// https://thorben-janssen.com/result-set-mapping-basics/
// https://thorben-janssen.com/result-set-mapping-complex-mappings/
// https://thorben-janssen.com/result-set-mapping-constructor-result-mappings/
// -------------------------------------------------

@SpringJUnitConfig(TestPersistenceConfig.class)
@TestConstructor(autowireMode = AutowireMode.ALL)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

@Transactional
@lombok.extern.slf4j.Slf4j
public class JpaApiNativeSQLTest {

    @PersistenceContext
    EntityManager entityManager;

    @Test
    @SuppressWarnings("unchecked")
    void test_nativeQuery_projection() {

        var sql = "SELECT d.id, d.name FROM Department d";
        List<Object[]> departments = entityManager.createNativeQuery(sql).getResultList();

        assertThat(departments.size()).isEqualTo(4);

        for(Object[] department : departments) {
            Long id = ((java.math.BigInteger) department[0]).longValue();
            String name = (String) department[1];
            log.info("Department({}, {})", id, name);
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    void test_nativeQuery_using_default_mapping() {

        var sql = "SELECT d.* FROM Department d";
        List<Department> departments = entityManager
            .createNativeQuery(sql, Department.class)
            .getResultList();

        assertThat(departments.size()).isEqualTo(4);

        for (Department department : departments) {
            log.info("{}", department);
        }
    }

    // Hibernate supports named parameter bindings for native queries
    //  but as I already said,
    //  this is not defined by the specification and might not be portable to other JPA implementations.

    @Test
    @SuppressWarnings("unchecked")
    void test_nativeQuery_using_named_parameter() {

        var sql = "SELECT e.* FROM Employee e WHERE e.name LIKE :pattern";
        List<Employee> employees = entityManager.createNativeQuery(sql, Employee.class)
            .setParameter("pattern", "R%")
            .getResultList();

        assertThat(employees).extracting("name").contains("Ramon", "Rosemary");
    }

    // Positional parameters are referenced as “?” in your native Query
    //  and their numbering starts at 1.

    @Test
    @SuppressWarnings("unchecked")
    void test_nativeQuery_using_positional_parameter() {

        var sql = "SELECT e.* FROM Employee e WHERE e.name LIKE ?";
        List<Employee> employees = entityManager.createNativeQuery(sql, Employee.class)
            .setParameter(1, "R%")
            .getResultList();

        assertThat(employees).extracting("name").contains("Ramon", "Rosemary");
    }


    @Test
    @SuppressWarnings("unchecked")
    void test_nativeQuery_using_custom_mapping_withEntityResult() {

        var sql = "SELECT d.id, d.name FROM Department d";
        List<Department> departments = entityManager
            .createNativeQuery(sql, "DepartmentMapping")
            .getResultList();

        assertThat(departments.size()).isEqualTo(4);

        for (Department department : departments) {
            log.info("{}", department);
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    void test_nativeQuery_using_custom_mapping_withConstructorResult() {

        var sql = "SELECT d.id, d.name FROM Department d";
        List<DepartmentValue> departments = entityManager
            .createNativeQuery(sql, "DepartmentValueMapping")
            .getResultList();

        assertThat(departments.size()).isEqualTo(4);

        for (DepartmentValue department : departments) {
            log.info("{}", department);
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    void test_nativeQuery_using_xml_custom_mapping_withEntityResult() {

        var sql = "SELECT d.id, d.name FROM Department d";
        List<Department> departments = entityManager
            .createNativeQuery(sql, "XmlDepartmentMapping")
            .getResultList();

        assertThat(departments.size()).isEqualTo(4);

        for (Department department : departments) {
            log.info("{}", department);
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    void test_nativeQuery_using_xml_custom_mapping_withConstructorResult() {

        var sql = "SELECT d.id, d.name FROM Department d";
        List<DepartmentValue> departments = entityManager
            .createNativeQuery(sql, "XmlDepartmentValueMapping")
            .getResultList();

        assertThat(departments.size()).isEqualTo(4);

        for (DepartmentValue department : departments) {
            log.info("{}", department);
        }
    }
}

