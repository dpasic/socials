package hr.vspr.dpasic.socials.rest.service;

import hr.vspr.dpasic.socials.rest.dao.mongo.UsersMongo;
import hr.vspr.dpasic.socials.rest.dao.mongo.factory.MongoDBFactory;
import hr.vspr.dpasic.socials.rest.model.User;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("usersService")
public class UsersService implements DisposableBean {

    private UsersMongo usersMongo;

    public UsersService() {
        this.usersMongo = new UsersMongo();
    }

    public List<User> getAllUsers() {
        return usersMongo.getAllUsers();
    }

    public User getUserForUsername(String username) {
        return usersMongo.getUserForUsername(username);
    }

    public void addUser(User user) {
        usersMongo.addUser(user);
    }

    public UsersMongo getUsersMongo() {
        return usersMongo;
    }

    public void setUsersMongo(UsersMongo usersMongo) {
        this.usersMongo = usersMongo;
    }

    @Override
    public void destroy() throws Exception {
        MongoDBFactory.close();
    }
}
