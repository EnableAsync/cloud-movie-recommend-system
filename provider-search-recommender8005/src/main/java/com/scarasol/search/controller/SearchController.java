package com.scarasol.search.controller;

import com.scarasol.search.model.recom.Recommendation;
import com.scarasol.search.model.request.SearchRecommendationRequest;
//import com.scarasol.search.service.MovieService;
//import com.scarasol.search.service.RecommenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("ALL")
@CrossOrigin
@RestController
@RequestMapping("/search")
@Slf4j
public class SearchController {
//
//    @Autowired
//    MovieService movieService;
//
//    @Autowired
//    private RecommenderService recommenderService;
//
//    @RequestMapping(value = "/search", produces = "application/json", method = RequestMethod.GET )
//    public Map<String, Object> getSearchMovies(@RequestParam("query")String query) {
//        List<Recommendation> recommendations = recommenderService.getContentBasedSearchRecommendations(new SearchRecommendationRequest(query,100));
//        Map<String, Object> map = new HashMap<>();
//        map.put("success", true);
//        map.put("movies", movieService.getRecommendeMovies(recommendations));
//        return map;
//    }
}
