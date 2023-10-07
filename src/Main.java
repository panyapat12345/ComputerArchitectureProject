import Exceptions.DuplicateLabel;
import Exceptions.UndefinedLabel;

import java.io.IOException;

public class Main {
    public static void main(String[] argv) throws SyntaxError, IOException {
        try{
            symbolicFiller.getMachineCode("src/test_1.txt");
        } catch (IOException | SyntaxError | DuplicateLabel | UndefinedLabel e) {
            System.err.println(e);
        }
    }
}
