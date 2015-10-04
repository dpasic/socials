package hr.vspr.dpasic.socials.rest.parser;

import com.sola.instagram.model.Media;
import com.sola.instagram.util.PaginatedCollection;
import hr.vspr.dpasic.socials.rest.model.SocialNetwork;
import hr.vspr.dpasic.socials.rest.model.SocialStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InstagramFeedParser {

    public static List<SocialStatus> parseInstagramFeedFromMedias(PaginatedCollection<Media> instagramMedias) {
        List<SocialStatus> instagramFeed = new ArrayList<SocialStatus>();

        int counter = 1;
        for (Media instagramMedia : instagramMedias) {
            if (counter > 20) {
                break;
            }
            if (instagramMedia.getType().equals("video")) { /* For now only images are supported */
                continue;
            }
            instagramFeed.add(parseSocialStatusFromMedia(instagramMedia));
            counter++;
        }

        return instagramFeed;
    }

    private static SocialStatus parseSocialStatusFromMedia(Media instagramMedia) {
        SocialStatus socialStatus = new SocialStatus();
        long createdAtTimestamp = Long.parseLong(instagramMedia.getCreatedTimestamp()) * 1000;
        Media.Caption instagramCaption = instagramMedia.getCaption();

        if (instagramCaption == null) {
            socialStatus.setText("");
        } else {
            socialStatus.setText(instagramMedia.getCaption().getText());
        }
        socialStatus.setCreatedAt(new Date(createdAtTimestamp));
        socialStatus.setCreatorName(instagramMedia.getUser().getUserName());
        socialStatus.setImageUrl(instagramMedia.getStandardResolutionImage().getUri());
        socialStatus.setSocialNetwork(SocialNetwork.INSTAGRAM);

        return socialStatus;
    }
}
