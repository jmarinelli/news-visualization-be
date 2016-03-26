package ar.edu.itba.pf.newsvisualization.domain.model.response;

import java.util.List;

/**
 * Created by juanjosemarinelli on 3/25/16.
 */
public class WordCountResponse {

    private Long min;
    private Long max;
    private List<WordCount> wordCount;

    public WordCountResponse(Long min, Long max, List<WordCount> wordCount) {
        this.min = min;
        this.max = max;
        this.wordCount = wordCount;
    }

    public Long getMin() {
        return min;
    }

    public Long getMax() {
        return max;
    }

    public List<WordCount> getWordCount() {
        return wordCount;
    }
}
