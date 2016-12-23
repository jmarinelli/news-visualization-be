package ar.edu.itba.pf.newsvisualization.domain.repository;

import ar.edu.itba.pf.newsvisualization.domain.model.ClientMedia;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by juanjosemarinelli on 11/29/16.
 */
public interface ClientMediaRepository extends CrudRepository<ClientMedia, String> {

    List<ClientMedia> findByClientKey(String clientKey);
}
