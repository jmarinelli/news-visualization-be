package ar.edu.itba.pf.newsvisualization.domain.model.response;

/**
 * Created by juanjosemarinelli on 2/21/16.
 */
public class MediaCount extends Count {

    public MediaCount(String media) {
        super(media);
    }

    public void addDateCount(Long count, String date) {
        this.addCount(count, simpleDtf.format(dtf.parse(date)));
    }
}
