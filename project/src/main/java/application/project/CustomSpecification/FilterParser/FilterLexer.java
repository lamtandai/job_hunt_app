package application.project.CustomSpecification.FilterParser;

import java.util.ArrayList;
import java.util.List;

public class FilterLexer {
public List<Token> tokenize(String input) {
        List<Token> out = new ArrayList<>();
        int i = 0, n = input.length();
        while (i < n) {
            char c = input.charAt(i);
            if (Character.isWhitespace(c)) { i++; continue; }
            if (c == '(') { out.add(new Token(Token.Type.LPAREN, "(")); i++; continue; }
            if (c == ')') { out.add(new Token(Token.Type.RPAREN, ")")); i++; continue; }
            if (c == ';') { out.add(new Token(Token.Type.AND, ";")); i++; continue; }
            if (c == '|') { out.add(new Token(Token.Type.OR,  "|")); i++; continue; }
            // operator symbols
            if ("=<>:".indexOf(c)>=0) {
                StringBuilder op = new StringBuilder();
                op.append(c);
                i++;
                // handle >= or <=
                if (i<n && input.charAt(i)=='=') { op.append('='); i++; }
                out.add(new Token(Token.Type.OP, op.toString()));

                continue;
            }
            // identifier or value
            if (Character.isLetterOrDigit(c) || c=='_' || c=='-') {
                StringBuilder sb = new StringBuilder();
                while (i<n && (Character.isLetterOrDigit(input.charAt(i))|| input.charAt(i)=='_'|| input.charAt(i)=='-'|| input.charAt(i)=='.')) {
                    sb.append(input.charAt(i)); i++;
                }
                String tok = sb.toString();
                // crude check: if next non-space is OP, this was IDENT, else VALUE
                int j=i; while (j<n && Character.isWhitespace(input.charAt(j))) j++;
                if (j<n && "=<>:".indexOf(input.charAt(j))>=0) {
                    out.add(new Token(Token.Type.IDENT, tok));
                } else if (tok.equals("is")){
                    out.add(new Token(Token.Type.IDENT, tok));
                } else{
                    out.add(new Token(Token.Type.VALUE, tok));
                }
                continue;
            }
            // anything else skip
            i++;
        }
        return out;
    }
}
