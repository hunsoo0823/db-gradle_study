package exercise.repository;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;

import exercise.model.*;
import exercise.repository.common.*;

@Repository
@Transactional
public class DepartmentRepositoryImpl extends AbstractRepositoryImpl<Department, Long> implements DepartmentRepository {

}

