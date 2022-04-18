package br.com.alura.aluraflixapi.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class VideoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void saveReturn400BadRequestIfAnyFieldIsEmpty() throws Exception {
        URI uri = new URI("/videos");
        String json = "{\"title\":\"Batman\",\"description\":\"Best movies\",\"url\":\"\"}";

        mockMvc.perform(MockMvcRequestBuilders
                .post(uri)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400)
        );
    }

    @Test
    void saveReturn201CreatedIfAllFieldsAreCorrect() throws Exception {
        URI uri = new URI("/videos");
        String json = "{\"title\":\"test4\",\"description\":\"test4\",\"url\":\"https://www.test4.com.br\"}";

        mockMvc.perform(MockMvcRequestBuilders
                .post(uri)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(201)
        );
    }

    @Test
    void saveReturn400BadRequestIfAnyFieldAlreadyExists() throws Exception {
        URI uri = new URI("/videos");
        String json = "{\"title\":\"test1\",\"description\":\"test1\",\"url\":\"https://www.test1.com.br\"}";

        mockMvc.perform(MockMvcRequestBuilders
                .post(uri)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400)
        );
    }

    @Test
    void getByIdReturn400BadRequestIfPathVariableIsIncorrect() throws Exception {
        URI uri = new URI("/videos/test");

        mockMvc.perform(MockMvcRequestBuilders
                .get(uri)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400)
        );
    }

    @Test
    void getByIdReturn404NotFoundIfPathVariableDoesNotMatchAnyVideo() throws Exception {
        URI uri = new URI("/videos/10000");

        mockMvc.perform(MockMvcRequestBuilders
                .get(uri)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(404)
        );
    }

    @Test
    void updateReturn400BadRequestIfAnyFieldIsEmpty() throws Exception {
        URI uri = new URI("/videos/1");
        String json = "{\"title\":\"test1\",\"description\":\"test1\",\"url\":\"\"}";

        mockMvc.perform(MockMvcRequestBuilders
                .put(uri)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400)
        );
    }

    @Test
    void updateReturn200OkIfAllFieldsAreCorrect() throws Exception {
        URI uri = new URI("/videos/1");
        String json = "{\"title\":\"test3\",\"description\":\"test3\",\"url\":\"https://www.test3.com.br\"}";

        mockMvc.perform(MockMvcRequestBuilders
                .put(uri)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200)
        );
    }

    @Test
    void updateReturn404NotFoundIfPathVariableDoesNotMatchAnyVideo() throws Exception {
        URI uri = new URI("/videos/10000");
        String json = "{\"title\":\"test3\",\"description\":\"test3\",\"url\":\"https://www.test3.com.br\"}";
        mockMvc.perform(MockMvcRequestBuilders
                .put(uri)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(404)
        );
    }

    @Test
    void deleteReturn200OkIfPathVariableMatchesOneVideo() throws Exception {
        URI uri = new URI("/videos/1");

        mockMvc.perform(MockMvcRequestBuilders
                .delete(uri)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200)
        );
    }

    @Test
    void deleteReturn404NotFoundIfPathVariableDoesNotMatchAnyVideo() throws Exception {
        URI uri = new URI("/videos/10000");

        mockMvc.perform(MockMvcRequestBuilders
                .delete(uri)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(404)
        );
    }
}