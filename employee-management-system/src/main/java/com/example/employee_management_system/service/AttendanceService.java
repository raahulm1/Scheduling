package com.example.employee_management_system.service;

import com.example.employee_management_system.model.Attendance;
import com.example.employee_management_system.repository.AttendanceRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final ReentrantLock lock = new ReentrantLock();
    
    @Scheduled(cron = "0 0 6 * * *", zone = "Asia/Kolkata")
    @Transactional
    public void generateDailyAttendanceReport() {
        if (!lock.tryLock()) {
            log.warn("Daily report already running");
            return;
        }
        try {
            LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC"));
            LocalDateTime startOfDay = now.toLocalDate().atStartOfDay();
            
            List<Attendance> dailyRecords = attendanceRepository
                .findByDateRange(startOfDay, now);
            
            // Process daily report
            log.info("Generated daily report for {} records", dailyRecords.size());
        } catch (Exception e) {
            log.error("Error in daily report generation", e);
        } finally {
            lock.unlock();
        }
    }
    
    @Scheduled(cron = "0 0 0 * * SUN", zone = "UTC")
    @Transactional
    public void archiveOldData() {
        try {
            LocalDateTime threshold = LocalDateTime.now(ZoneId.of("UTC"))
                .minusMonths(6);
            List<Attendance> oldRecords = attendanceRepository
                .findByDateRange(LocalDateTime.of(2000, 1, 1, 0, 0), threshold);
            
            // Archive logic here
            log.info("Archived {} old records", oldRecords.size());
        } catch (Exception e) {
            log.error("Error in data archiving", e);
        }
    }
    
    @Scheduled(fixedRate = 900000) // 15 minutes
    public void monitorAnomalies() {
        try {
            LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC"));
            LocalDateTime windowStart = now.minusHours(1);
            
            List<Attendance> absences = attendanceRepository
                .findAbsencesByDateRange(windowStart, now);
            
            if (absences.size() > 10) { // Example threshold
                log.warn("High absence rate detected: {}", absences.size());
                // Notify relevant parties
            }
        } catch (Exception e) {
            log.error("Error in anomaly monitoring", e);
        }
    }
    
    @Scheduled(cron = "0 0 0 1 * *", zone = "UTC")
    @Transactional
    public void calculateMonthlyAdjustments() {
        try {
            LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
            LocalDateTime startOfMonth = now.withDayOfMonth(1).toLocalDate().atStartOfDay();
            
            // Salary adjustment logic
            log.info("Processed monthly salary adjustments");
        } catch (Exception e) {
            log.error("Error in monthly adjustments", e);
        }
    }
}
