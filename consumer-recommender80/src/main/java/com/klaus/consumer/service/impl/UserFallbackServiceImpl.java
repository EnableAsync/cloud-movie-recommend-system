package com.klaus.consumer.service.impl;

import com.klaus.consumer.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther EnableAsync
 * @Date 2021/3/13
 */

@Service
public class UserFallbackServiceImpl implements UserService {
    public Map<String, Object> fail() {
        Map<String, Object> map = new HashMap<>();
        map.put("success", false);
        map.put("fallback", true);
        return map;
    }

    @Override
    public Map<String, Object> login(String username, String password) {
        return fail();
    }

    @Override
    public Map<String, Object> addUser(String username, String password) {
        return fail();
    }

    @Override
    public Map<String, Object> getInfo(String username) {
        return fail();
    }

    @Override
    public Map<String, Object> addPrefGenres(String username, String genres) {
        return fail();
    }
}
