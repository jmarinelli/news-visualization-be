package ar.edu.itba.pf.newsvisualization.service.controller;

import ar.edu.itba.pf.newsvisualization.domain.model.response.main.WordCloudResponse;
import ar.edu.itba.pf.newsvisualization.domain.repository.ElasticRepository;
import ar.edu.itba.pf.newsvisualization.service.transformer.MainTransformer;
import ar.edu.itba.pf.newsvisualization.service.transformer.TrendResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by juanjosemarinelli on 9/27/16.
 */
@RestController
@RequestMapping(value = "")
public class MainController {
    private ElasticRepository elasticRepository;

    @Autowired
    public MainController(ElasticRepository elasticRepository) {
        this.elasticRepository = elasticRepository;
    }

    @RequestMapping(method = RequestMethod.GET, value = "word-cloud")
    public List<List<Object>> getWordCount(@RequestParam String date1, @RequestParam String date2) {
        return MainTransformer.transformWordCloudResponse(elasticRepository.getWordCount(date1, date2));
    }

    @RequestMapping(method = RequestMethod.GET, value = "titles")
    public List<List<String>> getTitles(@RequestParam String date1, @RequestParam String date2,
                                        @RequestParam List<String> keyword,
                                        @RequestParam(defaultValue = "10") Integer limit, @RequestParam(defaultValue = "0") Integer offset) {
        return elasticRepository.getTitles(date1, date2, keyword, limit, offset);
    }

    @RequestMapping(method = RequestMethod.GET, value = "top")
    public List<List<String>> getTop(@RequestParam String from, @RequestParam String to,
                                        @RequestParam List<String> keywords,
                                        @RequestParam(defaultValue = "10") Integer limit, @RequestParam(defaultValue = "0") Integer offset) {
        return elasticRepository.getTop(from, to, keywords, limit, offset);
    }

    @RequestMapping(method = RequestMethod.GET, value = "trend")
    public String getWordCount(@RequestParam String date1, @RequestParam String date2,
                               @RequestParam List<String> keyword, HttpServletResponse response) {
        response.setContentType("text/plain; charset=utf-8");
        return MainTransformer.transformTrendResponse(elasticRepository.getTrends(keyword, date1, date2), keyword);
    }
}
