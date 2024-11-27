package gym_management.gym_management.controller;

import gym_management.gym_management.services.AttendanceService;
import gym_management.gym_management.model.AttendanceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api")
// "http://localhost:5500", 
@CrossOrigin(origins={"http://127.0.0.1:5500"})
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;
    public AttendanceController(AttendanceService attendanceService)
    {
        this.attendanceService=attendanceService;
    }
    @PostMapping("/addAttendance")
    public String storeAttendance(@RequestBody AttendanceEntity attendance) {
        return attendanceService.addAttendance(attendance);
    }
    @GetMapping("/attendance/daily/{id}")
    public List<AttendanceEntity> getDailyAttendance(@PathVariable("id") int memberId) {
        return attendanceService.getDailyAttendance(memberId);
    }
    @GetMapping("/attendance/weekly/{id}")
    public List<AttendanceEntity> getWeeklyAttendance(@PathVariable("id") int id,
                                                      @RequestParam("startDate") String startDate,
                                                      @RequestParam("endDate") String endDate) {
        LocalDate parsedStartDate = LocalDate.parse(startDate);
        LocalDate parsedEndDate = LocalDate.parse(endDate);
        return attendanceService.getWeeklyAttendance(id, parsedStartDate, parsedEndDate);
    }
}