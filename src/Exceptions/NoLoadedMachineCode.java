package Exceptions;

public class NoLoadedMachineCode extends RuntimeException{
    public NoLoadedMachineCode(){
        super();
    }

    public NoLoadedMachineCode(String s){
        super(s);
    }
}
