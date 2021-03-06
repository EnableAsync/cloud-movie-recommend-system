package com.klaus.offlinerecommender.service;


import com.klaus.offlinerecommender.model.request.*;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.rai.model.recom.Recommendation;
import com.rai.utils.Constant;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


@SuppressWarnings("ALL")
@Service
public class RecommenderService {

    // 混合推荐中CF的比例
    private static Double CF_RATING_FACTOR = 0.3;
    private static Double CB_RATING_FACTOR = 0.3;
    private static Double SR_RATING_FACTOR = 0.4;

    @Autowired
    private MongoClient mongoClient;


    // 协同过滤推荐【电影相似性】
    private List<Recommendation> findMovieCFRecs( int mid, int maxItems ) {
        MongoCollection<Document> movieRecsCollection = mongoClient.getDatabase(Constant.MONGODB_DATABASE).getCollection(Constant.MONGODB_MOVIE_RECS_COLLECTION);
        Document movieRecs = movieRecsCollection.find(new Document("mid", mid)).first();
        return parseRecs(movieRecs, maxItems);
    }

    // 协同过滤推荐【用户电影矩阵】
    private List<Recommendation> findUserCFRecs(int uid, int maxItems ) {
        MongoCollection<Document> movieRecsCollection = mongoClient.getDatabase(Constant.MONGODB_DATABASE).getCollection(Constant.MONGODB_USER_RECS_COLLECTION);
        Document userRecs = movieRecsCollection.find(new Document("uid", uid)).first();
        return parseRecs(userRecs, maxItems);
    }

    private List<Recommendation> findContentByMongoDb(int mid, int maxItems) {
        MongoCollection<Document> contentCollection = mongoClient.getDatabase(Constant.MONGODB_DATABASE).getCollection(Constant.MONGODB_CONTENT_MOVIE_RECS);
        Document contentRecs = contentCollection.find(new Document("mid", mid)).first();
        return parseRecs(contentRecs, maxItems);
    }


    private List<Recommendation> parseRecs( Document document, int maxItems ) {
        List<Recommendation> recommendations = new ArrayList<>();
        if (null == document || document.isEmpty())
            return recommendations;
        ArrayList<Document> recs = document.get("recs", ArrayList.class);
        for (Document recDoc : recs) {
            recommendations.add(new Recommendation(recDoc.getInteger("mid"), recDoc.getDouble("score")));
        }
        Collections.sort(recommendations, new Comparator<Recommendation>() {
            @Override
            public int compare( Recommendation o1, Recommendation o2 ) {
                return o1.getScore() > o2.getScore() ? -1 : 1;
            }
        });
        return recommendations.subList(0, maxItems > recommendations.size() ? recommendations.size() : maxItems);
    }



    // 混合推荐算法
    private List<Recommendation> findHybridRecommendations( int productId, int maxItems ) {
        List<Recommendation> hybridRecommendations = new ArrayList<>();

        List<Recommendation> cfRecs = findMovieCFRecs(productId, maxItems);
        for (Recommendation recommendation : cfRecs) {
            hybridRecommendations.add(new Recommendation(recommendation.getMid(), recommendation.getScore() * CF_RATING_FACTOR));
        }

        List<Recommendation> cbRecs = findContentByMongoDb(productId, maxItems);
        System.out.println(cbRecs);
        for (Recommendation recommendation : cbRecs) {
            hybridRecommendations.add(new Recommendation(recommendation.getMid(), recommendation.getScore() * CB_RATING_FACTOR));
        }

        Collections.sort(hybridRecommendations, new Comparator<Recommendation>() {
            @Override
            public int compare( Recommendation o1, Recommendation o2 ) {
                return o1.getScore() > o2.getScore() ? -1 : 1;
            }
        });
        return hybridRecommendations.subList(0, maxItems > hybridRecommendations.size() ? hybridRecommendations.size() : maxItems);
    }


    public List<Recommendation> getCollaborativeFilteringRecommendations( MovieRecommendationRequest request ) {
        return findMovieCFRecs(request.getMid(), request.getSum());
    }

    public List<Recommendation> getCollaborativeFilteringRecommendations( UserRecommendationRequest request ) {

        return findUserCFRecs(request.getUid(), request.getSum());
    }

    public List<Recommendation> getContentBasedMoreLikeThisRecommendations( MovieRecommendationRequest request ) {
//        return findContentBasedMoreLikeThisRecommendations(request.getMid(), request.getSum());
        return findContentByMongoDb(request.getMid(), request.getSum());
    }

    public List<Recommendation> getHybridRecommendations( MovieHybridRecommendationRequest request ) {
        return findHybridRecommendations(request.getMid(), request.getSum());
    }


    public List<Recommendation> getHotRecommendations( HotRecommendationRequest request ) {
        // 获取热门电影的条目
        MongoCollection<Document> rateMoreMoviesRecentlyCollection = mongoClient.getDatabase(Constant.MONGODB_DATABASE).getCollection(Constant.MONGODB_RATE_MORE_MOVIES_RECENTLY_COLLECTION);
        FindIterable<Document> documents = rateMoreMoviesRecentlyCollection.find().sort(Sorts.descending("yearmonth")).limit(request.getSum());

        List<Recommendation> recommendations = new ArrayList<>();
        for (Document document : documents) {
            Recommendation recomm = new Recommendation(document.getInteger("mid"), 0D);
            recommendations.add(new Recommendation(document.getInteger("mid"), 0D));
        }

        return recommendations;
    }

    public List<Recommendation> getRateMoreRecommendations( RateMoreRecommendationRequest request ) {

        // 获取评分最多电影的条目
        MongoCollection<Document> rateMoreMoviesCollection = mongoClient.getDatabase(Constant.MONGODB_DATABASE).getCollection(Constant.MONGODB_RATE_MORE_MOVIES_COLLECTION);
        FindIterable<Document> documents = rateMoreMoviesCollection.find().sort(Sorts.descending("count")).limit(request.getSum());

        List<Recommendation> recommendations = new ArrayList<>();
        for (Document document : documents) {
            recommendations.add(new Recommendation(document.getInteger("mid"), 0D));
        }
        return recommendations;
    }

    public List<Recommendation> getTopGenresRecommendations( TopGenresRecommendationRequest request ) {

        MongoCollection<Document> genresTopMovies = mongoClient.getDatabase(Constant.MONGODB_DATABASE).getCollection(Constant.MONGODB_GENRES_TOP_MOVIES_COLLECTION);
        FindIterable<Document> documents = genresTopMovies.find(Filters.eq("genres", request.getGenres()));
        List<Recommendation> recommendations = new ArrayList<>();
        for (Document document : documents) {
            List<Document> recs = (List<Document>) document.get("recs");
            for (Document rec : recs) {
                recommendations.add(new Recommendation(rec.getInteger("mid"), rec.getDouble("score")));
            }
        }

        return recommendations;
    }

    public List<Recommendation> getTopAllMovies( TopAllMoviesRequest request ) {
        MongoCollection<Document> genresTopMovies = mongoClient.getDatabase(Constant.MONGODB_DATABASE).getCollection(Constant.MONGODB_AVERAGE_MOVIES_SCORE_COLLECTION);
        FindIterable<Document> documents = genresTopMovies.find().sort(Sorts.descending("avg")).limit(request.getNum());
        System.out.println(request.getNum());
        List<Recommendation> recommendations = new ArrayList<>();
        for (Document document : documents) {
            recommendations.add(new Recommendation(document.getInteger("mid"), 0D));
        }
        return recommendations;
    }
}