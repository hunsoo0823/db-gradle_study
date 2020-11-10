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

@SpringJUnitConfig(TestPersistenceConfig.class)
@TestConstructor(autowireMode = AutowireMode.ALL)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

@Transactional
@lombok.extern.slf4j.Slf4j
public class JpaApiEagerLazyFetchTest {

    @PersistenceContext
    EntityManager entityManager;

    // When: LAZY, How: SELECT
    @Test
    void whenFindAllDepartment_thenFetchEmployeeLazily() {

        var qry = "SELECT d FROM Department d";
        var departments = entityManager.createQuery(qry, Department.class).getResultList();
        // -->
        // SELECT * FROM Department

        assertThat(departments).extracting("name").contains("Development", "Management", "Marketing", "Personnel");

        log.trace("{}", departments);
        // -->
        //[ Department(id=1, name=Development),
        //  Department(id=2, name=Management),
        //  Department(id=3, name=Marketing),
        //  Department(id=4, name=Personnel)
        //]

        log.trace("-----------------------------------------");
        log.trace("For each department, print its employees:");
        log.trace("-----------------------------------------");

        for (Department department: departments) {
            log.trace("{} <--- {}", department, department.getEmployees());
            // -->
            // SELECT * FROM Employee e WHERE e.department_id = 1
            // Department(id=1, name=Development) <--- [Employee(id=5, name=Allison), Employee(id=6, name=Lois), Employee(id=7, name=Ramon), Employee(id=8, name=Derek)]
            // -->
            // SELECT * FROM Employee e WHERE e.department_id = 2
            // Department(id=2, name=Management) <--- [Employee(id=9, name=Maria), Employee(id=10, name=Rosemary), Employee(id=11, name=Emma)]
            // -->
            // SELECT * FROM Employee e WHERE e.department_id = 3
            // Department(id=3, name=Marketing) <--- [Employee(id=12, name=Gabriel), Employee(id=13, name=Frances)]
            // -->
            // SELECT * FROM Employee e WHERE e.department_id = 4
            // Department(id=4, name=Personnel) <--- [Employee(id=14, name=Elaine)]

            // Notice: Employee(id=15, name=Bonnie) doesn't display!
        }
    }

    // When: EAGER, How: SELECT
    @Test
    void whenFindAllEmployee_thenFetchDepartmentEagerly() {

        var qry = "SELECT e FROM Employee e";
        var employees = entityManager.createQuery(qry, Employee.class).getResultList();
        // -->
        // SELECT * FROM Employee
        // SELECT * FROM Department WHERE id = 1
        // SELECT * FROM Department WHERE id = 2
        // SELECT * FROM Department WHERE id = 3
        // SELECT * FROM Department WHERE id = 4

        assertThat(employees).extracting("id").contains(5L,6L,7L,8L,9L,10L,11L,12L,13L,14L,15L);

        log.trace("{}", employees);
        // -->
        // [ Employee(id=5, name=Allison),
        //   Employee(id=6, name=Lois),
        //   Employee(id=7, name=Ramon),
        //   Employee(id=8, name=Derek),
        //   Employee(id=9, name=Maria),
        //   Employee(id=10, name=Rosemary),
        //   Employee(id=11, name=Emma),
        //   Employee(id=12, name=Gabriel),
        //   Employee(id=13, name=Frances),
        //   Employee(id=14, name=Elaine),
        //   Employee(id=15, name=Bonnie)
        // ]
    }

    // When: EAGER, How: JOIN FETCH
    @Test
    void whenFindAllDepartment_withJoinFetch_thenFetchEmployeeByTheSameQuery() {

        var qry = "SELECT d FROM Department d JOIN FETCH d.employees";
        var departments = entityManager.createQuery(qry, Department.class).getResultList();
        // -->
        // SELECT d.*, e.*
        // FROM Department d
        // JOIN Employee e ON e.department_id = d.id

        assertThat(departments).extracting("name").contains("Development", "Management", "Marketing", "Personnel");

        log.trace("{}", departments);

        log.trace("-----------------------------------------");
        log.trace("For each department, print its employees:");
        log.trace("-----------------------------------------");

        for (Department department: departments) {
            log.trace("{} <--- {}", department, department.getEmployees());
            // Notice: Employee(id=15, name=Bonnie) doesn't display!
        }
    }

    // When: EAGER, How: SELECT
    @Test
    void whenFindAllDepartmentOnly_withJoin_thenFetchEmployeeLazily() {

        var qry = "SELECT d FROM Department d JOIN d.employees";
        var departments = entityManager.createQuery(qry, Department.class).getResultList();
        // -->
        // SELECT d.*,
        // FROM Department d
        // JOIN Employee e ON e.department_id = d.id

        assertThat(departments).extracting("name").contains("Development", "Management", "Marketing", "Personnel");

        log.trace("{}", departments);

        log.trace("-----------------------------------------");
        log.trace("For each department, print its employees:");
        log.trace("-----------------------------------------");

        for (Department department: departments) {
            log.trace("{} <--- {}", department, department.getEmployees());
            // -->
            // SELECT * FROM Employee e WHERE e.department_id = 1
            // Department(id=1, name=Development) <--- [Employee(id=5, name=Allison), Employee(id=6, name=Lois), Employee(id=7, name=Ramon), Employee(id=8, name=Derek)]
            // -->
            // SELECT * FROM Employee e WHERE e.department_id = 2
            // Department(id=2, name=Management) <--- [Employee(id=9, name=Maria), Employee(id=10, name=Rosemary), Employee(id=11, name=Emma)]
            // -->
            // SELECT * FROM Employee e WHERE e.department_id = 3
            // Department(id=3, name=Marketing) <--- [Employee(id=12, name=Gabriel), Employee(id=13, name=Frances)]
            // -->
            // SELECT * FROM Employee e WHERE e.department_id = 4
            // Department(id=4, name=Personnel) <--- [Employee(id=14, name=Elaine)]

            // Notice: Employee(id=15, name=Bonnie) doesn't display!
        }
    }

    // When: EAGER, How: JOIN
    @Test
    void whenFindAllDepartmentAndEmployee_withJoin_thenFetchEmployeeByTheSameQuery_usingObjectArray() {

        var qry = "SELECT d, e FROM Department d JOIN d.employees e";
        List<Object[]> dept_emps = entityManager.createQuery(qry, Object[].class).getResultList();
        // -->
        // SELECT d.*, e.*
        // FROM Department d
        // JOIN Employee e ON e.department_id = d.id

        log.trace("{}", dept_emps);

        log.trace("-----------------------------------------");
        log.trace("For each department, print its employees:");
        log.trace("-----------------------------------------");

        for (Object[] dept_emp: dept_emps) {
            Department dept = (Department) dept_emp[0];
            Employee emp = (Employee) dept_emp[1];

            log.trace("{} <--- {} ::: {}", dept, emp, dept.getEmployees());
            // -->
            // Department(id=1, name=Development) <--- Employee(id=5, name=Allison)
            // Department(id=1, name=Development) <--- Employee(id=6, name=Lois)
            // Department(id=1, name=Development) <--- Employee(id=7, name=Ramon)
            // Department(id=1, name=Development) <--- Employee(id=8, name=Derek)
            // Department(id=2, name=Management) <--- Employee(id=9, name=Maria)
            // Department(id=2, name=Management) <--- Employee(id=10, name=Rosemary)
            // Department(id=2, name=Management) <--- Employee(id=11, name=Emma)
            // Department(id=3, name=Marketing) <--- Employee(id=12, name=Gabriel)
            // Department(id=3, name=Marketing) <--- Employee(id=13, name=Frances)
            // Department(id=4, name=Personnel) <--- Employee(id=14, name=Elaine)

            // Notice: Employee(id=15, name=Bonnie) doesn't display!
        }
    }

    // When: EAGER, How: JOIN
    @Test
    void whenFindAllDepartmentAndEmployee_withJoin_thenFetchEmployeeByTheSameQuery_usingTuple() {

        var qry = "SELECT d, e FROM Department d JOIN d.employees e";
        List<Tuple> dept_emps = entityManager.createQuery(qry, Tuple.class).getResultList();
        // -->
        // SELECT d.*, e.*
        // FROM Department d
        // JOIN Employee e ON e.department_id = d.id

        log.trace("{}", dept_emps);

        log.trace("-----------------------------------------");
        log.trace("For each department, print its employees:");
        log.trace("-----------------------------------------");

        for (Tuple dept_emp: dept_emps) {
            Department dept = dept_emp.get(0, Department.class);
            Employee emp = dept_emp.get(1, Employee.class);

            log.trace("{} <--- {} ::: {}", dept, emp, dept.getEmployees());
            // -->
            // Department(id=1, name=Development) <--- Employee(id=5, name=Allison)
            // Department(id=1, name=Development) <--- Employee(id=6, name=Lois)
            // Department(id=1, name=Development) <--- Employee(id=7, name=Ramon)
            // Department(id=1, name=Development) <--- Employee(id=8, name=Derek)
            // Department(id=2, name=Management) <--- Employee(id=9, name=Maria)
            // Department(id=2, name=Management) <--- Employee(id=10, name=Rosemary)
            // Department(id=2, name=Management) <--- Employee(id=11, name=Emma)
            // Department(id=3, name=Marketing) <--- Employee(id=12, name=Gabriel)
            // Department(id=3, name=Marketing) <--- Employee(id=13, name=Frances)
            // Department(id=4, name=Personnel) <--- Employee(id=14, name=Elaine)

            // Notice: Employee(id=15, name=Bonnie) doesn't display!
        }
    }
}

