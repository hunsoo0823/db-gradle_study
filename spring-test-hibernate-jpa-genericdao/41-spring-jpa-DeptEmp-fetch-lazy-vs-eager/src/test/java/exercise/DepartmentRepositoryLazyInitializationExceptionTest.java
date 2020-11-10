package exercise;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.test.context.TestConstructor.AutowireMode;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import exercise.config.TestPersistenceConfig;
import exercise.model.Department;
import exercise.repository.DepartmentRepository;

@SpringJUnitConfig(TestPersistenceConfig.class)
@TestConstructor(autowireMode = AutowireMode.ALL)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

@lombok.extern.slf4j.Slf4j
public class DepartmentRepositoryLazyInitializationExceptionTest {

    private DepartmentRepository departmentRepository;

    private DepartmentRepositoryLazyInitializationExceptionTest( DepartmentRepository departmentRepository ) {
        this.departmentRepository = departmentRepository;
    }

    @Test
    void test_getReference_LazyInitializationException() {

        var detachedProxyDepartment = departmentRepository.getReference(1L);

        assertThrows(org.hibernate.LazyInitializationException.class, () -> {
            log.info("{}", detachedProxyDepartment.getName());
        });
    }

    @Test
    void test_findAll_LazyInitializationException() {

        var departments = departmentRepository.findAll();

        assertThat(departments)
            .extracting("name")
            .contains("Development", "Management", "Marketing", "Personnel");

        assertThrows(org.hibernate.LazyInitializationException.class, () -> {
            for (Department department: departments) {
                System.out.printf("%s <--- %s\n", department, department.getEmployees());
            }
        });
    }

    @Test
    @Transactional  // extend the Range of Transaction
    void test_findAll_LazyInitializationException_Remedy_No1() {

        var departments = departmentRepository.findAll();

        assertThat(departments)
            .extracting("name")
            .contains("Development", "Management", "Marketing", "Personnel");

        for (Department department: departments) {
            log.trace("{} <--- {}", department, department.getEmployees());
        }
    }

    @Test
    void test_findAll_LazyInitializationException_Remedy_No2() {

        // use JOIN FETCH
        var qry = "SELECT d FROM Department d LEFT JOIN FETCH d.employees";
        var departments = departmentRepository.findMany(qry);

        assertThat(departments)
            .extracting("name")
            .contains("Development", "Management", "Marketing", "Personnel");

        for (Department department: departments) {
            log.trace("{} <--- {}", department, department.getEmployees());
        }
    }

}

