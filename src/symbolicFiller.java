import Exceptions.DuplicateLabel;
import Exceptions.SyntaxError;
import Exceptions.UndefinedLabel;

import java.io.IOException;
import java.util.*;

public class symbolicFiller {
    /** use for tracking labels */
    private static Map<String, Integer> line = new HashMap<>(), var = new HashMap<>();
    /** all instructions are allowed */
    private static String[] instructions = new String[]{"add", "nand", "lw", "sw", "beq", "jalr", "halt", "noop"};
    private static int lineCounter = 0, i = 0, n = 0;
    private static List<String[]> normalForm = new ArrayList<>();

    /** check that the given token is instruction? */
    private static boolean checkInstruction(String token){
        for (String instruction : instructions){
            if (token.equals(instruction))
                return true;
        }
        return false;
    }

    /** skip tokens (fields) after the given token (instruction) */
    private static void skipInstruction(String token){
        if (token.equals("add") || token.equals("nand")){
            i += 4;     // skip add|nand + 3  fields
        } else if (token.equals("lw") || token.equals("sw") || token.equals("beq")){
            i += 4;     // skip lw|sw|beq + 3  fields
        } else if (token.equals("jalr")){
            i += 3;     // skip jalr + 2  fields
        } else {        // halt, noop
            i += 1;     // skip halt|noop
        }
    }

    /** check that the given token is duplicate?  */
    private static void checkDuplicateLabel(String token){
        // check duplicate line map
        if (line.get(token) != null)
            // throw an exception
            throw new DuplicateLabel("Line " + lineCounter + " : DuplicateLabel " + token);
    }

    /** check that the given token is a number? */
    private static boolean isNumber(String token){
        try {
            Integer.valueOf(token);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }

    /** find all labels that in assembly languages */
    private static void findLabels(String[] tokens){
        line = new HashMap<>();     // a label keep a line position
        var = new HashMap<>();      // a label keep a value that be assigned
        n = tokens.length;
        lineCounter = 0;
        for (i = 0; i < n;){
            // is the token an instruction?
            if (checkInstruction(tokens[i])) {
                skipInstruction(tokens[i]);
            // the token is a label
            } else {
                checkDuplicateLabel(tokens[i]);
                // is it follows by ".fill" ?
                if (tokens[i+1].equals(".fill")){
                    // track a line position
                    line.put(tokens[i], lineCounter);
                    // is the ".fill" follows by a number?
                    if (isNumber(tokens[i+2]))  {
                        // keep the label and its value
                        var.put(tokens[i], Integer.valueOf(tokens[i+2]));
                    // is not a number it is labels together
                    } else {
                        // search a line position that be tracked
                        if (line.get(tokens[i+2]) != null)
                            var.put(tokens[i], line.get(tokens[i+2]));
                        // if not found throw UndefinedLabel exception
                        else    throw new UndefinedLabel("Line " + lineCounter + " : UndefinedLabel " + tokens[i+2]);
                    }
                    i += 3;

                // it follows by an instruction
                } else {
                    // track a line position
                    line.put(tokens[i], lineCounter);
                    // skip the label
                    i += 1;
                    skipInstruction(tokens[i]);
                }
            }
            lineCounter++;
        }
    }

    /** combine tokens to an instruction in a line */
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

    /** delete all labels that before instruction and .fill in assembly languages */
    private static void normalize(String[] tokens){
        normalForm = new ArrayList<>();
        n = tokens.length;
        for (i = 0; i < n;){
            if (checkInstruction(tokens[i])) {      // Ins
                normalizeInstruction(tokens);

            } else {    // Label
                if (tokens[i+1].equals(".fill")){   // .fill
                    // trans "label .fill number|label" to "number|value of label"
                    normalForm.add(new String[]{String.valueOf(var.get(tokens[i]))});
                    i += 3;
                } else {        // Ins
                    i += 1;     // delete a label
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

    /** check that the given token (label) is an undefined label */
    private static void checkUndefinedLabel(String token){
        if(line.get(token) == null)
            // throw UndefinedLabel exception
            throw new UndefinedLabel("Line " + lineCounter + " : UndefinedLabel " + token);
    }

    /** replace all symbolic labels to offset of lw, sw and beq */
    private static List<String> fillLabels(){
        List<String> machineCodes = new ArrayList<>();  // keep a final result
        lineCounter = 0;
        for (String[] readLine : normalForm){
            // R-type do nothing
            if (readLine[0].equals("add") || readLine[0].equals("nand")){
                machineCodes.add(readLine[0] + " " + readLine[1] + " " + readLine[2] + " " + readLine[3]);
            // T-type replace a symbolic label to an offset
            } else if (readLine[0].equals("lw") || readLine[0].equals("sw") || readLine[0].equals("beq")){
                // is the 3rd filed a number?
                if (isNumber(readLine[3]))
                    // do nothing
                    machineCodes.add(readLine[0] + " " + readLine[1] + " " + readLine[2] + " " + readLine[3]);
                // the 3rd filed is a label
                else if (readLine[0].equals("lw") || readLine[0].equals("sw")) {    // for lw and sw
                    checkUndefinedLabel(readLine[3]);
                    // find the line position of the 3rd filed
                    int offset = line.get(readLine[3]);
                    // replace
                    machineCodes.add(readLine[0] + " " + readLine[1] + " " + readLine[2] + " " + offset);
                } else {        // for beq
                    checkUndefinedLabel(readLine[3]);
                    // calculate offset that should be
                    int offset = line.get(readLine[3]) - lineCounter - 1;
                    // replace
                    machineCodes.add(readLine[0] + " " + readLine[1] + " " + readLine[2] + " " + offset);
                }
            // J-type do nothing
            } else if (readLine[0].equals("jalr")){
                machineCodes.add(readLine[0] + " " + readLine[1] + " " + readLine[2]);
            // O-type and variable do nothing
            } else {        // halt, noop and variable in memory
                machineCodes.add(readLine[0]);
            }
            lineCounter++;
        }
        return machineCodes;
    }

    /** delete all labels in assembly languages and replace they to a value they should be */
    public static String[] getMachineCode(String src) throws DuplicateLabel, UndefinedLabel, SyntaxError, IOException {
        // get all tokens from tokenizer
        String[] tokens = getTokenArray.getTokens(src);
        findLabels(tokens);
        normalize(tokens);
        List<String> machineCodes = fillLabels();
        return machineCodes.toArray(new String[0]);
    }

    /** for testing only */
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
