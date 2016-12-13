package ar.edu.itba.pf.newsvisualization.service.controller;

import ar.edu.itba.pf.newsvisualization.domain.repository.ElasticRepository;
import ar.edu.itba.pf.newsvisualization.service.argument.KeyArgument;
import ar.edu.itba.pf.newsvisualization.service.transformer.MainTransformer;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.Normalizer;
import java.util.List;
import java.util.stream.Collectors;

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
                                           @RequestParam(defaultValue = "1", required = false) Integer minfreq,
                                           KeyArgument keyArgument) {
        List<String> keywordList = getKeywords(keyword, keyArgument);
        List<String> mediaList = getMedia(medios, keyArgument);

        return MainTransformer.transformWordCloudResponse(
                elasticRepository.getWordCount(date1, date2, keywordList, mediaList, limit), minfreq);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, value = "titles")
    public List<List<String>> getTitles(@RequestParam String date1, @RequestParam(required = false) String date2,
                                        @RequestParam(required = false) List<String> keyword,
                                        @RequestParam(required = false) List<String> medios,
                                        @RequestParam(defaultValue = "10") Integer limit,
                                        @RequestParam(defaultValue = "0") Integer offset,
                                        KeyArgument keyArgument) {
        List<String> keywordList = getKeywords(keyword, keyArgument);
        List<String> mediaList = getMedia(medios, keyArgument);

        return elasticRepository.getTitles(date1, date2, keywordList, mediaList, limit, offset);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, value = "trend")
    public void getWordCount(@RequestParam String date1, @RequestParam(required = false) String date2,
                             @RequestParam List<String> keyword, @RequestParam(required = false) List<String> medios,
                             KeyArgument keyArgument, HttpServletResponse response) throws IOException {

        List<String> keywordList = getKeywords(keyword, keyArgument);
        List<String> mediaList = getMedia(medios, keyArgument);

        response.setContentType("application/json; charset=utf-8");
        response.getWriter().print(MainTransformer.transformTrendResponse(
                elasticRepository.getTrends(keywordList, date1, date2, mediaList), keywordList));
    }

    private List<String> getKeywords(List<String> keywords, KeyArgument keyArgument) {
        List<String> keywordList = Lists.newLinkedList();

        if (!CollectionUtils.isEmpty(keywords)) keywordList.addAll(keywords);

        keywordList.addAll(keyArgument.getDefaultKeywords());
        return keywordList.stream().map(k -> Normalizer.normalize(k, Normalizer.Form.NFD)).collect(Collectors.toList());
    }

    private List<String> getMedia(List<String> media, KeyArgument keyArgument) {
        List<String> mediaList = Lists.newLinkedList();

        if (!CollectionUtils.isEmpty(media)) mediaList.addAll(media);

        mediaList.addAll(keyArgument.getDefaultMedia().stream().map(m -> m.toString()).collect(Collectors.toList()));
        return mediaList;
    }
}
