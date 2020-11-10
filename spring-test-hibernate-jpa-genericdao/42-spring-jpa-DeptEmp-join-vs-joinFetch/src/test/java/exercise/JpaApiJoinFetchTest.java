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
import exercise.model.Employee;

// -------------------------------------------------------------------------------
// What is the difference between JOIN and JOIN FETCH when using JPA and Hibernate
// https://stackoverflow.com/questions/17431312/what-is-the-difference-between-join-and-join-fetch-when-using-jpa-and-hibernate
// -------------------------------------------------------------------------------

@SpringJUnitConfig(TestPersistenceConfig.class)
@TestConstructor(autowireMode = AutowireMode.ALL)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

@Transactional
@lombok.extern.slf4j.Slf4j
public class JpaApiJoinFetchTest {

    @PersistenceContext
    EntityManager entityManager;

    @Test
    void whenFindAllEmployee_thenFetchEmployeeOnly() {

        var qry = "SELECT e FROM Employee e";
        var employees = entityManager.createQuery(qry, Employee.class).getResultList();
        // -->
        // SELECT * FROM Employee

        assertThat(employees).extracting("id").contains(5L,6L,7L,8L,9L,10L,11L,12L,13L,14L,15L);

        for (Employee employee: employees) {
            log.trace("{} ---> {}", employee, employee.getDepartment());
            // -->
            // SELECT d.* FROM Department d WHERE d.id = 1
            // Employee(id=5, name=Allison) ---> Department(id=1, name=Development)
            // Employee(id=6, name=Lois) ---> Department(id=1, name=Development)
            // Employee(id=7, name=Ramon) ---> Department(id=1, name=Development)
            // Employee(id=8, name=Derek) ---> Department(id=1, name=Development)

            // SELECT d.* FROM Department d WHERE d.id = 2
            // Employee(id=9, name=Maria) ---> Department(id=2, name=Management)
            // Employee(id=10, name=Rosemary) ---> Department(id=2, name=Management)
            // Employee(id=11, name=Emma) ---> Department(id=2, name=Management)

            // SELECT d.* FROM Department d WHERE d.id = 3
            // Employee(id=12, name=Gabriel) ---> Department(id=3, name=Marketing)
            // Employee(id=13, name=Frances) ---> Department(id=3, name=Marketing)

            // SELECT d.* FROM Department d WHERE d.id = 4
            // Employee(id=14, name=Elaine) ---> Department(id=4, name=Personnel)
            // Employee(id=15, name=Bonnie) ---> null
        }
    }

    @Test
    void whenFindAllEmployeeOnly_withJoin_thenFetchDepartmentLazily() {

        var qry = "SELECT e FROM Employee e JOIN e.department d";
        var employees = entityManager.createQuery(qry, Employee.class).getResultList();
        // -->
        //SELECT e.* FROM Employee e INNER JOIN Department d ON e.department_id=d.id

        // Notice : 15L
        assertThat(employees).extracting("id").contains(5L,6L,7L,8L,9L,10L,11L,12L,13L,14L);

        for (Employee employee: employees) {
            log.trace("{} ---> {}", employee, employee.getDepartment());
            // -->
            // SELECT d.* FROM Department d WHERE d.id = 1
            // Employee(id=5, name=Allison) ---> Department(id=1, name=Development)
            // Employee(id=6, name=Lois) ---> Department(id=1, name=Development)
            // Employee(id=7, name=Ramon) ---> Department(id=1, name=Development)
            // Employee(id=8, name=Derek) ---> Department(id=1, name=Development)

            // SELECT d.* FROM Department d WHERE d.id = 2
            // Employee(id=9, name=Maria) ---> Department(id=2, name=Management)
            // Employee(id=10, name=Rosemary) ---> Department(id=2, name=Management)
            // Employee(id=11, name=Emma) ---> Department(id=2, name=Management)

            // SELECT d.* FROM Department d WHERE d.id = 3
            // Employee(id=12, name=Gabriel) ---> Department(id=3, name=Marketing)
            // Employee(id=13, name=Frances) ---> Department(id=3, name=Marketing)

            // SELECT d.* FROM Department d WHERE d.id = 4
            // Employee(id=14, name=Elaine) ---> Department(id=4, name=Personnel)
        }

    }

    @Test
    void whenFindAllEmployee_withJoinFetch_thenFetchDepartmentByTheSameQuery() {
        var qry = "SELECT e FROM Employee e JOIN FETCH e.department d";
        var employees = entityManager.createQuery(qry, Employee.class).getResultList();
        // -->
        //SELECT e.*, d.* FROM Employee e INNER JOIN Department d ON e.department_id=d.id

        assertThat(employees).extracting("id").contains(5L,6L,7L,8L,9L,10L,11L,12L,13L,14L);

        for (Employee employee: employees) {
            log.trace("{} ---> {}", employee, employee.getDepartment());
            // -->
            // Employee(id=5, name=Allison) ---> Department(id=1, name=Development)
            // Employee(id=6, name=Lois) ---> Department(id=1, name=Development)
            // Employee(id=7, name=Ramon) ---> Department(id=1, name=Development)
            // Employee(id=8, name=Derek) ---> Department(id=1, name=Development)

            // Employee(id=9, name=Maria) ---> Department(id=2, name=Management)
            // Employee(id=10, name=Rosemary) ---> Department(id=2, name=Management)
            // Employee(id=11, name=Emma) ---> Department(id=2, name=Management)

            // Employee(id=12, name=Gabriel) ---> Department(id=3, name=Marketing)
            // Employee(id=13, name=Frances) ---> Department(id=3, name=Marketing)

            // Employee(id=14, name=Elaine) ---> Department(id=4, name=Personnel)
        }

    }

    @Test
    void whenFindAllEmployee_withLeftJoinFetch_thenFetchDepartmentByTheSameQuery() {
        var qry = "SELECT e FROM Employee e LEFT JOIN FETCH e.department d";
        var employees = entityManager.createQuery(qry, Employee.class).getResultList();
        // -->
        //SELECT e.*, d.* FROM Employee e INNER JOIN Department d ON e.department_id=d.id

        assertThat(employees).extracting("id").contains(5L,6L,7L,8L,9L,10L,11L,12L,13L,14L,15L);

        for (Employee employee: employees) {
            log.trace("{} ---> {}", employee, employee.getDepartment());
            // -->
            // Employee(id=5, name=Allison) ---> Department(id=1, name=Development)
            // Employee(id=6, name=Lois) ---> Department(id=1, name=Development)
            // Employee(id=7, name=Ramon) ---> Department(id=1, name=Development)
            // Employee(id=8, name=Derek) ---> Department(id=1, name=Development)

            // Employee(id=9, name=Maria) ---> Department(id=2, name=Management)
            // Employee(id=10, name=Rosemary) ---> Department(id=2, name=Management)
            // Employee(id=11, name=Emma) ---> Department(id=2, name=Management)

            // Employee(id=12, name=Gabriel) ---> Department(id=3, name=Marketing)
            // Employee(id=13, name=Frances) ---> Department(id=3, name=Marketing)

            // Employee(id=14, name=Elaine) ---> Department(id=4, name=Personnel)

            // Employee(id=15, name=Bonnie) ---> null
        }

    }

}

