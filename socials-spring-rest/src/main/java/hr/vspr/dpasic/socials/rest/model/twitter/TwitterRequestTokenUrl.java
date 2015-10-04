package hr.vspr.dpasic.socials.rest.model.twitter;

public class TwitterRequestTokenUrl {

    private String requestToken;
    private String requestTokenSecret;
    private String authUrl;

    @Override
    public String toString() {
        return String.format("Request token: %s | request token secret: %s | authentication URL: %s", requestToken, requestTokenSecret, authUrl);
    }

    public String getRequestToken() {
        return requestToken;
    }

    public void setRequestToken(String requestToken) {
        this.requestToken = requestToken;
    }

    public String getRequestTokenSecret() {
        return requestTokenSecret;
    }

    public void setRequestTokenSecret(String requestTokenSecret) {
        this.requestTokenSecret = requestTokenSecret;
    }

    public String getAuthUrl() {
        return authUrl;
    }

    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
    }
}
