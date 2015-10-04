package rest.call.credentials.post;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class PostIsAuthenticatedForUsernameAndPassword {

    public static void main(String[] args) {

        String username = "user75096";
        String rawPassword = "pa$$w0rd";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<String>(rawPassword, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    String.format("http://localhost:8080/socials-rest/users/%s/authenticated", username),
                    HttpMethod.POST, entity, String.class);

            System.out.println(response.toString());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
