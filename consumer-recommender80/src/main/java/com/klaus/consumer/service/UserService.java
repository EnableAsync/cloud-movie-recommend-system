package com.klaus.consumer.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Component
@FeignClient(value = "cloud-user-service", path = "/user")
public interface UserService {
    @RequestMapping(value = "/login", produces = "application/json", method = RequestMethod.GET)
    Map<String, Object> login( @RequestParam("username") String username, @RequestParam("password") String password );


    @RequestMapping(value = "/register", produces = "application/json", method = RequestMethod.GET)
    Map<String, Object> addUser( @RequestParam("username") String username, @RequestParam("password") String password );

    @RequestMapping(value = "/info", produces = "application/json", method = RequestMethod.GET)
    Map<String, Object> getInfo( @RequestParam("username") String username );

    @RequestMapping(value = "/pref", produces = "application/json", method = RequestMethod.GET)
    Map<String, Object> addPrefGenres( @RequestParam("username") String username, @RequestParam("genres") String genres );
}
