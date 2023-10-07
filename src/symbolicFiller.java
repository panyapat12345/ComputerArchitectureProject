import Exceptions.DuplicateLabel;
import Exceptions.SyntaxError;
import Exceptions.UndefinedLabel;

import java.io.IOException;
import java.util.*;

public class symbolicFiller {
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

    private static Map<String, Integer> line = new HashMap<>(), var = new HashMap<>();
    private static String[] instructions = new String[]{"add", "nand", "lw", "sw", "beq", "jalr", "halt", "noop"};
    private static int lineCounter = 0, i = 0, n = 0;
    private static List<String[]> normalForm = new ArrayList<>();

    private static boolean checkInstruction(String token){
        for (String instruction : instructions){
            if (token.equals(instruction))
                return true;
        }
        return false;
    }

    private static void skipInstruction(String token){
        if (token.equals("add") || token.equals("nand")){
            i += 4;
        } else if (token.equals("lw") || token.equals("sw") || token.equals("beq")){
            i += 4;
        } else if (token.equals("jalr")){
            i += 3;
        } else {    // halt, noop
            i += 1;
        }
    }

    private static void checkDuplicateLabel(String token){  // check duplicate line map
        if (line.get(token) != null)
            throw new DuplicateLabel("Line " + lineCounter + " : DuplicateLabel " + token);
    }

    private static boolean isNumber(String token){
        try {
            Integer.valueOf(token);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }

    private static void findLabels(String[] tokens){
        line = new HashMap<>();
        var = new HashMap<>();
        n = tokens.length;
        lineCounter = 0;
        for (i = 0; i < n;){
            if (checkInstruction(tokens[i])) {      // Ins
                skipInstruction(tokens[i]);

            } else {    // Label
                checkDuplicateLabel(tokens[i]);
                if (tokens[i+1].equals(".fill")){   // .fill (var|line)
                    line.put(tokens[i], lineCounter);

                    if (isNumber(tokens[i+2]))  {   // add to var map
                        var.put(tokens[i], Integer.valueOf(tokens[i+2]));
                    } else {                        // search value from line
                        if (line.get(tokens[i+2]) != null)
                            var.put(tokens[i], line.get(tokens[i+2]));
                        else    throw new UndefinedLabel("Line " + lineCounter + " : UndefinedLabel " + tokens[i+2]);
                    }
                    i += 3;
                } else {        // Ins (line)
                    line.put(tokens[i], lineCounter);
                    i += 1;
                    skipInstruction(tokens[i]);
                }
            }
            lineCounter++;
        }

    }

    private static void normalizeInstruction(String[] tokens){
        if (tokens[i].equals("add") || tokens[i].equals("nand")){
            normalForm.add(new String[]{tokens[i], tokens[i+1], tokens[i+2], tokens[i+3]});
            i += 4;

        } else if (tokens[i].equals("lw") || tokens[i].equals("sw") || tokens[i].equals("beq")){
            normalForm.add(new String[]{tokens[i], tokens[i+1], tokens[i+2], tokens[i+3]});
            i += 4;

        } else if (tokens[i].equals("jalr")){
            normalForm.add(new String[]{tokens[i], tokens[i+1], tokens[i+2]});
            i += 3;

        } else {    // halt, noop
            normalForm.add(new String[]{tokens[i]});
            i += 1;
        }
    }

    private static void normalize(String[] tokens){
        normalForm = new ArrayList<>();
        n = tokens.length;
        for (i = 0; i < n;){
            if (checkInstruction(tokens[i])) {      // Ins
                normalizeInstruction(tokens);

            } else {    // Label
                if (tokens[i+1].equals(".fill")){   // .fill
                    normalForm.add(new String[]{String.valueOf(var.get(tokens[i]))});
                    i += 3;
                } else {        // Ins
                    i += 1;
                    normalizeInstruction(tokens);
                }
            }
        }
/*
        for (String[] line : normalForm){
            for (String token : line){
                System.out.print(token+" ");
            }
            System.out.println();
        }
*/
    }

    private static void checkUndefinedLabel(String token){
        if(line.get(token) == null)
            throw new UndefinedLabel("Line " + lineCounter + " : UndefinedLabel " + token);
    }

    private static List<String> fillLabels(){
        List<String> machineCodes = new ArrayList<>();
        lineCounter = 0;
        for (String[] readLine : normalForm){
            if (readLine[0].equals("add") || readLine[0].equals("nand")){
                machineCodes.add(readLine[0] + " " + readLine[1] + " " + readLine[2] + " " + readLine[3]);

            } else if (readLine[0].equals("lw") || readLine[0].equals("sw") || readLine[0].equals("beq")){
                if (isNumber(readLine[3]))                                              // filed 3 is number
                    machineCodes.add(readLine[0] + " " + readLine[1] + " " + readLine[2] + " " + readLine[3]);
                else if (readLine[0].equals("lw") || readLine[0].equals("sw")) {        // filed 3 is label
                    checkUndefinedLabel(readLine[3]);
                    int offset = line.get(readLine[3]);
                    machineCodes.add(readLine[0] + " " + readLine[1] + " " + readLine[2] + " " + offset);
                } else {        // beq
                    checkUndefinedLabel(readLine[3]);
                    int offset = line.get(readLine[3]) - lineCounter - 1;
                    machineCodes.add(readLine[0] + " " + readLine[1] + " " + readLine[2] + " " + offset);
                }

            } else if (readLine[0].equals("jalr")){
                machineCodes.add(readLine[0] + " " + readLine[1] + " " + readLine[2]);

            } else {        // halt, noop, variable in memory
                machineCodes.add(readLine[0]);
            }
            lineCounter++;
        }
        return machineCodes;
    }

    public static String[] getMachineCode(String src) throws DuplicateLabel, UndefinedLabel, SyntaxError, IOException {
        String[] tokens = getTokenArray.getTokens(src);
        findLabels(tokens);
        normalize(tokens);
        List<String> machineCodes = fillLabels();
        return machineCodes.toArray(new String[0]);
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
        System.out.println();

        //for (String s : getMachineCode("/input.txt"))
        //    System.out.println(s);

        System.out.println();
        System.out.println("line : " + line);
        System.out.println("var : " + var);

    }
}
