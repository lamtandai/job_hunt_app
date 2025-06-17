package application.project.repository.JdbcSpecification;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.core.convert.ConversionService;

import application.project.CustomSpecification.Core.ComparisonNode;
import application.project.CustomSpecification.Core.FilterExpression;
import application.project.CustomSpecification.Core.LogicalNode;

public class JdbcFilterSpecification<T> implements IjdbcSpecification<T> {
    private final FilterExpression ast;
    private final String tableName;
    private final ConversionService conversionService;
    private final Class<T> entityClass;

    @SuppressWarnings("unchecked")
    public JdbcFilterSpecification(FilterExpression ast,
                                   Class<?> entityType,
                                   ConversionService conversionService) {
        this.ast = ast;
        this.tableName = deriveTableName(entityType);
        this.conversionService = conversionService;
        this.entityClass = (Class<T>) entityType;
    }
    @Override
    public String toSqlClause() {
        if (ast.isEmpty()) {
            return "";
        }
        return renderExpression(ast);
    }

    @Override
    public Map<String, Object> getParameters() {
        Map<String, Object> params = new LinkedHashMap<>();
        collectParameters(ast, params);
        return params;
    }
    private String renderExpression(FilterExpression expr) {
        if (expr instanceof ComparisonNode cn) {
            String col = camelToSnake(cn.getField());
            String op  = mapOperator(cn.getOperator());
            return col + " " + op + " :" + cn.getField();
        }
        if (expr instanceof LogicalNode ln) {
            return ln.getChildren().stream()
                     .map(this::renderExpression)
                     .collect(Collectors.joining(
                         ln.getOp() == LogicalNode.Op.AND ? " AND " : " OR ",
                         "(", ")"));
        }
        return "";
    }
    private void collectParameters(FilterExpression expr, Map<String,Object> out) {
        if (expr instanceof ComparisonNode cn) {
            Object raw = cn.getRawValue();
            Object val = convertValue(cn.getField(), raw, cn.getOperator());
            out.put(cn.getField(), val);
        }
        if (expr instanceof LogicalNode ln) {
            ln.getChildren().forEach(child -> collectParameters(child, out));
        }
    }
    private Object convertValue(String field, Object raw, String op) {
        String str = raw.toString();
        if (":".equals(op)) {
            // LIKE
            return "%" + str + "%";
        }
        // fallback: try conversion to target type via ConversionService
        return conversionService.convert(str, String.class);
    }

    private String mapOperator(String op) {
        return switch (op) {
            case ":" -> "LIKE";
            default   -> op;
        };
    }

    private String camelToSnake(String s) {
        return s.replaceAll("([a-z])([A-Z])","$1_$2").toLowerCase();
    }

    private String pluralize(String word) {
    // 1) If it ends in a vowel + “y”, just add “s” (e.g. “key” → “keys”)
    if (word.matches(".*[aeiou]y$")) {
        return word + "s";
    }
    // 2) If it ends in consonant + “y”, drop “y” add “ies” (“company” → “companies”)
    if (word.endsWith("y")) {
        return word.substring(0, word.length() - 1) + "ies";
    }
    // 3) If it ends in s, x, z, “ch” or “sh”, add “es” (“class” → “classes”)
    if (word.matches(".*(s|x|z|ch|sh)$")) {
        return word + "es";
    }
    // 4) Otherwise just add “s”
    return word + "s";
    }
    private String deriveTableName(Class<?> cls) {
        // e.g. Company.class -> "companies"
        return camelToSnake(pluralize(cls.getSimpleName()));
    }

    @Override
    public String getTableName() {
        return tableName;
    }
    @Override
    public Class<T> getEntityClass() {
        return entityClass;
    }

 
    
}
