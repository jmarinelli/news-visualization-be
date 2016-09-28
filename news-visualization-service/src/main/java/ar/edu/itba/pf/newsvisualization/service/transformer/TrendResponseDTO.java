package ar.edu.itba.pf.newsvisualization.service.transformer;

import java.util.List;

/**
 * Created by juanjosemarinelli on 9/27/16.
 */
public class TrendResponseDTO {
    private String date;
    private List<CountDTO> words;

    public TrendResponseDTO(String date, List<CountDTO> words) {
        this.date = date;
        this.words = words;
    }

    public String getDate() {
        return date;
    }

    public List<CountDTO> getWords() {
        return words;
    }
}
