package ar.edu.itba.pf.newsvisualization.domain.model.response;

import com.google.common.collect.Lists;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by juanjosemarinelli on 2/21/16.
 */
public abstract class Count {
    protected static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
    protected static final DateTimeFormatter simpleDtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private String value;
    private List<Stats> stats;

    public Count(String value) {
        this.value = value;
        this.stats = Lists.newLinkedList();
    }

    public String getValue() {
        return value;
    }

    public List<Stats> getStats() {
        return stats;
    }

    protected void addCount(Long count, String value) { this.stats.add(new Stats(count, value)); }
}
