package ar.edu.itba.pf.newsvisualization.service.controller;

import ar.edu.itba.pf.newsvisualization.domain.model.Client;
import ar.edu.itba.pf.newsvisualization.domain.model.ClientMedia;
import ar.edu.itba.pf.newsvisualization.domain.repository.ClientMediaRepository;
import ar.edu.itba.pf.newsvisualization.domain.repository.ClientRepository;
import ar.edu.itba.pf.newsvisualization.service.KeyArgument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by juanjosemarinelli on 11/29/16.
 */
@RestController
@RequestMapping(value = "test")
public class TestController {

    private ClientMediaRepository entries;
    private ClientRepository clients;

    @Autowired
    public TestController(ClientMediaRepository entries, ClientRepository clients) {
        this.entries = entries;
        this.clients = clients;
    }

    @RequestMapping(method = RequestMethod.GET, value = "entries")
    public Iterable<ClientMedia> getEntries() {
        return entries.findAll();
    }

    @RequestMapping(method = RequestMethod.GET, value = "clientmedia")
    public Iterable<ClientMedia> getClientMedia(@RequestParam String key) {
        return entries.findByClientKey(key);
    }

    @RequestMapping(method = RequestMethod.GET, value = "clients")
    public KeyArgument getClients(@RequestParam String key, KeyArgument keyArgument) {
        return keyArgument;
    }
}
