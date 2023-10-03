import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class getTokenArray {
    private static int i, n, lineCounter;


    private static String[] instructions2 = new String[]{"add", "nand", "lw", "sw", "beq", "jalr", "halt", "noop",".fill"};

    private static final String[] R_TYPE_INSTRUCTIONS = {"add", "nand"};
    private static final String[] I_TYPE_INSTRUCTIONS = {"lw", "sw", "beq"};
    private static final String[] J_TYPE_INSTRUCTIONS = {"jalr"};
    private static final String[] O_TYPE_INSTRUCTIONS = {"halt", "noop"};
    private static final String[] fill_INSTRUCTIONS = {".fill"};


    private static   String[] getTokenArray(String input) throws SyntaxError {
        List<String> tokenList = new ArrayList<>();
        ExprTokenizer tokenizer = new ExprTokenizer(input);
        String instructionType = "";

        while (tokenizer.hasNextToken()) {
            String token = tokenizer.consume();
            if (!token.isEmpty()) {
                if (isLabel(token)) {
                    tokenList.add(token);
                } else if (Arrays.asList(fill_INSTRUCTIONS).contains(token)) {
                    tokenList.add(token);
                    if (tokenizer.hasNextToken()) {
                        tokenList.add(tokenizer.consume());
                        break;
                    }
                    else {
                        throw new SyntaxError("Invalid .fill directive. Expected a value.");
                    }
                } else if (isInstructionType(token)) {
                    instructionType = token;
                    tokenList.add(token);
                    //เช็คจำนวนFields
                    int expectedFields = determineExpectedFields(instructionType);
                    if (expectedFields >= 0) {
                        for (int i = 0; i < expectedFields; i++) {
                            token = tokenizer.consume();
                            if (!token.isEmpty()) {
                                tokenList.add(token);
                            }
                        }
                        break;
                    }
                }
            }
        }
        int expectedTokenCount = Arrays.asList(R_TYPE_INSTRUCTIONS).contains(instructionType) ? 5 : 4;

        while (tokenList.size() > expectedTokenCount) {
            tokenList.remove(tokenList.size() - 1);
        }
        return tokenList.toArray(new String[0]);
    }
    private static boolean isLabel(String token) {
        String labelRegex = "^[a-zA-Z][a-zA-Z0-9]{0,5}$";
        return token.matches(labelRegex) &&!isInstruction(token);
    }
    private static boolean isInstruction(String token) {
        return Arrays.asList(R_TYPE_INSTRUCTIONS).contains(token)
                || Arrays.asList(I_TYPE_INSTRUCTIONS).contains(token)
                || Arrays.asList(J_TYPE_INSTRUCTIONS).contains(token)
                || Arrays.asList(O_TYPE_INSTRUCTIONS).contains(token) || Arrays.stream(fill_INSTRUCTIONS).anyMatch(token::equals);
    }
    private static boolean isInstructionType(String token) throws SyntaxError {
        if (checkInstruction(token)) {
            return true;
        } else {
            throw new SyntaxError("Invalid instruction: " + token);
        }

    }
    private static int determineExpectedFields(String instructionType) throws SyntaxError {
        if (Arrays.asList(R_TYPE_INSTRUCTIONS).contains(instructionType)) {
            return 3; // R-Type instructions have 3 fields
        } else if (Arrays.asList(I_TYPE_INSTRUCTIONS).contains(instructionType)) {
            return 3; // I-Type instructions have 3 fields
        } else if (Arrays.asList(J_TYPE_INSTRUCTIONS).contains(instructionType)) {
            return 2; // J-Type instructions have 2 fields
        } else if (Arrays.asList(O_TYPE_INSTRUCTIONS).contains(instructionType)) {
            return 0; // O-Type instructions have 0 fields
        } else if (Arrays.asList(fill_INSTRUCTIONS).contains(instructionType)) {
            return 1; // .fill directive has 1 field
        } else if (isLabel(instructionType)) {
            return 1; // Labels have 1 field
        } else {
            throw new SyntaxError("Invalid instruction format. Expected more fields for instruction: " + instructionType);
        }
    }
    public static class SyntaxError extends Exception {
        public SyntaxError(String message) {
            super(message);
        }
    }
    private static boolean checkInstruction(String token){
        for (String instruction : instructions2){
            if (token.equals(instruction))
                return true;
        }
        return false;
    }

    private static void printInstruction(String[] tokens){
        if (tokens[i].equals("add") || tokens[i].equals("nand")){
            System.out.print(tokens[i] + " " + tokens[i+1] + " " + tokens[i+2] + " " + tokens[i+3]);
            i += 4;
        } else if (tokens[i].equals("lw") || tokens[i].equals("sw") || tokens[i].equals("beq")){
            System.out.print(tokens[i] + " " + tokens[i+1] + " " + tokens[i+2] + " " + tokens[i+3]);
            i += 4;
        } else if (tokens[i].equals("jalr")){
            System.out.print(tokens[i] + " " + tokens[i+1] + " " + tokens[i+2]);
            i += 3;
        } else {    // halt, noop
            System.out.print(tokens[i]);
            i += 1;
        }
    }

    private static void printAllInstructions(String[] tokens){
        n = tokens.length;
        lineCounter = 0;
        for (i = 0; i < n;){
            if (checkInstruction(tokens[i])) {      // Ins
                printInstruction(tokens);

            } else {    // Label
                if (tokens[i+1].equals(".fill")){   // .fill (var|line)
                    System.out.print(tokens[i] + " " + tokens[i+1] + " " + tokens[i+2]);
                    i += 3;
                } else {        // Ins (line)
                    System.out.print(tokens[i] + " ");
                    i += 1;
                    printInstruction(tokens);
                }
            }
            System.out.println();
            lineCounter++;
        }
    }

    public static void getTokens(){
        try {
            String fileName = "src/test2.txt";
            List<String> program = ReadFile.readFile(fileName);
            List<String> tokenresult = new ArrayList<>();
            for (int i = 0; i < program.size(); i++) {
                String inputLine = program.get(i);
                try {
                    String[] tokens = getTokenArray.getTokenArray(inputLine);
                    for(String T : tokens){
                        tokenresult.add(T);
                    }
                } catch (getTokenArray.SyntaxError e) {
                    System.err.println("Syntax Error in line " + (i) + ": " + e.getMessage());
                }
            }
            System.out.println(tokenresult);
            getTokenArray.printAllInstructions(tokenresult.toArray(new String[0]));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LexicalError e) {
            System.err.println("Lexical Error: " + e.getMessage());
        }
    }


}
