package com.mcnz.copilot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import jakarta.servlet.http.Cookie;

import static org.hamcrest.Matchers.containsString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class HangmanControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void homePageRendersAndCreatesTokenCookie() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(cookie().exists("HANGONMAN_TOKEN"))
                .andExpect(content().string(containsString("HANG ON, MAN")))
                .andExpect(content().string(containsString("CURRENT DUEL")));
    }

    @Test
    void submittingGuessRedirectsBackToHomePage() throws Exception {
        Cookie tokenCookie = mockMvc.perform(get("/"))
                .andReturn()
                .getResponse()
                .getCookie("HANGONMAN_TOKEN");

        assertThat(tokenCookie).isNotNull();

        mockMvc.perform(post("/guess")
                        .cookie(tokenCookie)
                        .param("guess", "a"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }
}

