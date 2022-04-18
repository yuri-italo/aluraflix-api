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
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void saveReturn400BadRequestIfAnyFieldIsEmpty() throws Exception {
        URI uri = new URI("/categories");
        String json = "{\"title\":\"Music\",\"color\":\"\"}";

        mockMvc.perform(MockMvcRequestBuilders
                .post(uri)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400)
        );
    }

    @Test
    void saveReturn201CreatedIfAllFieldsAreCorrect() throws Exception {
        URI uri = new URI("/categories");
        String json = "{\"title\":\"test3\",\"color\":\"test3\"}";

        mockMvc.perform(MockMvcRequestBuilders
                .post(uri)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(201)
        );
    }

    @Test
    void saveReturn400BadRequestIfAnyFieldAlreadyExists() throws Exception {
        URI uri = new URI("/categories");
        String json = "{\"title\":\"Podcast\",\"color\":\"#7D599C\"}";

        mockMvc.perform(MockMvcRequestBuilders
                .post(uri)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400)
        );
    }

    @Test
    void getAllReturn200OkIfRequestIsCorrect() throws Exception {
        URI uri = new URI("/categories");

        mockMvc.perform(MockMvcRequestBuilders
                .get(uri)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200)
        );
    }

    @Test
    void getByIdReturn200OkIfPathVariableMatchesOneCategory() throws Exception {
        URI uri = new URI("/categories/1");

        mockMvc.perform(MockMvcRequestBuilders
                .get(uri)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200)
        );
    }

    @Test
    void getByIdReturn400BadRequestIfPathVariableIsIncorrect() throws Exception {
        URI uri = new URI("/categories/test");

        mockMvc.perform(MockMvcRequestBuilders
                .get(uri)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400)
        );
    }

    @Test
    void getByIdReturn404NotFoundIfPathVariableDoesNotMatchAnyCategory() throws Exception {
        URI uri = new URI("/categories/10000");

        mockMvc.perform(MockMvcRequestBuilders
                .get(uri)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(404)
        );
    }

    @Test
    void getVideosByCategoryReturn200OkIfURLIsCorrectAndPathVariableMatchesOneCategory() throws Exception {
        URI uri = new URI("/categories/1/videos");

        mockMvc.perform(MockMvcRequestBuilders
                .get(uri)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200)
        );
    }

    @Test
    void getVideosByCategoryReturn404NotFoundIfPathVariableDoesNotMatchAnyCategory() throws Exception {
        URI uri = new URI("/categories/10000/videos");

        mockMvc.perform(MockMvcRequestBuilders
                .get(uri)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(404)
        );
    }

    @Test
    void updateReturn400BadRequestIfAnyFieldIsEmpty() throws Exception {
        URI uri = new URI("/categories/1");
        String json = "{\"title\":\"Music\",\"color\":\"\"}";

        mockMvc.perform(MockMvcRequestBuilders
                .put(uri)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400)
        );
    }

    @Test
    void updateReturn200OkIfAllFieldsAreCorrect() throws Exception {
        URI uri = new URI("/categories/1");
        String json = "{\"title\":\"test2\",\"color\":\"test2\"}";

        mockMvc.perform(MockMvcRequestBuilders
                .put(uri)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200)
        );
    }

    @Test
    void updateReturn404NotFoundIfPathVariableDoesNotMatchAnyCategory() throws Exception {
        URI uri = new URI("/categories/10000");
        String json = "{\"title\":\"Music\",\"color\":\"blue\"}";

        mockMvc.perform(MockMvcRequestBuilders
                .put(uri)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(404)
        );
    }

    @Test
    void deleteReturn200OkIfPathVariableMatchesOneCategory() throws Exception {
        URI uri = new URI("/categories/1");

        mockMvc.perform(MockMvcRequestBuilders
                .delete(uri)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200)
        );
    }

    @Test
    void deleteReturn404NotFoundIfPathVariableDoesNotMatchAnyCategory() throws Exception {
        URI uri = new URI("/categories/10000");

        mockMvc.perform(MockMvcRequestBuilders
                .delete(uri)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(404)
        );
    }
}