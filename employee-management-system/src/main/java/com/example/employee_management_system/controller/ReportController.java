package com.example.employee_management_system.controller;

import com.example.employee_management_system.model.Attendance;
import com.example.employee_management_system.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;
    
    @GetMapping("/low-attendance")
    public Page<Attendance> getLowAttendance(
            @RequestParam String employeeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return reportService.getLowAttendanceEmployees(employeeId, page, size);
    }
    
    
    @GetMapping("/department-rates")
    public Map<String, Double> getDepartmentRates(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return reportService.getDepartmentAttendanceRates(start, end);
    }
    
    @GetMapping("/trends")
    public List<Attendance> getTrends() {
        return reportService.getAttendanceTrends();
    }
}
