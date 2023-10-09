import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import Exceptions.*;

public class Simulator {
    private static final int NUMMEMORY = 65536; /* maximum number of words in memory */
    private static final int NUMREGS = 8; /* number of machine registers */
    // inti for simulate
    private static stateType state = new stateType(0, new int[NUMMEMORY], new int[NUMREGS], 0);;
    private static int instruction;
    private static final int sp = 2;
    private static int cause;           // 0 : NoLoadedMachineCode
                                        // 1 : NoSuchDynamicData
                                        // 2 : PCAddressOutOfBound
                                        // 3 : StackOverflow
                                        // 4 : MemoryAddressOutOfBound
                                        // 5 : Halted
                                        // 6 : FileReaderException

    private static class stateType {
        public int pc;
        public int[] mem;
        public int[] reg;
        public int numMemory;

        public stateType(int pc, int[] mem, int[] reg, int numMemory){
            this.pc = pc;
            this.mem = mem;
            this.reg = reg;
            this.numMemory = numMemory;
        }

        public void printState(){
            int i;
            System.out.print("\n@@@\nstate:\n");
            System.out.print("\t\tpc " + this.pc + "\n");
            System.out.print("\t\tmemory:\n");
            for(i=0; i<numMemory; i++){
                System.out.print("\t\t\t\tmem[ " + i + " ] " + mem[i] + "\n");
            }
            System.out.print("\t\tregisters:\n");
            for(i=0; i<NUMREGS; i++){
                System.out.print("\t\t\t\treg[ " + i + " ] " + reg[i] + "\n");
            }
            System.out.print("end state\n");
        }

    }

    private static void exit(){
        throw new Exit();
    }

    public static void load(String readPath) {
        Path readFile = Paths.get(readPath);
        Charset charset = StandardCharsets.UTF_8;

        // reset state
        state = new stateType(0, new int[NUMMEMORY], new int[NUMREGS], 0);

        // read in the entire machine-code file into memory
        try (BufferedReader reader = Files.newBufferedReader(readFile, charset);) {
            String line = null;

            for(; (line = reader.readLine()) != null; state.numMemory++) {
                state.mem[state.numMemory] = Integer.valueOf(line);
                System.out.println("memory[" + state.numMemory + "]=" + state.mem[state.numMemory]);
            }
            if(state.numMemory == 0){       // check if have no load any machine codes
                cause = 0;                  // NoLoadedMachineCode
                exit();
            }

            // print state test
            //state.printState();

        } catch (NoSuchFileException | AccessDeniedException | FileNotFoundException e) {
            System.err.println("error: can't open file " + readPath);
            cause = 6;
            exit();
        } catch (IOException e) {
            System.err.println("IOExcertion: " + e);
            cause = 6;
            exit();
        }
    }

    // component implement
    private static int offset(){
        int offset = instruction & 0xffff;
        if((offset>>15) % 2 == 0)    return offset;
        else    return offset | 0xffff0000;
    }

    private static int addALU(int a, int b){
        return a+b;
    }

    private static int nandALU(int a, int b){
        return ~(a & b);
    }

    private static int zeroALU(int a, int b){
        int ALUOutput = a-b;
        return ALUOutput == 0? 1 : 0;
    }

    private static boolean isInBoundAddress(int address){
        if(address >= 0 && address <= 65535)    return true;
        else    return false;
    }

    private static void fecthInstruction(){
        if(state.pc >= 0 && state.pc <= 65535){
            if(state.pc < state.reg[sp] + 65536)
                instruction = state.mem[state.pc];
            else {
                cause = 1;          // NoSuchDynamicData
                exit();
            }
        } else  {
            cause = 2;              // PCAddressOutOfBound
            exit();
        }
    }

    private static int[] readReg(){
        int readReg1 = (instruction >> 19) & 0b111;     // rs
        int readReg2 = (instruction >> 16) & 0b111;     // rt

        return new int []{state.reg[readReg1], state.reg[readReg2]};
    }

    private static void writeReg(int writeReg, int data){
        if(writeReg != 0)   state.reg[writeReg] = data;
        if(state.reg[sp] + 65536 < state.numMemory) {       // check overflow
            cause = 3;              // StackOverflow
            exit();
        }
    }

    private static int readMem(int address){
        if(isInBoundAddress(address))   return state.mem[address];
        else {
            cause = 4;          // MemoryAddressOutOfBound
            exit();
            return 0;
        }
    }

    private static void writeMem(int address, int writeData){
        if(isInBoundAddress(address))   state.mem[address] = writeData;
        else {
            cause = 4;          // MemoryAddressOutOfBound
            exit();
        }
    }

    private static void oneClockExcute(){
        fecthInstruction();
        int[] data = readReg();
        int opcode = (instruction >> 22) & 0b111;
        int rs = (instruction >> 19) & 0b111;
        int rt = (instruction >> 16) & 0b111;
        int rd = instruction & 0b111;
        //System.out.println(state.pc + " : " + opcode + " " + rs + " " + rt + " " + rd);

        if(opcode == 0b000){            // add
            int ALUOutput = addALU(data[0], data[1]);
            writeReg(rd, ALUOutput);
            state.pc++;
        } else if(opcode == 0b001){     // nand
            int ALUOutput = nandALU(data[0], data[1]);
            writeReg(rd, ALUOutput);
            state.pc++;
        } else if(opcode == 0b010){     //lw
            int ALUOutput = addALU(data[0], offset()), readData;
            if(rs == sp)    readData = readMem(ALUOutput + 65536);      // for stack pointer
            else    readData = readMem(ALUOutput);
            writeReg(rt, readData);
            state.pc++;
        } else if(opcode == 0b011){     // sw
            int ALUOutput = addALU(data[0], offset()), readData;
            if(rs == sp)    writeMem(ALUOutput + 65536, data[1]);       // for stack pointer
            else    writeMem(ALUOutput, data[1]);
            state.pc++;
        } else if(opcode == 0b100){     // beq
            if(zeroALU(data[0], data[1]) == 1)   state.pc = state.pc + 1 +offset();     // jump
            else    state.pc++;
        } else if(opcode == 0b101){     // jalr
            writeReg(rt, state.pc + 1);
            state.pc = data[0];                                                         // jump
        } else if(opcode == 0b110){     // halt
            state.pc++;
            cause = 5;                  // Halted
            exit();
        } else {                        // opcode = 0b111   noop
            state.pc++;
        }
    }

    public static void simulate(String src){       // machine codes only
        int instructionCount = 0;
        try {
            load(src);
            while(true){
                state.printState();
                instructionCount++;
                oneClockExcute();
            }

        } catch(Exit e){                    // Exception handler
            if(cause == 0) {                // NoLoadedMachineCode
                System.err.println("not have a machine code for loading");
            } else if(cause == 1){          // NoSuchDynamicData
                System.err.println("not have a dynamic data");
            } else if(cause == 2){          // PCAddressOutOfBound
                System.err.println("pc address out of bound.");
            } else if(cause == 3){           // StackOverflow
                System.err.println("stack overflow.");
            } else if(cause == 4){           // MemoryAddressOutOfBound
                System.err.println("memory address out of bound.");
            } else if(cause == 5){           // Halted
                System.out.println("machine halted");
                System.out.println("total of " + instructionCount + " instructions executed");
                System.out.println("final state of machine:");
                state.printState();
            } else if(cause == 6){           // FileReaderException
                System.err.println("FileReaderException occurred");
            }
        }
    }

    // for testing
    private static String bin(int value){
        String s = String.format("%32s", Integer.toBinaryString(value)).replace(' ', '0');
        StringBuilder result = new StringBuilder();
        for(int i=0; i<s.length(); i++){
            result.append(s.charAt(i));
            if(i%4 == 3)    result.append(" ");
        }
        return result.toString();
    }
}
