import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            String fileName = "test3.txt";  // Replace with the actual file path
            String program = ReadFile.readFile(fileName);

            ExprTokenizer tokenizer = new ExprTokenizer(program);

            while (tokenizer.hasNextToken()) {
                String token = tokenizer.consume();
                String[] tokens = token.trim().split("\\s+");
                for (String t : tokens) {
                    if (!t.isEmpty()) {
                        System.out.println("Token: " + t);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LexicalError | SyntaxError e) {
            System.err.print("Lexical or Syntax Error: " + e.getMessage());
        }
    }
}
