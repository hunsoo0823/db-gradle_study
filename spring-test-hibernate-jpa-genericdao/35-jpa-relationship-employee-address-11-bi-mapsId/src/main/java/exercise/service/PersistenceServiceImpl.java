package exercise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import exercise.model.*;
import exercise.repository.*;

@Service
@Transactional
public class PersistenceServiceImpl implements PersistenceService {

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public void initializeDatabase() {
        Department department = new Department("Java");
        departmentRepository.save(department);

        employeeRepository.save(new Employee("Rene", department));
        employeeRepository.save(new Employee("Tyrone", department));
    }

    @Override
    public void printAll() {
        var depts = departmentRepository.findAll();
        depts.forEach(System.out::println);
    }

}

