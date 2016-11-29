package ar.edu.itba.pf.newsvisualization.service.controller;

import ar.edu.itba.pf.newsvisualization.domain.model.Entry;
import ar.edu.itba.pf.newsvisualization.domain.repository.EntriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by juanjosemarinelli on 11/29/16.
 */
@RestController
@RequestMapping(value = "test")
public class TestController {

    private EntriesRepository entries;

    @Autowired
    public TestController(EntriesRepository entries) {
        this.entries = entries;
    }

    @RequestMapping(method = RequestMethod.GET, value = "entries")
    public Iterable<Entry> getEntries() {
        return entries.findAll();
    }
}
