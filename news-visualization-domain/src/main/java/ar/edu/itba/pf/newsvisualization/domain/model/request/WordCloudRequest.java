package ar.edu.itba.pf.newsvisualization.domain.model.request;

import ar.edu.itba.pf.newsvisualization.domain.model.request.aggregators.TitlesAggregator;

/**
 * Created by juanjosemarinelli on 9/20/16.
 */
public class WordCloudRequest {

    private ESQuery query;
    private TitlesAggregator aggs;

    public WordCloudRequest(String from, String to, String format) {
        this.query = new ESQuery(from, to, format);
        this.aggs = new TitlesAggregator();
    }

    public WordCloudRequest(String from, String to) {
        this(from, to, "yyyy-MM-dd");
    }

    public ESQuery getQuery() {
        return query;
    }

    public TitlesAggregator getAggs() {
        return aggs;
    }
}
