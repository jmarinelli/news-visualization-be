package ar.edu.itba.pf.newsvisualization.domain.model.response;

/**
 * Created by juanjosemarinelli on 12/26/16.
 */
public class NewsCountResponse {
    private Integer count;

    public NewsCountResponse(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }
}
