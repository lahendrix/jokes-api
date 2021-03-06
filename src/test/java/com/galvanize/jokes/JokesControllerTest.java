package com.galvanize.jokes;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class JokesControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    JokesService jokesService;

    @Test
    void createJoke_returnsJoke() throws Exception {
        // Setup
        Joke expectedJoke = new Joke(1L, JokeCategory.MOMJOKES, "Yo mamma so ugly, everybody thinks she's ugly");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(expectedJoke);
        when(jokesService.createJoke(ArgumentMatchers.any(Joke.class))).thenReturn(expectedJoke);

        // Exercise
        mockMvc.perform(post("/api/jokes").content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(notNullValue()))
                .andExpect(jsonPath("$.description").value(expectedJoke.getDescription()))
                .andExpect(jsonPath("$.category").value(expectedJoke.getCategory().name()));
    }

    @Test
    void createJoke_whenNoDescriptionExists_returns400() throws Exception {
        // Setup
        Joke noDescriptionJoke = new Joke();
        noDescriptionJoke.setCategory(JokeCategory.KIDJOKES);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(noDescriptionJoke);

        // Exercise & Assert
        mockMvc.perform(post("/api/jokes").content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Description cannot be null."));
    }

    @Test
    void createJoke_whenNoCategoryExists_returns400() throws Exception {
        // Setup
        Joke noCategoryJoke = new Joke();
        noCategoryJoke.setDescription("Some really funny joke");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(noCategoryJoke);

        // Exercise & Assert
        mockMvc.perform(post("/api/jokes").content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Category cannot be null."));
    }

    @Test
    void getJokes_whenJokesExist_returnsJokes() throws Exception {
        // Setup
        Joke expectedJoke = new Joke(3L, JokeCategory.TECHNOLOGY, "Yo computer is so old it is old.");
        ArrayList<Joke> expectedJokesList = new ArrayList<>();
        expectedJokesList.add(expectedJoke);
        when(jokesService.getAllJokes()).thenReturn(expectedJokesList);

        // Exercise and Assert
        mockMvc.perform(get("/api/jokes").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(expectedJokesList.size())))
                .andExpect(jsonPath("$[0].description").value(expectedJoke.getDescription()))
                .andExpect(jsonPath("$[0].category").value(expectedJoke.getCategory().name()));
    }

    @Test
    void getJokesById_whenJokeExists_returnsJoke() throws Exception {
        // Setup
        Joke expectedJoke = new Joke(3L, JokeCategory.TECHNOLOGY, "Yo computer is so old it is old.");
        when(jokesService.getJokeById(3L)).thenReturn(expectedJoke);

        // Exercise and Assert
        mockMvc.perform(get("/api/jokes/3").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3L))
                .andExpect(jsonPath("$.description").value(expectedJoke.getDescription()))
                .andExpect(jsonPath("$.category").value(expectedJoke.getCategory().name()));
    }

    @Test
    void deleteJokeById_whenJokeExists_deletesJoke() throws Exception {
        // Setup
        when(jokesService.deleteJokeById(ArgumentMatchers.any(Long.class))).thenReturn(true);

        // Exercise and Assert
        mockMvc.perform(delete("/api/jokes/3").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
        verify(jokesService).deleteJokeById(3L);
    }
}


