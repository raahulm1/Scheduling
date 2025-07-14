package com.example.employee_management_system.repository;

import com.example.employee_management_system.model.Attendance;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.List;

public interface AttendanceRepository extends MongoRepository<Attendance, String> {
    @Query("{'employeeId': ?0, 'checkIn': {$gte: ?1, $lte: ?2}}")
    Page<Attendance> findByEmployeeIdAndDateRange(String employeeId, 
        LocalDateTime start, LocalDateTime end, Pageable pageable);
    
    @Query("{'checkIn': {$gte: ?0, $lte: ?1}, 'status': 'ABSENT'}")
    List<Attendance> findAbsencesByDateRange(LocalDateTime start, LocalDateTime end);
    
    @Query("{'checkIn': {$gte: ?0, $lte: ?1}}")
    List<Attendance> findByDateRange(LocalDateTime start, LocalDateTime end);

    
}
