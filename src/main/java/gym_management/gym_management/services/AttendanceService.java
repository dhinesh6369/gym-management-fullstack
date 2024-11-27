package gym_management.gym_management.services;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import gym_management.gym_management.model.AttendanceEntity;
import gym_management.gym_management.repository.AttendanceRepository;
import gym_management.gym_management.repository.MemberRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.*;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private MemberRepository memberRepository;

    public String addAttendance(AttendanceEntity attendance) {
        try {
            if (!memberRepository.existsById(attendance.getMemberId())) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Member ID not found");
            }
            attendanceRepository.save(attendance);
            return "Attendance added successfully";
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error adding attendance: " + e.getMessage());
        }
    }
    public List<AttendanceEntity> getDailyAttendance(int memberId) {
        List<AttendanceEntity> attendenceList=attendanceRepository.findByMemberId(memberId);
        return attendenceList;
    }
    public List<AttendanceEntity> getWeeklyAttendance(int memberId, LocalDate startDate, LocalDate endDate) {
        return attendanceRepository.findByMemberIdAndDateRange(memberId, startDate, endDate);
    }
}