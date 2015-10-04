package hr.vspr.dpasic.socials.rest.controller.instagram;

import com.sola.instagram.auth.AccessToken;
import com.sola.instagram.auth.InstagramAuthentication;
import com.sola.instagram.model.User;
import hr.vspr.dpasic.socials.rest.model.instagram.InstagramCredentials;
import hr.vspr.dpasic.socials.rest.oauth.factory.InstagramAppFactory;
import hr.vspr.dpasic.socials.rest.service.networks.InstagramCredentialsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/instagram/auth")
public class InstagramAuthenticationController {

    private static final Logger LOG = LoggerFactory.getLogger(InstagramAuthenticationController.class);

    @Autowired
    private InstagramCredentialsService instagramCredentialsService;

    @RequestMapping(value = "/url", method = RequestMethod.GET, produces = "text/plain")
    public ResponseEntity<String> getInstagramAuthUrl() {
        String authUrl;

        try {
            InstagramAuthentication instagramAuth = InstagramAppFactory.getInstagramAuthInstance();
            authUrl = instagramAuth.getAuthorizationUri();

        } catch (Exception e) {
            LOG.warn(e.getMessage());
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }

        LOG.info("Retrieved Instagram authorization URL.");
        return new ResponseEntity<String>(authUrl, HttpStatus.OK);
    }

    @RequestMapping(value = "/{username}/credentials", method = RequestMethod.POST)
    public ResponseEntity<InstagramCredentials> addOrUpdateInstagramCredentialsForUsername(@PathVariable String username,
                                                                                      @RequestBody String oauthCode) {
        InstagramCredentials instagramCredentials = new InstagramCredentials();
        InstagramAuthentication instagramAuth = InstagramAppFactory.getInstagramAuthInstance();

        try {
            AccessToken accessToken = instagramAuth.build(oauthCode);
            User instagramUser = instagramAuth.getAuthenticatedUser();

            instagramCredentials.setUsername(username);
            instagramCredentials.setAccessToken(accessToken.getTokenString());
            instagramCredentials.setInstagramUserId(instagramUser.getId());
            instagramCredentials.setInstagramUsername(instagramUser.getUserName());

            instagramCredentialsService.addOrUpdateInstagramCredentials(instagramCredentials);

        } catch (Exception e) {
            LOG.warn(e.getMessage());
            return new ResponseEntity<InstagramCredentials>(HttpStatus.BAD_REQUEST);
        }

        LOG.info(String.format("Posted Instagram credentials for username %s.", username));
        return new ResponseEntity<InstagramCredentials>(instagramCredentials, HttpStatus.OK);
    }

    @RequestMapping(value = "/{username}/credentials", method = RequestMethod.GET)
    public ResponseEntity<InstagramCredentials> getInstagramCredentialsForUsername(@PathVariable String username) {
        InstagramCredentials instagramCredentials;

        try {
            instagramCredentials = instagramCredentialsService.getInstagramCredentialsForUsername(username);

        } catch (Exception e) {
            LOG.warn(e.getMessage());
            return new ResponseEntity<InstagramCredentials>(HttpStatus.BAD_REQUEST);
        }

        LOG.info(String.format("Retrieved Instagram credentials for username %s from MongoDB.", username));
        return new ResponseEntity<InstagramCredentials>(instagramCredentials, HttpStatus.OK);
    }

    @RequestMapping(value = "/{username}/credentials", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteCredentialsForUsername(@PathVariable String username) {

        try {
            instagramCredentialsService.deleteInstagramCredentialsForUsername(username);

        } catch (Exception e) {
            LOG.warn(e.getMessage());
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }

        LOG.info("Deleted Instagram credentials.");
        return new ResponseEntity<String>(HttpStatus.OK);
    }
}
