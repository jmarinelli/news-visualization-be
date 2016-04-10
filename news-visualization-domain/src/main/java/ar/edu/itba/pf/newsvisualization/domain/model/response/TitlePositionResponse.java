package ar.edu.itba.pf.newsvisualization.domain.model.response;

import sun.util.calendar.LocalGregorianCalendar;

import java.util.Date;
import java.util.List;

/**
 * Created by juanjosemarinelli on 4/9/16.
 */
public class TitlePositionResponse {

    private List<TitlePosition> titles;
    private Date date;

    public TitlePositionResponse(
            List<TitlePosition> titles, Date date) {
        this.titles = titles;
        this.date = date;
    }

    public List<TitlePosition> getTitles() {
        return titles;
    }

    public Date getDate() {
        return date;
    }
}
