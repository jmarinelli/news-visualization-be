package ar.edu.itba.pf.newsvisualization.service;

import ar.edu.itba.pf.newsvisualization.domain.model.Client;
import ar.edu.itba.pf.newsvisualization.domain.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by juanjosemarinelli on 12/7/16.
 */
@Component
public class KeyInterceptor extends HandlerInterceptorAdapter {

    private ClientRepository clients;

    @Autowired
    public KeyInterceptor(ClientRepository clients) {
        this.clients = clients;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String key = request.getParameter("key");
        if (StringUtils.isEmpty(key)) {
            response.setStatus(400);
            return false;
        }

        Client client = this.clients.findOneByKey(key);

        if (client == null) {
            response.setStatus(400);
            return false;
        }

        return super.preHandle(request, response, handler);
    }
}
