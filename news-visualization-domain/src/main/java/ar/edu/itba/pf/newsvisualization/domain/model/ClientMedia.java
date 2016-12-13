package ar.edu.itba.pf.newsvisualization.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

/**
 * Created by juanjosemarinelli on 12/7/16.
 */
@Entity(name = "cliente_medio")
@IdClass(ClientMediaId.class)
public class ClientMedia {
    @Id
    @Column(name = "cliente_key")
    private String clientKey;
    @Id
    @Column(name = "medio_id")
    private Integer mediaId;

    public String getClientKey() {
        return clientKey;
    }

    public void setClientKey(String clientKey) {
        this.clientKey = clientKey;
    }

    public Integer getMediaId() {
        return mediaId;
    }

    public void setMediaId(Integer mediaId) {
        this.mediaId = mediaId;
    }
}
