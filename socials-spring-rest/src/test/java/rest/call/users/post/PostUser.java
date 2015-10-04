package rest.call.users.post;

import hr.vspr.dpasic.socials.rest.model.User;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class PostUser {

    public static void main(String[] args) {

        User user = new User();
        String username = String.format("user%d", (int)(Math.random() * 99999));
        String email = String.format("%s@mail.com", username);
        user.setUsername(username);
        user.setEmail(email);

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<User> entity = new HttpEntity<User>(user, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    "http://localhost:8080/socials-rest/users/",
                    HttpMethod.POST, entity, String.class);

            System.out.println(response.toString());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
