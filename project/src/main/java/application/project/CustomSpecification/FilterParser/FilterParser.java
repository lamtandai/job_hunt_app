package application.project.CustomSpecification.FilterParser;

import java.util.List;

import org.springframework.stereotype.Component;

import application.project.CustomSpecification.Core.ComparisonNode;
import application.project.CustomSpecification.Core.FilterExpression;
import application.project.CustomSpecification.Core.LogicalNode;

@Component
public class FilterParser {
    private final FilterLexer lexer = new FilterLexer();

    /**
     * @param input raw filter string (e.g. "a:1;b>2|(c<3)")
     * @return root LogicalNode or ComparisonNode
     */
    public FilterExpression parse(String input) {
        List<Token> tokens = lexer.tokenize(input);
        return parseExpression(tokens, 0, tokens.size());
    }

    private FilterExpression parseExpression(List<Token> tokens, int start, int end) {
        // very basic recursive descent:
        // handle parentheses, comparisons, and AND/OR separators (';' and '|')
        // This is a placeholder — for production, expand to full grammar.
        // For now, split on top-level ';' for AND:
        List<Integer> andPositions = Token.findTopLevel(tokens, start, end, Token.Type.AND);
        if (!andPositions.isEmpty()) {
            List<FilterExpression> parts = new java.util.ArrayList<>();
            int prev = start;
            for (int pos : andPositions) {
                parts.add(parseExpression(tokens, prev, pos));
                prev = pos + 1;
            }
            parts.add(parseExpression(tokens, prev, end));
            return new LogicalNode(LogicalNode.Op.AND, parts);
        }
        // split on top-level '|' for OR
        List<Integer> orPositions = Token.findTopLevel(tokens, start, end, Token.Type.OR);
        if (!orPositions.isEmpty()) {
            List<FilterExpression> parts = new java.util.ArrayList<>();
            int prev = start;
            for (int pos : orPositions) {
                parts.add(parseExpression(tokens, prev, pos));
                prev = pos + 1;
            }
            parts.add(parseExpression(tokens, prev, end));
            return new LogicalNode(LogicalNode.Op.OR, parts);
        }
        // Comparison or parenthesis
        if (tokens.get(start).getType() == Token.Type.LPAREN &&
                tokens.get(end - 1).getType() == Token.Type.RPAREN) {
            return parseExpression(tokens, start + 1, end - 1);
        }
        // Must be a comparison: field OP value
        String field = tokens.get(start).getText();
        String op = tokens.get(start + 1).getText();
        String raw = tokens.get(start + 2).getText();
        return new ComparisonNode(field, op, raw);
    }
}
