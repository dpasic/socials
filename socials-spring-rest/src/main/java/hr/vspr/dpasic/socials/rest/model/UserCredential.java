package hr.vspr.dpasic.socials.rest.model;

import org.springframework.data.annotation.Id;

public class UserCredential {

    @Id
    private String username;

    private String hashedPassword;

    public UserCredential() {
    }

    public UserCredential(String username, String hashedPassword) {
        this.username = username;
        this.hashedPassword = hashedPassword;
    }

    @Override
    public String toString() {
        return String.format("Username: %s | hashed password: %s", username, hashedPassword);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }
}
