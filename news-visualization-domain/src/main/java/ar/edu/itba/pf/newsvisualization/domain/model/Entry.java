package ar.edu.itba.pf.newsvisualization.domain.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by juanjosemarinelli on 11/29/16.
 */
@Entity(name = "entries")
public class Entry {

    @Id
    private String id;
    private String title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
