package hr.vspr.dpasic.socials.rest.oauth.factory;

import facebook4j.RawAPIResponse;
import facebook4j.auth.AccessToken;
import hr.vspr.dpasic.socials.rest.config.EnvironmentUtils;
import facebook4j.Facebook;
import facebook4j.FacebookFactory;

import java.util.HashMap;
import java.util.Map;

public class FacebookAppFactory {

    private FacebookAppFactory() {
    }

    public static Facebook getFacebookInstance() {
    	Facebook facebookInstance = new FacebookFactory().getInstance();
    	
        facebookInstance.setOAuthAppId(EnvironmentUtils.getProperty("facebook.oauth.appId"), 
        		EnvironmentUtils.getProperty("facebook.oauth.appSecret"));
        facebookInstance.setOAuthPermissions(EnvironmentUtils.getProperty("facebook.oauth.permissions"));
        facebookInstance.setOAuthCallbackURL(EnvironmentUtils.getProperty("facebook.oauth.redirectUri"));

        return facebookInstance;
    }

    public static AccessToken getExtendedTokenForInstanceAndToken(Facebook facebook, String token) throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("client_id", EnvironmentUtils.getProperty("facebook.oauth.appId"));
        params.put("client_secret", EnvironmentUtils.getProperty("facebook.oauth.appSecret"));
        params.put("grant_type", "fb_exchange_token");
        params.put("fb_exchange_token", token);

        RawAPIResponse apiResponse = facebook.callGetAPI("/oauth/access_token", params);

        String response = apiResponse.asString();
        AccessToken extendedAccessToken = new AccessToken(response);

        return extendedAccessToken;
    }
}
