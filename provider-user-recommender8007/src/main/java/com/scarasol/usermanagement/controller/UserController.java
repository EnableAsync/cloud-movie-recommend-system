package com.scarasol.usermanagement.controller;


import com.rai.model.domain.User;
import com.scarasol.usermanagement.model.request.LoginUserRequest;
import com.scarasol.usermanagement.model.request.RegisterUserRequest;
import com.scarasol.usermanagement.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", produces = "application/json", method = RequestMethod.GET)
    public Map<String, Object> login(@RequestParam("username") String username, @RequestParam("password") String password) {
        User user = userService.loginUser(new LoginUserRequest(username, password));
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", user != null);
        resultMap.put("user", user);
        return resultMap;
    }

    @RequestMapping(value = "/register", produces = "application/json", method = RequestMethod.GET)
    public Map<String, Object> addUser(@RequestParam("username") String username, @RequestParam("password") String password) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", false);

        if (userService.checkUserExist(username)) {

            resultMap.put("success", false);
        } else {
            resultMap.put("success", userService.registerUser(new RegisterUserRequest(username, password)));
        }
        return resultMap;
    }

    @RequestMapping(value = "/info", produces = "application/json", method = RequestMethod.GET)
    public Map<String, Object> getInfo(@RequestParam("username") String username) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("new", userService.checkNew(username));
        return resultMap;
    }

    //冷启动问题
    @RequestMapping(value = "/pref", produces = "application/json", method = RequestMethod.GET)
    public Map<String, Object> addPrefGenres(@RequestParam("username") String username,@RequestParam("genres") String genres) {
        Map<String, Object> resultMap = new HashMap<>();
        User user = userService.findByUsername(username);
        user.getPrefGenres().addAll(Arrays.asList(genres.split(",")));
        user.setFirst(false);
        resultMap.put("success", userService.updateUser(user));
        return resultMap;
    }
}
