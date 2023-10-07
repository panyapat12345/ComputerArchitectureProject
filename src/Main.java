import Exceptions.DuplicateLabel;
import Exceptions.UndefinedLabel;

import java.io.IOException;

public class Main {
    public static void main(String[] argv) throws SyntaxError, IOException {
        try{
            for (String line : symbolicFiller.getMachineCode("src/test_1.txt"))
                System.out.println(line);

        } catch (IOException | SyntaxError | DuplicateLabel | UndefinedLabel e) {
            // System.err.println(e);
            throw e;
        }
    }
}
