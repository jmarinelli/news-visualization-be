package ar.edu.itba.pf.newsvisualization.domain.model.response;

/**
 * Created by juanjosemarinelli on 4/9/16.
 */
public class MediaStats {
    private Long count;
    private String media;

    public MediaStats(Long count, String media) {
        this.count = count;
        this.media = media;
    }

    public Long getCount() {
        return count;
    }

    public String getMedia() {
        return media;
    }
}
