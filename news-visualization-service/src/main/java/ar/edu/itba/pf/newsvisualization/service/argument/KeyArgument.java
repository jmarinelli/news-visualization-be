package ar.edu.itba.pf.newsvisualization.service.argument;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by juanjosemarinelli on 12/13/16.
 */
public class KeyArgument {
    private String key;
    private List<Integer> defaultMedia = Lists.newArrayList();
    private List<String> defaultKeywords = Lists.newArrayList();

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
