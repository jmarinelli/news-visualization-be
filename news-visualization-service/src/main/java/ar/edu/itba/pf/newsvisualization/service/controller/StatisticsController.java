package ar.edu.itba.pf.newsvisualization.service.controller;

import ar.edu.itba.pf.newsvisualization.domain.model.request.Grouping;
import ar.edu.itba.pf.newsvisualization.domain.model.response.Count;
import ar.edu.itba.pf.newsvisualization.domain.model.response.WordCount;
import ar.edu.itba.pf.newsvisualization.domain.model.response.WordCountResponse;
import ar.edu.itba.pf.newsvisualization.domain.service.EntriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by juanjosemarinelli on 2/21/16.
 */
@RestController
@RequestMapping(value = "statistics")
public class StatisticsController {

    private EntriesService entries;

    @Autowired
    public StatisticsController(EntriesService entries) {this.entries = entries;}

    @RequestMapping(method = RequestMethod.GET)
    public List<Count> getMediaCountByDate(@RequestParam(required = false) LocalDate from,
                                           @RequestParam(required = false) LocalDate to,
                                           @RequestParam(required = false) List<String> media,
                                           @RequestParam(required = false, defaultValue = "MEDIA") Grouping groupBy) {
        return entries.getCounts(from, to, media, groupBy);
    }

    @RequestMapping(method = RequestMethod.GET, value = "word-count")
    public List<WordCount> getWordCount(@RequestParam(required = false) List<String> media,
                                        @RequestParam(required = false, defaultValue = "10000") Long maxSize) {
        return this.entries.getWordCount(media, maxSize);
    }
}
