package ar.edu.itba.pf.newsvisualization.service.transformer;

/**
 * Created by juanjosemarinelli on 9/27/16.
 */
public class CountDTO {
    private String word;
    private Integer count;

    public CountDTO(String word) {
        this.word = word;
        this.count = 0;
    }

    public String getWord() {
        return word;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
