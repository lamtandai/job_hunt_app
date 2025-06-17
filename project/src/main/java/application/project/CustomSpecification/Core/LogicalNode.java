package application.project.CustomSpecification.Core;

import java.util.List;

public class LogicalNode implements FilterExpression{
    public enum Op { AND, OR }
    private final Op op;
    private final List<FilterExpression> children;

    public  LogicalNode(Op op, List<FilterExpression> children) {
        this.op = op;
        this.children = children;
    }

    public Op getOp() {
        return op;
    }

    public List<FilterExpression> getChildren() {
        return children;
    }

    @Override
    public boolean isEmpty() {
        return children == null;
    }
}
