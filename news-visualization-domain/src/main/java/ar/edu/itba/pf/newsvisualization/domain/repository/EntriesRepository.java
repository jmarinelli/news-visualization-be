package ar.edu.itba.pf.newsvisualization.domain.repository;

import ar.edu.itba.pf.newsvisualization.domain.model.Entry;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by lmoscovicz on 12/23/16.
 */
public interface EntriesRepository extends CrudRepository<Entry, String> {
    @Query(value = "SELECT COUNT(*), e.tmp, e.seccion FROM entries e WHERE e.source LIKE '%news.google.com%' GROUP BY e.tmp, e.seccion HAVING e.tmp in ?1")
    List<Object[]> getMediaCategories(List<String> medias);

    @Query(value = "SELECT tmp FROM entries WHERE source LIKE '%news.google.com%' GROUP BY tmp HAVING COUNT(*) > 3 ORDER BY COUNT(*) DESC")
    List<String> getMedia();

    @Query(value = "SELECT DISTINCT(seccion) FROM entries WHERE source LIKE '%news.google.com%'")
    List<String> getCategories();
}