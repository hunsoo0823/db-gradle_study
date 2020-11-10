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
public class DepartmentRepositoryEntityLifeCycleTest {

    private DepartmentRepository departmentRepository;

    private DepartmentRepositoryEntityLifeCycleTest( DepartmentRepository departmentRepository ) {
        this.departmentRepository = departmentRepository;
    }

    @Test
    void test_findAll() {
        var cnt = departmentRepository.count();

        var departments = departmentRepository.findAll();
        assertThat(departments.size()).isEqualTo(cnt);
    }

    @Test
    void test_create_and_findAll() {
        var cnt = departmentRepository.count();

        var department = new Department("Java");
        departmentRepository.create(department);

        var departments = departmentRepository.findAll();
        assertThat(departments.size()).isEqualTo(cnt + 1);
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
    @Transactional
    void test_create_and_findAll_and_delete() {
        var cnt = departmentRepository.count();

        var department = new Department("Java");
        departmentRepository.create(department);

        assertThat(departmentRepository.findAll().size()).isEqualTo(cnt + 1);

        departmentRepository.delete(department);

        assertThat(departmentRepository.findAll().size()).isEqualTo(cnt);
    }

}

