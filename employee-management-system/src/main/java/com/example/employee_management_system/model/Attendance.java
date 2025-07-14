package com.example.employee_management_system.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import java.time.LocalDateTime;

@Document(collection = "attendance")
@Data
public class Attendance {
    @Id
    private String id;
    private String employeeId;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private String status; // PRESENT, ABSENT, LATE
}

