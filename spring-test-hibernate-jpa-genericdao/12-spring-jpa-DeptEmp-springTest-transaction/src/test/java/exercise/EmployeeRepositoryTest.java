package exercise;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.PersistenceException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.test.context.TestConstructor.AutowireMode;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import exercise.config.TestPersistenceConfig;
import exercise.model.Employee;
import exercise.repository.EmployeeRepository;

@SpringJUnitConfig(TestPersistenceConfig.class)
@TestConstructor(autowireMode = AutowireMode.ALL)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

@lombok.extern.slf4j.Slf4j
public class EmployeeRepositoryTest {

    private EmployeeRepository employeeRepository;

    private EmployeeRepositoryTest( EmployeeRepository employeeRepository ) {
        this.employeeRepository = employeeRepository;
    }

    @Test
    void test_count() {
        var cnt = employeeRepository.count();

        assertThat(cnt).isEqualTo(11L);
    }

    @Test
    void test_findAll() {
        var cnt = employeeRepository.count();

        var employees = employeeRepository.findAll();
        assertThat(employees.size()).isEqualTo(cnt);

        assertThat(employees)
            .extracting("id")
            .contains(5L, 6L, 7L, 8L, 9L, 10L, 11L, 12L, 13L, 14L, 15L);
    }

    @Test
    void test_findById() {
        var employee = employeeRepository.findById(5L);

        assertThat(employee.getName()).isEqualTo("Allison");
    }

}

