package hr.vspr.dpasic.socials.rest.controller.instagram;

import com.sola.instagram.InstagramSession;
import com.sola.instagram.auth.AccessToken;
import com.sola.instagram.model.User;
import hr.vspr.dpasic.socials.rest.model.instagram.InstagramAccountInfo;
import hr.vspr.dpasic.socials.rest.model.instagram.InstagramCredentials;
import hr.vspr.dpasic.socials.rest.service.networks.InstagramCredentialsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/instagram/api")
public class InstagramApiController {

    private static final Logger LOG = LoggerFactory.getLogger(InstagramApiController.class);

    @Autowired
    private InstagramCredentialsService instagramCredentialsService;

    @RequestMapping(value = "/{username}/accountInfo", method = RequestMethod.GET)
    public ResponseEntity<InstagramAccountInfo> getAccountInfo(@PathVariable String username) {

        InstagramAccountInfo instagramAccountInfo = new InstagramAccountInfo();

        try {
            InstagramCredentials instagramCredentials = instagramCredentialsService.getInstagramCredentialsForUsername(username);

            AccessToken accessToken = new AccessToken(instagramCredentials.getAccessToken());
            InstagramSession instagramSession = new InstagramSession(accessToken);
            User instagramUser = instagramSession.getUserById(instagramCredentials.getInstagramUserId());

            instagramAccountInfo.setUsername(instagramUser.getUserName());
            instagramAccountInfo.setFullName(instagramUser.getFullName());
            instagramAccountInfo.setProfileImageUrl(instagramUser.getProfilePictureURI());
            instagramAccountInfo.setFollowersCount(instagramUser.getFollowerCount());
            instagramAccountInfo.setFollowingCount(instagramUser.getFollowingCount());

        } catch (Exception e) {
            LOG.warn(e.getMessage());
            return new ResponseEntity<InstagramAccountInfo>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<InstagramAccountInfo>(instagramAccountInfo, HttpStatus.OK);
    }
}
