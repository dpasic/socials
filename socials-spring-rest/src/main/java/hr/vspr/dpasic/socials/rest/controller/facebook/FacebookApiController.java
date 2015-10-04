package hr.vspr.dpasic.socials.rest.controller.facebook;

import facebook4j.Facebook;
import facebook4j.User;
import facebook4j.auth.AccessToken;
import hr.vspr.dpasic.socials.rest.model.facebook.FacebookAccountInfo;
import hr.vspr.dpasic.socials.rest.model.facebook.FacebookCredentials;
import hr.vspr.dpasic.socials.rest.oauth.factory.FacebookAppFactory;
import hr.vspr.dpasic.socials.rest.service.networks.FacebookCredentialsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/facebook/api")
public class FacebookApiController {

    private static final Logger LOG = LoggerFactory.getLogger(FacebookApiController.class);

    @Autowired
    private FacebookCredentialsService facebookCredentialsService;

    @RequestMapping(value = "/{username}/accountInfo", method = RequestMethod.GET)
    public ResponseEntity<FacebookAccountInfo> getAccountInfo(@PathVariable String username) {

        FacebookAccountInfo facebookAccountInfo = new FacebookAccountInfo();

        FacebookCredentials facebookCredentials = facebookCredentialsService.getFacebookCredentialsForUsername(username);
        Facebook facebook = FacebookAppFactory.getFacebookInstance();
        AccessToken accessTokenObj = new AccessToken(facebookCredentials.getAccessToken());

        facebook.setOAuthAccessToken(accessTokenObj);

        try {
            String facebookId = facebook.getId();
            User facebookUser = facebook.getUser(facebookId);

            facebookAccountInfo.setFullName(facebookUser.getName());
            //facebookAccountInfo.setRelationshipStatus(facebookUser.getRelationshipStatus());
            //facebookAccountInfo.setProfileImageUrl(facebookUser.getPicture().getURL().getPath());
            //facebookAccountInfo.setFriendsCount(facebook.getFriends().getCount());

        } catch (Exception e) {
            LOG.warn(e.getMessage());
            return new ResponseEntity<FacebookAccountInfo>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<FacebookAccountInfo>(facebookAccountInfo, HttpStatus.OK);
    }
}
