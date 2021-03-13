package com.klaus.consumer.service.impl;
import com.klaus.consumer.domain.Movie;
import com.klaus.consumer.service.OfflineMovieService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther EnableAsync
 * @Date 2021/3/13
 */

@Service
public class OfficeMovieFallbackServiceImpl implements OfflineMovieService {
    public Map<String, Object> fail() {
        Map<String, Object> map = new HashMap<>();
        map.put("success", false);
        map.put("movies", new ArrayList<Movie>());
        map.put("fallback", true);
        return map;
    }

    @Override
    public Map<String, Object> getHotMovies(int num) {
        return fail();
    }

    @Override
    public Map<String, Object> getWishMovies(String username, int num) {
        return null;
    }

    @Override
    public Map<String, Object> getRateMoreMovies(int num) {
        return fail();
    }

    @Override
    public Map<String, Object> getNewMovies(int num) {
        return fail();
    }

    @Override
    public Map<String, Object> getSameMovie(int id, int num) {
        return fail();
    }

    @Override
    public Map<String, Object> getMovieInfo(int id) {
        return fail();
    }

    @Override
    public Map<String, Object> getGenresMovies(String category, int num) {
        return fail();
    }

    @Override
    public Map<String, Object> getTopMovies(int num) {
        return fail();
    }

    @Override
    public Map<String, Object> getMovieTags(int mid) {
        return fail();
    }
}
