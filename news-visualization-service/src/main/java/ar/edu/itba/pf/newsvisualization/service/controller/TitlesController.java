package ar.edu.itba.pf.newsvisualization.service.controller;

import ar.edu.itba.pf.newsvisualization.domain.model.Title;
import ar.edu.itba.pf.newsvisualization.domain.model.response.TitlePositionResponse;
import ar.edu.itba.pf.newsvisualization.domain.repository.TitlesRepository;
import ar.edu.itba.pf.newsvisualization.domain.service.TitlesService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by juanjosemarinelli on 4/9/16.
 */
@RestController
@RequestMapping(value = "titles")
public class TitlesController {

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");

    private TitlesRepository titlesRepository;
    private TitlesService titlesService;

    @Autowired
    public TitlesController(TitlesRepository titlesRepository, TitlesService titlesService) {
        this.titlesRepository = titlesRepository;
        this.titlesService = titlesService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<TitlePositionResponse> find() {
        return titlesService.getTitlePositions();
    }

    @RequestMapping(method = RequestMethod.PUT)
    public List<Title> updateTitles() {
        List<Title> titles = Lists.newLinkedList();
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
          .setOAuthConsumerKey("Ryr5DGYl9jSp1e04Wdwb5t9f7")
          .setOAuthConsumerSecret("uIlq4g4DsaJ3JaZQGczEjIls9Miqx2b6JEJu4mzAh7cvPQTE5i")
          .setOAuthAccessToken("183082608-Wd03ruu9gERgx28W7MQLHJN32uLeHSJ1VLos2y1O")
          .setOAuthAccessTokenSecret("1iPlQaFpIkX9F6fFrvhucF1hl9WFoQSHdQ2Sguy0Sv5VL");
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        boolean blocked = false;
        List<Status> statuses = Lists.newLinkedList();
        List<Status> statusesAux;
        String user;
        user = "ln_title";
        int page = 1;
        do {
            try {
                statusesAux = twitter.getUserTimeline(user, new Paging(page++));
                statuses.addAll(statusesAux);
            } catch (Exception e) {
                blocked = true;
            }
            System.out.println(statuses.size());
        } while (!statuses.isEmpty() && !blocked);
        for (Status status : statuses) {
            Title title = new Title();
            title.setTimestamp(status.getCreatedAt().getTime());
            String[] preTitles = status.getText().split(",");
            title.setTitle1(preTitles[0].trim());
            if (preTitles.length > 1)
                title.setTitle2(preTitles[1].substring(1));
            if (preTitles.length > 2)
                title.setTitle3(preTitles[2].substring(1));
            titles.add(title);
        }
        titlesRepository.save(titles);
        return titles;
    }
}
