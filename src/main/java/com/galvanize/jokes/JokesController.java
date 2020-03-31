package com.galvanize.jokes;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/jokes")
class JokesController {

    JokesService jokesService;

    public JokesController(JokesService service) {
        this.jokesService = service;
    }

    @PostMapping
    ResponseEntity<Joke> createJoke(Joke joke) {
        return ResponseEntity.ok(joke);
    }
}
