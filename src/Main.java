import Exceptions.Exit;

public class Main {
    public static void main(String[] argv) {
        try {
//            Assembler.getMachineCodes("src/testCases/test_1.txt", "src/testCases/Assemblyoutput.txt");
//            Assembler.getMachineCodes("src/testCases/Fibonucci.txt", "src/testCases/Assemblyoutput.txt");
//            Assembler.getMachineCodes("src/testCases/multiplytest.txt", "src/testCases/Assemblyoutput.txt");
              Assembler.getMachineCodes("src/testCases/Combination.txt", "src/testCases/Assemblyoutput.txt");
//            Assembler.getMachineCodes("src/testCases/seriestest.txt", "src/testCases/Assemblyoutput.txt");

            System.out.println();
            System.out.println();
            System.out.println();

            Simulator.simulate("src/testCases/Assemblyoutput.txt");
        } catch(Exit e) {
            return;
        }
    }
}
