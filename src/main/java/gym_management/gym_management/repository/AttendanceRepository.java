package gym_management.gym_management.repository;

import gym_management.gym_management.model.AttendanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<AttendanceEntity, Integer> {
    @Query("SELECT a FROM AttendanceEntity a WHERE a.todayDate = :todayDate")
    List<AttendanceEntity> findAttendanceByDate(@Param("todayDate") LocalDate todayDate);

    List<AttendanceEntity> findByMemberId(int memberId);
    @Query("SELECT a FROM AttendanceEntity a WHERE a.memberId = :memberId AND a.todayDate BETWEEN :startDate AND :endDate")
    List<AttendanceEntity> findByMemberIdAndDateRange(@Param("memberId") int memberId,
                                                      @Param("startDate") LocalDate startDate,
                                                      @Param("endDate") LocalDate endDate);
}
