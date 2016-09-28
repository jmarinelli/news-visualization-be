package ar.edu.itba.pf.newsvisualization.domain.model.response.main;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by juanjosemarinelli on 9/27/16.
 */
public class ESField {
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
