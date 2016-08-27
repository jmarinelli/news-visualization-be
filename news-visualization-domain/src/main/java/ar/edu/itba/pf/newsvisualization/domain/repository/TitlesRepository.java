package ar.edu.itba.pf.newsvisualization.domain.repository;

import ar.edu.itba.pf.newsvisualization.domain.model.Title;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by juanjosemarinelli on 4/9/16.
 */
public interface TitlesRepository extends CrudRepository<Title, String> {
}
