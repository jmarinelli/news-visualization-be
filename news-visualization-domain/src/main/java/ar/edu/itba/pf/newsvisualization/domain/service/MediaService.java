package ar.edu.itba.pf.newsvisualization.domain.service;

import ar.edu.itba.pf.newsvisualization.domain.model.response.MediaCategories;
import ar.edu.itba.pf.newsvisualization.domain.model.response.Stats;
import ar.edu.itba.pf.newsvisualization.domain.repository.EntriesRepository;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by juanjosemarinelli on 3/25/16.
 */
//@Service
public class MediaService {

    private EntriesRepository entries;

    @Autowired
    public MediaService(EntriesRepository entries) {this.entries = entries;}

    public List<String> getMediaList() {
        Set<String> mediaList = Sets.newHashSet();
        this.entries.getMedia().forEach(m -> {
            String[] splittedValue = m.split("-");
            mediaList.add(splittedValue[splittedValue.length - 1].trim());
        });
        List<String> retList = Lists.newArrayList(mediaList);
        retList.sort(String.CASE_INSENSITIVE_ORDER);
        return retList;
    }

    public List<String> getCategoryList() {
        List<String> retList = this.entries.getCategories();
        retList.sort(String.CASE_INSENSITIVE_ORDER);
        return retList;
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
}
