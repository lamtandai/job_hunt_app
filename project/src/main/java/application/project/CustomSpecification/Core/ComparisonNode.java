package application.project.CustomSpecification.Core;

public class ComparisonNode implements FilterExpression {
    private final String field;
    private final String operator;
    private final String rawValue;

    public ComparisonNode(String field, String operator, String rawValue) {
        this.field = field;
        this.operator = operator;
        this.rawValue = rawValue;
    }

    public String getField() {
        return field;
    }

    public String getOperator() {
        return operator;
    }

    public String getRawValue() {
        return rawValue;
    }

    @Override
    public boolean isEmpty() {
       
        return false;
    }
}
