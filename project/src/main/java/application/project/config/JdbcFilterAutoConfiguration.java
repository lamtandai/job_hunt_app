package application.project.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import application.project.CustomSpecification.FilterParser.FilterParser;
import application.project.CustomSpecification.JdbcFilterProcessor.JdbcFilterProcessor;

@Configuration
public class JdbcFilterAutoConfiguration implements WebMvcConfigurer {
    @Autowired FilterParser parser;
    

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new JdbcFilterProcessor(parser));
    }
}
