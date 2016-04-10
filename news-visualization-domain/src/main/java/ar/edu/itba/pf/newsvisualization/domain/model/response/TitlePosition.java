package ar.edu.itba.pf.newsvisualization.domain.model.response;

import java.util.Date;

/**
 * Created by juanjosemarinelli on 4/9/16.
 */
public class TitlePosition {

    private String title;
    private Integer position;
    private Date date;

    public TitlePosition(String title, Integer position, Date date) {
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

    public Date getDate() {
        return date;
    }
}
