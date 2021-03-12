package com.scarasol.usermanagement.controller;


import com.scarasol.usermanagement.model.domain.User;
import com.scarasol.usermanagement.model.request.LoginUserRequest;
import com.scarasol.usermanagement.model.request.RegisterUserRequest;
import com.scarasol.usermanagement.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
@CrossOrigin
@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", produces = "application/json", method = RequestMethod.GET )
    @ResponseBody
    public User login(@RequestParam("username") String username, @RequestParam("password") String password, Model model) {
        User user  =userService.loginUser(new LoginUserRequest(username,password));
//        model.addAttribute("success",user != null);
//        model.addAttribute("user",user);
        return user;
    }

    @RequestMapping(value = "/register", produces = "application/json", method = RequestMethod.GET)
    @ResponseBody
    public boolean addUser(@RequestParam("username") String username,@RequestParam("password") String password,Model model) {
        if(userService.checkUserExist(username)){

            return false;
        }
        model.addAttribute("success",userService.registerUser(new RegisterUserRequest(username,password)));
        return true;
    }
}
