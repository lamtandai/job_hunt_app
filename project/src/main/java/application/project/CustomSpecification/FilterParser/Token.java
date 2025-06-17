package application.project.CustomSpecification.FilterParser;

import java.util.List;

public class Token {
public enum Type { IDENT, OP, VALUE, AND, OR, LPAREN, RPAREN }
    private final Type type;
    private final String text;

    public Token(Type type, String text) {
        this.type = type;
        this.text = text;
    }
    public Type getType() { return type; }
    public String getText() { return text; }

    /**
     * Find positions of top-level tokens of given type between [start,end)
     */
    public static List<Integer> findTopLevel(List<Token> tokens, int start, int end, Type t) {
        List<Integer> positions = new java.util.ArrayList<>();
        int depth = 0;
        for (int i = start; i < end; i++) {
            Token tk = tokens.get(i);
            if (tk.type == Type.LPAREN) depth++;
            if (tk.type == Type.RPAREN) depth--;
            if (depth == 0 && tk.type == t) positions.add(i);
        }
        return positions;
    }
}
