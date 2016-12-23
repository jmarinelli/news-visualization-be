package ar.edu.itba.pf.newsvisualization.domain.repository;

import ar.edu.itba.pf.newsvisualization.domain.model.Client;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by juanjosemarinelli on 12/7/16.
 */
public interface ClientRepository extends CrudRepository<Client, String> {

    Client findOneByKey(String key);
}
