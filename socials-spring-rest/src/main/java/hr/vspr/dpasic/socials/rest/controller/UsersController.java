package hr.vspr.dpasic.socials.rest.controller;

import hr.vspr.dpasic.socials.rest.model.User;
import hr.vspr.dpasic.socials.rest.service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    private static final Logger LOG = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    private UsersService usersService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getUsers() {
        List<User> users;

        try {
            users = usersService.getAllUsers();

        } catch (Exception e) {
            LOG.warn(e.getMessage());
            return new ResponseEntity<List<User>>(HttpStatus.BAD_REQUEST);
        }

        LOG.info("Retrieved users from MongoDB.");
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public ResponseEntity<User> getUserForUsername(@PathVariable String username) {
        User user;

        try {
            user = usersService.getUserForUsername(username);

        } catch (Exception e) {
            LOG.warn(e.getMessage());
            return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
        }

        LOG.info(String.format("Retrieved user with username %s from MongoDB.", username));
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<String> addUser(@RequestBody User user) {
        try {
            usersService.addUser(user);

        } catch (Exception e) {
            LOG.warn(e.getMessage());
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }

        LOG.info("Added user to MongoDB.");
        return new ResponseEntity<String>(HttpStatus.OK);
    }
}
