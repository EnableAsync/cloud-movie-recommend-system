package com.klaus.offlinerecommender.service;

import com.klaus.offlinerecommender.model.domain.Tag;
import com.klaus.offlinerecommender.utils.Constant;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.util.JSON;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TagService {

    @Autowired
    private MongoClient mongoClient;
    @Autowired
    private ObjectMapper objectMapper;


    private MongoCollection<Document> tagCollection;

    private MongoCollection<Document> getTagCollection() {
        if (null == tagCollection)
            tagCollection = mongoClient.getDatabase(Constant.MONGODB_DATABASE).getCollection(Constant.MONGODB_TAG_COLLECTION);
        return tagCollection;
    }

    private Tag documentToTag( Document document ) {
        try {
            return objectMapper.readValue(JSON.serialize(document), Tag.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public List<Tag> findMovieTags( int mid ) {
        List<Tag> tags = new ArrayList<>();
        FindIterable<Document> documents = getTagCollection().find(new Document("mid", mid));
        for (Document document : documents) {
            tags.add(documentToTag(document));
        }
        return tags;
    }

    public List<Tag> findMyMovieTags( int uid, int mid ) {
        List<Tag> tags = new ArrayList<>();
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.append("uid", uid);
        basicDBObject.append("mid", mid);
        FindIterable<Document> documents = getTagCollection().find(basicDBObject);
        for (Document document : documents) {
            tags.add(documentToTag(document));
        }
        return tags;
    }

    public void removeTag( int eid ) {
        getTagCollection().deleteOne(new Document("eid", eid));
    }


    public Tag[] tagList2Array(List<Tag> movieList){
        return movieList.toArray(new Tag[0]);
    }

}
