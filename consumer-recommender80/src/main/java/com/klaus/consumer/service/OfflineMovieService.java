package com.klaus.consumer.service;

import com.klaus.consumer.service.impl.OfficeMovieFallbackServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Component
@FeignClient(value = "cloud-offline-recommender-service", path = "/offline", fallback = OfficeMovieFallbackServiceImpl.class)
public interface OfflineMovieService {


    @RequestMapping(value = "/guess", produces = "application/json", method = RequestMethod.GET)
    public Map<String, Object> getGuessMovies(@RequestParam("username") String username, @RequestParam("num") int num);

    @RequestMapping(value = "/hot", produces = "application/json", method = RequestMethod.GET)
    Map<String, Object> getHotMovies(@RequestParam("num") int num);

    @RequestMapping(value = "/wish", produces = "application/json", method = RequestMethod.GET)
    Map<String, Object> getWishMovies(@RequestParam("username") String username, @RequestParam("num") int num);

    @RequestMapping(value = "/rate", produces = "application/json", method = RequestMethod.GET)
    Map<String, Object> getRateMoreMovies(@RequestParam("num") int num);

    @RequestMapping(value = "/new", produces = "application/json", method = RequestMethod.GET)
    Map<String, Object> getNewMovies(@RequestParam("num") int num);


    @RequestMapping(value = "/same/{id}", produces = "application/json", method = RequestMethod.GET)
    Map<String, Object> getSameMovie(@PathVariable("id") int id, @RequestParam("num") int num);

    @RequestMapping(value = "/info/{id}", produces = "application/json", method = RequestMethod.GET)
    Map<String, Object> getMovieInfo(@PathVariable("id") int id);

    @RequestMapping(value = "/genres", produces = "application/json", method = RequestMethod.GET)
    Map<String, Object> getGenresMovies(@RequestParam("category") String category, @RequestParam("num") int num);

    @RequestMapping(value = "/topAll", produces = "application/json", method = RequestMethod.GET)
    Map<String, Object> getTopMovies(@RequestParam("num") int num);

    @RequestMapping(value = "/tag/{mid}", produces = "application/json", method = RequestMethod.GET)
    Map<String, Object> getMovieTags(@PathVariable("mid") int mid);

}