package ar.edu.itba.pf.newsvisualization.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by juanjosemarinelli on 12/7/16.
 */
@Entity(name = "categoria")
public class Category {

    @Id
    private String source;
    @Column(name = "canal")
    private String channel;
    @Column(name = "medio_id")
    private Integer mediaId;
    @Column(name = "activo")
    private Boolean active;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Integer getMediaId() {
        return mediaId;
    }

    public void setMediaId(Integer mediaId) {
        this.mediaId = mediaId;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
