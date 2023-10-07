import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class test {
    public static void main(String[] args) {

        String[] input = { "lw 0 1 7", "lw 1 2 3", "add 1 2 1", "beq 0 1 2", "beq 0 0 -3", "noop", "halt", "5", "-1",
                "2", "jalr 2 5" };
        ArrayList<Integer> output = new ArrayList<>();

        for (int i = 0; i < input.length; i++) {

            if (input[i].charAt(0) == 'a') {
                // System.out.println("R add");
                String[] words = input[i].split(" ");
                for (String word : words) {
                    // System.out.println(word);
                }
                int rs = Integer.parseInt(words[1]);
                int rt = Integer.parseInt(words[2]);
                int rd = Integer.parseInt(words[3]);
                // System.out.println(rs + " " + rt + " " + rd);
                String binary_rs = Integer.toBinaryString(rs);
                String binary_rt = Integer.toBinaryString(rt);
                String binary_rd = Integer.toBinaryString(rd);
                // System.out.println(binary_rs + " " + binary_rt + " " + binary_rd);
               
                if(binary_rs.length() > 3){
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

                if(binary_rt.length() > 3){
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

                if(binary_rd.length() > 3){
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
                String binaryCode = "000" + binary_rs + binary_rt + "0000000000000" + binary_rd;
                int decimalCode = Integer.parseInt(binaryCode, 2);
                System.out.println("R add " + decimalCode);
                output.add(decimalCode);
            } else if (input[i].charAt(0) == 'n' && input[i].charAt(1) == 'a') {
                // System.out.println("R nand");
                String[] words = input[i].split(" ");
                for (String word : words) {
                    // System.out.println(word);
                }
                int rs = Integer.parseInt(words[1]);
                int rt = Integer.parseInt(words[2]);
                int rd = Integer.parseInt(words[3]);
                // System.out.println(rs + " " + rt + " " + rd);
                String binary_rs = Integer.toBinaryString(rs);
                String binary_rt = Integer.toBinaryString(rt);
                String binary_rd = Integer.toBinaryString(rd);
                // System.out.println(binary_rs + " " + binary_rt + " " + binary_rd);
                
                if(binary_rs.length() > 3){
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

                if(binary_rt.length() > 3){
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

                if(binary_rd.length() > 3){
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
                int decimalCode = Integer.parseInt(binaryCode, 2);
                System.out.println("R nand " + decimalCode);
                output.add(decimalCode);
            } else if (input[i].charAt(0) == 'l') {
                // System.out.println("I lw");
                String[] words = input[i].split(" ");
                for (String word : words) {
                    // System.out.println(word);
                }
                int rs = Integer.parseInt(words[1]);
                int rt = Integer.parseInt(words[2]);
                int rd = Integer.parseInt(words[3]);
                // System.out.println(rs + " " + rt + " " + rd);
                String binary_rs = Integer.toBinaryString(rs);
                String binary_rt = Integer.toBinaryString(rt);
                String binary_rd = Integer.toBinaryString(rd);
                // System.out.println(binary_rs + " " + binary_rt + " " + binary_rd);
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
                String binaryCode = "010" + binary_rs + binary_rt + binary_rd;
                int decimalCode = Integer.parseInt(binaryCode, 2);
                System.out.println("I lw " + decimalCode);
                output.add(decimalCode);
            } else if (input[i].charAt(0) == 's') {
                // System.out.println("I sw");
                String[] words = input[i].split(" ");
                for (String word : words) {
                    // System.out.println(word);
                }
                int rs = Integer.parseInt(words[1]);
                int rt = Integer.parseInt(words[2]);
                int rd = Integer.parseInt(words[3]);
                // System.out.println(rs + " " + rt + " " + rd);
                String binary_rs = Integer.toBinaryString(rs);
                String binary_rt = Integer.toBinaryString(rt);
                String binary_rd = Integer.toBinaryString(rd);
                // System.out.println(binary_rs + " " + binary_rt + " " + binary_rd);
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
                String binaryCode = "011" + binary_rs + binary_rt + binary_rd;
                int decimalCode = Integer.parseInt(binaryCode, 2);
                System.out.println("I sw " + decimalCode);
                output.add(decimalCode);
            } else if (input[i].charAt(0) == 'b') {
                // System.out.println("I beq");
                // System.out.println("I lw");
                String[] words = input[i].split(" ");
                for (String word : words) {
                    // System.out.println(word);
                }
                int rs = Integer.parseInt(words[1]);
                int rt = Integer.parseInt(words[2]);
                int rd = Integer.parseInt(words[3]);
                // System.out.println(rs + " " + rt + " " + rd);
                String binary_rs = Integer.toBinaryString(rs);
                String binary_rt = Integer.toBinaryString(rt);
                String binary_rd = Integer.toBinaryString(rd);
                // System.out.println(binary_rs + " " + binary_rt + " " + binary_rd);
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
                String binaryCode = "100" + binary_rs + binary_rt + binary_rd;
                int decimalCode = Integer.parseInt(binaryCode, 2);
                System.out.println("I beq " + decimalCode);
                output.add(decimalCode);
            } else if (input[i].charAt(0) == 'j') {
                // System.out.println("j jalr");
                String[] words = input[i].split(" ");
                for (String word : words) {
                    // System.out.println(word);
                }
                int rs = Integer.parseInt(words[1]);
                int rt = Integer.parseInt(words[2]);
                // System.out.println(rs + " " + rt + " " + rd);
                String binary_rs = Integer.toBinaryString(rs);
                String binary_rt = Integer.toBinaryString(rt);
                // System.out.println(binary_rs + " " + binary_rt + " " + binary_rd);
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
                // System.out.println("101" + binary_rs + binary_rt + "0000000000000000" +
                // binary_rd);
                String binaryCode = "101" + binary_rs + binary_rt + "0000000000000000";
                int decimalCode = Integer.parseInt(binaryCode, 2);
                System.out.println("J jalr " + decimalCode);
                output.add(decimalCode);
            } else if (input[i].charAt(0) == 'h') {
                System.out.println("O halt 25165824");
                output.add(25165824);
            } else if (input[i].charAt(0) == 'n' && input[i].charAt(1) == 'o') {
                System.out.println("O noop 29360128");
                output.add(29360128);
            } else {
                System.out.println(input[i]);
                int intinput = Integer.parseInt(input[i]);
                output.add(intinput);
            }
        }
        System.out.println(output);
        String fileName = "Assemblyoutput.txt";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

            for (Integer item : output) {
                writer.write(Integer.toString(item));
                writer.newLine();
            }

            writer.close();
            System.out.println(fileName);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
