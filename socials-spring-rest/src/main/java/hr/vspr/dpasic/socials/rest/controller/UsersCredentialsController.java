package hr.vspr.dpasic.socials.rest.controller;

import hr.vspr.dpasic.socials.rest.service.UsersCredentialsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsersCredentialsController {

    private static final Logger LOG = LoggerFactory.getLogger(UsersCredentialsController.class);

    @Autowired
    private UsersCredentialsService usersCredentialsService;

    @RequestMapping(value = "/{username}/credentials", method = RequestMethod.GET)
    public ResponseEntity<Boolean> doExistCredentialsForUsername(
            @PathVariable String username) {

        boolean existsCredential;

        try {
            existsCredential = usersCredentialsService.doesExistCredentialForUsername(username);

        } catch (Exception e) {
            LOG.warn(e.getMessage());
            return new ResponseEntity<Boolean>(HttpStatus.BAD_REQUEST);
        }

        LOG.info(String.format("Retrieved flag if exists credential for username %s in MongoDB.", username));
        return new ResponseEntity<Boolean>(existsCredential, HttpStatus.OK);
    }

    @RequestMapping(value = "/{username}/credentials", method = RequestMethod.POST)
    public ResponseEntity<String> addOrUpdateCredentialsForUsername(
            @PathVariable String username, @RequestBody String rawPassword) {

        try {
            usersCredentialsService.addOrUpdateUserCredentialForUsernameAndPassword(username, rawPassword);

        } catch (Exception e) {
            LOG.warn(e.getMessage());
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }

        LOG.info(String.format("Added credential for username %s to MongoDB.", username));
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{username}/credentials", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteCredentialsForUsername(
            @PathVariable String username) {

        try {
            usersCredentialsService.deleteUserCredentialForUsername(username);

        } catch (Exception e) {
            LOG.warn(e.getMessage());
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }

        LOG.info(String.format("Deleted credential for username %s from MongoDB.", username));
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{username}/authenticated", method = RequestMethod.POST)
    public ResponseEntity<Boolean> isUserAuthenticated(@PathVariable String username,
                                                  @RequestBody String providedRawPassword) {
        boolean isAuthenticated;

        try {
            isAuthenticated = usersCredentialsService.isAuthenticated(username, providedRawPassword);

        } catch (Exception e) {
            LOG.warn(e.getMessage());
            return new ResponseEntity<Boolean>(HttpStatus.BAD_REQUEST);
        }

        LOG.info(String.format("Retrieved flag if user with username %s is authenticated in MongoDB.", username));
        return new ResponseEntity<Boolean>(isAuthenticated, HttpStatus.OK);
    }
}
