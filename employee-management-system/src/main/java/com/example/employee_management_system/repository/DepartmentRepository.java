package com.example.employee_management_system.repository;

import com.example.employee_management_system.model.Department;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DepartmentRepository extends MongoRepository<Department, String> {
}
