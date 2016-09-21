package ar.edu.itba.pf.newsvisualization.domain.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by juanjosemarinelli on 9/20/16.
 */
public class WordCloudResponse {

    private ESAggregation aggregations;

    public List<ESAggregation.ESField.ESBucket> getWords() {
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

        public static class ESField {
            private List<ESBucket> buckets;

            public List<ESBucket> getBuckets() {
                return buckets;
            }

            public void setBuckets(List<ESBucket> buckets) {
                this.buckets = buckets;
            }

            public static class ESBucket {
                private String key;
                private Integer count;

                public String getKey() {
                    return key;
                }

                public Integer getCount() {
                    return count;
                }

                public void setKey(String key) {
                    this.key = key;
                }

                @JsonProperty("doc_count")
                public void setCount(Integer count) {
                    this.count = count;
                }
            }
        }
    }
}
