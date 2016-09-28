package ar.edu.itba.pf.newsvisualization.domain.model.request;

import ar.edu.itba.pf.newsvisualization.domain.model.request.aggregators.DateHistogramAggregator;

import java.util.List;

/**
 * Created by juanjosemarinelli on 9/27/16.
 */
public class TrendRequest {

    private ESQuery query;
    private DateHistogramAggregator aggs;

    public TrendRequest(List<String> terms, String from, String to) {
        this.query = new ESQuery(from, to, "yyyy-MM-dd");
        this.aggs = new DateHistogramAggregator(terms);
    }

    public DateHistogramAggregator getAggs() {
        return aggs;
    }

    public ESQuery getQuery() {
        return query;
    }
}
