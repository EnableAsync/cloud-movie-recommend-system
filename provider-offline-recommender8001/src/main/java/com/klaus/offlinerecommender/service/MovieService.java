package com.klaus.offlinerecommender.service;

import com.klaus.offlinerecommender.model.domain.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public MovieService( MongoTemplate mongoTemplate ) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<Movie> findAll() {
        return mongoTemplate.findAll(Movie.class);
    }

    public Movie findById(String mid){
        return mongoTemplate.findById(mid,Movie.class);
    }
}
