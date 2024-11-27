package gym_management.gym_management.model;



import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
public class TokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int userId;
    private String username;
    private String token;
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiryTime;

    public TokenEntity(int userId, String username,String token, Date expiryTime) {
        this.userId = userId;
        this.username=username;
        this.token = token;
        this.expiryTime = expiryTime;
    }

    public TokenEntity(){

    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getUserId() {
        return userId;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public Date getExpiryTime() {
        return expiryTime;
    }
    public void setExpiryTime(Date expiryTime) {
        this.expiryTime = expiryTime;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
}