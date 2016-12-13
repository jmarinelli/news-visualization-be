package ar.edu.itba.pf.newsvisualization.domain.model;

import java.io.Serializable;

/**
 * Created by juanjosemarinelli on 12/7/16.
 */
public class ClientMediaId implements Serializable{

    private String clientKey;
    private Integer mediaId;

    public ClientMediaId() {
    }

    public ClientMediaId(String clientKey, Integer mediaId) {
        this.clientKey = clientKey;
        this.mediaId = mediaId;
    }

    public String getClientKey() {
        return clientKey;
    }

    public void setClientKey(String clientKey) {
        this.clientKey = clientKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientMediaId that = (ClientMediaId) o;

        if (clientKey != null ? !clientKey.equals(that.clientKey) : that.clientKey != null) return false;
        return mediaId != null ? mediaId.equals(that.mediaId) : that.mediaId == null;

    }

    @Override
    public int hashCode() {
        int result = clientKey != null ? clientKey.hashCode() : 0;
        result = 31 * result + (mediaId != null ? mediaId.hashCode() : 0);
        return result;
    }

    public Integer getMediaId() {
        return mediaId;
    }

    public void setMediaId(Integer mediaId) {
        this.mediaId = mediaId;
    }
}
