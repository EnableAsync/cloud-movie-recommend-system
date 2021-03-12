package com.klaus.offlinerecommender.service;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.klaus.offlinerecommender.model.domain.User;
import com.klaus.offlinerecommender.utils.Constant;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.util.JSON;
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




    private User documentToUser( Document document) {
        try {
            return objectMapper.readValue(JSON.serialize(document), User.class);
        } catch (JsonParseException e) {
            e.printStackTrace();
            return null;
        } catch (JsonMappingException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean checkUserExist(String username) {
        return null != findByUsername(username);
    }

    public User findByUsername(String username) {
        Document user = getUserCollection().find(new Document("username", username)).first();
        if (null == user || user.isEmpty())
            return null;
        return documentToUser(user);
    }

    public boolean updateUser( User user) {
        getUserCollection().updateOne(Filters.eq("uid", user.getUid()), new Document().append("$set", new Document("first", user.isFirst())));
        getUserCollection().updateOne(Filters.eq("uid", user.getUid()), new Document().append("$set", new Document("prefGenres", user.getPrefGenres())));
        return true;
    }

    public boolean checkNew(String username) {
        User user = findByUsername(username);
        if (null == user) {
            return true;
        }
        return user.getPrefGenres().size() == 0;
    }
}
