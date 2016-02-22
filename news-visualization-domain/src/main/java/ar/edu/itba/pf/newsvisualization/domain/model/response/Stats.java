package ar.edu.itba.pf.newsvisualization.domain.model.response;

/**
 * Created by juanjosemarinelli on 2/21/16.
 */
public class Stats {

    private Long count;
    private String value;

    public Stats(Long count, String value) {
        this.count = count;
        this.value = value;
    }

    public Long getCount() {
        return count;
    }

    public String getValue() {
        return value;
    }
}
