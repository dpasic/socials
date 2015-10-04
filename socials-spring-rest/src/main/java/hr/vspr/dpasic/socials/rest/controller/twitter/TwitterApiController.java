package hr.vspr.dpasic.socials.rest.controller.twitter;

import hr.vspr.dpasic.socials.rest.model.twitter.TwitterAccountInfo;
import hr.vspr.dpasic.socials.rest.model.twitter.TwitterCredentials;
import hr.vspr.dpasic.socials.rest.oauth.factory.TwitterAppFactory;
import hr.vspr.dpasic.socials.rest.service.networks.TwitterCredentialsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import twitter4j.Twitter;
import twitter4j.User;
import twitter4j.auth.AccessToken;

@RestController
@RequestMapping("/twitter/api")
public class TwitterApiController {

    private static final Logger LOG = LoggerFactory.getLogger(TwitterApiController.class);

    @Autowired
    private TwitterCredentialsService twitterCredentialsService;

    @RequestMapping(value = "/{username}/accountInfo", method = RequestMethod.GET)
    public ResponseEntity<TwitterAccountInfo> getAccountInfo(@PathVariable String username) {

        TwitterAccountInfo twitterAccountInfo = new TwitterAccountInfo();

        TwitterCredentials twitterCredentials = twitterCredentialsService.getTwitterCredentialsForUsername(username);
        Twitter twitter = TwitterAppFactory.getTwitterInstance();
        AccessToken accessTokenObj = new AccessToken(twitterCredentials.getAccessToken(), twitterCredentials.getAccessTokenSecret());

        twitter.setOAuthAccessToken(accessTokenObj);

        try {
            String twitterScreenName = twitter.getScreenName();
            User twitterUser = twitter.showUser(twitterScreenName);

            twitterAccountInfo.setUsername(twitterScreenName);
            twitterAccountInfo.setFullName(twitterUser.getName());
            twitterAccountInfo.setProfileImageUrl(twitterUser.getProfileImageURL());
            twitterAccountInfo.setFollowersCount(twitterUser.getFollowersCount());
            twitterAccountInfo.setFriendsCount(twitterUser.getFriendsCount());

        } catch (Exception e) {
            LOG.warn(e.getMessage());
            return new ResponseEntity<TwitterAccountInfo>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<TwitterAccountInfo>(twitterAccountInfo, HttpStatus.OK);
    }
}
