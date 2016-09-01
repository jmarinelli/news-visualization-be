package ar.edu.itba.pf.newsvisualization.domain.model.response;

/**
 * Created by juanjosemarinelli on 9/1/16.
 */
public class CategoryStats {
    private final Long count;
    private final String category;

    public CategoryStats(Long count, String category) {
        this.count = count;
        this.category = category;
    }

    public Long getCount() {
        return count;
    }

    public String getCategory() {
        return category;
    }
}
