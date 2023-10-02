import java.io.IOException;
import java.util.Arrays;
import java.util.List;
public class Main {

    public static void main(String[] args) {
        try {

            String fileName = "src/test.txt";

            List<String> program = ReadFile.readFile(fileName);

            for (int i = 0; i < program.size(); i++) {
                String inputLine = program.get(i);
                try {
                    String[] expectedTokens = getExpectedTokensForLine(inputLine);
                    String[] actualTokens = getTokenArray.getTokenArray(inputLine);

                    System.out.println("Input: " + inputLine);
                    System.out.println("Expected tokens: " + Arrays.toString(expectedTokens));
                    System.out.println("Actual tokens: " + Arrays.toString(actualTokens));
                    System.out.println();
                } catch (getTokenArray.SyntaxError e) {
                    System.err.println("Syntax Error in line " + (i + 1) + ": " + e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LexicalError e) {
            System.err.println("Lexical Error: " + e.getMessage());
        }
    }
     static String[] getExpectedTokensForLine(String input) {
        String[] tokens = input.trim().split("\\s+");
        String instruction = tokens[0];

        // Instruction type arrays (assuming they are constants)
        String[] R_TYPE_INSTRUCTIONS = {"add", "nand"};
        String[] I_TYPE_INSTRUCTIONS = {"lw", "sw", "beq"};
        String[] J_TYPE_INSTRUCTIONS = {"jalr"};
        String[] O_TYPE_INSTRUCTIONS = {"halt", "noop"};
         String[] fill_INSTRUCTIONS = {".fill"};

        // Check instruction type and return expected tokens
        if (Arrays.asList(R_TYPE_INSTRUCTIONS).contains(instruction)) {
            return new String[]{"<instruction>", "<field0>", "<field1>", "<field2>"};
        } else if (Arrays.asList(I_TYPE_INSTRUCTIONS).contains(instruction)) {
            return new String[]{"<instruction>", "<field0>", "<field1>", "<field2>"};
        } else if (Arrays.asList(J_TYPE_INSTRUCTIONS).contains(instruction)) {
            return new String[]{"<instruction>", "<field0>", "<field1>"};
        } else if (Arrays.asList(O_TYPE_INSTRUCTIONS).contains(instruction)) {
            return new String[]{"<instruction>"};
        } else if (Arrays.asList(fill_INSTRUCTIONS).contains(instruction)) {
            return new String[]{"<.fill>","value"};
        }

        return new String[0];
    }
}
