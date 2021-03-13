package com.scarasol.search.service;

import com.scarasol.search.model.recom.Recommendation;
import com.scarasol.search.model.request.SearchRecommendationRequest;
import com.scarasol.search.utils.Constant;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecommenderService {

    @Autowired
    private TransportClient esClient;

    // 全文检索
    private List<Recommendation> findContentBasedSearchRecommendations(String text, int maxItems ) {
        MultiMatchQueryBuilder query = QueryBuilders.multiMatchQuery(text, "name", "descri");
        return parseESResponse(esClient.prepareSearch().setIndices(Constant.ES_INDEX).setTypes(Constant.ES_MOVIE_TYPE).setQuery(query).setSize(maxItems).execute().actionGet());
    }

    private List<Recommendation> parseESResponse( SearchResponse response ) {
        List<Recommendation> recommendations = new ArrayList<>();
        for (SearchHit hit : response.getHits()) {
            recommendations.add(new Recommendation((int) hit.getSourceAsMap().get("mid"), (double) hit.getScore()));
        }
        return recommendations;
    }

    public List<Recommendation> getContentBasedSearchRecommendations( SearchRecommendationRequest request ) {
        return findContentBasedSearchRecommendations(request.getText(), request.getSum());
    }
}
