package hr.vspr.dpasic.socials.rest.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SocialStatus implements Comparable<SocialStatus> {

    private final SimpleDateFormat SDF = new SimpleDateFormat("dd.MM.yyyy. HH:mm");

    private String text;
    private Date createdAt;
    private String creatorName;
    private String imageUrl;
    private SocialNetwork socialNetwork;

    @Override
    public String toString() {
        return String.format("%s | %s | %s | %s | %s", text, SDF.format(createdAt), creatorName, imageUrl, socialNetwork.toString());
    }

    @Override
    public int compareTo(SocialStatus other) {
        return this.createdAt.compareTo(other.createdAt) * (-1); /* descending */
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public SocialNetwork getSocialNetwork() {
        return socialNetwork;
    }

    public void setSocialNetwork(SocialNetwork socialNetwork) {
        this.socialNetwork = socialNetwork;
    }
}
