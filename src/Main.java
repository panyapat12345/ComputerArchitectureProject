import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            String[] tokens = getTokenArray.getTokens("src/test3.txt");
            for (String s : tokens)
                System.out.println(s);

        } catch (SyntaxError | IOException e) {
            System.err.println(e);
        }
    }

}

