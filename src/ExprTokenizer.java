import java.util.NoSuchElementException;

class LexicalError extends RuntimeException {
    public LexicalError(String message) {
        super(message);
    }
}

class SyntaxError extends RuntimeException {
    public SyntaxError(String message) {
        super(message);
    }
}

class ExprTokenizer {
    private String src;
    private int pos;
    private String next;

    public ExprTokenizer(String src) {
        this.src = src;
        pos = 0;
        computeNext();
    }

    private boolean isWhitespace(char c) {
        return c == ' ' || c == '\t';
    }

    private void computeNext() {
        StringBuilder s = new StringBuilder();
    
        // Skip whitespace and newline characters
        while (pos < src.length() && (isWhitespace(src.charAt(pos)) || src.charAt(pos) == '\n')) {
            pos++;
        }
    
        if (pos == src.length()) {
            next = null;
            return;
        }

        char c = src.charAt(pos);
        s.append(c);
        pos++;
    
        if (c == '.') {
            // Handle .fill
            while (pos < src.length() && !isWhitespace(src.charAt(pos))) {
                s.append(src.charAt(pos));
                pos++;
            }
        } else if (Character.isLetter(c)) {
            // Handle label or instruction
            while (pos < src.length() && (!isWhitespace(src.charAt(pos)) && src.charAt(pos) != '\n')) {
                s.append(src.charAt(pos));
                pos++;
            }
        } else if ((Character.isDigit(c) || (c == '-' && s.length() == 1)) && (pos < src.length() - 1)) {
            // Handle numeric field (positive or negative)
            while (pos < src.length() && (Character.isDigit(src.charAt(pos)))) {
                s.append(src.charAt(pos));
                pos++;
            }
        }
    
        next = s.toString();
    }
    
    
    
    

    public boolean hasNextToken() {
        return next != null;
    }

    public String peek() {
        if (!hasNextToken())
            throw new NoSuchElementException("No more tokens");
        return next;
    }

    public boolean peek(String s) {
        if (!hasNextToken())
            return false;
        return peek().equals(s);
    }

    public String consume() {
        if (!hasNextToken())
            throw new NoSuchElementException("No more tokens");
        else {
            String result= next;  
            computeNext();
            return result;
        }
    }

    public void consume(String s) throws SyntaxError {
        if (peek(s))
            consume();
        else
            throw new SyntaxError(s + " expected");
    }
}

