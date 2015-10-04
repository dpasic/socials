package hr.vspr.dpasic.socials.rest.proxy;

import facebook4j.Facebook;
import facebook4j.auth.AccessToken;
import hr.vspr.dpasic.socials.rest.model.facebook.FacebookCredentials;
import hr.vspr.dpasic.socials.rest.model.twitter.TwitterCredentials;
import hr.vspr.dpasic.socials.rest.oauth.factory.FacebookAppFactory;
import hr.vspr.dpasic.socials.rest.oauth.factory.TwitterAppFactory;
import hr.vspr.dpasic.socials.rest.service.networks.FacebookCredentialsService;
import hr.vspr.dpasic.socials.rest.service.networks.TwitterCredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import twitter4j.Twitter;

@Component("statusProxy")
public class StatusProxy {

    @Autowired
    private FacebookCredentialsService facebookCredentialsService;

    @Autowired
    private TwitterCredentialsService twitterCredentialsService;

    public void postStatusToFacebookForUsername(String statusText, String username) throws Exception {
        FacebookCredentials facebookCredentials = facebookCredentialsService.getFacebookCredentialsForUsername(username);
        Facebook facebook = FacebookAppFactory.getFacebookInstance();
        AccessToken accessTokenObj = new AccessToken(facebookCredentials.getAccessToken());

        facebook.setOAuthAccessToken(accessTokenObj);
        facebook.postStatusMessage(statusText);
    }

    public void postStatusToTwitterForUsername(String statusText, String username) throws Exception {
        TwitterCredentials twitterCredentials = twitterCredentialsService.getTwitterCredentialsForUsername(username);
        Twitter twitter = TwitterAppFactory.getTwitterInstance();
        twitter4j.auth.AccessToken accessTokenObj = new twitter4j.auth.AccessToken(twitterCredentials.getAccessToken(), twitterCredentials.getAccessTokenSecret());

        twitter.setOAuthAccessToken(accessTokenObj);
        twitter.updateStatus(statusText);
    }
}
