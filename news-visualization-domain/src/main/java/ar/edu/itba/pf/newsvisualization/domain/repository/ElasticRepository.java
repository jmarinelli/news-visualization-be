package ar.edu.itba.pf.newsvisualization.domain.repository;

import ar.edu.itba.pf.newsvisualization.domain.model.request.TrendRequest;
import ar.edu.itba.pf.newsvisualization.domain.model.request.WordCloudRequest;
import ar.edu.itba.pf.newsvisualization.domain.model.response.main.TrendResponse;
import ar.edu.itba.pf.newsvisualization.domain.model.response.main.WordCloudResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

/**
 * Created by juanjosemarinelli on 9/20/16.
 */
@Repository
public class ElasticRepository {

    private static final String BASE_URL = "http://localhost:9200/jdbc/jdbc/_search";

    public ElasticRepository() {
        final com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
                = new com.fasterxml.jackson.databind.ObjectMapper();

        jacksonObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Unirest.setObjectMapper(new ObjectMapper() {

            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return jacksonObjectMapper.readValue(value, valueType);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public String writeValue(Object value) {
                try {
                    return jacksonObjectMapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public WordCloudResponse getWordCount(String from, String to) {
        WordCloudRequest requestBody = new WordCloudRequest(from, to);

        try {
            HttpResponse<WordCloudResponse> response = Unirest.post(BASE_URL).body(requestBody).asObject(WordCloudResponse.class);

            return response.getBody();
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        return null;
    }

    public TrendResponse getTrends(List<String> terms, String from, String to) {
        TrendRequest requestBody = new TrendRequest(terms, from, to);

        try {
            HttpResponse<TrendResponse> response = Unirest.post(BASE_URL).body(requestBody).asObject(TrendResponse.class);

            return response.getBody();
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        return null;
    }
}
