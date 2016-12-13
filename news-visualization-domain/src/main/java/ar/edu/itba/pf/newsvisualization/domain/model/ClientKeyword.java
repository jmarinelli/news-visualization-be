package ar.edu.itba.pf.newsvisualization.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

/**
 * Created by juanjosemarinelli on 12/7/16.
 */
@Entity(name = "cliente_keywords")
@IdClass(ClientKeywordId.class)
public class ClientKeyword {
    @Id
    @Column(name = "cliente_key")
    private String clientKey;

    @Id
    private String keyword;

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
