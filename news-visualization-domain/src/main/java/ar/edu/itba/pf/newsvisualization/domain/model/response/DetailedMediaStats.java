package ar.edu.itba.pf.newsvisualization.domain.model.response;

import java.util.List;

/**
 * Created by juanjosemarinelli on 9/1/16.
 */
public class DetailedMediaStats extends MediaStats {

    private final List<CategoryStats> details;

    public DetailedMediaStats(Long count, String media, List<CategoryStats> details) {
        super(count, media);
        this.details = details;
    }

    public List<CategoryStats> getDetails() {
        return details;
    }
}
