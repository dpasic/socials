package rest.call.instagram;

import com.sola.instagram.InstagramSession;
import com.sola.instagram.auth.AccessToken;
import com.sola.instagram.auth.InstagramAuthentication;
import facebook4j.Facebook;
import facebook4j.FacebookFactory;

public class GetAuthUrl {

    public static void main(String[] args) {

        try {
            InstagramAuthentication auth =  new InstagramAuthentication();
            String authUrl = auth.setRedirectUri("http://localhost:8090/socials/oauth/callback")
                    .setClientSecret("")
                    .setClientId("")
                    .getAuthorizationUri();

            System.out.println(authUrl);

            InstagramSession instagramSession = new InstagramSession(new AccessToken(""));
            instagramSession.getUserById(34);


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
