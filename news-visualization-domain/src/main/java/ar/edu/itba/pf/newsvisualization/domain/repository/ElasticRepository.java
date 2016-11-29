package ar.edu.itba.pf.newsvisualization.domain.repository;

import ar.edu.itba.pf.newsvisualization.domain.model.TrendResponse;
import ar.edu.itba.pf.newsvisualization.domain.model.WordCloudResponse;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.stringtemplate.v4.ST;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by juanjosemarinelli on 9/20/16.
 */
public class ElasticRepository {

    private final String googleLogo;
    private final String searchUrl;

    public ElasticRepository(String baseUrl, String googleLogo) {
        this.googleLogo = googleLogo;
        this.searchUrl =  baseUrl + "/_search";
    }

    public WordCloudResponse getWordCount(String from, String to, List<String> keywords, Integer limit) {
        if (to == null) to = from;

        ST wordCloud = loadTemplate("word-cloud");

        wordCloud.add("from", from);
        wordCloud.add("to", to);
        wordCloud.add("keywords", keywords);
        wordCloud.add("limit", limit);

        try {
            HttpResponse<WordCloudResponse> response = Unirest.post(searchUrl).body(wordCloud.render()).asObject(WordCloudResponse.class);

            return response.getBody();
        } catch (UnirestException e) {
            e.printStackTrace();
            return null;
        }
    }

    public TrendResponse getTrends(List<String> terms, String from, String to) {
        if (to == null) to = from;

        ST requestBody = loadTemplate("trend");

        requestBody.add("from", from);
        requestBody.add("to", to);
        requestBody.add("keywords", terms);

        try {
            System.out.println(requestBody.render());


            HttpResponse<TrendResponse> response = Unirest.post(searchUrl).body(requestBody.render()).asObject(TrendResponse.class);

            return response.getBody();
        } catch (UnirestException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<List<String>> getTitles(String from, String to, List<String> keywords,
                                        Integer limit, Integer offset) {
        if (to == null) to = from;

        List<List<String>> ret = Lists.newLinkedList();
        ST titles = loadTemplate("titles");

        titles.add("from", from);
        titles.add("to", to);
        titles.add("keywords", keywords);
        titles.add("limit", limit);
        titles.add("offset", offset);

        try {
            HttpResponse<String> response = Unirest.post(searchUrl).body(titles.render()).asString();


            JSONArray hits = new JSONObject(response.getBody()).getJSONObject("hits").getJSONArray("hits");
            for (int i = 0; i < hits.length(); i++) {
                List<String> obj = Lists.newLinkedList();
                JSONObject hit = hits.getJSONObject(i).getJSONObject("_source");

                obj.add(hit.getString("title"));
                obj.add(hit.getString("id"));
                obj.add(hit.getString("summary"));
                obj.add("Google News");
                obj.add(this.googleLogo);
                obj.add(String.valueOf(hit.optInt("fb_like_count", 0)));
                obj.add(String.valueOf(hit.optInt("retweet_count", 0)));
                obj.add(hit.getString("fecha"));

                ret.add(obj);
            }

            return ret;
        } catch (UnirestException e) {
            e.printStackTrace();
            return null;
        }
    }

    private ST loadTemplate(String template) {
        try {
            URL url = getClass().getResource(template);
            return new ST(Resources.toString(url, Charset.defaultCharset()), '$', '$');
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
