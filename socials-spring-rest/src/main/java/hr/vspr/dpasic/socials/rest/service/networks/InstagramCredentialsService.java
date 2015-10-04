package hr.vspr.dpasic.socials.rest.service.networks;

import hr.vspr.dpasic.socials.rest.dao.mongo.factory.MongoDBFactory;
import hr.vspr.dpasic.socials.rest.dao.mongo.networks.InstagramCredentialsMongo;
import hr.vspr.dpasic.socials.rest.model.instagram.InstagramCredentials;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Service;

@Service("instagramCredentialsService")
public class InstagramCredentialsService implements DisposableBean {

    private InstagramCredentialsMongo instagramCredentialsMongo;

    public InstagramCredentialsService() {
        this.instagramCredentialsMongo = new InstagramCredentialsMongo();
    }

    public InstagramCredentials getInstagramCredentialsForUsername(String username) {
        return instagramCredentialsMongo.getInstagramCredentialsForUsername(username);
    }

    public void addOrUpdateInstagramCredentials(InstagramCredentials instagramCredentials) {
        String username = instagramCredentials.getUsername();

        /* If Instagram credentials for given username does not exist */
        if (!doesExistCredentialsForUsername(username)) {
            instagramCredentialsMongo.addInstagramCredentials(instagramCredentials);
        } else {
            instagramCredentialsMongo.updateInstagramCredentialsForNewCredential(instagramCredentials);
        }
    }

    private boolean doesExistCredentialsForUsername(String username) {
        return (instagramCredentialsMongo.getInstagramCredentialsForUsername(username) != null);
    }

    public void deleteInstagramCredentialsForUsername(String username) {
        instagramCredentialsMongo.deleteInstagramCredentialsForUsername(username);
    }

    public InstagramCredentialsMongo getInstagramCredentialsMongo() {
        return instagramCredentialsMongo;
    }

    public void setInstagramCredentialsMongo(InstagramCredentialsMongo instagramCredentialsMongo) {
        this.instagramCredentialsMongo = instagramCredentialsMongo;
    }

    @Override
    public void destroy() throws Exception {
        MongoDBFactory.close();
    }
}
