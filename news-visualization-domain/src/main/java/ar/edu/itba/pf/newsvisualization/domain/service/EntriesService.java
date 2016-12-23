package ar.edu.itba.pf.newsvisualization.domain.service;

import ar.edu.itba.pf.newsvisualization.domain.model.response.*;
import ar.edu.itba.pf.newsvisualization.domain.repository.EntriesRepository;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by lmoscovicz on 12/23/16.
 */
@Service
public class EntriesService {

    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private EntriesRepository entries;

    @Autowired
    public EntriesService(EntriesRepository entries) {this.entries = entries;}

    public List<String> getMedia() {
        return entries.getMedia();
    }

    public List<MediaCategories> getCategories(List<String> medias) {
        List<MediaCategories> ret = Lists.newLinkedList();
        List<Object[]> objects = entries.getMediaCategories(medias);
        List<String> categories = Lists.newLinkedList(getCategoryList());
        Map<String, Map<String, Long>> mediaMap = Maps.newHashMap();

        for (Object[] obj : objects) {
            if (!mediaMap.containsKey(String.valueOf(obj[1]))) {
                Map<String, Long> categoryMap = Maps.newHashMap();
                categories.forEach(c -> categoryMap.put(c, 0L));
                mediaMap.put(String.valueOf(obj[1]), categoryMap);
            }
            mediaMap.get(String.valueOf(obj[1])).put(String.valueOf(obj[2]), (Long) obj[0]);
        }

        mediaMap.forEach((k,v) -> {
            List<Stats> categoryStats = Lists.newLinkedList();
            v.forEach((c, count) -> categoryStats.add(new Stats(count, c)));
            ret.add(new MediaCategories(k, categoryStats));
        });

        return ret;
    }

    public List<String> getCategoryList() {
        List<String> retList = entries.getCategories();
        return retList;
    }
}
