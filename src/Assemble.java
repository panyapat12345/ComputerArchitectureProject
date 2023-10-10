import Exceptions.DuplicateLabel;
import Exceptions.Exit;
import Exceptions.SyntaxError;
import Exceptions.UndefinedLabel;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Assemble {

    String[] input = { "lw 0 1 7", "lw 1 2 3", "add 1 2 1", "beq 0 1 2", "beq 0 0 -3", "noop", "halt", "5", "-1",
            "2", "jalr 2 5" };

    private static void exit(int type) {
        if (type == 1)
            throw new Exit();
    }

    public static void getMachineCodes(String src, String dest) throws Exit {
        ArrayList<Integer> output = new ArrayList<>();

        try {
            String[] input = symbolicFiller.getMachineCode(src);
            // ฟังก์ชันรับอินพุตเป็น arrey of string
            // for loop ไล่ทำแต่ละสมาชิกของอินพุต
            for (int i = 0; i < input.length; i++) {
                System.out.print("line " + i + " : " + input[i] + " --> ");
                if (input[i].charAt(0) == 'a') {
                    // if ทำเมื่อเจอ prefix เป็นคำสั่ง add
                    // System.out.println("R add");
                    String[] words = input[i].split(" ");
                    // แยก string ด้วย " "จะแบ่งได้เป็น prefix rs rt rd
                    for (String word : words) {
                        // System.out.println(word);
                    }
                    int rs = Integer.parseInt(words[1]);
                    int rt = Integer.parseInt(words[2]);
                    int rd = Integer.parseInt(words[3]);
                    // System.out.println(rs + " " + rt + " " + rd);
                    // แปลง rs rt rd เป็น binary
                    String binary_rs = Integer.toBinaryString(rs);
                    String binary_rt = Integer.toBinaryString(rt);
                    String binary_rd = Integer.toBinaryString(rd);
                    // System.out.println(binary_rs + " " + binary_rt + " " + binary_rd);
                    // ปรับความยาวของ binary
                    if (binary_rs.length() > 3) {
                        System.err.println("binary_rs error");
                    }

                    if (binary_rs.length() < 3) {
                        StringBuilder sb_binary_rs = new StringBuilder();
                        for (int j = 0; j < 3 - binary_rs.length(); j++) {
                            sb_binary_rs.append('0');
                        }
                        sb_binary_rs.append(binary_rs);
                        binary_rs = sb_binary_rs.toString();
                    }

                    if (binary_rt.length() > 3) {
                        System.err.println("binary_rt error");
                    }

                    if (binary_rt.length() < 3) {
                        StringBuilder sb_binary_rt = new StringBuilder();
                        for (int j = 0; j < 3 - binary_rt.length(); j++) {
                            sb_binary_rt.append('0');
                        }
                        sb_binary_rt.append(binary_rt);
                        binary_rt = sb_binary_rt.toString();
                    }

                    if (binary_rd.length() > 3) {
                        System.err.println("binary_rd error");
                    }

                    if (binary_rd.length() < 3) {
                        StringBuilder sb_binary_rd = new StringBuilder();
                        for (int j = 0; j < 3 - binary_rd.length(); j++) {
                            sb_binary_rd.append('0');
                        }
                        sb_binary_rd.append(binary_rd);
                        binary_rd = sb_binary_rd.toString();
                    }
                    // System.out.println("000" + binary_rs + binary_rt + "0000000000000" +
                    // binary_rd);
                    // นำแต่ละส่วนมาร่วมเป็นคำสั่ง
                    String binaryCode = "000" + binary_rs + binary_rt + "0000000000000" + binary_rd;
                    // แปลงคำสั่ง binary เป็น decimal
                    int decimalCode = Integer.parseInt(binaryCode, 2);
                    System.out.println("R add " + decimalCode + " (" + binaryCode + ")");
                    // นำคำสั่งที่สร้างขึ้นไปรอที่ output arrey
                    output.add(decimalCode);
                } else if (input[i].charAt(0) == 'n' && input[i].charAt(1) == 'a') {
                    // if ทำเมื่อเจอ prefix เป็นคำสั่ง nand
                    // System.out.println("R nand");
                    String[] words = input[i].split(" ");
                    for (String word : words) {
                        // แยก string ด้วย " "จะแบ่งได้เป็น prefix rs rt rd
                        // System.out.println(word);
                    }
                    int rs = Integer.parseInt(words[1]);
                    int rt = Integer.parseInt(words[2]);
                    int rd = Integer.parseInt(words[3]);
                    // System.out.println(rs + " " + rt + " " + rd);
                    // แปลง rs rt rd เป็น binary
                    String binary_rs = Integer.toBinaryString(rs);
                    String binary_rt = Integer.toBinaryString(rt);
                    String binary_rd = Integer.toBinaryString(rd);
                    // System.out.println(binary_rs + " " + binary_rt + " " + binary_rd);
                    // ปรับความยาวของ binary
                    if (binary_rs.length() > 3) {
                        System.err.println("binary_rs error");
                    }

                    if (binary_rs.length() < 3) {
                        StringBuilder sb_binary_rs = new StringBuilder();
                        for (int j = 0; j < 3 - binary_rs.length(); j++) {
                            sb_binary_rs.append('0');
                        }
                        sb_binary_rs.append(binary_rs);
                        binary_rs = sb_binary_rs.toString();
                    }

                    if (binary_rt.length() > 3) {
                        System.err.println("binary_rt error");
                    }

                    if (binary_rt.length() < 3) {
                        StringBuilder sb_binary_rt = new StringBuilder();
                        for (int j = 0; j < 3 - binary_rt.length(); j++) {
                            sb_binary_rt.append('0');
                        }
                        sb_binary_rt.append(binary_rt);
                        binary_rt = sb_binary_rt.toString();
                    }

                    if (binary_rd.length() > 3) {
                        System.err.println("binary_rd error");
                    }

                    if (binary_rd.length() < 3) {
                        StringBuilder sb_binary_rd = new StringBuilder();
                        for (int j = 0; j < 3 - binary_rd.length(); j++) {
                            sb_binary_rd.append('0');
                        }
                        sb_binary_rd.append(binary_rd);
                        binary_rd = sb_binary_rd.toString();
                    }
                    // System.out.println("001" + binary_rs + binary_rt + "0000000000000" +
                    // binary_rd);
                    String binaryCode = "001" + binary_rs + binary_rt + "0000000000000" + binary_rd;
                    // นำแต่ละส่วนมาร่วมเป็นคำสั่ง
                    // แปลงคำสั่ง binary เป็น decimal
                    // นำคำสั่งที่สร้างขึ้นไปรอที่ output arrey
                    int decimalCode = Integer.parseInt(binaryCode, 2);
                    System.out.println("R nand " + decimalCode + " (" + binaryCode + ")");
                    output.add(decimalCode);
                } else if (input[i].charAt(0) == 'l') {
                    // System.out.println("I lw");
                    // if ทำเมื่อเจอ prefix เป็นคำสั่ง lw
                    String[] words = input[i].split(" ");
                    for (String word : words) {
                        // System.out.println(word);
                        // แยก string ด้วย " "จะแบ่งได้เป็น prefix rs rt rd
                    }
                    int rs = Integer.parseInt(words[1]);
                    int rt = Integer.parseInt(words[2]);
                    int rd = Integer.parseInt(words[3]);
                    // System.out.println(rs + " " + rt + " " + rd);
                    // แปลง rs rt rd เป็น binary
                    String binary_rs = Integer.toBinaryString(rs);
                    String binary_rt = Integer.toBinaryString(rt);
                    String binary_rd = Integer.toBinaryString(rd);
                    // System.out.println(binary_rs + " " + binary_rt + " " + binary_rd);
                    // ปรับความยาวของ binary
                    if (binary_rs.length() < 3) {
                        StringBuilder sb_binary_rs = new StringBuilder();
                        for (int j = 0; j < 3 - binary_rs.length(); j++) {
                            sb_binary_rs.append('0');
                        }
                        sb_binary_rs.append(binary_rs);
                        binary_rs = sb_binary_rs.toString();
                    }
                    if (binary_rt.length() < 3) {
                        StringBuilder sb_binary_rt = new StringBuilder();
                        for (int j = 0; j < 3 - binary_rt.length(); j++) {
                            sb_binary_rt.append('0');
                        }
                        sb_binary_rt.append(binary_rt);
                        binary_rt = sb_binary_rt.toString();
                    }
                    if (binary_rd.length() < 16) {
                        StringBuilder sb_binary_rd = new StringBuilder();
                        for (int j = 0; j < 16 - binary_rd.length(); j++) {
                            sb_binary_rd.append('0');
                        }
                        sb_binary_rd.append(binary_rd);
                        binary_rd = sb_binary_rd.toString();
                    }
                    if (binary_rd.length() > 16) {
                        binary_rd = binary_rd.substring(binary_rd.length() - 16);
                    }
                    // System.out.println("010" + binary_rs + binary_rt +
                    // binary_rd);
                    // นำแต่ละส่วนมาร่วมเป็นคำสั่ง
                    // แปลงคำสั่ง binary เป็น decimal
                    // นำคำสั่งที่สร้างขึ้นไปรอที่ output arrey
                    String binaryCode = "010" + binary_rs + binary_rt + binary_rd;
                    int decimalCode = Integer.parseInt(binaryCode, 2);
                    System.out.println("I lw " + decimalCode + " (" + binaryCode + ")");
                    output.add(decimalCode);
                } else if (input[i].charAt(0) == 's') {
                    // System.out.println("I sw");
                    // if ทำเมื่อเจอ prefix เป็นคำสั่ง sw
                    String[] words = input[i].split(" ");
                    for (String word : words) {
                        // System.out.println(word);
                        // แยก string ด้วย " "จะแบ่งได้เป็น prefix rs rt rd
                    }
                    int rs = Integer.parseInt(words[1]);
                    int rt = Integer.parseInt(words[2]);
                    int rd = Integer.parseInt(words[3]);
                    // System.out.println(rs + " " + rt + " " + rd);
                    // แปลง rs rt rd เป็น binary
                    String binary_rs = Integer.toBinaryString(rs);
                    String binary_rt = Integer.toBinaryString(rt);
                    String binary_rd = Integer.toBinaryString(rd);
                    // System.out.println(binary_rs + " " + binary_rt + " " + binary_rd);
                    // ปรับความยาวของ binary
                    if (binary_rs.length() < 3) {
                        StringBuilder sb_binary_rs = new StringBuilder();
                        for (int j = 0; j < 3 - binary_rs.length(); j++) {
                            sb_binary_rs.append('0');
                        }
                        sb_binary_rs.append(binary_rs);
                        binary_rs = sb_binary_rs.toString();
                    }
                    if (binary_rt.length() < 3) {
                        StringBuilder sb_binary_rt = new StringBuilder();
                        for (int j = 0; j < 3 - binary_rt.length(); j++) {
                            sb_binary_rt.append('0');
                        }
                        sb_binary_rt.append(binary_rt);
                        binary_rt = sb_binary_rt.toString();
                    }
                    if (binary_rd.length() < 16) {
                        StringBuilder sb_binary_rd = new StringBuilder();
                        for (int j = 0; j < 16 - binary_rd.length(); j++) {
                            sb_binary_rd.append('0');
                        }
                        sb_binary_rd.append(binary_rd);
                        binary_rd = sb_binary_rd.toString();
                    }
                    if (binary_rd.length() > 16) {
                        binary_rd = binary_rd.substring(binary_rd.length() - 16);
                    }
                    // System.out.println("011" + binary_rs + binary_rt +
                    // binary_rd);
                    // นำแต่ละส่วนมาร่วมเป็นคำสั่ง
                    // แปลงคำสั่ง binary เป็น decimal
                    // นำคำสั่งที่สร้างขึ้นไปรอที่ output arrey
                    String binaryCode = "011" + binary_rs + binary_rt + binary_rd;
                    int decimalCode = Integer.parseInt(binaryCode, 2);
                    System.out.println("I sw " + decimalCode + " (" + binaryCode + ")");
                    output.add(decimalCode);
                } else if (input[i].charAt(0) == 'b') {
                    // System.out.println("I beq");
                    // if ทำเมื่อเจอ prefix เป็นคำสั่ง beq

                    String[] words = input[i].split(" ");
                    for (String word : words) {
                        // System.out.println(word);
                        // แยก string ด้วย " "จะแบ่งได้เป็น prefix rs rt rd
                    }
                    int rs = Integer.parseInt(words[1]);
                    int rt = Integer.parseInt(words[2]);
                    int rd = Integer.parseInt(words[3]);
                    // System.out.println(rs + " " + rt + " " + rd);
                    // แปลง rs rt rd เป็น binary
                    String binary_rs = Integer.toBinaryString(rs);
                    String binary_rt = Integer.toBinaryString(rt);
                    String binary_rd = Integer.toBinaryString(rd);
                    // System.out.println(binary_rs + " " + binary_rt + " " + binary_rd);
                    // ปรับความยาวของ binary
                    if (binary_rs.length() < 3) {
                        StringBuilder sb_binary_rs = new StringBuilder();
                        for (int j = 0; j < 3 - binary_rs.length(); j++) {
                            sb_binary_rs.append('0');
                        }
                        sb_binary_rs.append(binary_rs);
                        binary_rs = sb_binary_rs.toString();
                    }
                    if (binary_rt.length() < 3) {
                        StringBuilder sb_binary_rt = new StringBuilder();
                        for (int j = 0; j < 3 - binary_rt.length(); j++) {
                            sb_binary_rt.append('0');
                        }
                        sb_binary_rt.append(binary_rt);
                        binary_rt = sb_binary_rt.toString();
                    }
                    if (binary_rd.length() < 16) {
                        StringBuilder sb_binary_rd = new StringBuilder();
                        for (int j = 0; j < 16 - binary_rd.length(); j++) {
                            sb_binary_rd.append('0');
                        }
                        sb_binary_rd.append(binary_rd);
                        binary_rd = sb_binary_rd.toString();
                    }
                    if (binary_rd.length() > 16) {
                        binary_rd = binary_rd.substring(binary_rd.length() - 16);
                    }
                    // System.out.println("100" + binary_rs + binary_rt +
                    // binary_rd);
                    // นำแต่ละส่วนมาร่วมเป็นคำสั่ง
                    // แปลงคำสั่ง binary เป็น decimal
                    // นำคำสั่งที่สร้างขึ้นไปรอที่ output arrey
                    String binaryCode = "100" + binary_rs + binary_rt + binary_rd;
                    int decimalCode = Integer.parseInt(binaryCode, 2);
                    System.out.println("I beq " + decimalCode + " (" + binaryCode + ")");
                    output.add(decimalCode);
                } else if (input[i].charAt(0) == 'j') {
                    // if ทำเมื่อเจอ prefix เป็นคำสั่ง jalr
                    // System.out.println("j jalr");
                    String[] words = input[i].split(" ");
                    for (String word : words) {
                        // System.out.println(word);
                        // แยก string ด้วย " "จะแบ่งได้เป็น prefix rs rt
                    }
                    int rs = Integer.parseInt(words[1]);
                    int rt = Integer.parseInt(words[2]);
                    // System.out.println(rs + " " + rt + );
                    // แปลง rs rt เป็น binary
                    String binary_rs = Integer.toBinaryString(rs);
                    String binary_rt = Integer.toBinaryString(rt);
                    // System.out.println(binary_rs + " " + binary_rt);
                    // ปรับความยาวของ binary
                    if (binary_rs.length() < 3) {
                        StringBuilder sb_binary_rs = new StringBuilder();
                        for (int j = 0; j < 3 - binary_rs.length(); j++) {
                            sb_binary_rs.append('0');
                        }
                        sb_binary_rs.append(binary_rs);
                        binary_rs = sb_binary_rs.toString();
                    }
                    if (binary_rt.length() < 3) {
                        StringBuilder sb_binary_rt = new StringBuilder();
                        for (int j = 0; j < 3 - binary_rt.length(); j++) {
                            sb_binary_rt.append('0');
                        }
                        sb_binary_rt.append(binary_rt);
                        binary_rt = sb_binary_rt.toString();
                    }
                    // System.out.println("101" + binary_rs + binary_rt + "0000000000000000";
                    // นำแต่ละส่วนมาร่วมเป็นคำสั่ง
                    // แปลงคำสั่ง binary เป็น decimal
                    // นำคำสั่งที่สร้างขึ้นไปรอที่ output arrey
                    String binaryCode = "101" + binary_rs + binary_rt + "0000000000000000";
                    int decimalCode = Integer.parseInt(binaryCode, 2);
                    System.out.println("J jalr " + decimalCode + " (" + binaryCode + ")");
                    output.add(decimalCode);
                } else if (input[i].charAt(0) == 'h') {
                    // ถ้าเป็น halt ใส่เป็นแลขคำสั่ง decimal ได้เลย
                    System.out.println("O halt 25165824  (1100000000000000000000000)");
                    output.add(25165824);
                } else if (input[i].charAt(0) == 'n' && input[i].charAt(1) == 'o') {
                    // ถ้าเป็น noop ใส่เป็นแลขคำสั่ง decimal ได้เลย
                    System.out.println("O noop 29360128  (1110000000000000000000000)");
                    output.add(29360128);
                } else {
                    // ถ้าเป็นตัวเลขให้ใช้ได้เลย
                    System.out.println(input[i]);
                    int intinput = Integer.parseInt(input[i]);
                    output.add(intinput);
                }
            }
            exit(0);
        } catch (SyntaxError | IOException | DuplicateLabel | UndefinedLabel e) {
            // output.clear();
            System.err.println(e);
            exit(1);
        }

        System.out.println(output);

        try {
            // เขียน output ลงในไฟล์ที่ dest
            BufferedWriter writer = new BufferedWriter(new FileWriter(dest));

            for (Integer item : output) {
                writer.write(Integer.toString(item));
                writer.newLine();
            }

            writer.close();
            System.out.println(dest);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
