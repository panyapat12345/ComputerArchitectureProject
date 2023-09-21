package Exceptions;

public class Halted extends RuntimeException{
    public Halted(){
        super();
    }

    public Halted(String s){
        super(s);
    }
}
