import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            // Read the program from the file
            String fileName = "src/test.txt";
            List<String> program = ReadFile.readFile(fileName);

            // Process each line in the program
            for (int i = 0; i < program.size(); i++) {
                String inputLine = program.get(i);
                try {
                    String[] tokens = getTokenArray.getTokenArray(inputLine);


                    System.out.println("Input: " + inputLine);
                    System.out.println("Tokens: " + Arrays.toString(tokens));
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

}

