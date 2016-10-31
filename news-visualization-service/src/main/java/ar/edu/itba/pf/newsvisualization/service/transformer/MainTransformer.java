package ar.edu.itba.pf.newsvisualization.service.transformer;

import ar.edu.itba.pf.newsvisualization.domain.model.response.main.TrendResponse;
import ar.edu.itba.pf.newsvisualization.domain.model.response.main.WordCloudResponse;
import com.google.common.collect.Lists;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by juanjosemarinelli on 9/27/16.
 */
public class MainTransformer {

    public static List<List<Object>> transformWordCloudResponse(WordCloudResponse response) {
        List<List<Object>> ret = Lists.newLinkedList();

        if (response != null && !CollectionUtils.isEmpty(response.getWords())) {
            response.getWords().forEach(w -> {
                List<Object> word = Lists.newLinkedList();

                word.add(w.getKey());
                word.add(w.getCount());

                ret.add(word);
            });
        }

        return ret;
    }

    public static List<TrendResponseDTO> transformTrendResponse(TrendResponse resp, List<String> terms) {
        final List<TrendResponseDTO> ret = Lists.newLinkedList();

        if (resp != null) {
            resp.getAggregations().getDayOfWeek().getBuckets().forEach(b -> {
                String date = b.getDate();
                List<CountDTO> wordCount = Lists.newLinkedList();
                terms.forEach(t -> wordCount.add(new CountDTO(t)));
                b.getTitles().getBuckets().forEach(t -> {
                    wordCount.stream().filter(w -> w.getWord().equals(t.getKey())).findFirst().orElse(new CountDTO(t.getKey())).setCount(t.getCount());
                });
                ret.add(new TrendResponseDTO(date, wordCount));
            });
        }

        return ret;
    }
}
