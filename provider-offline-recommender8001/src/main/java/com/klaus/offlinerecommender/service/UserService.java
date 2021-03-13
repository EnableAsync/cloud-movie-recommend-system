package com.klaus.offlinerecommender.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.klaus.offlinerecommender.utils.Constant;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.util.JSON;
import com.rai.model.domain.User;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UserService {

    @Autowired
    private MongoClient mongoClient;
    @Autowired
    private ObjectMapper objectMapper;

    private MongoCollection<Document> userCollection;

    private MongoCollection<Document> getUserCollection() {
        if (null == userCollection)
            userCollection = mongoClient.getDatabase(Constant.MONGODB_DATABASE).getCollection(Constant.MONGODB_USER_COLLECTION);
        return userCollection;
    }


    private User documentToUser(Document document) {
        try {
            return objectMapper.readValue(JSON.serialize(document), User.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public User findByUsername(String username) {
        Document user = getUserCollection().find(new Document("username", username)).first();
        if (null == user || user.isEmpty())
            return null;
        return documentToUser(user);
    }

}
