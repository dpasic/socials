package hr.vspr.dpasic.socials.rest.controller.twitter;

import hr.vspr.dpasic.socials.rest.config.EnvironmentUtils;
import hr.vspr.dpasic.socials.rest.model.twitter.TwitterCredentials;
import hr.vspr.dpasic.socials.rest.model.twitter.TwitterRequestTokenUrl;
import hr.vspr.dpasic.socials.rest.model.twitter.TwitterRequestTokenVerifier;
import hr.vspr.dpasic.socials.rest.oauth.factory.TwitterAppFactory;
import hr.vspr.dpasic.socials.rest.service.networks.TwitterCredentialsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import twitter4j.Twitter;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

@RestController
@RequestMapping("/twitter/auth")
public class TwitterAuthenticationController {

    private static final Logger LOG = LoggerFactory.getLogger(TwitterAuthenticationController.class);

    @Autowired
    private TwitterCredentialsService twitterCredentialsService;

    @RequestMapping(value = "/requestToken", method = RequestMethod.GET)
    public ResponseEntity<TwitterRequestTokenUrl> getTwitterRequestToken() {
        TwitterRequestTokenUrl twitterRequestTokenUrl = new TwitterRequestTokenUrl();
        Twitter twitter = TwitterAppFactory.getTwitterInstance();

        try {
            RequestToken requestToken = twitter.getOAuthRequestToken(EnvironmentUtils.getProperty("twitter.oauth.redirectUri"));

            twitterRequestTokenUrl.setAuthUrl(requestToken.getAuthorizationURL());
            twitterRequestTokenUrl.setRequestToken(requestToken.getToken());
            twitterRequestTokenUrl.setRequestTokenSecret(requestToken.getTokenSecret());

        } catch (Exception e) {
            LOG.warn(e.getMessage());
            return new ResponseEntity<TwitterRequestTokenUrl>(HttpStatus.BAD_REQUEST);
        }

        LOG.info("Retrieved TwitterRequestTokenUrl.");
        return new ResponseEntity<TwitterRequestTokenUrl>(twitterRequestTokenUrl, HttpStatus.OK);
    }

    @RequestMapping(value = "/{username}/credentials", method = RequestMethod.POST)
    public ResponseEntity<TwitterCredentials> addOrUpdateTwitterCredentialsForUsername(@PathVariable String username,
                                                                          @RequestBody TwitterRequestTokenVerifier twitterRequestTokenVerifier) {
        TwitterCredentials twitterCredentials;

        Twitter twitter = TwitterAppFactory.getTwitterInstance();
        RequestToken requestTokenObj = new RequestToken(twitterRequestTokenVerifier.getRequestToken(),
                twitterRequestTokenVerifier.getRequestTokenSecret());

        try {
            AccessToken accessTokenObj =  twitter.getOAuthAccessToken(requestTokenObj, twitterRequestTokenVerifier.getVerifier());

            twitterCredentials = new TwitterCredentials();
            twitterCredentials.setUsername(username);
            twitterCredentials.setTwitterUsername(twitter.getScreenName());
            twitterCredentials.setAccessToken(accessTokenObj.getToken());
            twitterCredentials.setAccessTokenSecret(accessTokenObj.getTokenSecret());

            twitterCredentialsService.addOrUpdateTwitterCredentials(twitterCredentials);

        } catch (Exception e) {
            LOG.warn(e.getMessage());
            return new ResponseEntity<TwitterCredentials>(HttpStatus.BAD_REQUEST);
        }

        LOG.info(String.format("Posted Twitter credentials for username %s.", username));
        return new ResponseEntity<TwitterCredentials>(twitterCredentials, HttpStatus.OK);
    }

    @RequestMapping(value = "/{username}/credentials", method = RequestMethod.GET)
    public ResponseEntity<TwitterCredentials> getTwitterCredentialsForUsername(@PathVariable String username) {
        TwitterCredentials twitterCredentials;

        try {
            twitterCredentials = twitterCredentialsService.getTwitterCredentialsForUsername(username);

        } catch (Exception e) {
            LOG.warn(e.getMessage());
            return new ResponseEntity<TwitterCredentials>(HttpStatus.BAD_REQUEST);
        }

        LOG.info(String.format("Retrieved Twitter credentials for username %s from MongoDB.", username));
        return new ResponseEntity<TwitterCredentials>(twitterCredentials, HttpStatus.OK);
    }

    @RequestMapping(value = "/verified", method = RequestMethod.POST)
    public ResponseEntity<Boolean> areCredentialsVerified(@RequestBody TwitterCredentials twitterCredentials) {
        boolean isVerified;

        Twitter twitter = TwitterAppFactory.getTwitterInstance();
        AccessToken accessTokenObj = new AccessToken(twitterCredentials.getAccessToken(), twitterCredentials.getAccessTokenSecret());
        twitter.setOAuthAccessToken(accessTokenObj);

        try {
            isVerified = twitter.verifyCredentials() != null;

        } catch (Exception e) {
            LOG.warn(e.getMessage());
            return new ResponseEntity<Boolean>(HttpStatus.BAD_REQUEST);
        }

        LOG.info("Retrieved flag if Twitter credentials are verified.");
        return new ResponseEntity<Boolean>(isVerified, HttpStatus.OK);
    }

    @RequestMapping(value = "/{username}/credentials", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteCredentialsForUsername(@PathVariable String username) {

        try {
            twitterCredentialsService.deleteTwitterCredentialsForUsername(username);

        } catch (Exception e) {
            LOG.warn(e.getMessage());
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }

        LOG.info("Deleted Twitter credentials.");
        return new ResponseEntity<String>(HttpStatus.OK);
    }
}
