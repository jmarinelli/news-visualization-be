package ar.edu.itba.pf.newsvisualization.domain.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by juanjosemarinelli on 2/21/16.
 */
@Entity(name = "entries")
public class Entry {

    @Id
    private String id;
    private String title;
    private String update;
    private String source;
    private String summary;
    private Date fecha;
    private String media;
    private String tmp;
    private String seccion;
    private String content;
    private Integer h24;
    private Integer min;
    private Integer año;
    private Integer mes;
    private Integer dia;
    private Integer retweet_count;
    private Integer fb_share_count;
    private Integer fb_like_count;
    private Integer fb_comment_count;
    private Integer fb_total_count;
    private String iptc;

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

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getTmp() {
        return tmp;
    }

    public void setTmp(String tmp) {
        this.tmp = tmp;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getH24() {
        return h24;
    }

    public void setH24(Integer h24) {
        this.h24 = h24;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getAño() {
        return año;
    }

    public void setAño(Integer año) {
        this.año = año;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Integer getDia() {
        return dia;
    }

    public void setDia(Integer dia) {
        this.dia = dia;
    }

    public Integer getRetweet_count() {
        return retweet_count;
    }

    public void setRetweet_count(Integer retweet_count) {
        this.retweet_count = retweet_count;
    }

    public Integer getFb_share_count() {
        return fb_share_count;
    }

    public void setFb_share_count(Integer fb_share_count) {
        this.fb_share_count = fb_share_count;
    }

    public Integer getFb_like_count() {
        return fb_like_count;
    }

    public void setFb_like_count(Integer fb_like_count) {
        this.fb_like_count = fb_like_count;
    }

    public Integer getFb_comment_count() {
        return fb_comment_count;
    }

    public void setFb_comment_count(Integer fb_comment_count) {
        this.fb_comment_count = fb_comment_count;
    }

    public Integer getFb_total_count() {
        return fb_total_count;
    }

    public void setFb_total_count(Integer fb_total_count) {
        this.fb_total_count = fb_total_count;
    }

    public String getIptc() {
        return iptc;
    }

    public void setIptc(String iptc) {
        this.iptc = iptc;
    }
}
