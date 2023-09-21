package Exceptions;

public class StackOverflow extends RuntimeException{
    public StackOverflow(){
        super();
    }

    public  StackOverflow(String s){
        super(s);
    }
}
