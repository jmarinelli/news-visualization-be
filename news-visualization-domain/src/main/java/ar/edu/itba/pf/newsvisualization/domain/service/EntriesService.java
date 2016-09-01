package ar.edu.itba.pf.newsvisualization.domain.service;

import ar.edu.itba.pf.newsvisualization.domain.model.request.Grouping;
import ar.edu.itba.pf.newsvisualization.domain.model.response.*;
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

    public List<MediaStats> getAggregatedMedia(LocalDate from, LocalDate to, List<String> categories, Integer limit) {
        List<Object[]> queryResult;
        try {
            if (CollectionUtils.isEmpty(categories)) {
                queryResult =
                        entries.countByDateAndMedia(sdf.parse(dtf.format(from)), sdf.parse(dtf.format(to)));
            } else {
                queryResult =
                        entries.countByDateAndMedia(sdf.parse(dtf.format(from)), sdf.parse(dtf.format(to)), categories);
            }
        } catch (ParseException e) {
            throw new IllegalArgumentException("Date format not supported");
        }
        final Map<String, Long> countByMedia = Maps.newHashMap();
        final Map<String, List<CategoryStats>> countDetailByMedia = Maps.newHashMap();
        final List<MediaStats> stats = Lists.newLinkedList();

        if (!CollectionUtils.isEmpty(queryResult)) {
            queryResult.forEach(q -> {
                String media = String.valueOf(q[2]);
                Long count = (Long) q[0];

                countByMedia.putIfAbsent(media, 0L);
                Long previousCount = countByMedia.get(media);
                countByMedia.put(media, previousCount + count);

                countDetailByMedia.putIfAbsent(media, Lists.newLinkedList());
                countDetailByMedia.get(media).add(new CategoryStats(count, String.valueOf(q[1])));
            });
        }

        countByMedia.forEach((m, c) -> stats.add(new DetailedMediaStats(c, m, countDetailByMedia.get(m))));
        stats.sort((s1, s2) -> s2.getCount().compareTo(s1.getCount()));

        return stats.subList(0, limit);
    }

    public List<Count> getCounts(LocalDate from, LocalDate to, List<String> media, Grouping groupBy) {
        switch (groupBy) {
            case DATE:
                return this.getMediaCountByDate(from, to, media);
            default:
                return this.getDateCountByMedia(from, to, media);
        }
    }

    public WordCountResponse getWordCount(List<String> media, Long minQuantity, Integer minWordLength) {
        final List<WordCount> ret = Lists.newLinkedList();
        final Map<String, Long> wordCount = Maps.newHashMap();
        this.entries.getContents().forEach(content -> {
            String[] wordArray = content.split(" ");
            for (int i = 0 ; i < wordArray.length ; i++) {
                String word = wordArray[i].toLowerCase();
                CharMatcher matcher = CharMatcher.JAVA_LETTER;
                word = matcher.retainFrom(word);
                if (word.length() >= minWordLength) {
                    wordCount.putIfAbsent(word, 0L);
                    long count = wordCount.get(word);
                    wordCount.put(word, ++count);
                }
            }
        });
        Long min = Long.MAX_VALUE, max = 0L;
        for (Map.Entry<String, Long> entry : wordCount.entrySet()) {
            Long value = entry.getValue();
            String key = entry.getKey();
            if (value >= minQuantity) {
                if (min > value) min = value;
                if (max < value) max = value;
                ret.add(new WordCount(key, value));
            }
        }
        return new WordCountResponse(min, max, ret.stream().collect(Collectors.toList()));
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
