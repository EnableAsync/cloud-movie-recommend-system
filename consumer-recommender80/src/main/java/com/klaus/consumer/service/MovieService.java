package com.klaus.consumer.service;

import com.klaus.consumer.domain.Movie;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

import static com.klaus.consumer.utils.Constant.OFFLINE_URL;

/**
 * @Auther EnableAsync
 * @Date 2021/3/11
 */
public class MovieService {

    @Resource
    private RestTemplate restTemplate;


}
