package ar.edu.itba.pf.newsvisualization.service.controller;

import ar.edu.itba.pf.newsvisualization.domain.model.request.Grouping;
import ar.edu.itba.pf.newsvisualization.domain.model.response.Count;
import ar.edu.itba.pf.newsvisualization.domain.model.response.MediaStats;
import ar.edu.itba.pf.newsvisualization.domain.model.response.WordCount;
import ar.edu.itba.pf.newsvisualization.domain.model.response.WordCountResponse;
import ar.edu.itba.pf.newsvisualization.domain.service.EntriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @CrossOrigin(origins = "*")
    @RequestMapping(method = RequestMethod.GET, value = "by-category")
    public List<MediaStats> getAggregatedMedia(@RequestParam LocalDate from,
                                               @RequestParam LocalDate to,
                                               @RequestParam(required = false) String category,
                                              @RequestParam(required = false, defaultValue = "10") Integer limit) {
        return entries.getAggregatedMedia(from, to, category, limit);
    }

    @RequestMapping(method = RequestMethod.GET, value = "word-count")
    public WordCountResponse getWordCount(@RequestParam(required = false) List<String> media,
                                          @RequestParam(required = false, defaultValue = "100") Long minQuantity,
                                          @RequestParam(required = false, defaultValue = "5") Integer minWordLength) {
        return this.entries.getWordCount(media, minQuantity, minWordLength);
    }
}
