package com.galvanize.jokes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class JokesServiceTest {

    @Autowired
    JokesRepository jokesRepository;

    JokesService jokesService;

    @BeforeEach
    void setup() {
        jokesService = new JokesService(jokesRepository);
    }

    @Test
    void createJoke_returnsJoke() {
        // Setup
        Joke expectedJoke = new Joke(JokeCategory.DADJOKES, "Yo dad so small, everybody thinks he's small.");

        // Exercise
        Joke savedJoke = jokesService.createJoke(expectedJoke);

        // Assert
        assertNotNull(savedJoke.getId());
        assertEquals(expectedJoke.getCategory(), savedJoke.getCategory());
        assertEquals(expectedJoke.getDescription(), savedJoke.getDescription());

        // Teardown
        jokesRepository.deleteAll();
    }

    @Test
    void getAllJokes_returnsJokes() {
        // Setup
        Joke expectedJoke = new Joke(JokeCategory.KNOCKKNOCK, "A bad joke");
        jokesRepository.save(expectedJoke);

        // Exercise
        List<Joke> actual = jokesService.getAllJokes();

        // Assert
        assertEquals(1, actual.size());
        assertEquals(expectedJoke.getCategory(), actual.get(0).getCategory());
        assertEquals(expectedJoke.getDescription(), actual.get(0).getDescription());

        // Teardown
        jokesRepository.deleteAll();
    }
}

