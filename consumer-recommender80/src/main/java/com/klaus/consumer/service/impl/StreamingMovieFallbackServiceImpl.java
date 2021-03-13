package com.klaus.consumer.service.impl;

import com.klaus.consumer.service.StreamingMovieService;
import com.rai.model.domain.Movie;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther EnableAsync
 * @Date 2021/3/13
 */
@Service
public class StreamingMovieFallbackServiceImpl implements StreamingMovieService {
    public Map<String, Object> fail() {
        Map<String, Object> map = new HashMap<>();
        map.put("success", false);
        map.put("movies", new ArrayList<Movie>());
        map.put("fallback", true);
        return map;
    }

    @Override
    public Map<String, Object> getStreamMovies(String username, int num) {
        return fail();
    }

    @Override
    public Map<String, Object> rateToMovie(int id, Double score, String username) {
        return fail();
    }
}
