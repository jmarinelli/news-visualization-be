
package ar.edu.itba.pf.newsvisualization.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by juanjosemarinelli on 2/21/16.
 */
@Entity(name = "titles")
public class Title {

    @Id
    private Long timestamp;
    private String title1;
    private String title2;
    private String title3;

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getTitle1() {
        return title1;
    }

    public void setTitle1(String title1) {
        this.title1 = title1;
    }

    public String getTitle2() {
        return title2;
    }

    public void setTitle2(String title2) {
        this.title2 = title2;
    }

    public String getTitle3() {
        return title3;
    }

    public void setTitle3(String title3) {
        this.title3 = title3;
    }
}
