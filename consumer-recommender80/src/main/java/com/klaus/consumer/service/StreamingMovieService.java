package com.klaus.consumer.service;

import com.klaus.consumer.service.impl.OfficeMovieFallbackServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @Auther EnableAsync
 * @Date 2021/3/13
 */
@Component
@FeignClient(value = "cloud-streaming-recommender-service",path = "/streaming")
public interface StreamingMovieService {

}
