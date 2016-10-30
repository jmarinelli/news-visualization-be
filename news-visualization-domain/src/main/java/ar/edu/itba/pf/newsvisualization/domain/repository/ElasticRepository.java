package ar.edu.itba.pf.newsvisualization.domain.repository;

import ar.edu.itba.pf.newsvisualization.domain.model.request.TrendRequest;
import ar.edu.itba.pf.newsvisualization.domain.model.request.WordCloudRequest;
import ar.edu.itba.pf.newsvisualization.domain.model.response.main.TrendResponse;
import ar.edu.itba.pf.newsvisualization.domain.model.response.main.WordCloudResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.google.common.collect.Lists;
import com.google.common.io.Resources;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupDir;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    public List<List<String>> getTop(String from, String to, List<String> keywords, String media,
                                        Integer limit, Integer offset) {
        List<List<String>> ret = Lists.newLinkedList();
        ST titles = loadTemplate("top");

        titles.add("from", from);
        titles.add("to", to);
        titles.add("media", media);
        titles.add("keywords", keywords);
        titles.add("limit", limit);
        titles.add("offset", offset);

        try {
            HttpResponse<String> response = Unirest.post(BASE_URL).body(titles.render()).asString();


            JSONArray hits = new JSONObject(response.getBody()).getJSONObject("hits").getJSONArray("hits");
            for (int i = 0; i < hits.length(); i++) {
                List<String> obj = Lists.newLinkedList();
                JSONObject hit = hits.getJSONObject(i).getJSONObject("_source");

                obj.add(hit.getString("title"));
                obj.add(hit.getString("id"));
                obj.add(hit.getString("tmp"));
                obj.add(String.valueOf(hit.optInt("fb_like_count", 0)));

                ret.add(obj);
            }

            return ret;
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<List<String>> getTitles(String from, String to, List<String> keywords, String media,
                                        Integer limit, Integer offset) {
        List<List<String>> ret = Lists.newLinkedList();
        ST titles = loadTemplate("titles");

        titles.add("from", from);
        titles.add("to", to);
        titles.add("media", media);
        titles.add("keywords", keywords);
        titles.add("limit", limit);
        titles.add("offset", offset);

        try {
            HttpResponse<String> response = Unirest.post(BASE_URL).body(titles.render()).asString();


            JSONArray hits = new JSONObject(response.getBody()).getJSONObject("hits").getJSONArray("hits");
            for (int i = 0; i < hits.length(); i++) {
                List<String> obj = Lists.newLinkedList();
                JSONObject hit = hits.getJSONObject(i).getJSONObject("_source");

                obj.add(hit.getString("title"));
                obj.add(hit.getString("id"));
                obj.add(hit.getString("summary"));
                obj.add("Google News");
                obj.add("http://www.google.com/images/branding/product/ico/googleg_lodp.ico");
                obj.add(String.valueOf(hit.optInt("fb_like_count", 0)));
                obj.add(String.valueOf(hit.optInt("retweet_count", 0)));
                obj.add(hit.getString("fecha"));

                ret.add(obj);
            }

            return ret;
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        return null;
    }

    private ST loadTemplate(String template) {
        try {
            URL url = getClass().getResource(template);
            return new ST(Resources.toString(url, Charset.defaultCharset()), '$', '$');
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
