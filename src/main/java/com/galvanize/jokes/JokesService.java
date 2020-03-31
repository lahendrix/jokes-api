package com.galvanize.jokes;

import org.springframework.stereotype.Service;

@Service
class JokesService {

    JokesRepository jokesRepository;

    public JokesService(JokesRepository repository) {
        this.jokesRepository = repository;
    }
}
