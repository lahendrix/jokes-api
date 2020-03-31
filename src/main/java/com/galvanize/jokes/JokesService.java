package com.galvanize.jokes;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
class JokesService {

    JokesRepository jokesRepository;

    public JokesService(JokesRepository repository) {
        this.jokesRepository = repository;
    }

    Joke createJoke(Joke joke) {
        return jokesRepository.save(joke);
    }

    List<Joke> getAllJokes() {
        return jokesRepository.findAll();
    }

}
