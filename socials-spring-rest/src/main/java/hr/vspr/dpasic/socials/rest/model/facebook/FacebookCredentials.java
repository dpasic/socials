package hr.vspr.dpasic.socials.rest.model.facebook;

import org.springframework.data.annotation.Id;

public class FacebookCredentials {

    @Id
    private String username;

    private String facebookFullName;
    private String accessToken;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFacebookFullName() {
        return facebookFullName;
    }

    public void setFacebookFullName(String facebookFullName) {
        this.facebookFullName = facebookFullName;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
