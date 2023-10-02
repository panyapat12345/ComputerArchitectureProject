import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class getTokenArray {
    private static final String[] R_TYPE_INSTRUCTIONS = {"add", "nand"};
    private static final String[] I_TYPE_INSTRUCTIONS = {"lw", "sw", "beq"};
    private static final String[] J_TYPE_INSTRUCTIONS = {"jalr"};
    private static final String[] O_TYPE_INSTRUCTIONS = {"halt", "noop"};
    private static final String[] fill_INSTRUCTIONS = {".fill"};

    public static String[] getTokenArray(String input) throws SyntaxError {
        List<String> tokenList = new ArrayList<>();
        ExprTokenizer tokenizer = new ExprTokenizer(input);

//        boolean isLabelPresent = false;
        String instructionType = "";

        while (tokenizer.hasNextToken()) {
            String token = tokenizer.consume();

            if (!token.isEmpty()) {
                if (isLabel(token)) {
//                    isLabelPresent = true;
                    tokenList.add(token);
                } else if (Arrays.stream(fill_INSTRUCTIONS).anyMatch(token::equals)) {
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
        int expectedTokenCount = Arrays.stream(R_TYPE_INSTRUCTIONS).anyMatch(instructionType::equals) ? 5 : 4;
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
        return Arrays.stream(R_TYPE_INSTRUCTIONS).anyMatch(token::equals)
                || Arrays.stream(I_TYPE_INSTRUCTIONS).anyMatch(token::equals)
                || Arrays.stream(J_TYPE_INSTRUCTIONS).anyMatch(token::equals)
                || Arrays.stream(O_TYPE_INSTRUCTIONS).anyMatch(token::equals)|| Arrays.stream(fill_INSTRUCTIONS).anyMatch(token::equals);
    }
    private static boolean isInstructionType(String token) throws SyntaxError {
        if (isInstruction(token)) {
            return true;
        } else {
            throw new SyntaxError("Invalid instruction: " + token);
        }

    }
    private static int determineExpectedFields(String instructionType) throws SyntaxError {
        if (Arrays.stream(R_TYPE_INSTRUCTIONS).anyMatch(instructionType::equals)) {
            return 3; // R-Type instructions have 3 fields
        } else if (Arrays.stream(I_TYPE_INSTRUCTIONS).anyMatch(instructionType::equals)) {
            return 3; // I-Type instructions have 3 fields
        } else if (Arrays.stream(J_TYPE_INSTRUCTIONS).anyMatch(instructionType::equals)) {
            return 2; // J-Type instructions have 2 fields
        } else if (Arrays.stream(O_TYPE_INSTRUCTIONS).anyMatch(instructionType::equals)) {
            return 0; // O-Type instructions have 0 fields
        } else if (Arrays.stream(fill_INSTRUCTIONS).anyMatch(instructionType::equals)) {
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
}
