package com.rai.streamingcommender.controller;

import com.rai.model.domain.User;
import com.rai.model.recom.Recommendation;
import com.rai.streamingcommender.request.MovieRatingRequest;
import com.rai.streamingcommender.request.RateMoreRecommendationRequest;
import com.rai.streamingcommender.service.*;
import com.rai.utils.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther EnableAsync
 * @Date 2021/3/13
 */
@CrossOrigin
@RestController
@RequestMapping("/streaming")
@Slf4j
public class StreamingController {

    @Resource
    private UserService userService;

    @Resource
    private RecommenderService recommenderService;

    @Resource
    private MovieService movieService;

    @Resource
    private RatingService ratingService;

    @Resource
    private KafkaService kafkaService;

    @RequestMapping(value = "/stream", produces = "application/json", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getStreamMovies(@RequestParam("username") String username, @RequestParam("num") int num) {
        User user = userService.findByUsername(username);
        List<Recommendation> recommendations = recommenderService.findStreamRecs(user.getUid(), num);
        Map<String, Object> map = new HashMap<>();
        map.put("movies", movieService.getHybirdRecommendeMovies(recommendations));
        return map;
    }


    @RequestMapping(value = "/rate/{id}", produces = "application/json", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> rateToMovie(@PathVariable("id") int id, @RequestParam("score") Double score, @RequestParam("username") String username) {
        User user = userService.findByUsername(username);
        MovieRatingRequest request = new MovieRatingRequest(user.getUid(), id, score);
        boolean complete = ratingService.movieRating(request);
        //埋点日志
        if (complete) {
            kafkaService.sendMessage(user.getUid() + "|" + id + "|" + request.getScore() + "|" + System.currentTimeMillis() / 1000);
            System.out.print("=========complete=========");
            log.info(Constant.MOVIE_RATING_PREFIX + ":" + user.getUid() + "|" + id + "|" + request.getScore() + "|" + System.currentTimeMillis() / 1000);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        map.put("message", " 已完成评分！");
        return map;
    }
}
