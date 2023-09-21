package Exceptions;

public class MemoryAddressOutOfBound extends RuntimeException{
    public MemoryAddressOutOfBound(){
        super();
    }

    public MemoryAddressOutOfBound(String s){
        super(s);
    }
}
