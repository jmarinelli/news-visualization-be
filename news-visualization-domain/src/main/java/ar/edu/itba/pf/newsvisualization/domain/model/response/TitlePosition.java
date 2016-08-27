package ar.edu.itba.pf.newsvisualization.domain.model.response;

/**
 * Created by juanjosemarinelli on 4/9/16.
 */
public class TitlePosition {

    private String title;
    private Integer position;
    private Long date;

    public TitlePosition(String title, Integer position, Long date) {
        this.title = title;
        this.position = position;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public Integer getPosition() {
        return position;
    }

    public Long getDate() {
        return date;
    }
}
