package com.klaus.consumer.service;

import com.klaus.consumer.domain.Movie;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(value = "http://localhost:8001")
public interface MovieFeignService {
    @RequestMapping(value = "/offline/hot", produces = "application/json", method = RequestMethod.GET)
    Movie[] getHotMovies( @RequestParam("num") int num );
}
