package ar.edu.itba.pf.newsvisualization.domain.model.response;

/**
 * Created by juanjosemarinelli on 3/25/16.
 */
public class WordCount {
    private String word;
    private Integer quantity;

    public WordCount(String word, Integer quantity) {
        this.word = word;
        this.quantity = quantity;
    }

    public String getWord() {
        return word;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
