package com.klaus.offlinerecommender.service;

import com.klaus.offlinerecommender.dao.UserDaoImpl;
import com.klaus.offlinerecommender.model.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserDaoImpl userDao;

    public List<User> findAll() {
        return userDao.findAll();
    }
}
