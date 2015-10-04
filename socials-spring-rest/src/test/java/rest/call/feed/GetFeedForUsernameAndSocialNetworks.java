package rest.call.feed;

import hr.vspr.dpasic.socials.rest.model.SocialStatus;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class GetFeedForUsernameAndSocialNetworks {

    public static void main(String[] args) {

        String username = "dpasic";

        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<List<SocialStatus>> socialStatusesResponse = restTemplate
                    .exchange(String.format("http://localhost:8080/socials-rest/feed/%s/socialNetworks?list=twitter+facebook+instagram",
                                    username), HttpMethod.GET, null,
                            new ParameterizedTypeReference<List<SocialStatus>>() {
                            });
            List<SocialStatus> socialStatuses = socialStatusesResponse.getBody();

            for (SocialStatus socialStatus : socialStatuses) {
                System.out.println(socialStatus.toString());
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
