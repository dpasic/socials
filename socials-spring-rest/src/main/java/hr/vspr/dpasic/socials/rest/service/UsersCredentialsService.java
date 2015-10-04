package hr.vspr.dpasic.socials.rest.service;

import hr.vspr.dpasic.socials.rest.dao.mongo.UsersCredentialsMongo;
import hr.vspr.dpasic.socials.rest.dao.mongo.factory.MongoDBFactory;
import hr.vspr.dpasic.socials.rest.model.UserCredential;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("usersCredentialsService")
public class UsersCredentialsService implements DisposableBean {

    @Autowired
    PasswordEncoder passwordEncoder;

    private UsersCredentialsMongo usersCredentialsMongo;

    public UsersCredentialsService() {
        this.usersCredentialsMongo = new UsersCredentialsMongo();
    }

    public boolean doesExistCredentialForUsername(String username) {
        return (usersCredentialsMongo.getUserCredentialForUsername(username) != null);
    }

    public void addOrUpdateUserCredentialForUsernameAndPassword(String username, String rawPassword) {
        String hashedPassword = passwordEncoder.encode(rawPassword);
        UserCredential userCredential = new UserCredential(username, hashedPassword);

        /* If user credential for given username does not exist */
        if (usersCredentialsMongo.getUserCredentialForUsername(username) == null) {
            usersCredentialsMongo.addUserCredential(userCredential);
        } else {
            usersCredentialsMongo.updateUserCredentialForNewCredential(userCredential);
        }
    }

    public void deleteUserCredentialForUsername(String username) {
        usersCredentialsMongo.deleteUserCredentialForUsername(username);
    }

    public boolean isAuthenticated(String username, String providedRawPassword) {
        UserCredential obtainedUserCredential = usersCredentialsMongo.getUserCredentialForUsername(username);

        /* If credential does not exist for the given username */
        if (obtainedUserCredential == null) {
            return false;
        }
        String hashedPasswordFromDB = obtainedUserCredential.getHashedPassword();
        if (passwordEncoder.matches(providedRawPassword, hashedPasswordFromDB)) {
            return true;
        }

        return false;
    }

    public UsersCredentialsMongo getUsersCredentialsMongo() {
        return usersCredentialsMongo;
    }

    public void setUsersCredentialsMongo(UsersCredentialsMongo usersCredentialsMongo) {
        this.usersCredentialsMongo = usersCredentialsMongo;
    }

    @Override
    public void destroy() throws Exception {
        MongoDBFactory.close();
    }
}
