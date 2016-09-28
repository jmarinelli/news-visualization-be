package ar.edu.itba.pf.newsvisualization.domain.model.request.aggregators;

import java.util.List;

/**
 * Created by juanjosemarinelli on 9/27/16.
 */
public class DateHistogramAggregator {

    private Aggregator dayOfWeek;

    public DateHistogramAggregator(List<String> includes) {
        this.dayOfWeek = new Aggregator(includes);
    }

    public Aggregator getDayOfWeek() {
        return dayOfWeek;
    }

    public class Aggregator {
        private TrendAggregator aggs;
        private DateHistogram date_histogram;

        public Aggregator(List<String> includes) {
            this.aggs = new TrendAggregator(includes);
            this.date_histogram = new DateHistogram();
        }

        public TrendAggregator getAggs() {
            return aggs;
        }

        public DateHistogram getDate_histogram() {
            return date_histogram;
        }

        public class TrendAggregator {

            private ESTag titles;

            public TrendAggregator(List<String> includes) {
                this.titles = new ESTag(includes);
            }

            public ESTag getTitles() {
                return titles;
            }

            public class ESTag {
                private ESTerm terms;

                public ESTag(List<String> includes) {
                    this.terms = new ESTerm(includes);
                }

                public ESTerm getTerms() {
                    return terms;
                }

                public class ESTerm {
                    private String field;
                    private List<String> include;
                    private Integer size;

                    public ESTerm(List<String> includes) {
                        this.field = "_all";
                        this.include = includes;
                        this.size = 2000;
                    }

                    public String getField() {
                        return field;
                    }

                    public List<String> getInclude() {
                        return include;
                    }

                    public Integer getSize() {
                        return size;
                    }
                }
            }
        }

        public class DateHistogram {
            private final String field = "fecha";
            private final String interval = "day";

            public String getField() {
                return field;
            }

            public String getInterval() {
                return interval;
            }
        }
    }
}
