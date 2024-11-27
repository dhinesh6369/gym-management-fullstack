package gym_management.gym_management.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import gym_management.gym_management.model.MemberEntity;
import java.time.LocalDate;
import java.util.List;
@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Integer> {
    List<MemberEntity> findByStatusAndEndDateAfter(String status, LocalDate currentDate);
    List<MemberEntity> findByStatusIn(List<String> statuses);
}
