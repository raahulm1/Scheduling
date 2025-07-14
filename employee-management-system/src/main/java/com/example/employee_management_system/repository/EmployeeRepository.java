package com.example.employee_management_system.repository;

import com.example.employee_management_system.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;

public interface EmployeeRepository extends MongoRepository<Employee, String> {
    @Query("{'email': ?0}")
    Employee findByEmail(String email);
    
    @Query("{'departmentId': ?0}")
    List<Employee> findByDepartmentId(String departmentId);
}
