package ar.edu.itba.pf.newsvisualization.service.argument;

import ar.edu.itba.pf.newsvisualization.domain.repository.ClientKeywordRepository;
import ar.edu.itba.pf.newsvisualization.domain.repository.ClientMediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by juanjosemarinelli on 12/13/16.
 */
@Component
public class KeyArgumentResolver implements HandlerMethodArgumentResolver {

    private final ClientKeywordRepository keywords;
    private final ClientMediaRepository media;

    @Autowired
    public KeyArgumentResolver(ClientKeywordRepository keywords, ClientMediaRepository media) {
        super();
        this.keywords = keywords;
        this.media = media;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return KeyArgument.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        if (KeyArgument.class.isAssignableFrom(parameter.getParameterType())) {
            String key = webRequest.getParameter("key");
            List<String> keywordList = keywords.findByClientKey(key).stream().map(keyword -> keyword.getKeyword()).collect(Collectors.toList());
            List<Integer> mediaList = media.findByClientKey(key).stream().map(keyword -> keyword.getMediaId()).collect(Collectors.toList());


            return new KeyArgument(key, mediaList, keywordList);
        }
        return null;
    }

}
