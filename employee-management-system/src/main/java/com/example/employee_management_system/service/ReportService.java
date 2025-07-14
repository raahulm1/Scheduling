package com.example.employee_management_system.service;

import com.example.employee_management_system.model.Attendance;
import com.example.employee_management_system.model.Employee;
import com.example.employee_management_system.repository.AttendanceRepository;
import com.example.employee_management_system.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;
    
    public Page<Attendance> getLowAttendanceEmployees(String employeeId, int page, int size) {
        LocalDateTime threeMonthsAgo = LocalDateTime.now().minusMonths(3);
        return attendanceRepository.findByEmployeeIdAndDateRange(
            employeeId, threeMonthsAgo, LocalDateTime.now(), 
            PageRequest.of(page, size));
    }
    
    public Map<String, Double> getDepartmentAttendanceRates(LocalDateTime start, LocalDateTime end) {
        List<Attendance> attendances = attendanceRepository.findByDateRange(start, end);
        return employeeRepository.findAll().stream()
            .collect(Collectors.groupingBy(
                Employee::getDepartmentId,
                Collectors.averagingDouble(emp -> 
                    attendances.stream()
                        .filter(a -> a.getEmployeeId().equals(emp.getId()))
                        .filter(a -> "PRESENT".equals(a.getStatus()))
                        .count() / (double)attendances.size()
            )));
    }
    
    public List<Attendance> getAttendanceTrends() {
        LocalDateTime threeMonthsAgo = LocalDateTime.now().minusMonths(3);
        return attendanceRepository.findByDateRange(threeMonthsAgo, LocalDateTime.now());
    }
}
