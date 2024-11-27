package gym_management.gym_management.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import gym_management.gym_management.model.TokenEntity;
import jakarta.transaction.Transactional;
@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM TokenEntity t WHERE t.expiryTime < CURRENT_TIMESTAMP")
    void deleteExpiredTokens();
    Optional<TokenEntity> findByUserId(int userId);
}
