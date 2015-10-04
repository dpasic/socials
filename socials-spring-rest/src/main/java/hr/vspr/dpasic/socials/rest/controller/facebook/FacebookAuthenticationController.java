package hr.vspr.dpasic.socials.rest.controller.facebook;

import facebook4j.Facebook;
import facebook4j.auth.AccessToken;
import hr.vspr.dpasic.socials.rest.config.EnvironmentUtils;
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
@RequestMapping("/facebook/auth")
public class FacebookAuthenticationController {

    private static final Logger LOG = LoggerFactory.getLogger(FacebookAuthenticationController.class);

    @Autowired
    private FacebookCredentialsService facebookCredentialsService;

    @RequestMapping(value = "/url", method = RequestMethod.GET, produces = "text/plain")
    public ResponseEntity<String> getFacebookAuthUrl() {
        String authUrl;

        try {
            Facebook facebook = FacebookAppFactory.getFacebookInstance();
            authUrl = facebook.getOAuthAuthorizationURL(EnvironmentUtils.getProperty("facebook.oauth.redirectUri"));

        } catch (Exception e) {
            LOG.warn(e.getMessage());
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }

        LOG.info("Retrieved Facebook authorization URL.");
        return new ResponseEntity<String>(authUrl, HttpStatus.OK);
    }

    @RequestMapping(value = "/{username}/credentials", method = RequestMethod.POST)
    public ResponseEntity<FacebookCredentials> addOrUpdateFacebookCredentialsForUsername(@PathVariable String username,
                                                                                      @RequestBody String oauthCode) {
        FacebookCredentials facebookCredentials = new FacebookCredentials();
        Facebook facebook = FacebookAppFactory.getFacebookInstance();

        try {
            AccessToken accessToken = facebook.getOAuthAccessToken(oauthCode);
            accessToken = FacebookAppFactory.getExtendedTokenForInstanceAndToken(facebook, accessToken.getToken());

            facebookCredentials.setUsername(username);
            facebookCredentials.setFacebookFullName(facebook.getName());
            facebookCredentials.setAccessToken(accessToken.getToken());

            facebookCredentialsService.addOrUpdateFacebookCredentials(facebookCredentials);

        } catch (Exception e) {
            LOG.warn(e.getMessage());
            return new ResponseEntity<FacebookCredentials>(HttpStatus.BAD_REQUEST);
        }

        LOG.info(String.format("Posted Facebook credentials for username %s.", username));
        return new ResponseEntity<FacebookCredentials>(facebookCredentials, HttpStatus.OK);
    }

    @RequestMapping(value = "/{username}/credentials", method = RequestMethod.GET)
    public ResponseEntity<FacebookCredentials> getFacebookCredentialsForUsername(@PathVariable String username) {
        FacebookCredentials facebookCredentials;

        try {
            facebookCredentials = facebookCredentialsService.getFacebookCredentialsForUsername(username);

        } catch (Exception e) {
            LOG.warn(e.getMessage());
            return new ResponseEntity<FacebookCredentials>(HttpStatus.BAD_REQUEST);
        }

        LOG.info(String.format("Retrieved Facebook credentials for username %s from MongoDB.", username));
        return new ResponseEntity<FacebookCredentials>(facebookCredentials, HttpStatus.OK);
    }

    //TODO: implement or remove
    @RequestMapping(value = "/valid", method = RequestMethod.POST)
    public ResponseEntity<Boolean> areCredentialsValid(@RequestBody FacebookCredentials facebookCredentials) {
        boolean areValid;

        try {
            areValid = false;

        } catch (Exception e) {
            LOG.warn(e.getMessage());
            return new ResponseEntity<Boolean>(HttpStatus.BAD_REQUEST);
        }

        LOG.info("Retrieved flag if Facebook credentials are valid.");
        return new ResponseEntity<Boolean>(areValid, HttpStatus.OK);
    }

    @RequestMapping(value = "/{username}/credentials", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteCredentialsForUsername(@PathVariable String username) {

        try {
            facebookCredentialsService.deleteFacebookCredentialsForUsername(username);

        } catch (Exception e) {
            LOG.warn(e.getMessage());
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }

        LOG.info("Deleted Facebook credentials.");
        return new ResponseEntity<String>(HttpStatus.OK);
    }
}
