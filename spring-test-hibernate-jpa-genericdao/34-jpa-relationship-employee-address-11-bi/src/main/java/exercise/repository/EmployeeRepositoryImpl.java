package exercise.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import exercise.model.*;
import exercise.repository.common.*;

@Repository
@Transactional
public class EmployeeRepositoryImpl extends AbstractRepositoryImpl<Employee, Long> implements EmployeeRepository {

}

