package application.project.CustomSpecification.JdbcFilterProcessor;

import org.springframework.core.MethodParameter;
import org.springframework.core.ResolvableType;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import application.project.CustomSpecification.Core.EmptyFilterExpression;
import application.project.CustomSpecification.Core.FilterExpression;
import application.project.CustomSpecification.FilterParser.FilterParser;
import application.project.repository.JdbcSpecification.IjdbcSpecification;
import application.project.repository.JdbcSpecification.JdbcFilterSpecification;
import application.project.util.CustomAnnotation.Filterable;

// TODO: Replace with the correct package if FilterParser exists elsewhere

public class JdbcFilterProcessor implements HandlerMethodArgumentResolver {
    private final FilterParser parser;
    
    public JdbcFilterProcessor(FilterParser parser) {
        this.parser = parser;
       
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Filterable.class)
            && IjdbcSpecification.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
                                    
    WebDataBinder binder = binderFactory.createBinder(webRequest, null, null);
    ConversionService conversionService = binder.getConversionService();
    String filterStr = webRequest.getParameter("filter");

        // 2. If it's null or blank, use an empty AST instead of calling parser.parse("")
        FilterExpression ast;
        if (filterStr == null || filterStr.isBlank()) {
            ast = new EmptyFilterExpression(); // <-- empty expression, no clauses
        } else {
            ast = parser.parse(filterStr);
        }

        // 3. Figure out the generic entity T
        Class<?> entityType = resolveEntityType(parameter);

        // 4. Build and return the spec; spec.toSqlClause() should produce "" if ast is empty
        return new JdbcFilterSpecification<>(ast, entityType, conversionService);
    }

    private Class<?> resolveEntityType(MethodParameter parameter) {
        ResolvableType rt = ResolvableType.forMethodParameter(parameter);
        return rt.getGeneric(0).resolve();
    }

}
