package rest.call.facebook;

import facebook4j.Facebook;
import facebook4j.FacebookFactory;

public class GetAuthUrl {

    public static void main(String[] args) {

        try {
            Facebook face = new FacebookFactory().getInstance();
            face.setOAuthAppId("", "");
            face.setOAuthPermissions("public_profile,user_friends,read_stream,user_posts,publish_actions");
            String callbackURL = "http://localhost:8090/oauth/callback";
            String authUrl = face.getOAuthAuthorizationURL(callbackURL);

            System.out.println(authUrl);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
