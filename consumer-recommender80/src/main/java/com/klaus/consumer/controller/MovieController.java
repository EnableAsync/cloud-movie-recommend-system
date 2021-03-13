package com.klaus.consumer.controller;

import com.klaus.consumer.service.OfflineMovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;


@RequestMapping("/rest/movie")
@CrossOrigin
@RestController
@Slf4j
public class MovieController {


    @Resource
    private OfflineMovieService offlineMovieService;


    @RequestMapping(value = "/hot", produces = "application/json", method = RequestMethod.GET)
    public Map<String, Object> getHotMovies( @RequestParam("num") int num ) {
        return offlineMovieService.getHotMovies(num);
    }


//    @RequestMapping(value = "/guess", produces = "application/json", method = RequestMethod.GET)
//    public Map<String,Object> getGuessMovies( @RequestParam("username") String username, @RequestParam("num") int num, Model model ) {
//
//    }


    @RequestMapping(value = "/wish", produces = "application/json", method = RequestMethod.GET)

    public Map<String, Object> getWishMovies( @RequestParam("username") String username, @RequestParam("num") int num, Model model ) {
        return offlineMovieService.getWishMovies(username, num);
    }


    @RequestMapping(value = "/rate", produces = "application/json", method = RequestMethod.GET)
    public Map<String, Object> getRateMoreMovies( @RequestParam("num") int num ) {
        return offlineMovieService.getRateMoreMovies(num);
    }

    @RequestMapping(value = "/new", produces = "application/json", method = RequestMethod.GET)
    public Map<String, Object> getNewMovies( @RequestParam("num") int num ) {
        return offlineMovieService.getNewMovies(num);
    }


    @RequestMapping(value = "/same/{id}", produces = "application/json", method = RequestMethod.GET)
    public Map<String, Object> getSameMovie( @PathVariable("id") int id, @RequestParam("num") int num, Model model ) {
        return offlineMovieService.getSameMovie(id, num);
    }

    @RequestMapping(value = "/info/{id}", produces = "application/json", method = RequestMethod.GET)
    public Map<String, Object> getMovieInfo( @PathVariable("id") int id ) {
        return offlineMovieService.getMovieInfo(id);
    }


    @RequestMapping(value = "/genres", produces = "application/json", method = RequestMethod.GET)
    public Map<String, Object> getGenresMovies( @RequestParam("category") String category, @RequestParam("num") int num, Model model ) {
        return offlineMovieService.getGenresMovies(category, num);
    }

    @RequestMapping(value = "/topAll", produces = "application/json", method = RequestMethod.GET)
    public Map<String, Object> getTopMovies( @RequestParam("num") int num ) {
        return getTopMovies(num);
    }

//    @RequestMapping(value = "/rate/{id}", produces = "application/json", method = RequestMethod.GET)
//    public Map<String, Object> rateToMovie( @PathVariable("id") int id, @RequestParam("score") Double score, @RequestParam("username") String username, Model model ) {
//
//    }

    @RequestMapping(value = "/tag/{mid}", produces = "application/json", method = RequestMethod.GET)
    public Map<String, Object> getMovieTags( @PathVariable("mid") int mid ) {
        return offlineMovieService.getMovieTags(mid);
    }

}
