package ar.edu.itba.pf.newsvisualization.domain.repository;

import ar.edu.itba.pf.newsvisualization.domain.model.Entry;
import ar.edu.itba.pf.newsvisualization.domain.model.response.MediaCategories;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by juanjosemarinelli on 2/21/16.
 */

public interface EntriesRepository extends CrudRepository<Entry, String> {
    @Query(value = "SELECT COUNT(*), e.fecha, e.tmp FROM entries e WHERE e.fecha between ?1 and ?2 group by e.fecha, e.tmp")
    List<Object[]> countByDateAndMedia(Date from, Date to);

    @Query(value = "SELECT COUNT(*), e.seccion, e.tmp FROM entries e WHERE e.fecha between ?1 and ?2 and e.seccion in ?3 group by e.seccion, e.tmp")
    List<Object[]> countByDateAndMedia(Date from, Date to, List<String> seccion);

    @Query(value = "SELECT COUNT(*), e.fecha, e.tmp FROM entries e group by e.fecha, e.tmp")
    List<Object[]> countByDateAndMedia();

    @Query(value = "SELECT DISTINCT(tmp) FROM entries")
    List<String> getMedia();

    @Query(value = "SELECT DISTINCT(seccion) FROM entries")
    List<String> getCategories();

    @Query(value = "SELECT e.summary FROM entries e")
    List<String> getContents();

    @Query(value = "SELECT e.summary FROM entries e")
    List<String> getContents(Pageable pageable);

    @Query(value = "SELECT COUNT(*), e.tmp, e.seccion FROM entries e GROUP BY e.tmp, e.seccion HAVING e.tmp in ?1")
    List<Object[]> getMediaCategories(List<String> medias);
}
