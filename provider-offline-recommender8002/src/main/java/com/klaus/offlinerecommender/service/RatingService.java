package com.klaus.offlinerecommender.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.klaus.offlinerecommender.model.domain.Rating;
import com.klaus.offlinerecommender.model.domain.User;
import com.klaus.offlinerecommender.utils.Constant;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.util.JSON;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.IOException;

@Service
public class RatingService {

    @Autowired
    private MongoClient mongoClient;

    @Autowired
    private ObjectMapper objectMapper;


    private MongoCollection<Document> ratingCollection;

    private MongoCollection<Document> getRatingCollection() {
        if (null == ratingCollection)
            ratingCollection = mongoClient.getDatabase(Constant.MONGODB_DATABASE).getCollection(Constant.MONGODB_RATING_COLLECTION);
        return ratingCollection;
    }

    private Rating documentToRating( Document document) {
        Rating rating = null;
        try {
            rating = objectMapper.readValue(JSON.serialize(document), Rating.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rating;

    }


    public boolean newRating(Rating rating) {
        try {
            getRatingCollection().insertOne(Document.parse(objectMapper.writeValueAsString(rating)));
            return true;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean ratingExist(int uid, int mid) {
        return null != findRating(uid, mid);
    }

    public boolean updateRating(Rating rating) {
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.append("uid", rating.getUid());
        basicDBObject.append("mid", rating.getMid());
        getRatingCollection().updateOne(basicDBObject,
                new Document().append("$set", new Document("score", rating.getScore())));
        return true;
    }


    public Rating findRating(int uid, int mid) {
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.append("uid", uid);
        basicDBObject.append("mid", mid);
        FindIterable<Document> documents = getRatingCollection().find(basicDBObject);
        if (documents.first() == null)
            return null;
        return documentToRating(documents.first());
    }

    public void removeRating(int uid, int mid) {
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.append("uid", uid);
        basicDBObject.append("mid", mid);
        getRatingCollection().deleteOne(basicDBObject);
    }

    public int[] getMyRatingStat( User user) {
        FindIterable<Document> documents = getRatingCollection().find(new Document("uid", user.getUid()));
        int[] stats = new int[10];
        for (Document document : documents) {
            Rating rating = documentToRating(document);
            Long index = Math.round(rating.getScore() / 0.5);
            stats[index.intValue()] = stats[index.intValue()] + 1;
        }
        return stats;
    }

}
