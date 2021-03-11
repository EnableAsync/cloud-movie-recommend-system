package com.klaus.consumer.controller;

import com.klaus.consumer.domain.Movie;
import com.klaus.consumer.domain.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.*;

import static com.klaus.consumer.utils.Constant.OFFLINE_URL;
import static com.klaus.consumer.utils.Constant.REALTIME_URL;

@RequestMapping("/rest/movie")
@CrossOrigin
@RestController
@Slf4j
public class MovieController {



    @Resource
    private RestTemplate restTemplate;


    @RequestMapping(value = "/guess", produces = "application/json", method = RequestMethod.GET)
    public Model getGuessMovies( @RequestParam("username") String username, @RequestParam("num") int num, Model model ) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("username", username);
        paramMap.put("num", num);
        // 离线
        Movie[] offlineMovies = restTemplate.getForObject(OFFLINE_URL + "/offline/guess", Movie[].class, paramMap);
        // 在线
        Movie[] realTimeMovies = restTemplate.getForObject(REALTIME_URL + "/realtime/guess", Movie[].class, paramMap);
        return getModel(model, offlineMovies, realTimeMovies);
    }

    @RequestMapping(value = "/stream", produces = "application/json", method = RequestMethod.GET)
    public Model getStreamMovies( @RequestParam("username") String username, @RequestParam("num") int num, Model model ) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("username", username);
        paramMap.put("num", num);
        Movie[] movies = restTemplate.getForObject(OFFLINE_URL + "/offline/stream", Movie[].class, paramMap);
        return getModel(model, movies);
    }

    @RequestMapping(value = "/wish", produces = "application/json", method = RequestMethod.GET)

    public Model getWishMovies( @RequestParam("username") String username, @RequestParam("num") int num, Model model ) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("username", username);
        paramMap.put("num", num);
        Movie[] movies = restTemplate.getForObject(OFFLINE_URL + "/offline/wish", Movie[].class, paramMap);
        return getModel(model, movies);
    }

    @RequestMapping(value = "/hot", produces = "application/json", method = RequestMethod.GET)

    public Model getHotMovies( @RequestParam("num") int num, Model model ) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("num", num);
        Movie[] movies = restTemplate.getForObject(OFFLINE_URL + "/offline/hot", Movie[].class, paramMap);
        return getModel(model, movies);
    }

    @RequestMapping(value = "/rate", produces = "application/json", method = RequestMethod.GET)
    public Model getRateMoreMovies( @RequestParam("num") int num, Model model ) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("num", num);
        Movie[] movies = restTemplate.getForObject(OFFLINE_URL + "/offline/rate", Movie[].class, paramMap);
        return getModel(model, movies);
    }

    @RequestMapping(value = "/new", produces = "application/json", method = RequestMethod.GET)
    public Model getNewMovies( @RequestParam("num") int num, Model model ) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("num", num);
        Movie[] movies = restTemplate.getForObject(OFFLINE_URL + "/offline/new", Movie[].class, paramMap);
        return getModel(model, movies);
    }


    @RequestMapping(value = "/same/{id}", produces = "application/json", method = RequestMethod.GET)
    public Model getSameMovie( @PathVariable("id") int id, @RequestParam("num") int num, Model model ) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        paramMap.put("num", num);
        Movie[] movies = restTemplate.getForObject(OFFLINE_URL + "/offline/same", Movie[].class, paramMap);
        return getModel(model, movies);
    }

    @RequestMapping(value = "/info/{id}", produces = "application/json", method = RequestMethod.GET)
    public Model getMovieInfo( @PathVariable("id") int id, Model model ) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        Movie[] movies = restTemplate.getForObject(OFFLINE_URL + "/offline/info", Movie[].class, paramMap);
        return getModel(model, movies);
    }


    @RequestMapping(value = "/genres", produces = "application/json", method = RequestMethod.GET)
    public Model getGenresMovies( @RequestParam("category") String category, @RequestParam("num") int num, Model model ) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("category", category);
        paramMap.put("num", num);
        Movie[] movies = restTemplate.getForObject(OFFLINE_URL + "/offline/genres", Movie[].class, paramMap);
        return getModel(model, movies);

    }

    @RequestMapping(value = "/topAll", produces = "application/json", method = RequestMethod.GET)
    public Model getTopMovies( @RequestParam("num") int num, Model model ) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("num", num);
        Movie[] movies = restTemplate.getForObject(OFFLINE_URL + "/offline/topAll", Movie[].class, paramMap);
        return getModel(model, movies);
    }

    @RequestMapping(value = "/rate/{id}", produces = "application/json", method = RequestMethod.GET)
    public Model rateToMovie( @PathVariable("id") int id, @RequestParam("score") Double score, @RequestParam("username") String username, Model model ) {
        MultiValueMap<String, Object> valueMap = new LinkedMultiValueMap<>();
        valueMap.add("id", id);
        valueMap.add("score", score);
        valueMap.add("username", username);
        String message = restTemplate.postForObject(REALTIME_URL + "/realtime/rate", valueMap, String.class);
        model.addAttribute("success", true);
        model.addAttribute("message", message);
        return model;
    }

    @RequestMapping(value = "/tag/{mid}", produces = "application/json", method = RequestMethod.GET)
    public Model getMovieTags( @PathVariable("mid") int mid, Model model ) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("mid", mid);
        Tag[] tags = restTemplate.getForObject(OFFLINE_URL + "/offline/tag", Tag[].class, paramMap);
        model.addAttribute("success", true);
        model.addAttribute("tags", Arrays.asList(tags));
        return model;
    }

    private Model getModel( Model model, Movie[]... moviesArray ) {
        List<Movie> movieList = new ArrayList<>();
        for (Movie[] movies : moviesArray) {
            movieList.addAll(Arrays.asList(movies));
        }
        model.addAttribute("success", true);
        model.addAttribute("movies", movieList);
        return model;
    }

}
