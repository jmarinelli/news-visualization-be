package ar.edu.itba.pf.newsvisualization.domain.model.response;

/**
 * Created by juanjosemarinelli on 4/9/16.
 */
public class TitleResponse {
    private String title1;
    private String title2;
    private String title3;
    private String date;
    private String time;

    public TitleResponse(String title1, String title2, String title3, String date, String time) {
        this.title1 = title1;
        this.title2 = title2;
        this.title3 = title3;
        this.date = date;
        this.time = time;
    }

    public String getTitle1() {
        return title1;
    }

    public String getTitle2() {
        return title2;
    }

    public String getTitle3() {
        return title3;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
