package Exceptions;

public class IllegalLabelFormat extends RuntimeException {
    public IllegalLabelFormat(){
        super();
    }

    public IllegalLabelFormat(String s){
        super(s);
    }
}
