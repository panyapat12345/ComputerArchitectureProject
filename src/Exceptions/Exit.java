package Exceptions;

public class Exit extends RuntimeException{
    public Exit(){
        super();
    }

    public Exit(String s){
        super(s);
    }
}
