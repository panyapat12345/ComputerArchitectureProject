package Exceptions;

public class DuplicateLabel extends RuntimeException {
    public DuplicateLabel(){
        super();
    }

    public DuplicateLabel(String s){
        super(s);
    }
}
