package ar.edu.itba.pf.newsvisualization.domain.repository;

import ar.edu.itba.pf.newsvisualization.domain.model.TrendResponse;
import ar.edu.itba.pf.newsvisualization.domain.model.WordCloudResponse;
import ar.edu.itba.pf.newsvisualization.domain.model.response.NewsCountResponse;
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
import org.springframework.util.StringUtils;
import org.stringtemplate.v4.ST;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by juanjosemarinelli on 9/20/16.
 */
public class ElasticRepository {

    private final String searchUrl;

    public ElasticRepository(String baseUrl) {
        this.searchUrl =  baseUrl + "/_search";
    }

    public NewsCountResponse getNewsCount(String date, List<String> medios) {
        ST newsCount = loadTemplate("news-count");

        newsCount.add("date", date);
        newsCount.add("medios", medios);

        try {

            HttpResponse<String> response = Unirest.post(searchUrl).body(newsCount.render()).asString();

            JSONObject hits = new JSONObject(response.getBody()).getJSONObject("hits");

            return new NewsCountResponse(hits.optInt("total", 0));
        } catch (UnirestException e) {
            e.printStackTrace();
            return null;
        }
    }

    public WordCloudResponse getWordCount(String from, String to, List<String> keywords, List<String> medios,
                                          Integer limit) {
        if (to == null) to = from;

        ST wordCloud = loadTemplate("word-cloud");

        wordCloud.add("from", from);
        wordCloud.add("to", to);
        wordCloud.add("keywords", keywords);
        wordCloud.add("limit", limit);
        wordCloud.add("medios", medios);

        try {

            HttpResponse<WordCloudResponse> response = Unirest.post(searchUrl).body(wordCloud.render()).asObject(WordCloudResponse.class);

            return response.getBody();
        } catch (UnirestException e) {
            e.printStackTrace();
            return null;
        }
    }

    public TrendResponse getTrends(List<String> terms, String from, String to, List<String> medios) {
        if (to == null) to = from;

        ST requestBody = loadTemplate("trend");

        requestBody.add("from", from);
        requestBody.add("to", to);
        requestBody.add("keywords", terms);
        requestBody.add("medios", medios);

        try {

            HttpResponse<TrendResponse> response = Unirest.post(searchUrl).body(requestBody.render()).asObject(TrendResponse.class);

            return response.getBody();
        } catch (UnirestException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<List<String>> getTitles(String from, String to, List<String> keywords, List<String> medios,
                                        Integer limit, Integer offset) {
        if (to == null) to = from;

        List<List<String>> ret = Lists.newLinkedList();
        ST titles = loadTemplate("titles");

        titles.add("from", from);
        titles.add("to", to);
        titles.add("keywords", keywords);
        titles.add("limit", limit);
        titles.add("offset", offset);
        titles.add("medios", medios);

        try {

            HttpResponse<String> response = Unirest.post(searchUrl).body(titles.render()).asString();


            JSONArray hits = new JSONObject(response.getBody()).getJSONObject("hits").getJSONArray("hits");
            for (int i = 0; i < hits.length(); i++) {
                List<String> obj = Lists.newLinkedList();
                JSONObject hit = hits.getJSONObject(i).getJSONObject("_source");

                if (!StringUtils.isEmpty(hit.optString("id"))) {
                    obj.add(hit.optString("title"));
                    obj.add(hit.optString("id"));
                    obj.add(hit.optString("summary"));
                    obj.add(hit.optString("nombre"));
                    obj.add(hit.optString("url_favicon"));
                    obj.add(String.valueOf(0));
                    obj.add(String.valueOf(0));
                    obj.add(hit.optString("fecha"));

                    ret.add(obj);
                }
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
