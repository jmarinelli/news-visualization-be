package ar.edu.itba.pf.newsvisualization.domain.service;

import ar.edu.itba.pf.newsvisualization.domain.repository.EntriesRepository;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * Created by juanjosemarinelli on 3/25/16.
 */
@Service
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
}
