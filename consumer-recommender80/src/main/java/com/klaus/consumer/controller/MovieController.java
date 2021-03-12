package com.klaus.consumer.controller;

import com.klaus.consumer.domain.Movie;
import com.klaus.consumer.domain.Tag;
import com.klaus.consumer.service.MovieFeignService;
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
    private MovieFeignService movieFeignService;

    @RequestMapping(value = "/hot", produces = "application/json", method = RequestMethod.GET)
    public Map<String,Object> getHotMovies( @RequestParam("num") int num ){
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("success",true);
        resultMap.put("movies",movieFeignService.getHotMovies(num));
        return resultMap;
    }


    @Resource
    private RestTemplate restTemplate;


    @RequestMapping(value = "/guess", produces = "application/json", method = RequestMethod.GET)
    public Map<String,Object> getGuessMovies( @RequestParam("username") String username, @RequestParam("num") int num ) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("username", username);
        paramMap.put("num", num);
        // 离线
        Movie[] offlineMovies = restTemplate.getForObject(OFFLINE_URL + "/offline/guess", Movie[].class, paramMap);
        // 在线
        Movie[] realTimeMovies = restTemplate.getForObject(REALTIME_URL + "/realtime/guess", Movie[].class, paramMap);
        return getModel(offlineMovies, realTimeMovies);
    }

    @RequestMapping(value = "/stream", produces = "application/json", method = RequestMethod.GET)
    public Map<String,Object> getStreamMovies( @RequestParam("username") String username, @RequestParam("num") int num ) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("username", username);
        paramMap.put("num", num);
        Movie[] movies = restTemplate.getForObject(OFFLINE_URL + "/offline/stream", Movie[].class, paramMap);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("success",true);
        resultMap.put("movies",movies);
        return resultMap;
    }

    @RequestMapping(value = "/wish", produces = "application/json", method = RequestMethod.GET)

    public Map<String,Object> getWishMovies( @RequestParam("username") String username, @RequestParam("num") int num) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("username", username);
        paramMap.put("num", num);
        Movie[] movies = restTemplate.getForObject(OFFLINE_URL + "/offline/wish", Movie[].class, paramMap);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("success",true);
        resultMap.put("movies",movies);
        return resultMap;
    }

//    @RequestMapping(value = "/hot", produces = "application/json", method = RequestMethod.GET)
//    public Movie[] getHotMovies( @RequestParam("num") int num ) {
//        Map<String, Object> paramMap = new HashMap<>();
//        paramMap.put("num", num);
//        return restTemplate.getForObject(OFFLINE_URL + "/offline/hot?num={num}", Movie[].class, paramMap);
//    }



    @RequestMapping(value = "/rate", produces = "application/json", method = RequestMethod.GET)
    public Map<String,Object> getRateMoreMovies( @RequestParam("num") int num) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("num", num);
        System.out.println(num);
        Movie[] movies = restTemplate.getForObject(OFFLINE_URL + "/offline/rate", Movie[].class, paramMap);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("success",true);
        resultMap.put("movies",movies);
        return resultMap;
    }

    @RequestMapping(value = "/new", produces = "application/json", method = RequestMethod.GET)
    public Map<String,Object> getNewMovies( @RequestParam("num") int num, Model model ) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("num", num);
        Movie[] movies = restTemplate.getForObject(OFFLINE_URL + "/offline/new", Movie[].class, paramMap);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("success",true);
        resultMap.put("movies",movies);
        return resultMap;
    }


    @RequestMapping(value = "/same/{id}", produces = "application/json", method = RequestMethod.GET)
    public Map<String,Object> getSameMovie( @PathVariable("id") int id, @RequestParam("num") int num ) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        paramMap.put("num", num);
        Movie[] movies = restTemplate.getForObject(OFFLINE_URL + "/offline/same", Movie[].class, paramMap);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("success",true);
        resultMap.put("movies",movies);
        return resultMap;
    }

    @RequestMapping(value = "/info/{id}", produces = "application/json", method = RequestMethod.GET)
    public Map<String,Object> getMovieInfo( @PathVariable("id") int id ) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        Movie movie = restTemplate.getForObject(OFFLINE_URL + "/offline/info", Movie.class, paramMap);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("success",true);
        resultMap.put("movies",movie);
        return resultMap;

    }


    @RequestMapping(value = "/genres", produces = "application/json", method = RequestMethod.GET)
    public Map<String,Object> getGenresMovies( @RequestParam("category") String category, @RequestParam("num") int num ) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("category", category);
        paramMap.put("num", num);
        Movie[] movies = restTemplate.getForObject(OFFLINE_URL + "/offline/genres", Movie[].class, paramMap);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("success",true);
        resultMap.put("movies",movies);
        return resultMap;

    }

    @RequestMapping(value = "/topAll", produces = "application/json", method = RequestMethod.GET)
    public Map<String,Object> getTopMovies( @RequestParam("num") int num) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("num", num);
        Movie[] movies = restTemplate.getForObject(OFFLINE_URL + "/offline/topAll", Movie[].class, paramMap);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("success",true);
        resultMap.put("movies",movies);
        return resultMap;
    }

    @RequestMapping(value = "/rate/{id}", produces = "application/json", method = RequestMethod.GET)
    public Map<String,Object> rateToMovie( @PathVariable("id") int id, @RequestParam("score") Double score, @RequestParam("username") String username) {
        MultiValueMap<String, Object> valueMap = new LinkedMultiValueMap<>();
        valueMap.add("id", id);
        valueMap.add("score", score);
        valueMap.add("username", username);
        String message = restTemplate.postForObject(REALTIME_URL + "/realtime/rate", valueMap, String.class);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("success",true);
        resultMap.put("message",message);
        return resultMap;

    }

    @RequestMapping(value = "/tag/{mid}", produces = "application/json", method = RequestMethod.GET)
    public Map<String,Object> getMovieTags( @PathVariable("mid") int mid ) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("mid", mid);
        Tag[] tags = restTemplate.getForObject(OFFLINE_URL + "/offline/tag", Tag[].class, paramMap);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("success",true);
        resultMap.put("tags",Arrays.asList(tags));
        return resultMap;

    }

    private Map<String,Object> getModel( Movie[]... moviesArray ) {
        List<Movie> movieList = new ArrayList<>();
        for (Movie[] movies : moviesArray) {
            movieList.addAll(Arrays.asList(movies));
        }
        Map<String,Object> resultMap=new HashMap<>();
        System.out.println(movieList);
        resultMap.put("success", true);
        resultMap.put("movies", movieList);
        return resultMap;
    }

}
