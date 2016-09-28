package ar.edu.itba.pf.newsvisualization.domain.model.response.main;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by juanjosemarinelli on 9/20/16.
 */
public class WordCloudResponse {

    private ESAggregation aggregations;

    public List<ESField.ESBucket> getWords() {
        return this.aggregations.getTitles().getBuckets();
    }

    public void setAggregations(ESAggregation aggregations) {
        this.aggregations = aggregations;
    }

    public static class ESAggregation {
        private ESField titles;

        public ESField getTitles() {
            return titles;
        }

        public void setTitles(ESField titles) {
            this.titles = titles;
        }
    }
}
