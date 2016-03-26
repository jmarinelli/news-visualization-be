package ar.edu.itba.pf.newsvisualization.domain.model.response;

import java.util.List;

/**
 * Created by juanjosemarinelli on 3/25/16.
 */
public class WordCountResponse {

    private List<WordCount> wordCount;

    public WordCountResponse(List<WordCount> wordCount) {

        this.wordCount = wordCount;
    }

    public List<WordCount> getWordCount() {
        return wordCount;
    }
}
