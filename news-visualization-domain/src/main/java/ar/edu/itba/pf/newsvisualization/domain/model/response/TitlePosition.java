package ar.edu.itba.pf.newsvisualization.domain.model.response;

/**
 * Created by juanjosemarinelli on 4/9/16.
 */
public class TitlePosition {

    private String title;
    private Integer position;

    public TitlePosition(String title, Integer position) {
        this.title = title;
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public Integer getPosition() {
        return position;
    }
}
