package com.klaus.consumer.controller;

import com.klaus.consumer.domain.Movie;
import com.klaus.consumer.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

import static com.klaus.consumer.utils.Constant.*;

@SuppressWarnings("ALL")
@RequestMapping("/rest/users")
@CrossOrigin
@RestController
@Slf4j
public class UserController {

    @Resource
    private RestTemplate restTemplate;

    @RequestMapping(value = "/login", produces = "application/json", method = RequestMethod.GET)
    public Model login( @RequestParam("username") String username, @RequestParam("password") String password, Model model ) {
        MultiValueMap<String, Object> valueMap = new LinkedMultiValueMap<>();
        valueMap.add("username", username);
        valueMap.add("password", password);


        User user = restTemplate.postForObject(USER_URL + "/login", valueMap, User.class);
        model.addAttribute("success", user != null);
        model.addAttribute("user", user);
        return model;
    }

    @RequestMapping(value = "/register", produces = "application/json", method = RequestMethod.GET)
    public Model addUser( @RequestParam("username") String username, @RequestParam("password") String password, Model model ) {
        MultiValueMap<String, Object> valueMap = new LinkedMultiValueMap<>();
        valueMap.add("username", username);
        valueMap.add("password", password);


        Boolean res = restTemplate.postForObject(USER_URL + "/register", valueMap, Boolean.class);
        if (res) {
            model.addAttribute("success", res);
        } else {
            model.addAttribute("success", false);
            model.addAttribute("message", "用户名已经被注册！");
        }
        return model;
    }

    //冷启动问题
    @RequestMapping(value = "/pref", produces = "application/json", method = RequestMethod.GET)

    public Model addPrefGenres( @RequestParam("username") String username, @RequestParam("genres") String genres, Model model ) {
        MultiValueMap<String, Object> valueMap = new LinkedMultiValueMap<>();
        valueMap.add("username", username);
        valueMap.add("genres", genres);


        Boolean res = restTemplate.postForObject(USER_URL + "/pref", valueMap, Boolean.class);
        model.addAttribute("success", res);
        return model;
    }

    @RequestMapping(value = "/info", produces = "application/json", method = RequestMethod.GET)
    @ResponseBody
    public Model getInfo( @RequestParam("username") String username, Model model ) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("username", username);
        restTemplate.getForObject(USER_URL + "/info", Movie[].class, paramMap);
        return model;
    }
}