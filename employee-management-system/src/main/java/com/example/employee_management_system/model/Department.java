package com.example.employee_management_system.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Document(collection = "departments")
@Data
public class Department {
    @Id
    private String id;
    private String name;
    private String managerId;
}
