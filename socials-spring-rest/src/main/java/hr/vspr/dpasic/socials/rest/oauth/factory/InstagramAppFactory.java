package hr.vspr.dpasic.socials.rest.oauth.factory;


import com.sola.instagram.auth.InstagramAuthentication;
import hr.vspr.dpasic.socials.rest.config.EnvironmentUtils;

public class InstagramAppFactory {

    private InstagramAppFactory() {
    }

    public static InstagramAuthentication getInstagramAuthInstance() {
        InstagramAuthentication instagramAuthentication =  new InstagramAuthentication();

        instagramAuthentication
                .setClientId(EnvironmentUtils.getProperty("instagram.oauth.clientId"))
                .setClientSecret(EnvironmentUtils.getProperty("instagram.oauth.clientSecret"))
                .setRedirectUri(EnvironmentUtils.getProperty("instagram.oauth.redirectUri"));

        return instagramAuthentication;
    }
}
