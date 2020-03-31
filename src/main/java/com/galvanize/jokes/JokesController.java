package com.galvanize.jokes;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/jokes")
class JokesController {

    JokesService jokesService;

    public JokesController(JokesService service) {
        this.jokesService = service;
    }

    @PostMapping
    ResponseEntity<Object> createJoke(@RequestBody Joke joke) {
        if(joke.getCategory() == null) {
            return ResponseEntity.badRequest().body("Category cannot be null.");
        }

        if(joke.getDescription() == null) {
            return ResponseEntity.badRequest().body("Description cannot be null.");
        }
        return ResponseEntity.ok(jokesService.createJoke(joke));
    }

    @GetMapping
    ResponseEntity<List<Joke>> getJokes() {
        return ResponseEntity.ok(jokesService.getAllJokes());
    }

    @GetMapping("/{id}")
    ResponseEntity<Joke> getJokeById(@PathVariable Long id) {
        return ResponseEntity.ok(jokesService.getJokeById(id));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Object> deleteJokeById(@PathVariable Long id) {
        boolean successfulDeletion = jokesService.deleteJokeById(id);
        if(successfulDeletion) {
            return ResponseEntity.ok(null);
        } else {
            return ResponseEntity.badRequest().body("Joke with id" + id + " was not found.");
        }
    }
}
