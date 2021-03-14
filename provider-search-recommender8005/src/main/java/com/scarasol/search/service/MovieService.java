//package com.scarasol.search.service;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.mongodb.MongoClient;
//import com.mongodb.client.FindIterable;
//import com.mongodb.client.MongoCollection;
//import com.mongodb.client.model.Filters;
//import com.mongodb.util.JSON;
//import com.rai.model.domain.Movie;
//import com.scarasol.search.model.recom.Recommendation;
//import com.scarasol.search.utils.Constant;
//import org.bson.Document;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class MovieService {
//
//    @Autowired
//    private MongoClient mongoClient;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    private MongoCollection<Document> movieCollection;
//    private MongoCollection<Document> averageMoviesScoreCollection;
//
//    public List<Movie> getRecommendeMovies(List<Recommendation> recommendations) {
//        List<Integer> ids = new ArrayList<>();
//        for (Recommendation rec : recommendations) {
//            ids.add(rec.getMid());
//        }
//        return getMovies(ids);
//    }
//
//    public List<Movie> getMovies(List<Integer> mids) {
//        FindIterable<Document> documents = getMovieCollection().find(Filters.in("mid", mids));
//        List<Movie> movies = new ArrayList<>();
//        for (Document document : documents) {
//            Movie m = documentToMovie(document);
//            movies.add(m);
//        }
//        movies.sort((movie, t1) -> {
//            double val = movie.getScore() - t1.getScore();
//            if (val < 0) {
//                return 1;
//            } else if (val == 0)
//                return 0;
//            else
//                return -1;
//        });
//        return movies;
//    }
//
//    private MongoCollection<Document> getMovieCollection() {
//        if (null == movieCollection)
//            movieCollection = mongoClient.getDatabase(Constant.MONGODB_DATABASE).getCollection(Constant.MONGODB_MOVIE_COLLECTION);
//        return movieCollection;
//    }
//
//    private Movie documentToMovie(Document document) {
//        Movie movie = null;
//        try {
//            movie = objectMapper.readValue(JSON.serialize(document), Movie.class);
//            Document score = getAverageMoviesScoreCollection().find(Filters.eq("mid", movie.getMid())).first();
//            if (null == score || score.isEmpty())
//                movie.setScore(0D);
//            else
//                movie.setScore((Double) score.get("avg", 0D));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return movie;
//    }
//
//    private MongoCollection<Document> getAverageMoviesScoreCollection() {
//        if (null == averageMoviesScoreCollection)
//            averageMoviesScoreCollection = mongoClient.getDatabase(Constant.MONGODB_DATABASE).getCollection(Constant.MONGODB_AVERAGE_MOVIES_SCORE_COLLECTION);
//        return averageMoviesScoreCollection;
//    }
//}
