package rest.call.twitter.auth.get;

import hr.vspr.dpasic.socials.rest.model.twitter.TwitterRequestTokenUrl;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class GetTwitterRequestToken {

    public static void main(String[] args) {

        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<TwitterRequestTokenUrl> twitterRequestTokenResponse = restTemplate
                    .exchange("http://localhost:8080/twitter/auth/requestToken",
                            HttpMethod.GET, null,
                            new ParameterizedTypeReference<TwitterRequestTokenUrl>() {
                            });
            TwitterRequestTokenUrl twitterRequestTokenUrl = twitterRequestTokenResponse.getBody();

            System.out.println(twitterRequestTokenUrl.toString());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
