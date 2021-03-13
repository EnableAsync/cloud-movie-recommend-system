package com.klaus.consumer.controller;

import com.klaus.consumer.service.OfflineUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;


@RequestMapping("/rest/users")
@CrossOrigin
@RestController
@Slf4j
public class UserController {

    @Resource
    private OfflineUserService offlineUserService;


    @RequestMapping(value = "/login", produces = "application/json", method = RequestMethod.GET)
    public Map<String,Object> login( @RequestParam("username") String username, @RequestParam("password") String password) {
        return offlineUserService.login(username, password);
    }

    @RequestMapping(value = "/register", produces = "application/json", method = RequestMethod.GET)
    public Map<String,Object> addUser( @RequestParam("username") String username, @RequestParam("password") String password) {
        return offlineUserService.addUser(username, password);
    }

    //冷启动问题
    @RequestMapping(value = "/pref", produces = "application/json", method = RequestMethod.GET)
    public Map<String,Object> addPrefGenres( @RequestParam("username") String username, @RequestParam("genres") String genres) {
        return offlineUserService.addPrefGenres(username, genres);
    }

    @RequestMapping(value = "/info", produces = "application/json", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getInfo( @RequestParam("username") String username) {
        return offlineUserService.getInfo(username);
    }
}