package Exceptions;

public class UndefinedLabel extends RuntimeException {
    public UndefinedLabel(){
        super();
    }

    public UndefinedLabel(String s){
        super(s);
    }
}
