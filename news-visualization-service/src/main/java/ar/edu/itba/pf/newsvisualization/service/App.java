package ar.edu.itba.pf.newsvisualization.service;

import ar.edu.itba.pf.newsvisualization.service.argument.KeyArgumentResolver;
import ar.edu.itba.pf.newsvisualization.service.argument.KeyInterceptor;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by juanjosemarinelli on 2/21/16.
 */
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = {"ar.edu.itba.pf"})
@EntityScan(basePackages = {"ar.edu.itba.pf.newsvisualization.domain.model"})
@EnableJpaRepositories(basePackages = {"ar.edu.itba.pf.newsvisualization.domain.repository"})
public class App extends WebMvcConfigurerAdapter {

    @Autowired
    private KeyInterceptor keyInterceptor;
    @Autowired
    private KeyArgumentResolver keyArgumentResolver;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(getLocalDateConverter());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(keyInterceptor);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        converters.add(new MappingJackson2HttpMessageConverter(getObjectMapper()));
    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {

        super.configureHandlerExceptionResolvers(exceptionResolvers);

        ExceptionHandlerExceptionResolver exceptionHandlerExceptionResolver = new ExceptionHandlerExceptionResolver();
        List<HttpMessageConverter<?>> messageConverters = exceptionHandlerExceptionResolver.getMessageConverters();
        messageConverters.add(new MappingJackson2HttpMessageConverter(getObjectMapper()));
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(keyArgumentResolver);
    }

    public KeyInterceptor getKeyInterceptor() {
        return keyInterceptor;
    }

    public void setKeyInterceptor(KeyInterceptor keyInterceptor) {
        this.keyInterceptor = keyInterceptor;
    }

    public KeyArgumentResolver getKeyArgumentResolver() {
        return keyArgumentResolver;
    }

    public void setKeyArgumentResolver(KeyArgumentResolver keyArgumentResolver) {
        this.keyArgumentResolver = keyArgumentResolver;
    }

    private ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        return objectMapper;
    }

    private Converter<String, LocalDate> getLocalDateConverter() {
        return new Converter<String, LocalDate>() {
            final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            public LocalDate convert(String s) {
                return LocalDate.parse(s, dtf);
            }
        };
    }
}
