package rest.call.twitter.credential.post;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class PostTwitterCredential {

    public static void main(String[] args) {

        String username = "user75096";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Request-Token", "");
        headers.set("Request-Token-Secret", "");
        headers.set("Oauth-Verifier", "");

        HttpEntity<Void> entity = new HttpEntity<Void>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    String.format("http://localhost:8080/socials-rest/twitter/auth/%s/accessToken", username),
                    HttpMethod.POST, entity, String.class);

            System.out.println(response.toString());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
