package application.project.config;

import java.util.List;
import java.util.Set;

import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import application.project.util.Pluralize.Pluralize;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Value("${basepath.baseApi}")
    private String basePath;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
        resolver.setOneIndexedParameters(true);
        argumentResolvers.add(resolver);
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
       
        // Scan all classes with @RestController and end with Controller
        Set<Class<?>> controllerClasses = findAllRestControllers();

        for (Class<?> controllerClass : controllerClasses) {
            String name = controllerClass.getSimpleName();
            if (name.equals("AuthController")){
                String subPath = name.substring(0, name.indexOf("Controller")).toLowerCase();
                configurer.addPathPrefix(basePath + subPath,
                        HandlerTypePredicate.forAssignableType(controllerClass));
                continue;
            }
            if (name.endsWith("Controller")) {
                String subPath = Pluralize.pluralize(name.substring(0, name.indexOf("Controller")).toLowerCase());
                configurer.addPathPrefix(basePath + subPath,
                        HandlerTypePredicate.forAssignableType(controllerClass));
            }
        }
    }

    private Set<Class<?>> findAllRestControllers() {
        // This scans your classpath and finds all @RestController classes
        Reflections reflections = new Reflections("application.project.controller"); // Your base package

        return reflections.getTypesAnnotatedWith(RestController.class);
    }
    
}
