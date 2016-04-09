package ar.edu.itba.pf.newsvisualization.service.controller;

import ar.edu.itba.pf.newsvisualization.domain.model.Title;
import ar.edu.itba.pf.newsvisualization.domain.model.response.TitleResponse;
import ar.edu.itba.pf.newsvisualization.domain.repository.TitlesRepository;
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

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private TitlesRepository titlesRepository;

    @Autowired
    public TitlesController(TitlesRepository titlesRepository) {this.titlesRepository = titlesRepository;}

    @RequestMapping(method = RequestMethod.GET)
    public List<TitleResponse> find() {
        List<TitleResponse> response = Lists.newLinkedList();

        this.titlesRepository.findAll().forEach(t -> {
            response.add(new TitleResponse(t.getTitle1(), t.getTitle2(), t.getTitle3(), sdf.format(t.getDate())));
        });

        return response;
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
        try {
            List<Status> statuses;
            String user;
            user = "ln_title";
            statuses = twitter.getUserTimeline(user, new Paging(1, Integer.MAX_VALUE));
            for (Status status : statuses) {
                Title title = new Title();
                title.setDate(status.getCreatedAt());
                String[] preTitles = status.getText().split(",");
                title.setTitle1(preTitles[0].trim());
                if (preTitles.length > 1)
                    title.setTitle2(preTitles[1].substring(1));
                if (preTitles.length > 2)
                    title.setTitle3(preTitles[2].substring(1));
                titles.add(title);
            }
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get timeline: " + te.getMessage());
            System.exit(-1);
        }
        titlesRepository.save(titles);
        return titles;
    }
}
