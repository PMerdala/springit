package pl.pmerdala.springit.controller;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import pl.pmerdala.springit.repositories.LinkRepository;

@SpringBootTest
@Tag("E2E")
public class GetRootLinkControllerE2E {

    @Autowired
    private ApplicationContext context;

    @Autowired
    LinkRepository linkRepository;

    @Test
    @WithAnonymousUser
    void getListOfLinkAsAnonymousUser() {
        //arrange
        WebTestClient client = WebTestClient.bindToApplicationContext(context).build();
        //act
        client.get().uri("/").accept(MediaType.TEXT_HTML)
                .exchange()
                //assert
        ;
    }
}
