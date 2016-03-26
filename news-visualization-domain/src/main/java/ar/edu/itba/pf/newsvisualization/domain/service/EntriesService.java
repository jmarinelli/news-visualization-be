package ar.edu.itba.pf.newsvisualization.domain.service;

import ar.edu.itba.pf.newsvisualization.domain.model.request.Grouping;
import ar.edu.itba.pf.newsvisualization.domain.model.response.Count;
import ar.edu.itba.pf.newsvisualization.domain.model.response.DateCount;
import ar.edu.itba.pf.newsvisualization.domain.model.response.MediaCount;
import ar.edu.itba.pf.newsvisualization.domain.model.response.WordCount;
import ar.edu.itba.pf.newsvisualization.domain.repository.EntriesRepository;
import com.google.common.base.CharMatcher;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by juanjosemarinelli on 2/21/16.
 */
@Service
public class EntriesService {

    private final Integer bufferSize = 1000;
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private EntriesRepository entries;

    @Autowired
    public EntriesService(EntriesRepository entries) {this.entries = entries;}

    public List<Count> getCounts(LocalDate from, LocalDate to, List<String> media, Grouping groupBy) {
        switch (groupBy) {
            case DATE:
                return this.getMediaCountByDate(from, to, media);
            default:
                return this.getDateCountByMedia(from, to, media);
        }
    }

    public List<WordCount> getWordCount(List<String> media, Long maxSize) {
        List<WordCount> ret = Lists.newLinkedList();
        Map<String, Integer> wordCount = Maps.newHashMap();
        this.entries.getContents().forEach(content -> {
            String[] wordArray = content.split(" ");
            for (int i = 0 ; i < wordArray.length ; i++) {
                String word = wordArray[i].toLowerCase();
                CharMatcher matcher = CharMatcher.JAVA_LETTER;
                word = matcher.retainFrom(word);
                if (word.length() > 3) {
                    wordCount.putIfAbsent(word, 0);
                    int count = wordCount.get(word);
                    wordCount.put(word, ++count);
                }
            }
        });
        wordCount.forEach((k, v) -> ret.add(new WordCount(k, v)));
        return ret.stream().limit(maxSize).collect(Collectors.toList());
    }

    private List<Count> getDateCountByMedia(LocalDate from, LocalDate to, List<String> selectedMedia) {
        final Map<String, MediaCount> dateCountByMedia = Maps.newHashMap();
        final List<String> mediaList = sanitizeMedia(selectedMedia);
        final List<Object[]> queryResult = this.doQuery(from, to);

        if (!CollectionUtils.isEmpty(queryResult)) {
            queryResult.forEach(obj -> {
                String media = String.valueOf(obj[2]);
                if (CollectionUtils.isEmpty(mediaList) || mediaList.contains(media.toLowerCase())) {
                    dateCountByMedia.putIfAbsent(media, new MediaCount(media));
                    MediaCount currentMediaCount = dateCountByMedia.get(media);
                    currentMediaCount.addDateCount((Long) obj[0], String.valueOf(obj[1]));
                }
            });
        }

        dateCountByMedia.values().forEach(mc -> mc.sort());

        return dateCountByMedia.values().stream().collect(Collectors.toList());
    }

    private List<Count> getMediaCountByDate(LocalDate from, LocalDate to, List<String> selectedMedia) {
        final Map<String, DateCount> mediaCountByDate = Maps.newHashMap();
        final List<String> mediaList = sanitizeMedia(selectedMedia);
        final List<Object[]> queryResult = this.doQuery(from, to);

        if (!CollectionUtils.isEmpty(queryResult)) {
            queryResult.forEach(obj -> {
                String media = String.valueOf(obj[2]);
                if (CollectionUtils.isEmpty(mediaList) || mediaList.contains(media.toLowerCase())) {
                    mediaCountByDate.putIfAbsent(String.valueOf(obj[1]), new DateCount(String.valueOf(obj[1])));
                    DateCount currentDateCount = mediaCountByDate.get(String.valueOf(obj[1]));
                    currentDateCount.addMediaCount((Long) obj[0], media);
                }
            });
        }

        List<DateCount> response = mediaCountByDate.values().stream().collect(Collectors.toList());

        Collections.sort(response);

        return response.stream().collect(Collectors.toList());
    }

    private List<Object[]> doQuery(LocalDate from, LocalDate to) {
        List<Object[]> queryResult;

        if (!StringUtils.isEmpty(from) && !StringUtils.isEmpty(to)){
            try {
                queryResult = this.entries.countByDateAndMedia(sdf.parse(dtf.format(from)), sdf.parse(dtf.format(to)));
            } catch (ParseException e) {
                throw new IllegalArgumentException("Date format not supported");
            }
        } else {
            queryResult = this.entries.countByDateAndMedia();
        }

        return queryResult;
    }

    private List<String> sanitizeMedia(List<String> media) {
        if (CollectionUtils.isEmpty(media))
            return Lists.newLinkedList();
        return media.stream().map(m -> {
            String[] splittedMedia = m.toLowerCase().split("-");
            return splittedMedia[splittedMedia.length - 1].trim();
        }).collect(Collectors.toList());
    }
}
