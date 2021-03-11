package com.klaus.offlinerecommender.dao;

import com.klaus.offlinerecommender.model.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDaoImpl implements UserDao{

    @Autowired
    MongoTemplate mongoTemplate;


    public List<User> findAll(){
        return mongoTemplate.findAll(User.class);
    }
}
