package exercise;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.test.context.TestConstructor.AutowireMode;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import exercise.config.TestPersistenceConfig;
import exercise.model.Department;
import exercise.repository.DepartmentRepository;

@SpringJUnitConfig(TestPersistenceConfig.class)
@TestConstructor(autowireMode = AutowireMode.ALL)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class DepartmentRepositoryLazyInitializationExceptionTest {

    private DepartmentRepository departmentRepository;

    private DepartmentRepositoryLazyInitializationExceptionTest( DepartmentRepository departmentRepository ) {
        this.departmentRepository = departmentRepository;
    }

    @Test
    void test_create_and_find_v1() {
        var detachedDepartment = new Department("Java");
        departmentRepository.create(detachedDepartment);

        var managedDepartment = departmentRepository.find(Department.class, detachedDepartment.getId());
        assertThat(managedDepartment.getId()).isEqualTo(detachedDepartment.getId());
    }

    @Test
    void test_create_and_find_v2() {
        var detachedDepartment = new Department("Java");
        departmentRepository.create(detachedDepartment);

        var managedDepartment = departmentRepository.find(detachedDepartment.getId());
        assertThat(managedDepartment.getId()).isEqualTo(detachedDepartment.getId());
    }

    @Test
    void test_create_and_getReference_v1() {
        var detachedDepartment = new Department("Java");
        departmentRepository.create(detachedDepartment);

        var managedDepartment = departmentRepository.getReference(Department.class, detachedDepartment.getId());
        assertThat(managedDepartment.getId()).isEqualTo(detachedDepartment.getId());
    }

    @Test
    void test_create_and_getReference_v2() {
        var detachedDepartment = new Department("Java");
        departmentRepository.create(detachedDepartment);

        var managedDepartment = departmentRepository.getReference(detachedDepartment.getId());
        assertThat(managedDepartment.getId()).isEqualTo(detachedDepartment.getId());
    }

    @Test
    void test_create_and_getReference_v3_LazyInitializationException() {
        var detachedDepartment = new Department("Java");
        departmentRepository.create(detachedDepartment);

        var managedDepartment = departmentRepository.getReference(detachedDepartment.getId());
        assertThat(managedDepartment.getId()).isEqualTo(detachedDepartment.getId());

        assertThrows(org.hibernate.LazyInitializationException.class, () -> {
            managedDepartment.getName();
        });
    }


}

