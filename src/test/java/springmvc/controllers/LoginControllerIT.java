package springmvc.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URL;

import static org.hamcrest.Matchers.equalTo;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginControllerIT {

    @LocalServerPort
    private int port;

    private URL base;

    @Autowired
    private TestRestTemplate template;

    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/login");
    }

    @Test
    public void getHeadline() throws Exception {
        //String response//; = template.getForObject(base.toString(), String.class);
        String response = template.getForObject(base.toString(), String.class);
        assertThat(response).contains("Login");
        //ResponseEntity<String> response = template.getForEntity(base.toString(),
        //        String.class);
        //assertThat(response.getBody(), equalTo("Greetings from Spring Boot!"));
    }
}
