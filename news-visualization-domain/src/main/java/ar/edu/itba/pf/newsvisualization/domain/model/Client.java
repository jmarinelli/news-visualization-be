package ar.edu.itba.pf.newsvisualization.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by juanjosemarinelli on 12/7/16.
 */
@Entity(name = "cliente")
public class Client {

    @Id
    @Column(name = "nombre")
    private String name;
    private String mail;
    private String key;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
