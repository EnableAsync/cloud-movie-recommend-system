package com.klaus.consumer.service;

import com.klaus.consumer.service.impl.OfficeMovieFallbackServiceImpl;
import com.klaus.consumer.service.impl.StreamingMovieFallbackServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Auther EnableAsync
 * @Date 2021/3/13
 */
@Component
@FeignClient(value = "cloud-streaming-recommender-service", path = "/streaming", fallback = StreamingMovieFallbackServiceImpl.class)
public interface StreamingMovieService {
    @RequestMapping(value = "/stream", produces = "application/json", method = RequestMethod.GET)
    @ResponseBody
    Map<String, Object> getStreamMovies(@RequestParam("username") String username, @RequestParam("num") int num);

    @RequestMapping(value = "/rate/{id}", produces = "application/json", method = RequestMethod.GET)
    @ResponseBody
    Map<String, Object> rateToMovie(@PathVariable("id") int id, @RequestParam("score") Double score, @RequestParam("username") String username);
}
