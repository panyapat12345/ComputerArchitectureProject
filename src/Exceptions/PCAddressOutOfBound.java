package Exceptions;

public class PCAddressOutOfBound extends RuntimeException{
    public PCAddressOutOfBound(){
        super();
    }

    public PCAddressOutOfBound(String s){
        super(s);
    }
}
