package ru.thewhite.edu.eduserver.employee.repository;

import org.springframework.data.repository.CrudRepository;
import ru.thewhite.edu.eduserver.employee.entry.Employee;

import java.util.List;
import java.util.UUID;

/**
 * @author Maxim Seredkin
 */
public interface EmployeeRepository extends CrudRepository<Employee, UUID> {
    List<Employee> findAllByTokenId(UUID tokenId);
}
