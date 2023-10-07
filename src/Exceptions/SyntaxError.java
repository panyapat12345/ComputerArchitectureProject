package Exceptions;

public class SyntaxError extends Exception{
    public SyntaxError(){
        super();
    }

    public SyntaxError(String s){
        super(s);
    }
}
