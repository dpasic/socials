package hr.vspr.dpasic.socials.rest.model;

public class SocialsPost {

    private String postText;
    private SocialNetworksSelection socialNetworksSelection;

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public SocialNetworksSelection getSocialNetworksSelection() {
        return socialNetworksSelection;
    }

    public void setSocialNetworksSelection(SocialNetworksSelection socialNetworksSelection) {
        this.socialNetworksSelection = socialNetworksSelection;
    }
}
