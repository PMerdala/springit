package pl.pmerdala.springit.controller;

import org.hamcrest.Matchers;
import org.hamcrest.core.IsInstanceOf;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import pl.pmerdala.springit.repositories.LinkRepository;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Tag("Mvc")
@SpringBootTest
@AutoConfigureMockMvc
public class GetRootLinkControllerMvc {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    LinkRepository linkRepository;

    @Test
    @WithAnonymousUser
    void getListOfLinkAsAnonymousUser() throws Exception {
        //arrange
        mockMvc.perform(get("/"))
                //.andDo(print())
                //act
                .andExpect(status().isOk())
                .andExpect(model().attribute("links", IsInstanceOf.instanceOf(List.class)))
                .andExpect(content().string(Matchers.containsString("")))
        ;
    }
}
