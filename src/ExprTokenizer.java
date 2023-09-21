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

    // private boolean isValidInstruction(String instruction) {
    //     String[] validInstructions = {"add", "nand", "lw", "sw", "beq", "jalr", "halt", "noop"};
    //     for (String valid : validInstructions) {
    //         if (instruction.equals(valid)) {
    //             return true;
    //         }
    //     }
    //     return false;
    // }

    private void computeNext() {
        StringBuilder s = new StringBuilder();
    
        // Skip whitespace and newline characters
        while (pos < src.length() && (isWhitespace(src.charAt(pos)) )) {
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
            //.fill
            while (pos < src.length() && !isWhitespace(src.charAt(pos))) {
                s.append(src.charAt(pos));
                pos++;
            }
        } else if (Character.isLetter(c)) {
        
            //label or instruction
            while (pos < src.length() && (!isWhitespace(src.charAt(pos)) )) {
                s.append(src.charAt(pos));
                pos++;
            }
        } else if ((Character.isDigit(c) || (c == '-' && s.length() == 1)) && (pos < src.length() - 1)) {
            //numeric field (positive or negative)
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



//    StringBuilder s = new StringBuilder();
    
//         // Skip whitespace and newline characters
//         while (pos < src.length() && (isWhitespace(src.charAt(pos)) || src.charAt(pos) == '\n')) {
//             pos++;
//         }
    
//         if (pos == src.length()) {
//             next = null;
//             return;
//         }

//         char c = src.charAt(pos);
//         s.append(c);
//         pos++;
    
//         if (c == '.') {
//             // Handle .fill
//             while (pos < src.length() && !isWhitespace(src.charAt(pos))) {
//                 s.append(src.charAt(pos));
//                 pos++;
//             }
//         } else if (Character.isLetter(c)) {
//             // Handle label or instruction
//             while (pos < src.length() && (!isWhitespace(src.charAt(pos)) && src.charAt(pos) != '\n')) {
//                 s.append(src.charAt(pos));
//                 pos++;
//             }
//         } else if ((Character.isDigit(c) || (c == '-' && s.length() == 1)) && (pos < src.length() - 1)) {
//             // Handle numeric field (positive or negative)
//             while (pos < src.length() && (Character.isDigit(src.charAt(pos)))) {
//                 s.append(src.charAt(pos));
//                 pos++;
//             }
//         }
    
//         next = s.toString();




//    // Handle label or instruction
//             StringBuilder instructionBuilder = new StringBuilder();
//             while (pos < src.length() && Character.isLetterOrDigit(src.charAt(pos))) {
//                 instructionBuilder.append(src.charAt(pos));
//                 pos++;
//             }
        
//             String instruction = instructionBuilder.toString();
        
//             // Check if the instruction is valid and determine its type
//             if (isValidInstruction(instruction)) {
//                 switch (instruction) {
//                     case "add":
//                     case "nand":
//                         // R-Type instruction (3 fields)
//                         for (int i = 0; i < 3 && pos < src.length(); i++) {
//                             if (!isWhitespace(src.charAt(pos)) && src.charAt(pos) != '\n') {
//                                 s.append(src.charAt(pos));
//                                 pos++;
//                             }
//                         }
//                         break;
//                     case "lw":
//                     case "sw":
//                     case "beq":
//                         // I-Type instruction (3 fields)
//                         for (int i = 0; i < 3 && pos < src.length(); i++) {
//                             if (!isWhitespace(src.charAt(pos)) && src.charAt(pos) != '\n') {
//                                 s.append(src.charAt(pos));
//                                 pos++;
//                             }
//                         }
//                         break;
//                     case "jalr":
//                         // J-Type instruction (2 fields)
//                         for (int i = 0; i < 2 && pos < src.length(); i++) {
//                             if (!isWhitespace(src.charAt(pos)) && src.charAt(pos) != '\n') {
//                                 s.append(src.charAt(pos));
//                                 pos++;
//                             }
//                         }
//                         break;
//                     default:
//                         // O-Type instruction (no fields)
//                         break;
//                 }
//             } else {
//                 // Invalid instruction, handle accordingly
//                 while (pos < src.length() && (!isWhitespace(src.charAt(pos)) && src.charAt(pos) != '\n')) {
//                     s.append(src.charAt(pos));
//                     pos++;
//                 }
//             }