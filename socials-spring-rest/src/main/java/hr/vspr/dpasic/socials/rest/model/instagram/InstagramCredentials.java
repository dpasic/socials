package hr.vspr.dpasic.socials.rest.model.instagram;

import org.springframework.data.annotation.Id;

public class InstagramCredentials {

    @Id
    private String username;

    private int instagramUserId;
    private String instagramUsername;
    private String accessToken;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getInstagramUserId() {
        return instagramUserId;
    }

    public void setInstagramUserId(int instagramUserId) {
        this.instagramUserId = instagramUserId;
    }

    public String getInstagramUsername() {
        return instagramUsername;
    }

    public void setInstagramUsername(String instagramUsername) {
        this.instagramUsername = instagramUsername;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
