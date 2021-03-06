package ar.edu.itba.pf.newsvisualization.domain.model.response;

/**
 * Created by lmoscovicz on 12/23/16.
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
