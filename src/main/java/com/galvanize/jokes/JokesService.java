package com.galvanize.jokes;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    Joke getJokeById(Long id) {
        Optional<Joke> optionalJoke = jokesRepository.findById(id);
        return optionalJoke.orElse(null);
    }

}
