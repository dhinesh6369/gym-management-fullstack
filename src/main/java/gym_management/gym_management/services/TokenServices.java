package gym_management.gym_management.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import gym_management.gym_management.repository.TokenRepository;

@Service
public class TokenServices {
    @Autowired
    private TokenRepository tokenRepository;
    
    @Scheduled(fixedRate = 60000)
    public void cleanupExpiredTokens() {
        tokenRepository.deleteExpiredTokens();
    }
}
