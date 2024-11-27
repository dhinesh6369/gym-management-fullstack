package gym_management.gym_management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfigure {
    @Bean(name = "securityFilterChainConfig")
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .requestMatchers("/login", "/register", "/").permitAll()  // Allow root URL and login/register pages
            .anyRequest().authenticated()                             // Require authentication for all other requests
            .and()
            .formLogin()
            .loginPage("/login")                                       // Custom login page
            .defaultSuccessUrl("/gym.html", true)                      // Redirect to gym.html on successful login
            .permitAll();                                              // Allow access to login page
        return http.build();
    }
}
