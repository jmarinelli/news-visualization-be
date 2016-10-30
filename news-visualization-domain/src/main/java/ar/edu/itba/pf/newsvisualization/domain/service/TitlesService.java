package ar.edu.itba.pf.newsvisualization.domain.service;

import ar.edu.itba.pf.newsvisualization.domain.model.Title;
import ar.edu.itba.pf.newsvisualization.domain.model.response.TitlePosition;
import ar.edu.itba.pf.newsvisualization.domain.model.response.TitlePositionResponse;
import ar.edu.itba.pf.newsvisualization.domain.repository.TitlesRepository;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

/**
 * Created by juanjosemarinelli on 4/9/16.
 */
//@Service
public class TitlesService {

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
    private TitlesRepository titlesRepository;

    @Autowired
    public TitlesService(TitlesRepository titlesRepository) {
        this.titlesRepository = titlesRepository;
    }

    public List<TitlePosition> getTitlePositions(Boolean includeAllTitles) {
        List<TitlePosition> titlePositions = Lists.newLinkedList();
        Set<String> titleList = Sets.newHashSet();
        Iterable<Title> titles = this.titlesRepository.findAll();
        titles.forEach(t -> {
            titleList.add(t.getTitle1());
            titleList.add(t.getTitle2());
            titleList.add(t.getTitle3());
        });

        titles.forEach(t -> {

            titlePositions.add(new TitlePosition(t.getTitle1(), 3, t.getTimestamp()));
            titlePositions.add(new TitlePosition(t.getTitle2(), 2, t.getTimestamp()));
            titlePositions.add(new TitlePosition(t.getTitle3(), 1, t.getTimestamp()));
            if (includeAllTitles) {
                titleList.forEach(tt -> {
                    if (!(tt.equals(t.getTitle1()) || tt.equals(t.getTitle2()) || tt.equals(t.getTitle3()))) {
                        titlePositions.add(new TitlePosition(tt, 0, t.getTimestamp()));
                    }
                });
            }
        });

        titlePositions.sort((t1, t2) -> t1.getDate().compareTo(t2.getDate()));

        return titlePositions;
    }
}
