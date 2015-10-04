package rest.call.facebook;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import facebook4j.Facebook;
import facebook4j.FacebookFactory;
import facebook4j.RawAPIResponse;
import facebook4j.auth.AccessToken;

public class GetAccessTokenFromCode {

    public static void main(String[] args) {

    	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    	
        try {
            Facebook face = new FacebookFactory().getInstance();
            face.setOAuthAppId("", "");
            face.setOAuthPermissions("public_profile,user_friends,read_stream,user_posts,publish_actions");
            face.setOAuthCallbackURL("http://localhost:8090/oauth/callback");
            AccessToken accessToken = new AccessToken("");
            face.setOAuthAccessToken(accessToken);
            /*AccessToken accessToken = face.getOAuthAccessToken("");*/

            Map<String, String> params = new HashMap<String, String>();
            params.put("client_id", "");
            params.put("client_secret", "");
            params.put("grant_type", "fb_exchange_token");
            params.put("fb_exchange_token", face.getOAuthAccessToken().getToken());

            RawAPIResponse apiResponse = face.callGetAPI("/oauth/access_token", params);

            String response = apiResponse.asString();
            AccessToken extendedAccessToken = new AccessToken(response);
            System.out.println(String.valueOf(extendedAccessToken.getExpires()));

            System.out.println(face.getId());
            System.out.println(face.getName());
            /*face.postStatusMessage("API post");*/

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}