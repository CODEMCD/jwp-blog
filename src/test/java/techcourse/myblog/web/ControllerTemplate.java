package techcourse.myblog.web;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

@AutoConfigureWebTestClient
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ControllerTemplate {

    protected static final String NAME = "park";
    protected static final String EMAIL = "user@gmail.com";
    protected static final String PASSWORD = "Password123!";

    @Autowired
    protected WebTestClient webTestClient;

    public WebTestClient.ResponseSpec requestSignUp(String name, String email, String password) {
        return webTestClient.post().uri("/users")
                .body(BodyInserters.fromFormData("name", name)
                        .with("email", email)
                        .with("password", password))
                .exchange()
                ;
    }

    public WebTestClient.ResponseSpec requestLogin(String email, String password) {
        return webTestClient.post().uri("/login")
                .body(BodyInserters.fromFormData("email", email)
                        .with("password", password))
                .exchange()
                ;
    }

    public String getCookie(String email, String password) {
        return requestLogin(email, password)
                .expectStatus()
                .isFound()
                .returnResult(String.class)
                .getResponseHeaders()
                .getFirst("Set-cookie")
                ;
    }

    public WebTestClient.ResponseSpec requestWriteArticle(String cookie, String title, String coverUrl, String contents) {
        return webTestClient.post()
                .uri("/articles")
                .header("Cookie", cookie)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters
                        .fromFormData("title", title)
                        .with("coverUrl", coverUrl)
                        .with("contents", contents))
                .exchange()
                ;
    }

    public WebTestClient.ResponseSpec requestWriteComment(String cookie, String contents) {
        return webTestClient.post()
                .uri("/articles/1/comments")
                .header("Cookie", cookie)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters
                        .fromFormData("contents", contents))
                .exchange()
                ;
    }
}
