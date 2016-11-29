package ar.edu.itba.pf.newsvisualization.domain.repository;

import ar.edu.itba.pf.newsvisualization.domain.model.Entry;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by juanjosemarinelli on 11/29/16.
 */
public interface EntriesRepository extends CrudRepository<Entry, String> {
}
