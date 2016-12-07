package ar.edu.itba.pf.newsvisualization.service.controller;

import ar.edu.itba.pf.newsvisualization.domain.repository.ElasticRepository;
import ar.edu.itba.pf.newsvisualization.service.transformer.MainTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, value = "word-cloud")
    public List<List<Object>> getWordCount(@RequestParam String date1, @RequestParam(required = false) String date2,
                                           @RequestParam(required = false) List<String> keyword,
                                           @RequestParam(required = false) List<String> medios,
                                           @RequestParam(defaultValue = "1000", required = false) Integer limit,
                                           @RequestParam(defaultValue = "1", required = false) Integer minfreq) {
        return MainTransformer.transformWordCloudResponse(
                elasticRepository.getWordCount(date1, date2, keyword, medios, limit), minfreq);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, value = "titles")
    public List<List<String>> getTitles(@RequestParam String date1, @RequestParam(required = false) String date2,
                                        @RequestParam(required = false) List<String> keyword,
                                        @RequestParam(required = false) List<String> medios,
                                        @RequestParam(defaultValue = "10") Integer limit,
                                        @RequestParam(defaultValue = "0") Integer offset) {
        return elasticRepository.getTitles(date1, date2, keyword, medios, limit, offset);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, value = "trend")
    public void getWordCount(@RequestParam String date1, @RequestParam(required = false) String date2,
                             @RequestParam List<String> keyword, @RequestParam(required = false) List<String> medios,
                             HttpServletResponse response) throws IOException {
        response.setContentType("application/json; charset=utf-8");


        response.getWriter().print(MainTransformer.transformTrendResponse(
                elasticRepository.getTrends(keyword, date1, date2, medios), keyword));
    }
}
