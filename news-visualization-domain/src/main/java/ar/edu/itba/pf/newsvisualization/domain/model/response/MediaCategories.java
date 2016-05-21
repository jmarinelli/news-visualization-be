package ar.edu.itba.pf.newsvisualization.domain.model.response;

import java.util.List;

public class MediaCategories {

    private String media;
    private List<Stats> categories;

    public MediaCategories(String media, List<Stats> categories) {
        this.media = media;
        this.categories = categories;
    }

    public List<Stats> getCategories() {
        return categories;
    }

    public String getMedia() {
        return media;
    }
}
