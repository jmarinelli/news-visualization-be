package ar.edu.itba.pf.newsvisualization.service.controller;

import ar.edu.itba.pf.newsvisualization.domain.repository.EntriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by juanjosemarinelli on 2/21/16.
 */
@RestController
@RequestMapping(value = "entries")
public class EntriesController {

    private EntriesRepository entries;

    @Autowired
    public EntriesController(EntriesRepository entries) {this.entries = entries;}

    @RequestMapping(method = RequestMethod.GET, value = "count")
    public Long findOne() {
        return this.entries.count();
    }
}
