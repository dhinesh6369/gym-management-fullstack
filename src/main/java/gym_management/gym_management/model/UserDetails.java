package gym_management.gym_management.model;

import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String userPassword;
    public UserDetails() {

    }

    public UserDetails(int id, String username, String userPassword) {
        this.id = id;
        this.username = username;
        this.userPassword = userPassword;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
