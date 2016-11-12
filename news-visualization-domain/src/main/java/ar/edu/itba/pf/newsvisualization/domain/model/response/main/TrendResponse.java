package ar.edu.itba.pf.newsvisualization.domain.model.response.main;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

/**
 * Created by juanjosemarinelli on 9/27/16.
 */
public class TrendResponse {
    private ESAggregation aggregations;

    public ESAggregation getAggregations() {
        return aggregations;
    }

    public void setAggregations(ESAggregation aggregations) {
        this.aggregations = aggregations;
    }

    public static class ESAggregation {
        private DayOfTheWeek dayOfWeek;

        public DayOfTheWeek getDayOfWeek() {
            return dayOfWeek;
        }

        public void setDayOfWeek(DayOfTheWeek dayOfWeek) {
            this.dayOfWeek = dayOfWeek;
        }

        public static class DayOfTheWeek {
            private List<ESBucket> buckets;

            public List<ESBucket> getBuckets() {
                return buckets;
            }

            public void setBuckets(List<DayOfTheWeek.ESBucket> buckets) {
                this.buckets = buckets;
            }

            public static class ESBucket {
                private String date;
                private ESField titles;

                public ESField getTitles() {
                    return titles;
                }

                public void setTitles(ESField titles) {
                    this.titles = titles;
                }

                public String getDate() {
                    return date;
                }

                @JsonProperty("key_as_string")
                public void setDate(String date) {
                    this.date = date;
                }

                public static class ESField {
                    private List<ESSubBucket> buckets;

                    public List<ESSubBucket> getBuckets() {
                        return buckets;
                    }

                    public void setBuckets(List<ESSubBucket> buckets) {
                        this.buckets = buckets;
                    }

                    public static class ESSubBucket {
                        private String key;
                        private Integer count;

                        public ESSubBucket() {
                            this.count = 0;
                        }

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
    }
}
