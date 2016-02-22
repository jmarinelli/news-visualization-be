package ar.edu.itba.pf.newsvisualization.domain.model.response;

import java.time.LocalDate;

/**
 * Created by juanjosemarinelli on 2/21/16.
 */
public class DateCount extends Count implements Comparable<DateCount>{

    public DateCount(String date) {
        super(simpleDtf.format(dtf.parse(date)));
    }

    public void addMediaCount(Long count, String value) { this.addCount(count, value); }

    @Override
    public int compareTo(DateCount o) {
        final LocalDate d1 = LocalDate.parse(this.getValue(), simpleDtf);
        final LocalDate d2 = LocalDate.parse(o.getValue(), simpleDtf);
        return d1.compareTo(d2);
    }
}
