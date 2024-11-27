package gym_management.gym_management.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import gym_management.gym_management.model.UserDetails;
public interface UserDetailsRepository extends JpaRepository<UserDetails, Integer> {
    boolean existsByUsername(String username);
    UserDetails findByUsername(String username);
}
