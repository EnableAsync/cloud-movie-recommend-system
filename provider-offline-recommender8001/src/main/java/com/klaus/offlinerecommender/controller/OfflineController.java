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

import java.awt.image.ImageProducer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public Map<String, Object> getStreamMovies(@RequestParam("username") String username, @RequestParam("num") int num ) {
        User user = userService.findByUsername(username);
        List<Recommendation> recommendations = recommenderService.findStreamRecs(user.getUid(), num);
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("success",true);
        resultMap.put("movies",movieService.movieList2Array(movieService.getRecommendedMovies(recommendations)));
        return resultMap;

    }

    @RequestMapping(value = "/wish", produces = "application/json", method = RequestMethod.GET)
    public Map<String, Object> getWishMovies(@RequestParam("username") String username, @RequestParam("num") int num ) {
        User user = userService.findByUsername(username);
        List<Recommendation> recommendations = recommenderService.getCollaborativeFilteringRecommendations(new UserRecommendationRequest(user.getUid(), num));
        if (recommendations.size() == 0) {
            String randomGenres = user.getPrefGenres().get(new Random().nextInt(user.getPrefGenres().size()));
            recommendations = recommenderService.getTopGenresRecommendations(new TopGenresRecommendationRequest(randomGenres.split(" ")[0], num));
        }
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("success",true);
        resultMap.put("movies",movieService.movieList2Array(movieService.getRecommendedMovies(recommendations)));
        return resultMap;

    }

    @RequestMapping(value = "/hot", produces = "application/json", method = RequestMethod.GET)
    public Map<String, Object> getHotMovies(@RequestParam("num") int num ) {
        List<Recommendation> recommendations = recommenderService.getHotRecommendations(new HotRecommendationRequest(num));
        System.out.println(recommendations.size());
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("success",true);
        resultMap.put("movies",movieService.movieList2Array(movieService.getRecommendedMovies(recommendations)));
        return resultMap;

    }


    @RequestMapping(value = "/rate", produces = "application/json", method = RequestMethod.GET)
    public Map<String, Object> getRateMoreMovies(@RequestParam("num") int num ) {
        List<Recommendation> recommendations = recommenderService.getRateMoreRecommendations(new RateMoreRecommendationRequest(num));
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("success",true);
        resultMap.put("movies",movieService.movieList2Array(movieService.getRecommendedMovies(recommendations)));
        return resultMap;

    }

    @RequestMapping(value = "/new", produces = "application/json", method = RequestMethod.GET)
    public Map<String, Object> getNewMovies(@RequestParam("num") int num ) {
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("success",true);
        resultMap.put("movies",movieService.movieList2Array(movieService.getNewMovies(new NewRecommendationRequest(num))));
        return resultMap;

    }


    @RequestMapping(value = "/same/{id}", produces = "application/json", method = RequestMethod.GET)
    public Map<String, Object> getSameMovie(@PathVariable("id") int id, @RequestParam("num") int num ) {
        List<Recommendation> recommendations = recommenderService.getCollaborativeFilteringRecommendations(new MovieRecommendationRequest(id, num));
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("success",true);
        resultMap.put("movies",movieService.movieList2Array(movieService.getRecommendedMovies(recommendations)));
        return resultMap;

    }


    @RequestMapping(value = "/info/{id}", produces = "application/json", method = RequestMethod.GET)
    public Map<String, Object> getMovieInfo(@PathVariable("id") int id ) {
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("success",true);
        resultMap.put("movies",movieService.findByMID(id));
        return resultMap;
    }


    @RequestMapping(value = "/genres", produces = "application/json", method = RequestMethod.GET)
    public Map<String, Object> getGenresMovies(@RequestParam("category") String category, @RequestParam("num") int num ) {
        List<Recommendation> recommendations = recommenderService.getTopGenresRecommendations(new TopGenresRecommendationRequest(category, num));
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("success",true);
        resultMap.put("movies",movieService.movieList2Array(movieService.getRecommendedMovies(recommendations)));
        return resultMap;
    }

    @RequestMapping(value = "/topAll", produces = "application/json", method = RequestMethod.GET)
    public Map<String, Object> getTopMovies(@RequestParam("num") int num ) {
        List<Recommendation> recommendations = recommenderService.getTopAllMovies(new TopAllMoviesRequest(num));
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("success",true);
        resultMap.put("movies",movieService.movieList2Array(movieService.getRecommendedMovies(recommendations)));
        return resultMap;
    }


    @RequestMapping(value = "/tag/{mid}", produces = "application/json", method = RequestMethod.GET)
    public Map<String, Object> getMovieTags(@PathVariable("mid") int mid ) {
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("success",true);
        resultMap.put("movies",tagService.tagList2Array(tagService.findMovieTags(mid)));
        return resultMap;

    }



}