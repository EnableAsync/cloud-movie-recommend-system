package com.klaus.offlinerecommender.controller;

import com.klaus.offlinerecommender.model.domain.Movie;
import com.klaus.offlinerecommender.model.domain.Tag;
import com.klaus.offlinerecommender.model.domain.User;
import com.klaus.offlinerecommender.model.recom.Recommendation;
import com.klaus.offlinerecommender.model.request.*;
import com.klaus.offlinerecommender.service.MovieService;
import com.klaus.offlinerecommender.service.RecommenderService;
import com.klaus.offlinerecommender.service.TagService;
import com.klaus.offlinerecommender.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RequestMapping("/offline")
@CrossOrigin
@RestController
@Slf4j
public class OfflineController {
    @Autowired
    MovieService movieService;

    @Autowired
    private RecommenderService recommenderService;

    @Autowired
    private UserService userService;

    @Autowired
    private TagService tagService;

//    @RequestMapping(value = "/guess", produces = "application/json", method = RequestMethod.GET)
//    public Movie[] getGuessMovies( @RequestParam("username") String username, @RequestParam("num") int num ) {
//
//    }

    @RequestMapping(value = "/stream", produces = "application/json", method = RequestMethod.GET)
    public Movie[] getStreamMovies( @RequestParam("username") String username, @RequestParam("num") int num ) {
        User user = userService.findByUsername(username);
        List<Recommendation> recommendations = recommenderService.findStreamRecs(user.getUid(), num);
        return movieService.movieList2Array(movieService.getRecommendedMovies(recommendations));
    }

    @RequestMapping(value = "/wish", produces = "application/json", method = RequestMethod.GET)
    public Movie[] getWishMovies( @RequestParam("username") String username, @RequestParam("num") int num ) {
        User user = userService.findByUsername(username);
        List<Recommendation> recommendations = recommenderService.getCollaborativeFilteringRecommendations(new UserRecommendationRequest(user.getUid(), num));
        if (recommendations.size() == 0) {
            String randomGenres = user.getPrefGenres().get(new Random().nextInt(user.getPrefGenres().size()));
            recommendations = recommenderService.getTopGenresRecommendations(new TopGenresRecommendationRequest(randomGenres.split(" ")[0], num));
        }
        return movieService.movieList2Array(movieService.getRecommendedMovies(recommendations));
    }

    @RequestMapping(value = "/hot", produces = "application/json", method = RequestMethod.GET)
    public Movie[] getHotMovies( @RequestParam("num") int num ) {
        List<Recommendation> recommendations = recommenderService.getHotRecommendations(new HotRecommendationRequest(num));
        System.out.println(recommendations.size());
        return movieService.movieList2Array(movieService.getRecommendedMovies(recommendations));
    }


    @RequestMapping(value = "/rate", produces = "application/json", method = RequestMethod.GET)
    public Movie[] getRateMoreMovies( @RequestParam("num") int num ) {
        List<Recommendation> recommendations = recommenderService.getRateMoreRecommendations(new RateMoreRecommendationRequest(num));

        return movieService.movieList2Array(movieService.getRecommendedMovies(recommendations));
    }

    @RequestMapping(value = "/new", produces = "application/json", method = RequestMethod.GET)
    public Movie[] getNewMovies( @RequestParam("num") int num ) {
        return movieService.movieList2Array(movieService.getNewMovies(new NewRecommendationRequest(num)));
    }


    @RequestMapping(value = "/same/{id}", produces = "application/json", method = RequestMethod.GET)
    public Movie[] getSameMovie( @PathVariable("id") int id, @RequestParam("num") int num ) {
        List<Recommendation> recommendations = recommenderService.getCollaborativeFilteringRecommendations(new MovieRecommendationRequest(id, num));
        return movieService.movieList2Array(movieService.getRecommendedMovies(recommendations));
    }


    @RequestMapping(value = "/info/{id}", produces = "application/json", method = RequestMethod.GET)
    public Movie getMovieInfo( @PathVariable("id") int id ) {
        return movieService.findByMID(id);
    }


    @RequestMapping(value = "/genres", produces = "application/json", method = RequestMethod.GET)
    public Movie[] getGenresMovies( @RequestParam("category") String category, @RequestParam("num") int num ) {
        List<Recommendation> recommendations = recommenderService.getTopGenresRecommendations(new TopGenresRecommendationRequest(category, num));
        return movieService.movieList2Array(movieService.getRecommendedMovies(recommendations));
    }

    @RequestMapping(value = "/topAll", produces = "application/json", method = RequestMethod.GET)
    public Movie[] getTopMovies( @RequestParam("num") int num ) {
        List<Recommendation> recommendations = recommenderService.getTopAllMovies(new TopAllMoviesRequest(num));
        return movieService.movieList2Array(movieService.getRecommendedMovies(recommendations));
    }


    @RequestMapping(value = "/tag/{mid}", produces = "application/json", method = RequestMethod.GET)
    public Tag[] getMovieTags( @PathVariable("mid") int mid, Model model ) {
        return tagService.tagList2Array(tagService.findMovieTags(mid));
    }



}