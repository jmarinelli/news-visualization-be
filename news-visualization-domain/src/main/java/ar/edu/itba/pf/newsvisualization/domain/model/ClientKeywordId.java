package ar.edu.itba.pf.newsvisualization.domain.model;

import java.io.Serializable;

/**
 * Created by juanjosemarinelli on 12/7/16.
 */
public class ClientKeywordId implements Serializable {
    private String clientKey;
    private String keyword;

    public ClientKeywordId() {
    }

    public ClientKeywordId(String clientKey, String keyword) {
        this.clientKey = clientKey;
        this.keyword = keyword;
    }

    public String getClientKey() {
        return clientKey;
    }

    public void setClientKey(String clientKey) {
        this.clientKey = clientKey;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
