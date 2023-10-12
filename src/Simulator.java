import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import Exceptions.*;

public class Simulator {
    private static final int NUMMEMORY = 65536; /** maximum number of words in memory */
    private static final int NUMREGS = 8; /** number of machine registers */
    // inti for simulate
    private static stateType state = new stateType(0, new int[NUMMEMORY], new int[NUMREGS], 0);;
    private static int instruction;
    private static final int sp = 2;    /** reg use for stack pointer */
    private static int cause;           // 0 : NoLoadedMachineCode
                                        // 1 : NoSuchDynamicData
                                        // 2 : PCAddressOutOfBound
                                        // 3 : StackOverflow
                                        // 4 : MemoryAddressOutOfBound
                                        // 5 : Halted
                                        // 6 : FileReaderException

    /** class for keep all states */
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

        /** print current values of state */
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

    /** use for exit simulator */
    private static void exit(){
        throw new Exit();
    }

    /** load machine codes from the given readPath */
    public static void load(String readPath) {
        Path readFile = Paths.get(readPath);
        Charset charset = StandardCharsets.UTF_8;

        // reset all states
        state = new stateType(0, new int[NUMMEMORY], new int[NUMREGS], 0);

        // read in the entire machine-code file into memory
        try (BufferedReader reader = Files.newBufferedReader(readFile, charset);) {
            String line = null;

            for(; (line = reader.readLine()) != null; state.numMemory++) {
                // store each lines in the memory
                state.mem[state.numMemory] = Integer.valueOf(line);
                // print a value that read
                System.out.println("memory[" + state.numMemory + "]=" + state.mem[state.numMemory]);
            }
            if(state.numMemory == 0){       // check that have any machine codes loaded?
                cause = 0;                  // NoLoadedMachineCode occur
                exit();
            }

        // check that have any exceptions occur?
        } catch (NoSuchFileException | AccessDeniedException | FileNotFoundException e) {
            System.err.println("error: can't open file " + readPath);
            cause = 6;      // FileReaderException occur
            exit();
        } catch (IOException e) {
            System.err.println("IOExcertion: " + e);
            cause = 6;      // FileReaderException occur
            exit();
        }
    }

    /** -------------------- component implement ---------------------------- */

    /** calculate offset from the current instruction */
    private static int offset(){
        int offset = instruction & 0xffff;
        // signed extending to 32 bits
        if((offset>>15) % 2 == 0)    return offset;
        else    return offset | 0xffff0000;
    }

    /** add two values as ALU */
    private static int addALU(int a, int b){
        return a+b;
    }

    /** nand two values as ALU */
    private static int nandALU(int a, int b){
        return ~(a & b);
    }

    /** calculate zero signal as ALU from two values */
    private static int zeroALU(int a, int b){
        int ALUOutput = a-b;
        return ALUOutput == 0? 1 : 0;
    }

    /** check that the given address is in bound? */
    private static boolean isInBoundAddress(int address){
        if(address >= 0 && address <= 65535)    return true;
        else    return false;
    }

    /** fetch an instruction according to PC */
    private static void fetchInstruction(){
        if(isInBoundAddress(state.pc)){
            // check that it reach to the stack pointer?
            if(state.pc < state.reg[sp] + 65536)
                // fetching an instruction
                instruction = state.mem[state.pc];
            else {
                cause = 1;          // NoSuchDynamicData occur
                exit();
            }
        } else  {
            cause = 2;              // PCAddressOutOfBound occur
            exit();
        }
    }

    /** read values of rs and rt from the register */
    private static int[] readReg(){
        int rs = (instruction >> 19) & 0b111;
        int rt = (instruction >> 16) & 0b111;
        return new int []{state.reg[rs], state.reg[rt]};
    }

    /** write the register according to the given writeReg with the given data */
    private static void writeReg(int writeReg, int data){
        // check that the writeReg is not x0?
        if(writeReg != 0)   state.reg[writeReg] = data;
        // check overflow is occurred from use stack pointer against conditions?
        if(state.reg[sp] + 65536 < state.numMemory) {
            cause = 3;              // StackOverflow occur
            exit();
        }
    }

    /** read the value of the given address from the memory */
    private static int readMem(int address){
        if(isInBoundAddress(address))   return state.mem[address];
        else {
            cause = 4;          // MemoryAddressOutOfBound occur
            exit();
            return 0;           // fix bug
        }
    }

    /** write the memory according to the given address with the given writeData */
    private static void writeMem(int address, int writeData){
        if(isInBoundAddress(address))   state.mem[address] = writeData;
        else {
            cause = 4;          // MemoryAddressOutOfBound occur
            exit();
        }
    }

    /** simulate as the instruction execution of single cycle CPU */
    private static void oneClockExecute(){
        fetchInstruction();
        // read values fo rs and rt from the register as data[0] and data[1]
        int[] data = readReg();
        int opcode = (instruction >> 22) & 0b111;
        int rs = (instruction >> 19) & 0b111;
        int rt = (instruction >> 16) & 0b111;
        int rd = instruction & 0b111;
        //System.out.println(state.pc + " : " + opcode + " " + rs + " " + rt + " " + rd);

        if(opcode == 0b000){            // add
            // calculate rs + rt
            int ALUOutput = addALU(data[0], data[1]);
            // store in the register
            writeReg(rd, ALUOutput);
            state.pc++;
        } else if(opcode == 0b001){     // nand
            // calculate rs nand rt
            int ALUOutput = nandALU(data[0], data[1]);
            // store in the register
            writeReg(rd, ALUOutput);
            state.pc++;
        } else if(opcode == 0b010){     //lw
            // find address
            int ALUOutput = addALU(data[0], offset());
            // access the memory
            int readData;
            if(rs == sp)    readData = readMem(ALUOutput + 65536);      // for stack pointer, must shift the address
            else    readData = readMem(ALUOutput);
            // store in the register
            writeReg(rt, readData);
            state.pc++;
        } else if(opcode == 0b011){     // sw
            // find address
            int ALUOutput = addALU(data[0], offset());
            // store rt in the memory
            if(rs == sp)    writeMem(ALUOutput + 65536, data[1]);       // for stack pointer, must shift the address
            else    writeMem(ALUOutput, data[1]);
            state.pc++;
        } else if(opcode == 0b100){     // beq
            // if zero signal is 1 then the PC jump
            if(zeroALU(data[0], data[1]) == 1)   state.pc = state.pc + 1 +offset();
            else    state.pc++;
        } else if(opcode == 0b101){     // jalr
            // store a returned address
            writeReg(rt, state.pc + 1);
            // PC = rs (jump)
            state.pc = data[0];
        } else if(opcode == 0b110){     // halt
            state.pc++;
            cause = 5;                  // Halted occur
            exit();
        } else {                        // opcode = 0b111   noop
            state.pc++;
        }
    }

    /** simulate according to the loaded machine codes from the given src path */
    public static void simulate(String src){       // machine codes only
        int instructionCount = 0;
        try {
            // load machine codes
            load(src);
            // execute each machine code
            while(true){
                state.printState();
                instructionCount++;
                oneClockExecute();
            }

        // handle with exceptions
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

    // for testing only
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
