package ar.edu.itba.pf.newsvisualization.service.transformer;

import ar.edu.itba.pf.newsvisualization.domain.model.TrendResponse;
import ar.edu.itba.pf.newsvisualization.domain.model.WordCloudResponse;
import com.google.common.collect.Lists;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by juanjosemarinelli on 9/27/16.
 */
public class MainTransformer {

    public static List<List<Object>> transformWordCloudResponse(WordCloudResponse response, Integer minFreq) {
        List<List<Object>> ret = Lists.newLinkedList();

        if (response != null && !CollectionUtils.isEmpty(response.getWords())) {
            response.getWords().forEach(w -> {
                if (w.getCount() >= minFreq) {
                    List<Object> word = Lists.newLinkedList();

                    word.add(w.getKey());
                    word.add(w.getCount());

                    ret.add(word);
                }
            });
        }

        return ret;
    }

    public static String transformTrendResponse(TrendResponse resp, List<String> terms) {
        final Integer maxCount = getMaxCount(resp);
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append("x,");
        terms.forEach(t -> sBuilder.append(t).append(",") );
        sBuilder.deleteCharAt(sBuilder.length() - 1);
        sBuilder.append(System.getProperty("line.separator"));

        if (resp != null) {
            resp.getAggregations().getDayOfWeek().getBuckets().forEach(b -> {
                sBuilder.append(b.getDate().split("T")[0]).append(",");
                terms.forEach(term -> {
                    sBuilder.append("0,");
                    b.getTitles().getBuckets().forEach(title -> {
                        if (term.equals(title.getKey())) {
                            Integer count = title.getCount() * 100 / maxCount;
                            sBuilder.replace(sBuilder.length() - 2, sBuilder.length(), count + ",");
                        }
                    });
                });
                sBuilder.deleteCharAt(sBuilder.length() - 1);
                sBuilder.append(System.getProperty("line.separator"));
            });
        }

        return sBuilder.toString();
    }

    private static Integer getMaxCount(TrendResponse resp) {
        Integer maxCount = Integer.MIN_VALUE;

        for (TrendResponse.ESAggregation.DayOfTheWeek.ESBucket b : resp.getAggregations().getDayOfWeek().getBuckets()) {
            for (TrendResponse.ESAggregation.DayOfTheWeek.ESBucket.ESField.ESSubBucket t : b.getTitles().getBuckets()) {
                if (t.getCount() >= maxCount) maxCount = t.getCount();
            }
        }

        return maxCount;
    }
}
