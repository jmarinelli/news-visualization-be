package ar.edu.itba.pf.newsvisualization.service.controller;

import ar.edu.itba.pf.newsvisualization.domain.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by juanjosemarinelli on 2/21/16.
 */
@RestController
@RequestMapping(value = "categories")
public class CategoriesController {

    private MediaService media;

    @Autowired
    public CategoriesController(MediaService media) {
        this.media = media;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<String> find() {
        return this.media.getCategoryList();
    }
}
