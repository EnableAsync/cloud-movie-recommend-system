package com.klaus.offlinerecommender.controller;

import com.klaus.offlinerecommender.model.domain.Movie;
import com.klaus.offlinerecommender.model.domain.User;
import com.klaus.offlinerecommender.service.MovieService;
import com.klaus.offlinerecommender.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class Controller {
    @Autowired
    MovieService movieService;

    @Autowired
    UserService userService;

    @RequestMapping("/findAllMovies")
    public String findAllMovies() {
        List<Movie> allMovies = movieService.findAll();
        for(Movie movie:allMovies)
            System.out.println(movie);
        return "sucdess";
    }


    @RequestMapping("/findAllUsers")
    public String findAllUsers(){
        List<User> allUsers =userService.findAll();
        for (User user : allUsers)
            System.out.println(user);
        return "success";
    }

    // -----------------------------------------------------------



}
