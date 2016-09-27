package ar.edu.itba.pf.newsvisualization.domain.repository;

import ar.edu.itba.pf.newsvisualization.domain.model.request.WordCloudRequest;
import ar.edu.itba.pf.newsvisualization.domain.model.response.WordCloudResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.stereotype.Repository;

import java.io.IOException;

/**
 * Created by juanjosemarinelli on 9/20/16.
 */
@Repository
public class ElasticRepository {

    private static final String BASE_URL = "https://fxj6i0pf:g3e97ijddwmmizu4@jasmine-1526871.us-east-1.bonsai.io/jdbc/jdbc/_search";

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
}
