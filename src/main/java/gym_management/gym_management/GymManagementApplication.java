package gym_management.gym_management;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
@SpringBootApplication
@EnableScheduling
public class GymManagementApplication {
    private static final Logger logger = LoggerFactory.getLogger(GymManagementApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(GymManagementApplication.class, args);
        logger.info("Gym Management Application has started.");
    }
}