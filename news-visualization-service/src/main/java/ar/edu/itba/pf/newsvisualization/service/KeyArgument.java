package ar.edu.itba.pf.newsvisualization.service;

import java.util.List;

/**
 * Created by juanjosemarinelli on 12/13/16.
 */
public class KeyArgument {
    private String key;
    private List<Integer> defaultMedia;
    private List<String> defaultKeywords;

    public KeyArgument(String key, List<Integer> defaultMedia, List<String> defaultKeywords) {
        this.key = key;
        this.defaultMedia = defaultMedia;
        this.defaultKeywords = defaultKeywords;
    }

    public String getKey() {
        return key;
    }

    public List<Integer> getDefaultMedia() {
        return defaultMedia;
    }

    public List<String> getDefaultKeywords() {
        return defaultKeywords;
    }
}
