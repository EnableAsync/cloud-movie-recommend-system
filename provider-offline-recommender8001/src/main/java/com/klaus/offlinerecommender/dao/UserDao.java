package com.klaus.offlinerecommender.dao;

import com.klaus.offlinerecommender.model.domain.User;

import java.util.List;

public interface UserDao {
    List<User> findAll();
}
