package ar.edu.itba.pf.newsvisualization.domain.model.response;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;

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

    public void sort() {
        Collections.sort(this.getStats(), comparator());
    }

    private Comparator<Stats> comparator() {
        return (o1, o2) -> {
            final LocalDate d1 = LocalDate.parse(o1.getValue(), simpleDtf);
            final LocalDate d2 = LocalDate.parse(o2.getValue(), simpleDtf);
            return d1.compareTo(d2);
        };
    }
}
