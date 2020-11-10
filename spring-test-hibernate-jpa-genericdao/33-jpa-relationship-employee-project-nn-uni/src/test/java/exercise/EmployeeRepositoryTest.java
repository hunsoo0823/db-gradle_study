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
    @Transactional
    void test_phones_of_employee() {
        var employee = employeeRepository.findById(5L);
        var phones = employee.getPhones();

        assertThat(employee.getName()).isEqualTo("Allison");
        assertThat(phones).extracting("model").contains("Galaxy", "iPhone");
    }

    @Test
    @Transactional
    void test_projects_of_employee() {
        var employee = employeeRepository.findById(5L);
        var projects = employee.getProjects();

        assertThat(employee.getName()).isEqualTo("Allison");
        assertThat(projects).extracting("title").contains("Java Projects", "Android App Projects");
    }

}

