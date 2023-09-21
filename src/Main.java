import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            String fileName = "test2.txt";  // Replace with the actual file path
            List<String> program = ReadFile.readFile(fileName);

            for (int i = 0; i < program.size(); i++) {
                ExprTokenizer tokenizer = new ExprTokenizer(program.get(i));
                System.out.println("line: " + i );
                while (tokenizer.hasNextToken()){
                        System.out.println("Token: " + tokenizer.consume());
                   
                }
                System.out.println();
                }

            
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LexicalError | SyntaxError e) {
            System.err.print("Lexical or Syntax Error: " + e.getMessage());
        }
    }
}
