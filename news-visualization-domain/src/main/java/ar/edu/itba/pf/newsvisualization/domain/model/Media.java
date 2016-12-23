package ar.edu.itba.pf.newsvisualization.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by juanjosemarinelli on 12/7/16.
 */
@Entity(name = "medios")
public class Media {

    @Id
    private String id;
    @Column(name = "nombre")
    private String name;
    @Column(name = "url_favicon")
    private String url;
    private String logo;
    @Column(name = "tipo")
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
