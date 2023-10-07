public class Main {

    private static int i, n, lineCounter;

    private static String[] instructions = new String[]{"add", "nand", "lw", "sw", "beq", "jalr", "halt", "noop"};

    public static String[] ex1 = new String[] { "lw",       "0",        "1",        "five",
            "lw",       "1",        "2",        "3",
            "start",    "add",      "1",        "2",        "1",
            "beq",      "0",        "1",        "2",
            "beq",      "0",        "0",        "start",
            "noop",
            "done",     "halt",
            "five",     ".fill",    "5",
            "neg1",     ".fill",    "-1",
            "stAddr",   ".fill",    "start"};

    private static boolean checkInstruction(String token){
        for (String instruction : instructions){
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

    public static void main(String[] args) {
        printAllInstructions(ex1);
    }
}